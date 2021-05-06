package br.com.eventhorizon.graph;

import br.com.eventhorizon.common.datastructures.graphs.UndirectedGraphTraverseOrder;
import br.com.eventhorizon.common.datastructures.graphs.VertexWeightedUndirectedGraph;

import java.util.*;

/**
 * This graph processing class finds the maximum weighted independent set within a tree represented
 * by an undirected graph.
 * The maximum weighted independent set it the one which the sum of all its respective vertex
 * weights is the maximum possible.
 */
public class MaxWeightedIndependentSetInTree {

  private final VertexWeightedUndirectedGraph graph;

  private int weight;

  /**
   * Creates an instance of this processing graph class.
   *
   * @param graph A undirected graph representing the tree to find its respective maximum weighted independent set
   */
  public MaxWeightedIndependentSetInTree(VertexWeightedUndirectedGraph graph) {
    this.graph = graph;
    this.weight = Integer.MIN_VALUE;
    findMaxWeightedIndependentSet();
  }

  /**
   * Gets the weight of the maximum weighted independent set.
   *
   * @return The weight of the maximum weighted independent set
   */
  public int weight() {
    return weight;
  }

  /**
   * Gets the size of the maximum weighted independent set in the tree.
   *
   * @return The size of maximum weighted independent set in the tree
   */
//  public int size() {
//    return set.size();
//  }

  /**
   * Gets the elements of the maximum weighted independent set of the tree.
   *
   * @return An iterable over the maximum weighted independent set elements in the tree
   */
//  public Iterable<Integer> elements() {
//    return set;
//  }

  private void findMaxWeightedIndependentSet() {
    int[] weights = new int[graph.numberOfVertices()];
    Arrays.fill(weights, Integer.MAX_VALUE);
    UndirectedGraphTraverseOrder.order(graph, UndirectedGraphTraverseOrder.Type.DEPTH_FIRST_POSTORDER).forEach(vertex -> {
      if (graph.degree(vertex) == 1) {
        // Leaves have only one adjacency, its parent
        weights[vertex] = graph.weight(vertex);
      } else {
        // Non leaves, when processed all their children have already been processed and parent not yet
        int weight1 = 0;
        int weight2 = graph.weight(vertex);
        for (int child : graph.adjacencies(vertex)) {
          // If not processed yet, it is not actually a child and instead the parent
          // So we just ignore it and continue
          if (weights[child] == Integer.MAX_VALUE) {
            continue;
          }
          weight1 += weights[child];
          for (int grandChild : graph.adjacencies(child)) {
            if (grandChild == vertex) {
              continue;
            }
            weight2 += weights[grandChild];
          }
        }
        weights[vertex] = Math.max(weight1, weight2);
      }
      weight = Math.max(weight, weights[vertex]);
    });
  }
}
