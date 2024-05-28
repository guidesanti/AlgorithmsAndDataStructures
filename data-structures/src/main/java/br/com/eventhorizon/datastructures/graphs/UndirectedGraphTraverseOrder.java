package br.com.eventhorizon.datastructures.graphs;

import br.com.eventhorizon.datastructures.LinkedList;
import br.com.eventhorizon.datastructures.Stack;
import br.com.eventhorizon.datastructures.queues.Queue;

public final class UndirectedGraphTraverseOrder {

  private UndirectedGraphTraverseOrder() { }

  public static Iterable<Integer> order(UndirectedGraph graph, Type type) {
    switch (type) {
      case DEPTH_FIRST_PREORDER:
        return depthFirstPreorder(graph);
      case DEPTH_FIRST_POSTORDER:
        return depthFirstPostorder(graph);
      case DEPTH_FIRST_REVERSE_POSTORDER:
        return depthFirstReversePostorder(graph);
      case BREADTH_FIRST:
        return breadthFirst(graph);
      default:
        throw new IllegalArgumentException("Invalid traverse order type");
    }
  }

  private static Iterable<Integer> depthFirstPreorder(UndirectedGraph graph) {
    boolean[] marked = new boolean[graph.numberOfVertices()];
    LinkedList<Integer> order = new LinkedList<>();
    Stack<Integer> stack = new Stack<>();
    for (int i = 0; i < graph.numberOfVertices(); i++) {
      if (!marked[i]) {
        stack.push(i);
        marked[i] = true;
        while (!stack.isEmpty()) {
          Integer vertex = stack.pop();
          order.addLast(vertex);
          LinkedList<Integer> adjVertices = graph.adjacencies(vertex);
          adjVertices.forEach(adjVertex -> {
            if (!marked[adjVertex]) {
              stack.push(adjVertex);
              marked[adjVertex] = true;
            }
          });
        }
      }
    }
    return order;
  }

  private static Iterable<Integer> depthFirstPostorder(UndirectedGraph graph) {
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
          order.addLast(stack.pop());
        }
      }
    }
    return order;
  }

  private static Iterable<Integer> depthFirstReversePostorder(UndirectedGraph graph) {
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

  private static Iterable<Integer> breadthFirst(UndirectedGraph graph) {
    boolean[] marked = new boolean[graph.numberOfVertices()];
    LinkedList<Integer> order = new LinkedList<>();
    Queue<Integer> queue = new Queue<>();
    for (int i = 0; i < graph.numberOfVertices(); i++) {
      if (!marked[i]) {
        queue.enqueue(i);
        marked[i] = true;
        while (!queue.isEmpty()) {
          Integer vertex = queue.dequeue();
          order.addLast(vertex);
          LinkedList<Integer> adjVertices = graph.adjacencies(vertex);
          adjVertices.forEach(adjVertex -> {
            if (!marked[adjVertex]) {
              queue.enqueue(adjVertex);
              marked[adjVertex] = true;
            }
          });
        }
      }
    }
    return order;
  }

  public enum Type {
    DEPTH_FIRST_PREORDER,
    DEPTH_FIRST_POSTORDER,
    DEPTH_FIRST_REVERSE_POSTORDER,
    BREADTH_FIRST
  }
}
