package model.dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

import model.graphutils.BfsGraph;
import model.graphutils.KruskalGraph;
import model.inventory.InventoryType;
import model.inventory.Monster;
import model.inventory.Treasure;
import model.inventory.Weapon;

/**
 * This class represents a dungeon with all its attributes. The dungeon map, the paths between the
 * locations in the dungeon and the player are all part of the dungeon. The class can create both,
 * random and deterministic dungeons.
 */
public class Dungeon implements GameMap {
  private final int height;
  private final int width;
  private final int degreeOfInterconnectivity;
  private final boolean isWrapping;
  private final int percentTreasure;
  private final Location[][] grid;
  private final int start;
  private final int end;
  private final Player player;
  private final long randomSeed;
  private final boolean[][] visited;

  /**
   * Constructor to initialize a randomized dungeon.
   *
   * @param height                    height of the dungeon.
   * @param width                     width of the dungeon.
   * @param degreeOfInterconnectivity degree of interconnectivity.
   * @param isWrapping                whether the dungeon is wrapping or not.
   * @param percentTreasure           %age of caves which contain treasure.
   * @param playerName                Name of the Player.
   */
  public Dungeon(int height, int width, int degreeOfInterconnectivity, boolean isWrapping,
                 int percentTreasure, String playerName) {
    randomSeed = 0;
    this.height = height;
    this.width = width;
    this.degreeOfInterconnectivity = degreeOfInterconnectivity;
    this.isWrapping = isWrapping;
    this.percentTreasure = percentTreasure;
    List<Path> paths = generatePaths();
    this.grid = generateCaves(paths);
    int[] startEnd = BfsGraph.getShortestPath(this.grid,
            this.height, this.width, paths, randomSeed);
    this.start = startEnd[0];
    this.end = startEnd[1];
    this.player = new PlayerImpl(playerName, this.start);
    this.visited = new boolean[height][width];
    this.setVisited();
  }

  /**
   * Constructor to initialize a deterministic dungeon.
   *
   * @param height                    height of the dungeon.
   * @param width                     width of the dungeon.
   * @param degreeOfInterconnectivity degree of interconnectivity.
   * @param isWrapping                whether the dungeon is wrapping or not.
   * @param percentTreasure           %age of caves which contain treasure.
   * @param playerName                Name of the Player.
   * @param seed                      seed for randomizer.
   */
  public Dungeon(int height, int width, int degreeOfInterconnectivity, boolean isWrapping,
                 int percentTreasure, String playerName, long seed) {
    randomSeed = seed;
    this.height = height;
    this.width = width;
    this.degreeOfInterconnectivity = degreeOfInterconnectivity;
    this.isWrapping = isWrapping;
    this.percentTreasure = percentTreasure;
    List<Path> paths = generatePaths();
    this.grid = generateCaves(paths);
    int[] startEnd = BfsGraph.getShortestPath(this.grid,
            this.height, this.width, paths, randomSeed);
    this.start = startEnd[0];
    this.end = startEnd[1];
    this.player = new PlayerImpl(playerName, this.start);
    this.visited = new boolean[height][width];
    this.setVisited();
  }

  private Location[][] generateCaves(List<Path> paths) {
    Location[][] tempGrid = new Location[height][width];
    Map<Integer, boolean[]> cavePaths = new HashMap<>();

    for (Path path : paths) {
      int src = path.getSource();
      int dest = path.getDestination();
      boolean[] srcPath = cavePaths.getOrDefault(src, new boolean[4]);
      boolean[] destPath = cavePaths.getOrDefault(dest, new boolean[4]);

      if (Math.abs(src - dest) == 1) {
        srcPath[3] = true;
        destPath[2] = true;
        cavePaths.put(src, srcPath);
        cavePaths.put(dest, destPath);
      } else if (Math.abs(src - dest) == width) {
        srcPath[1] = true;
        destPath[0] = true;
        cavePaths.put(src, srcPath);
        cavePaths.put(dest, destPath);
      } else if (Math.abs(src - dest) == width - 1) {
        srcPath[2] = true;
        destPath[3] = true;
        cavePaths.put(src, srcPath);
        cavePaths.put(dest, destPath);
      } else if (Math.abs(src - dest) == (width * (height - 1))) {
        srcPath[0] = true;
        destPath[1] = true;
        cavePaths.put(src, srcPath);
        cavePaths.put(dest, destPath);
      }
    }

    int id = 0;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Connector connector = new Connector(cavePaths.get(id));
        if (connector.getNumRoutes() == 2) {
          tempGrid[i][j] = new Tunnel(id, connector);
        } else {
          tempGrid[i][j] = new Cave(id, connector);
        }
        id += 1;
      }
    }

    return tempGrid;
  }

  private List<Path> generatePaths() {
    Set<Path> uniquePaths = new HashSet<>();
    int vertices = height * width;

    for (int i = 0; i < vertices; i++) {
      int north = i - width;
      int south = i + width;
      int west = i - 1;
      int east = i + 1;
      if (north >= 0) {
        uniquePaths.add(new Path(i, north));
      }
      if (south < height) {
        uniquePaths.add(new Path(i, south));
      }
      if (west >= 0 && (west + 1) % width != 0) {
        uniquePaths.add(new Path(i, west));
      }
      if (east < width && east % width != 0) {
        uniquePaths.add(new Path(i, east));
      }
    }

    if (isWrapping) {
      int offset = 0;
      for (int i = 0; i < width; i++) {
        uniquePaths.add(new Path(i, (width * (height - 1)) + offset));
        offset += 1;
      }
      offset = 0;
      for (int i = 0; i < height; i++) {
        uniquePaths.add(new Path(i + offset, i + offset + width - 1));
        offset += width - 1;
      }
    }

    List<Path> allPaths = new ArrayList<>(uniquePaths);
    if (randomSeed != 0) {
      Collections.shuffle(allPaths, new Random(randomSeed));
    } else {
      Collections.shuffle(allPaths);
    }

    List<Path> finalPaths = KruskalGraph.generateMst(allPaths, vertices, degreeOfInterconnectivity);

    Collections.sort(finalPaths);


    return finalPaths;
  }

  @Override
  public void generateMonsters(int totalNum) {
    int countCaves = 0;
    List<Integer> caves = new ArrayList<>();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (!this.grid[i][j].isTunnel()) {
          countCaves += 1;
          caves.add(this.grid[i][j].getId());
        }
      }
    }

    if (countCaves < totalNum) {
      throw new IllegalArgumentException("Monsters in the dungeon cannot be more than "
              + countCaves + "\n");
    }

    Location endLocation = this.grid[getPosition(this.end).getXpos()][getPosition(this.end)
            .getYpos()];
    endLocation.add(InventoryType.MONSTER);

    Random random;
    if (randomSeed != 0) {
      random = new Random(randomSeed);
    } else {
      random = new Random();
    }

    int i = 0;
    while (i < totalNum - 1) {
      int randomId = random.ints(0, caves.size()).findAny().getAsInt();
      Position position = getPosition(caves.get(randomId));
      Location currentLocation = this.grid[position.getXpos()][position.getYpos()];
      if (!currentLocation.contains(InventoryType.MONSTER)
              && (currentLocation.getId() != this.start)) {
        try {
          currentLocation.add(InventoryType.MONSTER);
        } catch (IllegalArgumentException iae) {
          System.out.println(iae.getMessage());
        } catch (IllegalStateException ise) {
          continue;
        }
        i += 1;
      }
    }
  }

  @Override
  public void collectWeaponForPlayer() {
    Position position = getPosition(this.player.getLocation());
    Location currLocation = this.grid[position.getXpos()][position.getYpos()];

    try {
      List<Object> weaponList = currLocation.get(InventoryType.WEAPON);
      for (Object weapon : weaponList) {
        this.player.pickWeapon((Weapon) weapon);
      }
      currLocation.remove(InventoryType.WEAPON);
    } catch (NoSuchElementException | ClassCastException nse) {
      System.out.println(nse.getMessage());
    }

    this.checkIfBattle();
  }

  private void battleWithMonster(Location playerLocation) {
    Monster monster = (Monster) playerLocation.get(InventoryType.MONSTER).get(0);
    this.player.setAlive(!monster.hitPlayer());
  }

  @Override
  public boolean checkPlayerState() {
    return this.player.isAlive();
  }

  private String shootNorth(int id, int distance) {
    Location location = this.grid[getPosition(id).getXpos()][getPosition(id).getYpos()];

    if (distance <= 0) {
      if (location.contains(InventoryType.MONSTER)) {
        Monster monster = (Monster) location.get(InventoryType.MONSTER).get(0);
        monster.receiveDamage();
        if (monster.getHealth() <= 0) {
          location.remove(InventoryType.MONSTER);
        }
        return "You hear a great howl in the distance";
      } else {
        return "You shoot an arrow into the darkness";
      }
    }

    Connector routes = location.getRoutes();
    Position nextPosition;
    if (location.isTunnel()) {
      if (routes.isNorth()) {
        nextPosition = getPosition(getNorth(id));
        if (this.grid[nextPosition.getXpos()][nextPosition.getYpos()].isTunnel()) {
          return shootNorth(getNorth(id), distance);
        }
        return shootNorth(getNorth(id), distance - 1);
      } else if (routes.isWest()) {
        nextPosition = getPosition(getWest(id));
        if (this.grid[nextPosition.getXpos()][nextPosition.getYpos()].isTunnel()) {
          return shootWest(getWest(id), distance);
        }
        return shootWest(getWest(id), distance - 1);
      } else {
        nextPosition = getPosition(getEast(id));
        if (this.grid[nextPosition.getXpos()][nextPosition.getYpos()].isTunnel()) {
          return shootEast(getEast(id), distance);
        }
        return shootEast(getEast(id), distance - 1);
      }
    } else if (!routes.isNorth()) {
      return "You shoot an arrow into the darkness";
    } else {
      nextPosition = getPosition(getNorth(id));
      if (this.grid[nextPosition.getXpos()][nextPosition.getYpos()].isTunnel()) {
        return shootNorth(getNorth(id), distance);
      }
      return shootNorth(getNorth(id), distance - 1);
    }
  }

  private String shootSouth(int id, int distance) {
    Location location = this.grid[getPosition(id).getXpos()][getPosition(id).getYpos()];
    if (distance <= 0) {
      if (location.contains(InventoryType.MONSTER)) {
        Monster monster = (Monster) location.get(InventoryType.MONSTER).get(0);
        monster.receiveDamage();
        if (monster.getHealth() <= 0) {
          location.remove(InventoryType.MONSTER);
        }
        return "You hear a great howl in the distance";
      } else {
        return "You shoot an arrow into the darkness";
      }
    }

    Connector routes = location.getRoutes();
    Position nextPosition;
    if (location.isTunnel()) {
      if (routes.isSouth()) {
        nextPosition = getPosition(getSouth(id));
        if (this.grid[nextPosition.getXpos()][nextPosition.getYpos()].isTunnel()) {
          return shootSouth(getSouth(id), distance);
        }
        return shootSouth(getSouth(id), distance - 1);
      } else if (routes.isWest()) {
        nextPosition = getPosition(getWest(id));
        if (this.grid[nextPosition.getXpos()][nextPosition.getYpos()].isTunnel()) {
          return shootWest(getWest(id), distance);
        }
        return shootWest(getWest(id), distance - 1);
      } else {
        nextPosition = getPosition(getEast(id));
        if (this.grid[nextPosition.getXpos()][nextPosition.getYpos()].isTunnel()) {
          return shootEast(getEast(id), distance);
        }
        return shootEast(getEast(id), distance - 1);
      }
    } else {
      if (!routes.isSouth()) {
        return "You shoot an arrow into the darkness";
      } else {
        nextPosition = getPosition(getSouth(id));
        if (this.grid[nextPosition.getXpos()][nextPosition.getYpos()].isTunnel()) {
          return shootSouth(getSouth(id), distance);
        }
        return shootSouth(getSouth(id), distance - 1);
      }
    }
  }

  private String shootWest(int id, int distance) {
    Location location = this.grid[getPosition(id).getXpos()][getPosition(id).getYpos()];
    if (distance <= 0) {
      if (location.contains(InventoryType.MONSTER)) {
        Monster monster = (Monster) location.get(InventoryType.MONSTER).get(0);
        monster.receiveDamage();
        if (monster.getHealth() <= 0) {
          location.remove(InventoryType.MONSTER);
        }
        return "You hear a great howl in the distance";
      } else {
        return "You shoot an arrow into the darkness";
      }
    }

    Connector routes = location.getRoutes();
    Position nextPosition;
    if (location.isTunnel()) {
      if (routes.isWest()) {
        nextPosition = getPosition(getWest(id));
        if (this.grid[nextPosition.getXpos()][nextPosition.getYpos()].isTunnel()) {
          return shootWest(getWest(id), distance);
        }
        return shootWest(getWest(id), distance - 1);
      } else if (routes.isSouth()) {
        nextPosition = getPosition(getSouth(id));
        if (this.grid[nextPosition.getXpos()][nextPosition.getYpos()].isTunnel()) {
          return shootSouth(getSouth(id), distance);
        }
        return shootSouth(getSouth(id), distance - 1);
      } else {
        nextPosition = getPosition(getNorth(id));
        if (this.grid[nextPosition.getXpos()][nextPosition.getYpos()].isTunnel()) {
          return shootNorth(getNorth(id), distance);
        }
        return shootNorth(getNorth(id), distance - 1);
      }
    } else {
      if (!routes.isWest()) {
        return "You shoot an arrow into the darkness";
      } else {
        nextPosition = getPosition(getWest(id));
        if (this.grid[nextPosition.getXpos()][nextPosition.getYpos()].isTunnel()) {
          return shootWest(getWest(id), distance);
        }
        return shootWest(getWest(id), distance - 1);
      }
    }
  }

  private String shootEast(int id, int distance) {
    Location location = this.grid[getPosition(id).getXpos()][getPosition(id).getYpos()];

    if (distance <= 0) {
      if (location.contains(InventoryType.MONSTER)) {
        Monster monster = (Monster) location.get(InventoryType.MONSTER).get(0);
        monster.receiveDamage();
        if (monster.getHealth() <= 0) {
          location.remove(InventoryType.MONSTER);
        }
        return "You hear a great howl in the distance";
      } else {
        return "You shoot an arrow into the darkness";
      }
    }

    Connector routes = location.getRoutes();
    Position nextPosition;
    if (location.isTunnel()) {
      if (routes.isEast()) {
        nextPosition = getPosition(getEast(id));
        if (this.grid[nextPosition.getXpos()][nextPosition.getYpos()].isTunnel()) {
          return shootEast(getEast(id), distance);
        }
        return shootEast(getEast(id), distance - 1);
      } else if (routes.isSouth()) {
        nextPosition = getPosition(getSouth(id));
        if (this.grid[nextPosition.getXpos()][nextPosition.getYpos()].isTunnel()) {
          return shootSouth(getSouth(id), distance);
        }
        return shootSouth(getSouth(id), distance - 1);
      } else {
        nextPosition = getPosition(getNorth(id));
        if (this.grid[nextPosition.getXpos()][nextPosition.getYpos()].isTunnel()) {
          return shootNorth(getEast(id), distance);
        }
        return shootNorth(getNorth(id), distance - 1);
      }
    } else {
      if (!routes.isEast()) {
        return "You shoot an arrow into the darkness";
      } else {
        nextPosition = getPosition(getEast(id));
        if (this.grid[nextPosition.getXpos()][nextPosition.getYpos()].isTunnel()) {
          return shootEast(getEast(id), distance);
        }
        return shootEast(getEast(id), distance - 1);
      }
    }
  }

  @Override
  public String playerShootArrowNorth(int distance) {
    try {
      this.player.shoot();
    } catch (NoSuchElementException nse) {
      throw new IllegalArgumentException(nse.getMessage());
    }
    return this.shootNorth(this.player.getLocation(), distance);
  }

  @Override
  public String playerShootArrowSouth(int distance) {
    try {
      this.player.shoot();
    } catch (NoSuchElementException nse) {
      throw new IllegalArgumentException(nse.getMessage());
    }
    return this.shootSouth(this.player.getLocation(), distance);
  }

  @Override
  public String playerShootArrowWest(int distance) {
    try {
      this.player.shoot();
    } catch (NoSuchElementException nse) {
      throw new IllegalArgumentException(nse.getMessage());
    }
    return this.shootWest(this.player.getLocation(), distance);
  }

  @Override
  public String playerShootArrowEast(int distance) {
    try {
      this.player.shoot();
    } catch (NoSuchElementException nse) {
      throw new IllegalArgumentException(nse.getMessage());
    }
    return this.shootEast(this.player.getLocation(), distance);
  }

  @Override
  public void addWeapons() {
    Random random;
    if (randomSeed != 0) {
      random = new Random(randomSeed);
    } else {
      random = new Random();
    }
    Set<Location> locations = new HashSet<>();

    int numNodes = this.height * this.width;
    int weaponLocationNum = (int) (numNodes * ((double) this.percentTreasure / (double) 100));

    int i = 0;
    while (i < weaponLocationNum) {
      int randomId = random.ints(0, numNodes).findAny().getAsInt();
      Position nodePos = getPosition(randomId);
      Location currentLocation = this.grid[nodePos.getXpos()][nodePos.getYpos()];

      currentLocation.add(InventoryType.WEAPON);
      if (!locations.contains(currentLocation)) {
        i += 1;
        locations.add(currentLocation);
      }
    }
  }

  @Override
  public void addTreasures() {
    Random random;
    if (randomSeed != 0) {
      random = new Random(randomSeed);
    } else {
      random = new Random();
    }

    Set<Location> locationSet = new HashSet<>();

    int numNodes = this.height * this.width;
    int treasureCaveNum = (int) (numNodes * ((double) this.percentTreasure / (double) 100));

    int i = 0;
    while (i < treasureCaveNum) {
      int randomId = random.ints(0, numNodes).findAny().getAsInt();
      Position nodePos = getPosition(randomId);
      Location currentLocation = this.grid[nodePos.getXpos()][nodePos.getYpos()];
      if (currentLocation.isTunnel()) {
        continue;
      }

      currentLocation.add(InventoryType.TREASURE);
      if (!locationSet.contains(currentLocation)) {
        i += 1;
        locationSet.add(currentLocation);
      }
    }
  }

  @Override
  public Position getPosition(int id) {
    int row = (id - (id % width)) / width;
    int col = id % width;

    return new Position(row, col);
  }


  @Override
  public String toString() {
    Position playerLocation = getPosition(this.player.getLocation());
    Position start = getPosition(this.start);
    Position end = getPosition(this.end);
    String result = "\n";
    String nodeString = "";

    for (int i = 0; i < height; i++) {
      for (int k = 0; k < 3; k++) {
        for (int j = 0; j < width; j++) {
          if (k == 0) {
            if (grid[i][j].getRoutes().isNorth()) {
              result += " | ";
            } else {
              result += "   ";
            }
          } else if (k == 1) {
            if (i == playerLocation.getXpos() && j == playerLocation.getYpos()) {
              nodeString = "P";
            } else if (i == start.getXpos() && j == start.getYpos()) {
              nodeString = "S";
            } else if (i == end.getXpos() && j == end.getYpos()) {
              nodeString = "E";
            } else if (grid[i][j].isTunnel()) {
              nodeString = "T";
            } else {
              nodeString = "C";
            }

            if (grid[i][j].getRoutes().isWest() && grid[i][j].getRoutes().isEast()) {
              result += String.format("-" + nodeString + "-");
            } else if (!grid[i][j].getRoutes().isWest() && grid[i][j].getRoutes().isEast()) {
              result += String.format(" " + nodeString + "-");
            } else if (grid[i][j].getRoutes().isWest() && !grid[i][j].getRoutes().isEast()) {
              result += String.format("-" + nodeString + " ");
            } else {
              result += String.format(" " + nodeString + " ");
            }
          } else {
            if (grid[i][j].getRoutes().isSouth()) {
              result += " | ";
            } else {
              result += "   ";
            }
          }
        }
        result += "\n";
      }
    }
    return result;
  }

  @Override
  public Connector getPossibleMoves() {
    Position playerLocation = getPosition(this.player.getLocation());
    return this.grid[playerLocation.getXpos()][playerLocation.getYpos()].getRoutes();
  }

  @Override
  public void collectTreasureForPlayer() {
    Position position = getPosition(this.player.getLocation());
    Location currLocation = this.grid[position.getXpos()][position.getYpos()];

    try {
      List<Object> treasureList = currLocation.get(InventoryType.TREASURE);
      for (Object treasure : treasureList) {
        this.player.pickTreasure(((Treasure) treasure));
      }
      currLocation.remove(InventoryType.TREASURE);
    } catch (NoSuchElementException | ClassCastException nse) {
      System.out.println(nse.getMessage());
    }

    this.checkIfBattle();
  }

  private int checkIfMonsterPresent(int id, int distance, int playerLocation,
                                    Set<Integer> visited) {
    if (distance < 0) {
      return 0;
    }
    if (visited.contains(id)) {
      return 0;
    }
    visited.add(id);

    Location location = this.grid[getPosition(id)
            .getXpos()][getPosition(id).getYpos()];
    int north = 0;
    int south = 0;
    int west = 0;
    int east = 0;

    if (location.getRoutes().isNorth()) {
      int northId = getNorth(id);
      north += northId == playerLocation ? 0 : checkIfMonsterPresent(northId,
              distance - 1, playerLocation, visited);
    }
    if (location.getRoutes().isWest()) {
      int westId = getWest(id);
      west += westId == playerLocation ? 0 : checkIfMonsterPresent(westId,
              distance - 1, playerLocation, visited);
    }
    if (location.getRoutes().isEast()) {
      int eastId = getEast(id);
      east += eastId == playerLocation ? 0 : checkIfMonsterPresent(eastId,
              distance - 1, playerLocation, visited);
    }
    if (location.getRoutes().isSouth()) {
      int southId = getSouth(id);
      south += southId == playerLocation ? 0 : checkIfMonsterPresent(southId,
              distance - 1, playerLocation, visited);
    }

    return (location.contains(InventoryType.MONSTER) ? distance + 1 : 0)
            + (north + south + west + east);
  }

  @Override
  public String getSmellAtPlayerLocation() {
    Set<Integer> visited = new HashSet<>();
    int smell = 0;
    int distance = 2;
    int playerLocationId = this.player.getLocation();
    Location playerLocation = this.grid[getPosition(playerLocationId)
            .getXpos()][getPosition(playerLocationId).getYpos()];
    Connector playerRoutes = playerLocation.getRoutes();

    if (playerRoutes.isNorth()) {
      smell += this.checkIfMonsterPresent(getNorth(playerLocationId),
              distance - 1, playerLocationId, visited);
    }
    if (playerRoutes.isEast()) {
      smell += this.checkIfMonsterPresent(getEast(playerLocationId),
              distance - 1, playerLocationId, visited);
    }
    if (playerRoutes.isWest()) {
      smell += this.checkIfMonsterPresent(getWest(playerLocationId),
              distance - 1, playerLocationId, visited);
    }
    if (playerRoutes.isSouth()) {
      smell += this.checkIfMonsterPresent(getSouth(playerLocationId),
              distance - 1, playerLocationId, visited);
    }

    if (smell >= 2) {
      return "You smell something unbearably filthy nearby...";
    } else if (smell == 1) {
      return "You smell something filthy nearby...";
    } else {
      return "";
    }


  }

  @Override
  public void playerMoveNorth() {
    int north = getNorth(this.player.getLocation());
    this.player.move(north);
    this.setVisited();
    this.checkIfBattle();
  }

  private int getNorth(int id) {
    if (id < width) {
      return (width * (height - 1)) + id % width;
    } else {
      return id - width;
    }
  }

  @Override
  public void playerMoveSouth() {
    int south = getSouth(this.player.getLocation());
    this.player.move(south);
    this.setVisited();
    this.checkIfBattle();
  }

  private int getSouth(int id) {
    if (id >= (width * (height - 1))) {
      return id % width;
    } else {
      return id + width;
    }
  }

  @Override
  public void playerMoveEast() {
    int east = getEast(this.player.getLocation());
    this.player.move(east);
    this.setVisited();
    this.checkIfBattle();
  }

  private int getEast(int id) {
    if (id % width == (width - 1)) {
      return (id - (width - 1));
    } else {
      return id + 1;
    }
  }

  @Override
  public void playerMoveWest() {
    int west = getWest(this.player.getLocation());
    this.player.move(west);
    this.setVisited();
    this.checkIfBattle();
  }

  private int getWest(int id) {
    if (id % width == 0) {
      return (id + (width - 1));
    } else {
      return id - 1;
    }
  }

  private void checkIfBattle() {
    Position playerPosition = getPosition(this.player.getLocation());
    Location playerLocation = this.grid[playerPosition.getXpos()][playerPosition.getYpos()];

    if (playerLocation.contains(InventoryType.MONSTER)) {
      this.battleWithMonster(playerLocation);
    }
  }

  @Override
  public String getPlayerStatus() {
    return this.player.toString();
  }

  @Override
  public String getLocationStatus() {
    return this.grid[getPosition(
            this.player.getLocation()).getXpos()][getPosition(
            this.player.getLocation()).getYpos()].toString();
  }

  @Override
  public boolean checkIfPlayerAtEndLocation() {
    return this.player.getLocation() == this.end;
  }

  @Override
  public Location[][] getGrid() {
    Location[][] loc = new Location[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        loc[i][j] = this.grid[i][j];
      }
    }
    return loc;
  }

  @Override
  public Player getPlayer() {
    return player;
  }

  @Override
  public String getPlayerName() {
    return player.getName();
  }

  @Override
  public int getPlayerLocation() {
    return player.getLocation();
  }

  @Override
  public Position getPlayerGridLocation() {
    return getPosition(player.getLocation());
  }

  @Override
  public Map<String, Integer> getPlayerTreasureCount() {
    return player.getTreasureCount();
  }

  @Override
  public int getPlayerWeaponCount() {
    return player.getArrowCount();
  }

  @Override
  public boolean[][] getVisited() {
    boolean[][] newArr = new boolean[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        newArr[i][j] = visited[i][j];
      }
    }
    return newArr;
  }

  @Override
  public String getMove(int id) {
    int playerLocation = player.getLocation();
    Position playerPosition = getPosition(player.getLocation());
    Connector routes = this.grid[playerPosition.getXpos()][playerPosition.getYpos()].getRoutes();
    if (getNorth(playerLocation) == id && routes.isNorth()) {
      return "n";
    } else if (getSouth(playerLocation) == id && routes.isSouth()) {
      return "s";
    } else if (getWest(playerLocation) == id && routes.isWest()) {
      return "w";
    } else if (getEast(playerLocation) == id && routes.isEast()) {
      return "e";
    }

    return "";
  }

  private void setVisited() {
    Position playerLocation = getPosition(this.player.getLocation());
    visited[playerLocation.getXpos()][playerLocation.getYpos()] = true;
  }
}
