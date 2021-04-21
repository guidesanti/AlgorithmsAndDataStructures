package br.com.eventhorizon.common.datastructures.graphs;

import br.com.eventhorizon.common.datastructures.LinkedList;

/**
 * Data structure to represent a weighted directed graph.
 * The implementation uses adjacency lists to represent the graph,
 * so for each vertex in the graph a list of its respective adjacent vertices is created and stored.
 * Note that for each vertex v its respective adjacency list contains all the vertices that could be
 * reached from the respective vertex v.
 *
 * The number of vertices in the graph is fixed by the time of its creation and cannot be changed
 * later, so only edges can be added to the graph after its constructor is executed.
 */
public class WeightedDirectedGraph {

  private final int numberOfVertices;

  private final LinkedList<WeightedDirectEdge>[] adjacencies;

  private final LinkedList<WeightedDirectEdge> edges;

  /**
   * Creates a weighted directed graph with vertices and no edges.
   *
   * @param numberOfVertices The number of vertices in the graph
   */
  public WeightedDirectedGraph(int numberOfVertices) {
    this.numberOfVertices = numberOfVertices;
    this.adjacencies = new LinkedList[numberOfVertices];
    for (int i = 0; i < numberOfVertices; i++) {
      this.adjacencies[i] = new LinkedList<>();
    }
    this.edges = new LinkedList<>();
  }

  /**
   * Adds a new edge to the graph.
   *
   * @param fromVertex The vertex the edge points from
   * @param toVertex The vertex the edge points to
   * @param weight The edge weight
   */
  public void addEdge(int fromVertex, int toVertex, double weight) {
    if (fromVertex < 0 || fromVertex >= numberOfVertices ||
        toVertex < 0 || toVertex >= numberOfVertices) {
      throw new IndexOutOfBoundsException();
    }
    WeightedDirectEdge edge = new WeightedDirectEdge(fromVertex, toVertex, weight);
    adjacencies[fromVertex].addLast(edge);
    edges.addLast(edge);
  }

  /**
   * Gets the number of vertices in the graph.
   *
   * @return The number of vertices in the graph
   */
  public int numberOfVertices() {
    return numberOfVertices;
  }

  /**
   * Gets the number of edges in the graph.
   *
   * @return The number of edges in the graph
   */
  public int numberOfEdges() {
    return edges.size();
  }

  /**
   * Gets the list of edges pointing from a specified source vertex.
   *
   * @param vertex The source vertex to look for edges poiting from it
   * @return The list of vertices pointing from <code>vertex</code>
   */
  public LinkedList<WeightedDirectEdge> adjacencies(int vertex) {
    if (vertex < 0 || vertex >= numberOfVertices) {
      throw new IndexOutOfBoundsException();
    }
    return adjacencies[vertex];
  }

  /**
   * Gets all edges in this graph.
   *
   * @return All edges in this graph
   */
  public LinkedList<WeightedDirectEdge> edges() {
    return edges;
  }

  public static class WeightedDirectEdge {

    private final int from;

    private final int to;

    private final double weight;

    private WeightedDirectEdge(int from, int to, double weight) {
      this.from = from;
      this.to = to;
      this.weight = weight;
    }

    public int from() {
      return from;
    }

    public int to() {
      return to;
    }

    public double weight() {
      return weight;
    }

    @Override
    public String toString() {
      return "WeightedDirectEdge {(" + weight + ")" + from + " -> " + to + "}";
    }
  }
}
