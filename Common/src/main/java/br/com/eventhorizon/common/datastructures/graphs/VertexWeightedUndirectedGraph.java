package br.com.eventhorizon.common.datastructures.graphs;

import java.util.Arrays;

/**
 * Data structure to represent a vertex weighted undirected graph.
 * The implementation uses adjacency lists to represent the graph,
 * so for each vertex in the graph a list of its respective adjacent vertices is created and stored.
 * Also for each vertex it is stored its respective weight within a vertex indexed array.
 *
 * The number of vertices in the graph is fixed by the time of its creation and cannot be changed
 * later, so only edges can be added to the graph after its constructor is executed.
 */
public class VertexWeightedUndirectedGraph extends UndirectedGraph {

  private final int[] weights;

  /**
   * Creates a graph with vertices and no edges.
   *
   * @param weights A vertex indexed array with all vertex weights
   */
  public VertexWeightedUndirectedGraph(int[] weights) {
    super(weights.length);
    this.weights = Arrays.copyOf(weights, weights.length);
  }

  /**
   * Gets the weight of a specific vertex.
   *
   * @param vertex The vertex to retrieve its weight
   * @return The weight of the specified vertex
   */
  public int weight(int vertex) {
    if (vertex < 0 || vertex >= numberOfVertices) {
      throw new IndexOutOfBoundsException();
    }
    return weights[vertex];
  }
}
