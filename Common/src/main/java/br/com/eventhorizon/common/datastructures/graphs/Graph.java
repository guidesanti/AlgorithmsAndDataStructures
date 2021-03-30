package br.com.eventhorizon.common.datastructures.graphs;

import br.com.eventhorizon.common.datastructures.LinkedList;

/**
 * Data structure to represent an undirected graph.
 * The implementation uses adjacency lists to represent the graph,
 * so for each vertex in the graph a list of its respective adjacent vertices is created and stored.
 *
 * The number of vertices in the graph is fixed by the time of its creation and cannot be changed
 * later, so only edges can be added to the graph after its constructor is executed.
 */
public class Graph {

  private final int numberOfVertices;

  private int numberOfEdges;

  private final LinkedList<Integer>[] adjacencies;

  private int maxDegree;

  /**
   * Creates a graph with vertices and no edges.
   *
   * @param numberOfVertices The number of vertices in the graph
   */
  public Graph(int numberOfVertices) {
    this.numberOfVertices = numberOfVertices;
    this.adjacencies = new LinkedList[numberOfVertices];
    for (int i = 0; i < numberOfVertices; i++) {
      this.adjacencies[i] = new LinkedList<>();
    }
  }

  /**
   * Adds a new edge to the graph.
   *
   * @param vertex1 The vertex on one side of the edge
   * @param vertex2 The vertex on the other side of the edge
   */
  public void addEdge(int vertex1, int vertex2) {
    if (vertex1 < 0 || vertex1 >= numberOfVertices ||
        vertex2 < 0 || vertex2 >= numberOfVertices) {
      throw new IndexOutOfBoundsException();
    }
    adjacencies[vertex1].addLast(vertex2);
    adjacencies[vertex2].addLast(vertex1);
    maxDegree = Math.max(maxDegree, Math.max(adjacencies[vertex1].size(), adjacencies[vertex2].size()));
    numberOfEdges++;
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
    return numberOfEdges;
  }

  /**
   * Get the degree of a specific vertex, that is the number of adjacent vertices.
   *
   * @param vertex The vertex to look for its degree
   * @return The number of vertices adjacent to the respective vertex
   */
  public int degree(int vertex) {
    if (vertex < 0 || vertex >= numberOfVertices) {
      throw new IndexOutOfBoundsException();
    }
    return adjacencies[vertex].size();
  }

  /**
   * Gets the maximum degree in the graph.
   *
   * @return The maximum degree in the graph
   */
  public int maxDegree() {
    return maxDegree;
  }

  /**
   * Gets the list of adjacencies of a specific vertex.
   *
   * @param vertex The vertex to look for its respective adjacecies
   * @return The list of adjacencies of the specific vertex
   */
  public LinkedList<Integer> adjacencies(int vertex) {
    if (vertex < 0 || vertex >= numberOfVertices) {
      throw new IndexOutOfBoundsException();
    }
    return adjacencies[vertex];
  }
}
