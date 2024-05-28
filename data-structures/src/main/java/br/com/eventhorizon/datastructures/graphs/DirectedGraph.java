package br.com.eventhorizon.datastructures.graphs;

import br.com.eventhorizon.datastructures.LinkedList;

/**
 * Data structure to represent a directed graph.
 * The implementation uses adjacency lists to represent the graph,
 * so for each vertex in the graph a list of its respective adjacent vertices is created and stored.
 * Note that for each vertex v its respective adjacency list contains all the vertices that could be
 * reached from the respective vertex v.
 *
 * The number of vertices in the graph is fixed by the time of its creation and cannot be changed
 * later, so only edges can be added to the graph after its constructor is executed.
 */
public class DirectedGraph {

  private final int numberOfVertices;

  private int numberOfEdges;

  private final LinkedList<Integer>[] adjacencies;

  /**
   * Creates a graph with vertices and no edges.
   *
   * @param numberOfVertices The number of vertices in the graph
   */
  public DirectedGraph(int numberOfVertices) {
    this.numberOfVertices = numberOfVertices;
    this.adjacencies = new LinkedList[numberOfVertices];
    for (int i = 0; i < numberOfVertices; i++) {
      this.adjacencies[i] = new LinkedList<>();
    }
  }

  /**
   * Adds a new edge to the graph.
   *
   * @param fromVertex The vertex on one side of the edge
   * @param toVertex The vertex on the other side of the edge
   */
  public void addEdge(int fromVertex, int toVertex) {
    if (fromVertex < 0 || fromVertex >= numberOfVertices ||
        toVertex < 0 || toVertex >= numberOfVertices) {
      throw new IndexOutOfBoundsException();
    }
    adjacencies[fromVertex].addLast(toVertex);
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
   * Get the output degree of a specific vertex,
   * that is the number of vertices that can be reached from the specified vertex.
   *
   * @param vertex The vertex to look for its output degree
   * @return The number of vertices that can be reached from vertex <code>vertex</code>
   */
  public int outDegree(int vertex) {
    if (vertex < 0 || vertex >= numberOfVertices) {
      throw new IndexOutOfBoundsException();
    }
    return adjacencies[vertex].size();
  }

  /**
   * Gets the list of vertices that can be reached from a specific vertex.
   *
   * @param vertex The vertex to look for its respective adjacecies
   * @return The list of vertices that can be reached from vertex <code>vertex</code>
   */
  public LinkedList<Integer> adjacencies(int vertex) {
    if (vertex < 0 || vertex >= numberOfVertices) {
      throw new IndexOutOfBoundsException();
    }
    return adjacencies[vertex];
  }

  /**
   * Creates a new graph that is the reverse of this graph, that is all the edges with
   * its respective directions reversed.
   *
   * @return The respective reverse graph of this
   */
  public DirectedGraph reverse() {
    DirectedGraph graph = new DirectedGraph(numberOfVertices);
    for (int i = 0; i < numberOfVertices; i++) {
      LinkedList<Integer> adjacencies = this.adjacencies[i];
      for (int j = 0; j < adjacencies.size(); j++) {
        graph.addEdge(adjacencies.get(j), i);
      }
    }
    return graph;
  }
}
