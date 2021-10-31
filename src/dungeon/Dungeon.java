package dungeon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static java.util.stream.Collectors.groupingBy;

import graph.DjikstraGraph;
import graph.KruskalGraph;

public class Dungeon {
  private final int height;
  private final int width;
  private final int degreeOfInterconnectivity;
  private final boolean isWrapping;
  private final int percentTreasure;
  private final Cave[][] grid;
  private final List<Path> paths;
  private final int start;
  private final int end;

  public Dungeon(int height, int width, int degreeOfInterconnectivity, boolean isWrapping, int percentTreasure) {
    this.height = height;
    this.width = width;
    this.degreeOfInterconnectivity = degreeOfInterconnectivity;
    this.isWrapping = isWrapping;
    this.percentTreasure = percentTreasure;
    this.paths = generatePaths();
    this.grid = generateCaves(paths);
    int[] startEnd = DjikstraGraph.getShortestPath(height * width, this.paths);
    this.start = startEnd[0];
    this.end = startEnd[1];
  }

  private Cave[][] generateCaves(List<Path> paths){
    Cave[][] tempGrid = new Cave[height][width];
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
        tempGrid[i][j] = new Cave(id, new Connector(cavePaths.get(id)));
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

  private void setBounds(){

  }

  @Override
  public String toString(){
    String result = "";
    for(int i = 0; i < height; i++){
      for(int j = 0; j < width; j++){
        result = String.format(result + grid[i][j] + "  ");
      }
      result = result + "\n";
    }
    return result;
  }
}
