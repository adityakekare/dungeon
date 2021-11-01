package dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import static java.util.stream.Collectors.groupingBy;

import graph.BfsGraph;
import graph.KruskalGraph;
import player.Player;
import player.PlayerImpl;

public class Dungeon implements GameMap{
  private final int height;
  private final int width;
  private final int degreeOfInterconnectivity;
  private final boolean isWrapping;
  private final int percentTreasure;
  private final Location[][] grid;
  private final List<Path> paths;
  private final int start;
  private final int end;
  private final Player player;

  public Dungeon(int height, int width, int degreeOfInterconnectivity, boolean isWrapping,
                 int percentTreasure, String playerName) {
    this.height = height;
    this.width = width;
    this.degreeOfInterconnectivity = degreeOfInterconnectivity;
    this.isWrapping = isWrapping;
    this.percentTreasure = percentTreasure;
    this.paths = generatePaths();
    this.grid = generateCaves(paths);
    int[] startEnd = BfsGraph.getShortestPath(this.grid,
            this.height,  this.width, this.paths);
    this.start = startEnd[0];
    this.end = startEnd[1];
    this.player = new PlayerImpl(playerName, this.start);
    this.addTreasures();
  }

  private Location[][] generateCaves(List<Path> paths){
    Location[][] tempGrid = new Location[height][width];
    int numCaves = height * width;
    Map<Integer, boolean[]> cavePaths = new HashMap<>();

//    for(int i = 0; i < numCaves; i++){
//      cavePaths.put(i, new boolean[4]);
//    }

//    Map<Integer, List<Path>> connectorsList = paths.stream().collect(groupingBy(Path:: getSource));

//    for(Map.Entry<Integer, List<Path>> entry: connectorsList.entrySet()){
//        for(Path connector: entry.getValue()){
    for(Path path: paths){
      int src = path.getSource();
      int dest = path.getDestination();
      boolean[] srcPath = cavePaths.getOrDefault(src, new boolean[4]);
      boolean[] destPath = cavePaths.getOrDefault(dest, new boolean[4]);

      if(Math.abs(src - dest) == 1){
        srcPath[3] = true;
        destPath[2] = true;
        cavePaths.put(src, srcPath);
        cavePaths.put(dest, destPath);
      }
      else if(Math.abs(src - dest) == width){
        srcPath[1] = true;
        destPath[0] = true;
        cavePaths.put(src, srcPath);
        cavePaths.put(dest, destPath);
      }
      else if(Math.abs(src - dest) == width - 1){
        srcPath[2] = true;
        destPath[3] = true;
        cavePaths.put(src, srcPath);
        cavePaths.put(dest, destPath);
      }
      else if(Math.abs(src - dest) == (width * (height - 1))) {
        srcPath[0] = true;
        destPath[1] = true;
        cavePaths.put(src, srcPath);
        cavePaths.put(dest, destPath);
      }
  }

    int id = 0;

    for(int i = 0; i < height; i++){
      for(int j = 0; j < width; j++){
        Connector connector = new Connector(cavePaths.get(id));
        if(connector.getNumRoutes() == 2){
          tempGrid[i][j] = new Tunnel(id, connector);
        }
        else{
          tempGrid[i][j] = new Cave(id, connector);
        }
        id += 1;
      }
    }
//    for(int key: cavePaths.keySet()){
//      boolean[] value = cavePaths.get(key);
//      System.out.println(key + ": " + "[" + value[0] + " " + value[1] + " " + value[2] + " " + value[3] + "]");
//    }

    return tempGrid;
  }

  private List<Path> generatePaths(){
    List<Path> allPaths = new ArrayList<>();
    Set<Path> uniquePaths = new HashSet<>();
    int vertices = height * width;

    for(int i = 0; i < vertices; i++){
      int north = i - width;
      int south = i + width;
      int west = i - 1;
      int east = i + 1;
      if(east < width && east % width != 0){
        uniquePaths.add(new Path(i, east));
      }
      if(west >= 0 && (west + 1) % width != 0){
        uniquePaths.add(new Path(i, west));
      }
      if(south < height){
        uniquePaths.add(new Path(i, south));
      }
      if(north >= 0){
        uniquePaths.add(new Path(i, north));
      }
    }

    if(isWrapping){
        int offset = 0;
        for(int i = 0; i < width; i++){
          uniquePaths.add(new Path(i, (width * (height - 1)) + offset));
          offset += 1;
        }
        offset = 0;
        for(int i = 0; i < height; i++){
          uniquePaths.add(new Path(i + offset, i + offset + width - 1));
          offset += width - 1;
        }
    }

    allPaths.addAll(uniquePaths);

//    for(Path path: allPaths){
//      System.out.println(path.getSource() + " : " + path.getDestination());
//    }
//    Collections.sort(allPaths);
    List<Path> finalPaths = KruskalGraph.generateMst(allPaths, vertices, degreeOfInterconnectivity);

//    System.out.println();

    Collections.sort(finalPaths);

//    for(Path path: finalPaths){
//      System.out.println(path.getSource() + " : " + path.getDestination());
//    }

    return finalPaths;
  }

  private void addTreasures(){
    Random random = new Random();
    Map<Integer, Treasure> treasureMap = new HashMap<>();

    treasureMap.put(0, Treasure.DIAMOND);
    treasureMap.put(1, Treasure.RUBY);
    treasureMap.put(2, Treasure.SAPPHIRE);
    int numNodes = this.height * this.width;
    int treasureCaveNum = (int) (numNodes * ((double) this.percentTreasure/ (double) 100));

    int i = 0;
//    System.out.println("wheew " + i);
    while(i < treasureCaveNum){
//      System.out.println("wheew " + i);
      int randomId = random.ints(0, numNodes).findAny().getAsInt();
      Position nodePos = getPosition(randomId);
      if(this.grid[nodePos.getX()][nodePos.getY()].isTunnel()){
        continue;
      }
//      System.out.println(randomId + " " + treasureMap.get(i % 3));
      Cave chosenCave = ((Cave) this.grid[nodePos.getX()][nodePos.getY()]);
      List<Treasure> treasures = chosenCave.getTreasures();
      treasures.add(treasureMap.get(i % 3));
      chosenCave.setTreasures(treasures);
      i += 1;
    }
  }

  private Position getPosition(int id){
    int row = (id - (id % width))/ width;
    int col = id % width;

    return new Position(row, col);
  }


  @Override
  public String toString(){
    Position playerLocation = getPosition(this.player.getLocation());
    Position start = getPosition(this.start);
    Position end = getPosition(this.end);
    String result = "\n";
    String nodeString = "";

      for(int i = 0; i < height; i++){
        for(int k = 0; k < 3; k++){
          for(int j = 0; j < width; j++){
            if(k == 0){
              if(grid[i][j].getRoutes().isNorth()){
                result += " | ";
              }
              else{
                result += "   ";
              }
            }
            else if(k == 1){
              if(i == playerLocation.getX() && j == playerLocation.getY()){
                nodeString = "P";
              }
              else if(i == start.getX() && j == start.getY()){
                nodeString = "S";
              }
              else if(i == end.getX() && j == end.getY()){
                nodeString = "E";
              }
              else if(grid[i][j].isTunnel()){
                nodeString = "T";
              }
              else{
                nodeString = "C";
              }

              if(grid[i][j].getRoutes().isWest() && grid[i][j].getRoutes().isEast()){
                result += String.format("-" + nodeString + "-");
              }
              else if(!grid[i][j].getRoutes().isWest() && grid[i][j].getRoutes().isEast()){
                result += String.format(" " + nodeString + "-");
              }
              else if(grid[i][j].getRoutes().isWest() && !grid[i][j].getRoutes().isEast()){
                result += String.format("-" + nodeString + " ");
              }
              else{
                result += String.format(" " + nodeString + " ");
              }
            }
            else{
              if(grid[i][j].getRoutes().isSouth()){
                result += " | ";
              }
              else{
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
    return this.grid[playerLocation.getX()][playerLocation.getY()].getRoutes();
  }

  private void collectTreasureForPlayer(){
    Position position = getPosition(this.player.getLocation());
    Location currLocation = this.grid[position.getX()][position.getY()];

    if(!currLocation.isTunnel()){
      for(Treasure treasure: ((Cave) currLocation).getTreasures()){
        this.player.pickTreasure(treasure);
      }
    }
  }

  @Override
  public void playerMoveNorth() {
      int north = getNorth(this.player.getLocation());
      this.player.move(north);
      collectTreasureForPlayer();
  }

  private int getNorth(int id){
    if(id < width){
      return (width * (height - 1)) + id % width;
    }
    else{
      return id - width;
    }
  }

  @Override
  public void playerMoveSouth() {
    int south = getSouth(this.player.getLocation());
    this.player.move(south);
    collectTreasureForPlayer();
  }

  private int getSouth(int id){
    if(id >= (width * (height - 1))){
      return id % width;
    }
    else{
      return id + width;
    }
  }

  @Override
  public void playerMoveEast() {
    int east = getEast(this.player.getLocation());
    this.player.move(east);
    collectTreasureForPlayer();
  }

  private int getEast(int id){
    if(id % width == (width - 1)){
      return (id - (width - 1));
    }
    else{
      return id + 1;
    }
  }

  @Override
  public void playerMoveWest() {
    int west = getWest(this.player.getLocation());
    this.player.move(west);
    collectTreasureForPlayer();
  }

  @Override
  public String getPlayerStatus() {
    return this.player.toString();
  }

  @Override
  public boolean checkIfPlayerAtEndLocation() {
    return this.player.getLocation() == this.end;
  }

  private int getWest(int id){
    if(id % width == 0){
      return (id + (width - 1));
    }
    else{
      return id - 1;
    }
  }
}
