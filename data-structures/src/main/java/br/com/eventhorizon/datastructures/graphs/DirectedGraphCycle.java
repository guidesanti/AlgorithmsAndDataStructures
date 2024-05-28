package br.com.eventhorizon.datastructures.graphs;

import br.com.eventhorizon.datastructures.LinkedList;
import br.com.eventhorizon.datastructures.Stack;

/**
 * This graph processing class finds any cycle in a directed graph.
 * It uses depth first search to traverse the graph vertices starting from the first vertex and it
 * stops searching as soon as the first cycle if found.
 */
public class DirectedGraphCycle {

  private final boolean[] marked;

  private final int[] edgeTo;

  private final boolean[] onPath;

  private final  LinkedList<Integer> cycle;

  public DirectedGraphCycle(DirectedGraph graph) {
    if (graph == null) {
      throw new IllegalArgumentException("graph cannot be null");
    }
    marked = new boolean[graph.numberOfVertices()];
    edgeTo = new int[graph.numberOfVertices()];
    onPath = new boolean[graph.numberOfVertices()];
    cycle = new LinkedList<>();
    for (int vertex = 0; vertex < graph.numberOfVertices() && cycle.isEmpty(); vertex++) {
      if (!marked[vertex]) {
        depthFirstSearch(graph, vertex);
      }
    }
  }

  public boolean hasCycle() {
    return !cycle.isEmpty();
  }

  public Iterable<Integer> cycle() {
    return cycle;
  }

  private void depthFirstSearch(DirectedGraph graph, int sourceVertex) {
    Stack<Integer> stack = new Stack<>();
    stack.push(sourceVertex);
    marked[sourceVertex] = true;
    while (!stack.isEmpty()) {
      while (true) {
        Integer vertex = stack.peek();
        LinkedList<Integer> adjVertices = graph.adjacencies(vertex);
        onPath[vertex] = true;
        boolean stop = true;
        for (int i = 0; i < adjVertices.size(); i++) {
          int adjVertex = adjVertices.get(i);
          if (!marked[adjVertex]) {
            stack.push(adjVertex);
            edgeTo[adjVertex] = vertex;
            marked[adjVertex] = true;
            stop = false;
          } else if (onPath[adjVertex]) {
            cycle.addFirst(adjVertex);
            for (int v = vertex; v != adjVertex; v = edgeTo[v]) {
              cycle.addFirst(v);
            }
            cycle.addFirst(adjVertex);
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
