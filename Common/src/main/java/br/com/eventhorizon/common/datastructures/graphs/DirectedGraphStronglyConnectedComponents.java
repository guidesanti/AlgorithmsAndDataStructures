package br.com.eventhorizon.common.datastructures.graphs;

import br.com.eventhorizon.common.datastructures.LinkedList;
import br.com.eventhorizon.common.datastructures.Stack;

public class DirectedGraphStronglyConnectedComponents {

  private final boolean[] marked;

  private final int[] id;

  private int count;

  public DirectedGraphStronglyConnectedComponents(DirectedGraph graph) {
    marked = new boolean[graph.numberOfVertices()];
    id = new int[graph.numberOfVertices()];
    processGraph(graph);
  }

  public boolean areStronglyConnected(int vertex1, int vertex2) {
    return id[vertex1] == id[vertex2];
  }

  public int count() {
    return count;
  }

  public int id(int vertex) {
    return id[vertex];
  }

  private void processGraph(DirectedGraph graph) {
    Iterable<Integer> order = depthFirstReversePostorder(graph.reverse());
    for (int vertex : order) {
      if (!marked[vertex]) {
        depthFirstPreorder(graph, vertex);
        count++;
      }
    }
  }

  private void depthFirstPreorder(DirectedGraph graph, int sourceVertex) {
    Stack<Integer> stack = new Stack<>();
    stack.push(sourceVertex);
    marked[sourceVertex] = true;
    while (!stack.isEmpty()) {
      Integer vertex = stack.pop();
      id[vertex] = count;
      LinkedList<Integer> adjVertices = graph.adjacencies(vertex);
      adjVertices.forEach(adjVertex -> {
        if (!marked[adjVertex]) {
          stack.push(adjVertex);
          marked[adjVertex] = true;
        }
      });
    }
  }

  private Iterable<Integer> depthFirstReversePostorder(DirectedGraph graph) {
    boolean[] marked = new boolean[graph.numberOfVertices()];
    LinkedList<Integer> order = new LinkedList<>();
    Stack<Integer> stack = new Stack<>();
    for (int i = 0; i < graph.numberOfVertices(); i++) {
      if (!marked[i]) {
        stack.push(i);
        marked[i] = true;
        while (!stack.isEmpty()) {
          while (true) {
            Integer vertex = stack.peek();
            LinkedList<Integer> adjVertices = graph.adjacencies(vertex);
            boolean stop = true;
            for (int adjVertex : adjVertices) {
              if (!marked[adjVertex]) {
                stack.push(adjVertex);
                marked[adjVertex] = true;
                stop = false;
                break;
              }
            }
            if (stop) {
              break;
            }
          }
          order.addFirst(stack.pop());
        }
      }
    }
    return order;
  }
}
