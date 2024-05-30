package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

public class LongestPathInDAG implements PA {

  private static int source;

  private static int sink;

  private static Graph graph;

  private static int longestPathLength;

  private static List<Integer> longestPath;

  private static Map<Integer, Integer> distanceCache;

  private static Map<Integer, List<Integer>> pathCache;

  @Override
  public void trivialSolution() {
    readInput();
    if (source == sink) {
      longestPathLength = 0;
      longestPath = new ArrayList<>();
      longestPath.add(source);
    } else {
      naiveFindLongestPath();
    }
    writeOutput();
  }

  private static void naiveFindLongestPath() {
    List<Integer> longestPathList = new ArrayList<>();
    longestPathLength = naiveFindLongestPath(source, longestPathList);
    longestPath.addAll(longestPathList);
    if (!longestPath.contains(sink)) {
      longestPath = null;
    }
  }

  private static int naiveFindLongestPath(int vertex, List<Integer> path) {
    if (vertex == sink) {
      path.add(sink);
      return 0;
    }
    path.add(vertex);
    int maxDistance = Integer.MIN_VALUE;
    List<Integer> longestPath = null;
    for (Edge edge : graph.adjacencies.getOrDefault(vertex, Collections.emptyList())) {
      List<Integer> newPath = new ArrayList<>();
      int distance = edge.weight + naiveFindLongestPath(edge.to, newPath);
      if (distance > maxDistance) {
        maxDistance = distance;
        longestPath = newPath;
      }
    }
    if (longestPath != null) {
      path.addAll(longestPath);
    }
    return maxDistance;
  }

  public void intermediateSolution1() {
    readInput();
    if (source == sink) {
      longestPathLength = 0;
      longestPath = new ArrayList<>();
      longestPath.add(source);
    } else {
      intermediateSolution1Impl();
    }
    writeOutput();
  }

  private static void intermediateSolution1Impl() {
    distanceCache = new HashMap<>();
    pathCache = new HashMap<>();
    List<Integer> order = depthFirstReversePostorder(graph, source);
    if (!order.contains(sink)) {
      longestPath = null;
      return;
    }
    order.remove(order.size() - 1);
    distanceCache.put(sink, 0);
    List<Integer> l = new ArrayList<>();
    l.add(sink);
    pathCache.put(sink, l);
    while (!order.isEmpty()) {
      int vertex = order.remove(order.size() - 1);
      int longestDistanceToSink = Integer.MIN_VALUE;
      List<Integer> longestPathToSink = new ArrayList<>();
      longestPathToSink.add(vertex);
      List<Integer> longestPathToSinkTemp = null;
      for (Edge edge : graph.adjacencies.getOrDefault(vertex, Collections.emptyList())) {
        int distance = distanceCache.get(edge.to);
        if (edge.weight + distance > longestDistanceToSink) {
          longestDistanceToSink = edge.weight + distance;
          longestPathToSinkTemp = pathCache.get(edge.to);
        }
      }
      distanceCache.put(vertex, longestDistanceToSink);
      longestPathToSink.addAll(longestPathToSinkTemp);
      pathCache.put(vertex, longestPathToSink);
    }
    longestPathLength = distanceCache.get(source);
    longestPath.addAll(pathCache.get(source));
  }

  @Override
  public void finalSolution() {
    readInput();
    if (source == sink) {
      longestPathLength = 0;
      longestPath = new ArrayList<>();
      longestPath.add(source);
    } else {
      findLongestPath();
    }
    writeOutput();
  }

  private static List<Integer> depthFirstReversePostorder(Graph graph, int source) {
    Map<Integer, Boolean> marked = new HashMap<>();
    List<Integer> order = new LinkedList<>();
    Stack<Integer> stack = new Stack<>();
    stack.push(source);
    marked.put(source, true);
    while (!stack.isEmpty()) {
      while (true) {
        Integer vertex = stack.peek();
        List<Edge> adjEdges = graph.adjacencies.getOrDefault(vertex, Collections.emptyList());
        boolean stop = true;
        for (Edge adjEdge : adjEdges) {
          int adjVertex = adjEdge.to;
          if (!marked.getOrDefault(adjVertex, false)) {
            stack.push(adjVertex);
            marked.put(adjVertex, true);
            stop = false;
            break;
          }
        }
        if (stop) {
          break;
        }
      }
      order.add(0, stack.pop());
    }
    return order;
  }

  private static void findLongestPath() {
    Map<Integer, Integer> distances = new HashMap<>();
    Map<Integer, Edge> edgeTo = new HashMap<>();
    distances.put(source, 0);
    List<Integer> order = depthFirstReversePostorder(graph, source);
    if (!order.contains(sink)) {
      longestPath = null;
      return;
    }
    order.forEach(vertex -> {
      int distanceToCurrentVertex = distances.get(vertex);
      List<Edge> adjEdges = graph.adjacencies.getOrDefault(vertex, Collections.emptyList());
      adjEdges.forEach(edge -> {
        int currentDistanceToAdjVertex = distances.getOrDefault(edge.to, Integer.MIN_VALUE);
        if (currentDistanceToAdjVertex < distanceToCurrentVertex + edge.weight) {
          distances.put(edge.to, distanceToCurrentVertex + edge.weight);
          edgeTo.put(edge.to, edge);
        }
      });
    });
    if (edgeTo.containsKey(sink)) {
      longestPath.add(0, sink);
      Edge edge = edgeTo.get(sink);
      while (edge != null) {
        longestPath.add(0, edge.from);
        longestPathLength += edge.weight;
        edge = edgeTo.get(edge.from);
      }
    }
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);
    source = scanner.nextInt();
    sink = scanner.nextInt();
    graph = new Graph();
    while (true) {
      String edgeString = scanner.next();
      if (edgeString == null) {
        break;
      }
      String[] strings = edgeString.split("->");
      int from = Integer.parseInt(strings[0]);
      strings = strings[1].split(":");
      int to = Integer.parseInt(strings[0]);
      int weight = Integer.parseInt(strings[1]);
      graph.addEdge(from, to, weight);
    }
    longestPathLength = 0;
    longestPath = new ArrayList<>();
  }

  private static void writeOutput() {
    if (longestPath != null) {
      System.out.println(longestPathLength);
      while (true) {
        int next = longestPath.remove(0);
        if (!longestPath.isEmpty()) {
          System.out.print(next + "->");
        } else {
          System.out.println(next);
          break;
        }
      }
    } else {
      System.out.println("infinity");
    }
  }

  private static class Edge {

    final int from;

    final int to;

    final int weight;

    Edge(int from, int to, int weight) {
      this.from = from;
      this.to = to;
      this.weight = weight;
    }

    @Override
    public String toString() {
      return "Edge {" + from + " -> " + to + ":" + weight + "}";
    }
  }

  private static class Graph {

    final Map<Integer, List<Edge>> adjacencies;

    Graph() {
      this.adjacencies = new HashMap<>();
    }

    void addEdge(int fromVertex, int toVertex, int weight) {
      Edge edge = new Edge(fromVertex, toVertex, weight);
      List<Edge> fromVertexAdjacencies = adjacencies.getOrDefault(fromVertex, new ArrayList<>());
      fromVertexAdjacencies.add(edge);
      adjacencies.put(fromVertex, fromVertexAdjacencies);
    }
  }
}
