package br.com.eventhorizon.edx.ucsandiego.algs202x.pa5;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

public class Clustering implements PA {

  @Override
  public void naiveSolution() {
    finalSolution();
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    WeightedUndirectedGraph graph = readGraph(scanner);
    int numberOfClusters = scanner.nextInt();;
    System.out.printf("%.9f", clustering(graph, numberOfClusters));
  }

  private static double clustering(WeightedUndirectedGraph graph, int numberOfClusters) {
    RootedTreeDisjointSets.Node[] disjointSets = new RootedTreeDisjointSets.Node[graph.numberOfVertices];
    PriorityQueue<WeightedUndirectedEdge> queue = new PriorityQueue<>(graph.edges);
    for (int vertex = 0; vertex < graph.numberOfVertices; vertex++) {
      disjointSets[vertex] = RootedTreeDisjointSets.build(vertex);
    }
    int count = graph.numberOfVertices;
    while (!queue.isEmpty() && count > numberOfClusters) {
      WeightedUndirectedEdge edge = queue.poll();
      RootedTreeDisjointSets.Node set1 = RootedTreeDisjointSets.find(disjointSets[edge.vertex1]);
      RootedTreeDisjointSets.Node set2 = RootedTreeDisjointSets.find(disjointSets[edge.vertex2]);
      if (set1.equals(set2)) {
        continue;
      }
      RootedTreeDisjointSets.union(set1, set2);
      count--;
    }
    double distance = 0.0;
    while (!queue.isEmpty() && distance == 0.0) {
      WeightedUndirectedEdge edge = queue.poll();
      RootedTreeDisjointSets.Node set1 = RootedTreeDisjointSets.find(disjointSets[edge.vertex1]);
      RootedTreeDisjointSets.Node set2 = RootedTreeDisjointSets.find(disjointSets[edge.vertex2]);
      if (set1.equals(set2)) {
        continue;
      }
      distance = edge.weight;
    }
    return distance;
  }

  private static WeightedUndirectedGraph readGraph(FastScanner scanner) {
    int numberOfVertices = scanner.nextInt();
    WeightedUndirectedGraph graph = new WeightedUndirectedGraph(numberOfVertices);
    int[] x = new int[numberOfVertices];
    int[] y = new int[numberOfVertices];
    for (int i = 0; i < numberOfVertices; i++) {
      x[i] = scanner.nextInt();
      y[i] = scanner.nextInt();
    }
    for (int i = 0; i < numberOfVertices; i++) {
      for (int j = i + 1; j < numberOfVertices; j++) {
        double distance = Math.sqrt(
            ((x[i] - x[j]) * (x[i] - x[j])) +
            ((y[i] - y[j]) * (y[i] - y[j])));
        graph.addEdge(i, j, distance);
      }
    }
    return graph;
  }

  private static class WeightedUndirectedEdge implements Comparable<WeightedUndirectedEdge> {

    final int hashCode;

    final int vertex1;

    final int vertex2;

    final double weight;

    public WeightedUndirectedEdge(int vertex1, int vertex2, double weight) {
      String str = vertex1 < vertex2 ? vertex1 + ":" + vertex2 : vertex2 + ":" + vertex1;
      str += ":" + weight;
      hashCode = str.hashCode();
      this.vertex1 = vertex1;
      this.vertex2 = vertex2;
      this.weight = weight;
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
      if (!(object instanceof WeightedUndirectedEdge)) {
        return false;
      }
      WeightedUndirectedEdge other = (WeightedUndirectedEdge) object;
      return (this.vertex1 == other.vertex1 && this.vertex2 == other.vertex2
          && this.weight == other.weight) || (this.vertex1 == other.vertex2
          && this.vertex2 == other.vertex1 && this.weight == other.weight);
    }

    @Override
    public int compareTo(WeightedUndirectedEdge other) {
      return Double.compare(this.weight, other.weight);
    }
  }

  private static class WeightedUndirectedGraph {

    final int numberOfVertices;

    final LinkedList<WeightedUndirectedEdge>[] adjacencies;

    final LinkedList<WeightedUndirectedEdge> edges;

    WeightedUndirectedGraph(int numberOfVertices) {
      this.numberOfVertices = numberOfVertices;
      this.adjacencies = new LinkedList[numberOfVertices];
      for (int i = 0; i < numberOfVertices; i++) {
        this.adjacencies[i] = new LinkedList<>();
      }
      this.edges = new LinkedList<>();
    }

    void addEdge(int vertex1, int vertex2, double weight) {
      if (vertex1 < 0 || vertex1 >= numberOfVertices ||
          vertex2 < 0 || vertex2 >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      WeightedUndirectedEdge
          edge = new WeightedUndirectedEdge(vertex1, vertex2, weight);
      adjacencies[vertex1].addLast(edge);
      adjacencies[vertex2].addLast(edge);
      edges.addLast(edge);
    }

    void addEdge(WeightedUndirectedEdge edge) {
      if (edge == null) {
        throw new IllegalArgumentException("edge cannot be null");
      }
      int vertex1 = edge.vertex1;
      int vertex2 = edge.vertex2;
      if (vertex1 < 0 || vertex1 >= numberOfVertices ||
          vertex2 < 0 || vertex2 >= numberOfVertices) {
        throw new IllegalArgumentException("Invalid edge, vertex1 and/or vertex2 cannot be less than 0 or greater than or equal number of vertices in the graph");
      }
      adjacencies[vertex1].addLast(edge);
      adjacencies[vertex2].addLast(edge);
      edges.addLast(edge);
    }

    LinkedList<WeightedUndirectedEdge> adjacencies(int vertex) {
      if (vertex < 0 || vertex >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      return adjacencies[vertex];
    }
  }

  private static class RootedTreeDisjointSets {

    private RootedTreeDisjointSets() { }

    public static <T> Node<T> build(T object) {
      if (object == null) {
        throw new IllegalArgumentException("object cannot be null");
      }
      return new Node<>(object);
    }

    public static <T> Node<T> union(Node<T> node1, Node<T> node2) {
      Node<T> root1 = findRoot(node1);
      Node<T> root2 = findRoot(node2);
      if (root1 == root2) {
        return root1;
      }
      if (root1.rank < root2.rank) {
        root1.parent = root2;
        root2.rank++;
        return root2;
      } else {
        root2.parent = root1;
        root1.rank++;
        return root1;
      }
    }

    public static <T> Node<T> find(Node<T> node) {
      if (node == null) {
        return null;
      }
      return findRoot(node);
    }

    private static <T> Node<T> findRoot(Node<T> node) {
      if (node.parent != node) {
        node.parent = findRoot(node.parent);
      }
      return node.parent;
    }

    public static class Node<T> {

      protected Node<T> parent;

      protected int rank;

      private final T object;

      public Node(T object) {
        this.parent = this;
        this.rank = 0;
        this.object = object;
      }

      public T getObject() {
        return object;
      }
    }
  }
}
