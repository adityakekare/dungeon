package graph;

import java.util.ArrayList;
import java.util.List;

import dungeon.Path;

public final class KruskalGraph {

  static class Subset {
    private int parent;
    private int rank;
  }
  public static List<Path> generateMst(List<Path> allPaths, int vertices,
                                             int degreeOfInterconnectivity){
      List<Path> finalPaths = new ArrayList<>();
      List<Path> leftoverPaths = new ArrayList<>();

      Subset subsets[] = new Subset[vertices];
      for(int i = 0; i < vertices; i++){
        subsets[i] = new Subset();
        subsets[i].parent = i;
        subsets[i].rank = 0;
      }

      int edgeCount = 0;

      while(finalPaths.size() < vertices - 1){
        Path path = allPaths.get(edgeCount);
        edgeCount += 1;

        int x = find(subsets, path.getSource());
        int y = find(subsets, path.getDestination());

        if(x != y){
          finalPaths.add(path);
          Union(subsets, x, y);
        }
        else{
          leftoverPaths.add(path);
        }
      }

      while(edgeCount < allPaths.size()){
        leftoverPaths.add(allPaths.get(edgeCount));
        edgeCount += 1;
      }

      if(degreeOfInterconnectivity > leftoverPaths.size()){
        throw new IllegalArgumentException("Given degree of interconnectivity " +
                "not possible for the given graph");
      }

      for(int i = 0; i < degreeOfInterconnectivity; i++){
        finalPaths.add(leftoverPaths.get(i));
      }

      return finalPaths;
  }

  private static int find(Subset subsets[], int nodeId){
    if(subsets[nodeId].parent != nodeId){
      subsets[nodeId].parent = find(subsets, subsets[nodeId].parent);
    }

    return subsets[nodeId].parent;
  }

  private static void Union(Subset subsets[], int x, int y){
    int xRoot = find(subsets, x);
    int yRoot = find(subsets, y);

    if(subsets[xRoot].rank < subsets[yRoot].rank){
      subsets[xRoot].parent = yRoot;;
    }
    else{
      subsets[yRoot].parent = xRoot;
      subsets[xRoot].rank += 1;
    }
  }
}
