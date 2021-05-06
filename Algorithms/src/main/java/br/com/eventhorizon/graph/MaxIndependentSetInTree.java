package br.com.eventhorizon.graph;

import br.com.eventhorizon.common.datastructures.ArrayList;
import br.com.eventhorizon.common.datastructures.graphs.UndirectedGraph;
import br.com.eventhorizon.common.datastructures.graphs.UndirectedGraphTraverseOrder;

/**
 * This graph processing class finds the maximum independent set within a tree represented
 * by an undirected graph.
 */
public class MaxIndependentSetInTree {

  private final UndirectedGraph graph;

  private final ArrayList<Integer> maxIndependentSet;

  /**
   * Creates an instance of this processing graph class.
   *
   * @param graph A undirected graph representing the tree to find its respective maximum independent set
   */
  public MaxIndependentSetInTree(UndirectedGraph graph) {
    this.graph = graph;
    this.maxIndependentSet = new ArrayList<>();
    findMaxIndependentSet();
  }

  /**
   * Gets the size of the maximum independent set in the tree.
   *
   * @return The size of maximum independent set in the tree
   */
  public int maxIndependentSetSize() {
    return maxIndependentSet.size();
  }

  /**
   * Gets the maximum independent set of the tree.
   *
   * @return An iterable over the maximum independent set in the tree
   */
  public Iterable<Integer> maxIndependentSet() {
    return maxIndependentSet;
  }

  private void findMaxIndependentSet() {
    boolean[] marked = new boolean[graph.numberOfVertices()];
    UndirectedGraphTraverseOrder
        .order(graph, UndirectedGraphTraverseOrder.Type.DEPTH_FIRST_POSTORDER)
        .forEach(vertex -> {
          if (!marked[vertex]) {
            maxIndependentSet.add(vertex);
            graph.adjacencies(vertex).forEach(adjVertex -> marked[adjVertex] = true);
          }
        });
  }
}
