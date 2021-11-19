package model.graphUtils;

import java.util.ArrayList;
import java.util.List;

import model.dungeon.Path;

/**
 * This is a static class which provides an implementation for the Kruskal algorithm to get the
 * minimum spanning tree from a given set of edges.
 * Citation: https://www.geeksforgeeks.org/kruskals-minimum-spanning-tree-algorithm-greedy-algo-2/
 */
public final class KruskalGraph {

  /**
   * This method generates a minimum spanning tree from a given set of paths, number of vertices and
   * a degree of connectivity.
   *
   * @param allPaths                  set of paths between nodes.
   * @param vertices                  number of nodes.
   * @param degreeOfInterconnectivity additional edges to be added to the MST.
   * @return List of edges calculated as a MST.
   */
  public static List<Path> generateMst(List<Path> allPaths, int vertices,
                                       int degreeOfInterconnectivity) {
    List<Path> finalPaths = new ArrayList<>();
    List<Path> leftoverPaths = new ArrayList<>();

    Subset[] subsets = new Subset[vertices];
    for (int i = 0; i < vertices; i++) {
      subsets[i] = new Subset();
      subsets[i].parent = i;
      subsets[i].rank = 0;
    }

    int edgeCount = 0;

    while (finalPaths.size() < vertices - 1) {
      Path path = allPaths.get(edgeCount);
      edgeCount += 1;

      int x = find(subsets, path.getSource());
      int y = find(subsets, path.getDestination());

      if (x != y) {
        finalPaths.add(path);
        union(subsets, x, y);
      } else {
        leftoverPaths.add(path);
      }
    }

    while (edgeCount < allPaths.size()) {
      leftoverPaths.add(allPaths.get(edgeCount));
      edgeCount += 1;
    }

    finalPaths.addAll(addDegreePaths(degreeOfInterconnectivity, leftoverPaths));

    return finalPaths;
  }

  private static int find(Subset[] subsets, int nodeId) {
    if (subsets[nodeId].parent != nodeId) {
      subsets[nodeId].parent = find(subsets, subsets[nodeId].parent);
    }

    return subsets[nodeId].parent;
  }

  private static void union(Subset[] subsets, int x, int y) {
    int xroot = find(subsets, x);
    int yroot = find(subsets, y);

    if (subsets[xroot].rank < subsets[yroot].rank) {
      subsets[xroot].parent = yroot;
    } else {
      subsets[yroot].parent = xroot;
      subsets[xroot].rank += 1;
    }
  }

  private static List<Path> addDegreePaths(int degree, List<Path> leftoverPaths) {
    List<Path> paths = new ArrayList<>();
    if (degree > leftoverPaths.size()) {
      throw new IllegalArgumentException("Given degree of interconnectivity "
              + "not possible for the given graph");
    }

    for (int i = 0; i < degree; i++) {
      paths.add(leftoverPaths.get(i));
    }

    return paths;
  }

  static class Subset {
    private int parent;
    private int rank;
  }
}
