package model.game;

import java.util.Map;
import model.dungeon.Dungeon;
import model.dungeon.GameMap;
import model.dungeon.Location;
import model.dungeon.Position;

/**
 * Class for implementing methods in the Game interface. provides methods to interact with the
 * dungeon and mutate its state according to the input.
 */
public class GameImpl implements Game {

  private final GameMap dungeon;

  /**
   * Constructor which takes in all the attributes required to create the Dungeon object.
   *
   * @param height                    height of the dungeon.
   * @param width                     width of the dungeon.
   * @param degreeOfInterconnectivity degree of interconnectivity.
   * @param isWrapping                whether the dungeon is wrapping or not.
   * @param percentTreasure           %age of caves which contain treasure.
   * @param playerName                Name of the Player.
   */
  public GameImpl(int height, int width, int degreeOfInterconnectivity, boolean isWrapping,
                  int percentTreasure, String playerName) {
    if (height + width < 7 || height <= 0 || width <= 0) {
      throw new IllegalArgumentException("Please give a bigger size for the model.dungeon");
    } else if (degreeOfInterconnectivity < 0) {
      throw new IllegalArgumentException("Degree of connectivity less than 0 not possible");
    } else if (percentTreasure < 0 || percentTreasure > 100) {
      throw new IllegalArgumentException("Percentage of treasure should be between 0 and 100");
    }

    this.dungeon = new Dungeon(height, width, degreeOfInterconnectivity,
            isWrapping, percentTreasure, playerName);
  }

  /**
   * Constructor which takes in all the attributes required to create a deterministic Dungeon.
   *
   * @param height                    height of the dungeon.
   * @param width                     width of the dungeon.
   * @param degreeOfInterconnectivity degree of interconnectivity.
   * @param isWrapping                whether the dungeon is wrapping or not.
   * @param percentTreasure           %age of caves which contain treasure.
   * @param playerName                Name of the Player.
   * @param seed                      seed for randomizer.
   */
  public GameImpl(int height, int width, int degreeOfInterconnectivity, boolean isWrapping,
                  int percentTreasure, String playerName, long seed) {
    if (height + width < 7 || height <= 0 || width <= 0) {
      throw new IllegalArgumentException("Please give a bigger size for the model.dungeon");
    } else if (degreeOfInterconnectivity < 0) {
      throw new IllegalArgumentException("Degree of connectivity less than 0 not possible");
    } else if (percentTreasure < 0 || percentTreasure > 100) {
      throw new IllegalArgumentException("Percentage of treasure should be between 0 and 100");
    }

    this.dungeon = new Dungeon(height, width, degreeOfInterconnectivity,
            isWrapping, percentTreasure, playerName, seed);
  }

  @Override
  public void move(String direction) {
    switch (direction.toLowerCase()) {
      case "n":
        if (dungeon.getPossibleMoves().isNorth()) {
          dungeon.playerMoveNorth();
        } else {
          throw new IllegalArgumentException("No such path exists");
        }
        break;
      case "s":
        if (dungeon.getPossibleMoves().isSouth()) {
          dungeon.playerMoveSouth();
        } else {
          throw new IllegalArgumentException("No such path exists");
        }
        break;
      case "w":
        if (dungeon.getPossibleMoves().isWest()) {
          dungeon.playerMoveWest();
        } else {
          throw new IllegalArgumentException("No such path exists");
        }
        break;
      case "e":
        if (dungeon.getPossibleMoves().isEast()) {
          dungeon.playerMoveEast();
        } else {
          throw new IllegalArgumentException("No such path exists");
        }
        break;
      default:
        throw new IllegalArgumentException("Invalid Input");
    }
  }

  @Override
  public String move(int row, int col) {
    int clickId = dungeon.getGrid()[row][col].getId();
    String direction = dungeon.getMove(clickId);

    if (direction.isEmpty()) {
      return "Can't go there";
    } else {
      this.move(direction);
    }
    return "";
  }

  @Override
  public String dumpDungeon() {
    return dungeon.toString();
  }

  @Override
  public String getMovesForPlayer() {
    return dungeon.getPossibleMoves().toString();
  }

  @Override
  public String getStatusLocation() {
    return dungeon.getLocationStatus();
  }

  @Override
  public String getStatusPlayer() {
    return dungeon.getPlayerStatus();
  }

  @Override
  public boolean isEnd() {
    return dungeon.checkIfPlayerAtEndLocation() && dungeon.checkPlayerState();
  }

  @Override
  public boolean isEndState() {
    return dungeon.checkIfPlayerAtEndLocation() || !dungeon.checkPlayerState();
  }

  @Override
  public void addTreasuresToDungeon() {
    this.dungeon.addTreasures();
  }

  @Override
  public void pickUpTreasure() {
    this.dungeon.collectTreasureForPlayer();
  }

  @Override
  public GameMap getDungeon() {
    return dungeon;
  }

  @Override
  public void addMonstersToDungeon(int num) {
    this.dungeon.generateMonsters(num);
  }

  @Override
  public void addWeaponsToDungeon() {
    this.dungeon.addWeapons();
  }

  @Override
  public void pickupWeapon() {
    this.dungeon.collectWeaponForPlayer();
  }

  @Override
  public boolean isPlayerAlive() {
    return this.dungeon.checkPlayerState();
  }

  @Override
  public String shootArrow(String direction, String distance) {
    int dist = 0;
    try {
      dist = Integer.parseInt(distance);
      if (dist <= 0 || dist > 5) {
        throw new IllegalArgumentException("Please enter an integer between 1-5");
      }
    } catch (NumberFormatException nfe) {
      System.out.println("Please enter an integer between 1-5");
    }
    switch (direction.toLowerCase()) {
      case "n":
        if (dungeon.getPossibleMoves().isNorth()) {
          return dungeon.playerShootArrowNorth(dist);
        } else {
          throw new IllegalArgumentException("Cannot shoot in that direction");
        }
      case "s":
        if (dungeon.getPossibleMoves().isSouth()) {
          return dungeon.playerShootArrowSouth(dist);
        } else {
          throw new IllegalArgumentException("Cannot shoot in that direction");
        }
      case "w":
        if (dungeon.getPossibleMoves().isWest()) {
          return dungeon.playerShootArrowWest(dist);
        } else {
          throw new IllegalArgumentException("Cannot shoot in that direction");
        }
      case "e":
        if (dungeon.getPossibleMoves().isEast()) {
          return dungeon.playerShootArrowEast(dist);
        } else {
          throw new IllegalArgumentException("Cannot shoot in that direction");
        }
      default:
        throw new IllegalArgumentException("Invalid Input");
    }
  }

  @Override
  public String getMonsterSmell() {
    return this.dungeon.getSmellAtPlayerLocation();
  }

  @Override
  public Location[][] getGrid() {
    return dungeon.getGrid();
  }

  @Override
  public Position getPlayerPosition() {
    return dungeon.getPlayerGridLocation();
  }

  @Override
  public int getPlayerLocationId() {
    return dungeon.getPlayerLocation();
  }

  @Override
  public Map<String, Integer> getPlayerTreasureCount() {
    return dungeon.getPlayerTreasureCount();
  }

  @Override
  public int getPlayerWeaponCount() {
    return dungeon.getPlayerWeaponCount();
  }

  @Override
  public boolean[][] getVisited() {
    return dungeon.getVisited();
  }

  @Override
  public String getName() {
    return dungeon.getPlayerName();
  }
}
