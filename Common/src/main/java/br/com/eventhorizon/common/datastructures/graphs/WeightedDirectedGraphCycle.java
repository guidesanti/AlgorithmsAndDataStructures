package br.com.eventhorizon.common.datastructures.graphs;

import br.com.eventhorizon.common.datastructures.LinkedList;
import br.com.eventhorizon.common.datastructures.Stack;

/**
 * This graph processing class finds any cycle in a weighted directed graph.
 * It uses depth first search to traverse the graph vertices starting from the first vertex and it
 * stops searching as soon as the first cycle if found.
 */
public class WeightedDirectedGraphCycle {

  private final boolean[] marked;

  private final WeightedDirectedGraph.WeightedDirectEdge[] edgeTo;

  private final boolean[] onPath;

  private final  Stack<WeightedDirectedGraph.WeightedDirectEdge> cycle;

  public WeightedDirectedGraphCycle(WeightedDirectedGraph graph) {
    if (graph == null) {
      throw new IllegalArgumentException("graph cannot be null");
    }
    marked = new boolean[graph.numberOfVertices()];
    edgeTo = new WeightedDirectedGraph.WeightedDirectEdge[graph.numberOfVertices()];
    onPath = new boolean[graph.numberOfVertices()];
    cycle = new Stack<>();
    for (int vertex = 0; vertex < graph.numberOfVertices() && cycle.isEmpty(); vertex++) {
      if (!marked[vertex]) {
        depthFirstSearch(graph, vertex);
      }
    }
  }

  public boolean hasCycle() {
    return !cycle.isEmpty();
  }

  public Iterable<WeightedDirectedGraph.WeightedDirectEdge> cycle() {
    return cycle;
  }

  private void depthFirstSearch(WeightedDirectedGraph graph, int sourceVertex) {
    Stack<Integer> stack = new Stack<>();
    stack.push(sourceVertex);
    marked[sourceVertex] = true;
    while (!stack.isEmpty()) {
      while (true) {
        Integer vertex = stack.peek();
        LinkedList<WeightedDirectedGraph.WeightedDirectEdge> adjEdges = graph.adjacencies(vertex);
        onPath[vertex] = true;
        boolean stop = true;
        for (int i = 0; i < adjEdges.size(); i++) {
          WeightedDirectedGraph.WeightedDirectEdge adjEdge = adjEdges.get(i);
          int adjVertex = adjEdge.to();
          if (!marked[adjVertex]) {
            stack.push(adjVertex);
            edgeTo[adjVertex] = adjEdge;
            marked[adjVertex] = true;
            stop = false;
          } else if (onPath[adjVertex]) {
            cycle.push(adjEdge);
            for (WeightedDirectedGraph.WeightedDirectEdge edge = edgeTo[vertex]; edge != null; edge = edgeTo[edge.from()]) {
              cycle.push(edge);
            }
            return;
          }
        }
        if (stop) {
          break;
        }
      }
      onPath[stack.pop()] = false;
    }
  }
}
