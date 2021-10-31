package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import dungeon.Path;

public final class DjikstraGraph {

  public static int[] getShortestPath(int numNodes, List<Path> paths){
    ArrayList<ArrayList<Integer>> adjacencyList = new ArrayList<ArrayList<Integer>>(numNodes);
    for (int i = 0; i < numNodes; i++) {
      adjacencyList.add(new ArrayList<Integer>());
    }

    for(Path path: paths){
      addEdge(adjacencyList, path.getSource(), path.getDestination());
    }

    Random random = new Random();
    int shortestDistance = 0;
    int randomStart = 0;
    int randomEnd = 0;
    int[] startEnd = new int[2];

    while(shortestDistance < 5){
      randomStart = random.ints(0, numNodes).findAny().getAsInt();
      randomEnd = random.ints(0, numNodes).findAny().getAsInt();

      shortestDistance = getShortestDistance(adjacencyList, randomStart, randomEnd, numNodes);
    }

    startEnd[0] = randomStart;
    startEnd[1] = randomEnd;

    return startEnd;
  }

  private static void addEdge(ArrayList<ArrayList<Integer>> adjacencyList, int src, int dest){
    adjacencyList.get(src).add(dest);
    adjacencyList.get(dest).add(src);
  }

  private static int getShortestDistance(ArrayList<ArrayList<Integer>> adjacencyList, int src,
                                         int dest, int numVertices){

    int[] previous = new int[numVertices];
    int[] distance = new int[numVertices];

    if (!(BreadthFirst(adjacencyList, src, dest, numVertices, previous, distance))) {
      System.out.println("Given source and destination" +
              "are not connected");
      return 0;
    }

    LinkedList<Integer> path = new LinkedList<Integer>();
    int crawl = dest;
    path.add(crawl);
    while (previous[crawl] != -1) {
      path.add(previous[crawl]);
      crawl = previous[crawl];
    }

    System.out.println("Shortest path length is: " + distance[dest]);

    System.out.println("Path is ::");
    for (int i = path.size() - 1; i >= 0; i--) {
      System.out.print(path.get(i) + " ");
    }

    return distance[dest];
  }

  private static boolean BreadthFirst(ArrayList<ArrayList<Integer>> adjacencyList, int src, int dest, int numVertices,
                                      int previous[], int distance[]){

    LinkedList<Integer> queue = new LinkedList<Integer>();

    boolean[] visited = new boolean[numVertices];

    for (int i = 0; i < numVertices; i++) {
      visited[i] = false;
      distance[i] = Integer.MAX_VALUE;
      previous[i] = -1;
    }

    visited[src] = true;
    distance[src] = 0;
    queue.add(src);

    while (!queue.isEmpty()) {
      int u = queue.remove();
      for (int i = 0; i < adjacencyList.get(u).size(); i++) {
        if (!visited[adjacencyList.get(u).get(i)]) {
          visited[adjacencyList.get(u).get(i)] = true;
          distance[adjacencyList.get(u).get(i)] = distance[u] + 1;
          previous[adjacencyList.get(u).get(i)] = u;
          queue.add(adjacencyList.get(u).get(i));

          if (adjacencyList.get(u).get(i) == dest)
            return true;
        }
      }
    }
    return false;
  }
}
