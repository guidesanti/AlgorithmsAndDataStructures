package br.com.eventhorizon.common.datastructures.graphs;

import br.com.eventhorizon.common.datastructures.LinkedList;

/**
 * Data structure to represent an undirected graph.
 * The implementation uses adjacency lists to represent the graph,
 * so for each vertex in the graph a list of its respective adjacent vertices is created and stored.
 * This extension of UndirectedGraph class adds support to list all edges in addition to only
 * provide the adjacent vertices for each vertex in the graph.
 *
 * The number of vertices in the graph is fixed by the time of its creation and cannot be changed
 * later, so only edges can be added to the graph after its constructor is executed.
 */
public class UndirectedGraphV2 extends UndirectedGraph {

  protected final LinkedList<Edge> edges;

  /**
   * Creates a graph with vertices and no edges.
   *
   * @param numberOfVertices The number of vertices in the graph
   */
  public UndirectedGraphV2(int numberOfVertices) {
    super(numberOfVertices);
    this.edges = new LinkedList<>();
  }

  /**
   * Adds a new edge to the graph.
   *
   * @param vertex1 The vertex on one side of the edge
   * @param vertex2 The vertex on the other side of the edge
   */
  @Override
  public void addEdge(int vertex1, int vertex2) {
    super.addEdge(vertex1, vertex2);
    edges.addLast(new Edge(vertex1, vertex2));
  }

  /**
   * Gets all edges in this graph.
   *
   * @return All edges in this graph
   */
  public LinkedList<Edge> edges() {
    return edges;
  }

  public static class Edge {

    private final int vertex1;

    private final int vertex2;

    public Edge(int vertex1, int vertex2) {
      this.vertex1 = vertex1;
      this.vertex2 = vertex2;
    }

    public int vertex1() {
      return vertex1;
    }

    public int vertex2() {
      return vertex2;
    }
  }
}
