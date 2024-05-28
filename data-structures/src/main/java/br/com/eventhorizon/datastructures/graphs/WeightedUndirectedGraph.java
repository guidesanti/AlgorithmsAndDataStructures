package br.com.eventhorizon.datastructures.graphs;

import br.com.eventhorizon.datastructures.LinkedList;

/**
 * Data structure to represent a weighted undirected graph.
 * The implementation uses adjacency lists to represent the graph,
 * so for each vertex in the graph a list of its respective adjacent vertices is created and stored.
 *
 * The number of vertices in the graph is fixed by the time of its creation and cannot be changed
 * later, so only edges can be added to the graph after its constructor is executed.
 */
public class WeightedUndirectedGraph {

  private final int numberOfVertices;

  private final LinkedList<WeightedUndirectedEdge>[] adjacencies;

  private final LinkedList<WeightedUndirectedEdge> edges;

  /**
   * Creates a weighted undirected graph with vertices and no edges.
   *
   * @param numberOfVertices The number of vertices in the graph
   */
  public WeightedUndirectedGraph(int numberOfVertices) {
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
   * @param vertex1 One of the vertices in the edge
   * @param vertex2 The other vertex in the edge
   * @param weight The edge weight
   */
  public void addEdge(int vertex1, int vertex2, double weight) {
    if (vertex1 < 0 || vertex1 >= numberOfVertices ||
        vertex2 < 0 || vertex2 >= numberOfVertices) {
      throw new IndexOutOfBoundsException();
    }
    WeightedUndirectedEdge edge = new WeightedUndirectedEdge(vertex1, vertex2, weight);
    adjacencies[vertex1].addLast(edge);
    adjacencies[vertex2].addLast(edge);
    edges.addLast(edge);
  }

  /**
   * Adds a new edge to the graph.
   *
   * @param edge The new edge to add to the graph
   */
  public void addEdge(WeightedUndirectedEdge edge) {
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
   * Gets the list of edges adjacent to a vertex.
   *
   * @param vertex The vertex to look for adjacent edges
   * @return The list of adjacent edges
   */
  public LinkedList<WeightedUndirectedEdge> adjacencies(int vertex) {
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
  public LinkedList<WeightedUndirectedEdge> edges() {
    return edges;
  }
}
