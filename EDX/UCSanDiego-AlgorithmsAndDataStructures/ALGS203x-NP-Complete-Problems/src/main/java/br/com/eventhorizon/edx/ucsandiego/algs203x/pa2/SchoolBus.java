package br.com.eventhorizon.edx.ucsandiego.algs203x.pa2;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

public class SchoolBus implements PA {

  private static int numberOfVertices;

  private static Graph graph;

  @Override
  public void trivialSolution() {
    graph = readGraph();
    output(naiveSearch(0, new Path()));
  }

  private static Graph readGraph() {
    FastScanner scanner = new FastScanner(System.in);
    numberOfVertices = scanner.nextInt();
    int numberOfEdges = scanner.nextInt();
    Graph graph = new Graph(numberOfVertices);
    for (int edge = 0; edge < numberOfEdges; edge++) {
      graph.addEdge(scanner.nextInt() - 1, scanner.nextInt() - 1, scanner.nextInt());
    }
    return graph;
  }

  private static Path naiveSearch(final int vertex, final Path path) {
    if (graph.degree(vertex) <= 1 && graph.numberOfVertices > 2) {
      return new Path();
    }
    path.vertices.add(vertex);
    if (path.vertices.size() == graph.numberOfVertices) {
      Optional<Edge> edge = graph.adjacencies(vertex).stream().filter(e -> e.other(vertex) == 0).findFirst();
      if (edge.isPresent()) {
        path.length += edge.get().weight;
        return path;
      } else {
        return new Path();
      }
    }
    int minPathLength = Integer.MAX_VALUE;
    Path minPath = new Path();
    for (Edge adjEdge : graph.adjacencies(vertex)) {
      int adjVertex = adjEdge.other(vertex);
      if (!path.vertices.contains(adjVertex)) {
        Path newPath = new Path(path);
        newPath.length += adjEdge.weight;
        Path result = naiveSearch(adjVertex, newPath);
        if (result.length > 0 && result.length < minPathLength) {
          minPath = result;
        }
      }
    }
    return minPath;
  }

  @Override
  public void finalSolution() {
    int[][] weights = readWeights();
    output(finalSearch(weights));
  }

  private static int[][] readWeights() {
    FastScanner scanner = new FastScanner(System.in);
    numberOfVertices = scanner.nextInt();
    int numberOfEdges = scanner.nextInt();
    int[][] weights = new int[numberOfVertices][numberOfVertices];
    for (int i = 0; i < numberOfVertices; i++) {
      Arrays.fill(weights[i], Integer.MAX_VALUE);
    }
    for (int edge = 0; edge < numberOfEdges; edge++) {
      int vertex1 = scanner.nextInt() - 1;
      int vertex2 = scanner.nextInt() - 1;
      int weight = scanner.nextInt();
      weights[vertex1][vertex2] = weight;
      weights[vertex2][vertex1] = weight;
    }
    return weights;
  }

  private static Path finalSearch(int[][] weights) {
    int numberOfSets = (int) Math.pow(2, numberOfVertices);
    int[][] predecessor = new int[numberOfSets][numberOfVertices];
    Path[][] lengths = new Path[numberOfSets][numberOfVertices];
    for (int i = 0; i < lengths.length; i++) {
      for (int j = 0; j < lengths[0].length; j++) {
        lengths[i][j] = new Path(Integer.MAX_VALUE);
      }
    }
    // Calculate minimum distance to all sets of size 1
    for (int k = 1; k < numberOfSets; k++) {
      List<Integer> set = set(k);
      if (set.size() != 1) {
        continue;
      }
      int vertex = set.get(0);
      lengths[k][vertex].length = weights[vertex][0];
      for (int adjVertex = 0; adjVertex < numberOfVertices; adjVertex++) {
        if (weights[vertex][adjVertex] < Integer.MAX_VALUE) {
          predecessor[k][adjVertex] = vertex;
        }
      }
    }
    // Calculate minimum distance to all sets of size greater than or equals 2
    for (int setSize = 2; setSize <= numberOfVertices; setSize++) {
      for (int k = 1; k < numberOfSets; k++) {
        List<Integer> set = set(k);
        if (set.size() != setSize) {
          continue;
        }
        if (set.contains(0)) {
          continue;
        }
        for (int i : set) {
          for (int j : set) {
            if (j == i) {
              continue;
            }
            int setMinusI = k ^ (1 << i);
            if (lengths[setMinusI][j].length + weights[j][i] < lengths[k][i].length) {
              lengths[k][i].length = lengths[setMinusI][j].length + weights[j][i];
              predecessor[k][i] = j;
            }
          }
        }
      }
    }
    // Find the minimum path
    Path minPath = new Path(Integer.MAX_VALUE);
    int a = -1;
    for (int vertex = 1; vertex < numberOfVertices; vertex++) {
      int d = weights[vertex][0];
      if (lengths[numberOfSets - 2][vertex].length + d < minPath.length) {
        minPath.length = lengths[numberOfSets - 2][vertex].length + d;
        a = vertex;
      }
    }
    // Reconstruct the path
    int k = numberOfSets - 2;
    int next = a;
    while (next > 0) {
      int curr = next;
      minPath.vertices.add(next);
      next = predecessor[k][next];
      k = k ^ (1 << curr);
    }
    minPath.vertices.add(0);
    Collections.reverse(minPath.vertices);
    return minPath;
  }

  private static List<Integer> set(int k) {
    List<Integer> set = new ArrayList<>(numberOfVertices);
    for (int i = 0; i < 32; i++) {
      if ((k & 0x1) == 1) {
        set.add(i);
      }
      k = k >> 1;
    }
    return set;
  }

  private static void output(Path path) {
    if (path.length == 0 || path.length == Integer.MAX_VALUE || path.vertices.size() != numberOfVertices) {
      System.out.println(-1);
    } else {
      System.out.println(path.length);
      path.vertices.forEach(vertex -> System.out.print(vertex + 1 + " "));
    }
  }

  private static class Path {

    final List<Integer> vertices;

    long length;

    public Path() {
      vertices = new ArrayList<>();
      length = 0;
    }

    public Path(int length) {
      vertices = new ArrayList<>();
      this.length = length;
    }

    public Path(Path path) {
      vertices = new ArrayList<>(path.vertices);
      length = path.length;
    }

    @Override
    public String toString() {
      return "Path{length=" + length + ", vertices=" + vertices + '}';
    }
  }

  private static class Edge implements Comparable<Edge> {

    final int hashCode;

    final int vertex1;

    final int vertex2;

    final int weight;

    public Edge(int vertex1, int vertex2, int weight) {
      String str = vertex1 < vertex2 ? vertex1 + ":" + vertex2 : vertex2 + ":" + vertex1;
      str += ":" + weight;
      hashCode = str.hashCode();
      this.vertex1 = vertex1;
      this.vertex2 = vertex2;
      this.weight = weight;
    }

    public int vertex1() {
      return vertex1;
    }

    public int vertex2() {
      return vertex2;
    }

    public int either() {
      return vertex1;
    }

    public int other(int vertex) {
      if (vertex == vertex1) {
        return vertex2;
      } else if (vertex == vertex2) {
        return vertex1;
      } else {
        throw new IllegalArgumentException("Invalid vertex for this edge");
      }
    }

    public double weight() {
      return weight;
    }

    @Override
    public String toString() {
      return "WeightedDirectEdge {(" + weight + ") " + vertex1 + " - " + vertex2 + "}";
    }

    @Override
    public int hashCode() {
      return hashCode;
    }

    @Override
    public boolean equals(Object object) {
      if (object == null) {
        return false;
      }
      if (!(object instanceof Edge)) {
        return false;
      }
      Edge other = (Edge) object;
      return (this.vertex1 == other.vertex1 && this.vertex2 == other.vertex2
          && this.weight == other.weight) || (this.vertex1 == other.vertex2
          && this.vertex2 == other.vertex1 && this.weight == other.weight);
    }

    @Override
    public int compareTo(Edge other) {
      return Double.compare(this.weight, other.weight);
    }
  }

  private static class Graph {

    final int numberOfVertices;

    final List<Edge>[] adjacencies;

    public Graph(int numberOfVertices) {
      this.numberOfVertices = numberOfVertices;
      this.adjacencies = new LinkedList[numberOfVertices];
      for (int i = 0; i < numberOfVertices; i++) {
        this.adjacencies[i] = new LinkedList<>();
      }
    }

    public void addEdge(int vertex1, int vertex2, int weight) {
      if (vertex1 < 0 || vertex1 >= numberOfVertices ||
          vertex2 < 0 || vertex2 >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      Edge edge = new Edge(vertex1, vertex2, weight);
      adjacencies[vertex1].add(edge);
      adjacencies[vertex2].add(edge);
    }

    public List<Edge> adjacencies(int vertex) {
      if (vertex < 0 || vertex >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      return adjacencies[vertex];
    }

    public int degree(int vertex) {
      return adjacencies[vertex].size();
    }
  }
}
