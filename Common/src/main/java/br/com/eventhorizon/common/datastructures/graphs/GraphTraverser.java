package br.com.eventhorizon.common.datastructures.graphs;

import br.com.eventhorizon.common.datastructures.LinkedList;
import br.com.eventhorizon.common.datastructures.Queue;
import br.com.eventhorizon.common.datastructures.Stack;

import java.util.NoSuchElementException;
import java.util.function.Function;

public class GraphTraverser {

  private final Graph graph;

  private final Type type;

  private final Function<Void, Integer> traverser;

  private final Stack<Integer> stack;

  private final Queue<Integer> queue;

  private final boolean[] visited;

  public GraphTraverser(Graph graph, int vertex, Type type) {
    this.graph = graph;
    this.type = type;
    switch (type) {
      case DEPTH_FIRST:
        this.traverser = unused -> depthFirst();
        break;
      case BREADTH_FIRST:
        this.traverser = unused -> breadthFirst();
        break;
      default:
        throw new IllegalArgumentException("Unknown type");
    }
    this.stack = new Stack<>();
    this.stack.push(vertex);
    this.queue = new Queue<>();
    this.queue.enqueue(vertex);
    visited = new boolean[graph.numberOfVertices()];
  }

  private Integer depthFirst() {
    if (stack.isEmpty()) {
      throw new NoSuchElementException();
    }
    Integer vertex = stack.pop();
    if (!visited[vertex]) {
      LinkedList<Integer> adjVertices = graph.adjacencies(vertex);
      for (int i = 0; i < adjVertices.size(); i++) {
        int adjVertex = adjVertices.get(i);
        if (!visited[adjVertex]) {
          stack.push(adjVertex);
        }
      }
      visited[vertex] = true;
    }
    return vertex;
  }

  private Integer breadthFirst() {
    if (queue.isEmpty()) {
      throw new NoSuchElementException();
    }
    Integer vertex = queue.dequeue();
    if (!visited[vertex]) {
      LinkedList<Integer> adjVertices = graph.adjacencies(vertex);
      for (int i = 0; i < adjVertices.size(); i++) {
        int adjVertex = adjVertices.get(i);
        if (!visited[adjVertex]) {
          queue.enqueue(adjVertices.get(i));
        }
      }
      visited[vertex] = true;
    }
    return vertex;
  }

  public boolean hasNext() {
    if (type == Type.DEPTH_FIRST) {
      return !stack.isEmpty();
    } else {
      return !queue.isEmpty();
    }
  }

  public Object next() {
    return traverser.apply(null);
  }

  public enum Type {
    DEPTH_FIRST,
    BREADTH_FIRST
  }
}
