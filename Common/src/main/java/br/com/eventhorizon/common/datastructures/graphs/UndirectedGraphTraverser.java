package br.com.eventhorizon.common.datastructures.graphs;

import br.com.eventhorizon.common.datastructures.LinkedList;
import br.com.eventhorizon.common.datastructures.Queue;
import br.com.eventhorizon.common.datastructures.Stack;

import java.util.NoSuchElementException;
import java.util.function.Function;

public class UndirectedGraphTraverser {

  private final UndirectedGraph graph;

  private final Type type;

  private final Function<Void, Integer> traverser;

  private final Stack<Integer> stack;

  private final Queue<Integer> queue;

  private final boolean[] marked;

  public UndirectedGraphTraverser(UndirectedGraph graph, int vertex, Type type) {
    this.graph = graph;
    this.type = type;
    switch (type) {
      case DEPTH_FIRST_PREORDER:
        this.traverser = unused -> depthFirstPreorder();
        break;
      case DEPTH_FIRST_POSTORDER:
        this.traverser = unused -> depthFirstPostorder();
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
    marked = new boolean[graph.numberOfVertices()];
    marked[vertex] = true;
  }

  private Integer depthFirstPreorder() {
    if (stack.isEmpty()) {
      throw new NoSuchElementException();
    }
    Integer vertex = stack.pop();
    LinkedList<Integer> adjVertices = graph.adjacencies(vertex);
    for (int i = 0; i < adjVertices.size(); i++) {
      int adjVertex = adjVertices.get(i);
      if (!marked[adjVertex]) {
        stack.push(adjVertex);
        marked[adjVertex] = true;
      }
    }
    return vertex;
  }

  private Integer depthFirstPostorder() {
    if (stack.isEmpty()) {
      throw new NoSuchElementException();
    }
    Integer vertex = stack.peek();
    LinkedList<Integer> adjVertices = graph.adjacencies(vertex);
    while (true) {
      boolean stop = true;
      for (int i = 0; i < adjVertices.size(); i++) {
        int adjVertex = adjVertices.get(i);
        if (!marked[adjVertex]) {
          stack.push(adjVertex);
          marked[adjVertex] = true;
          stop = false;
        }
      }
      if (stop) {
        break;
      }
      vertex = stack.peek();
      adjVertices = graph.adjacencies(vertex);
    }
    return stack.pop();
  }

  private Integer breadthFirst() {
    if (queue.isEmpty()) {
      throw new NoSuchElementException();
    }
    Integer vertex = queue.dequeue();
    LinkedList<Integer> adjVertices = graph.adjacencies(vertex);
    for (int i = 0; i < adjVertices.size(); i++) {
      int adjVertex = adjVertices.get(i);
      if (!marked[adjVertex]) {
        queue.enqueue(adjVertices.get(i));
        marked[adjVertex] = true;
      }
    }
    return vertex;
  }

  public boolean hasNext() {
    if (type == Type.DEPTH_FIRST_PREORDER ||
        type == Type.DEPTH_FIRST_POSTORDER) {
      return !stack.isEmpty();
    } else {
      return !queue.isEmpty();
    }
  }

  public Integer next() {
    return traverser.apply(null);
  }

  public boolean[] marked() {
    return marked;
  }

  public enum Type {
    DEPTH_FIRST_PREORDER,
    DEPTH_FIRST_POSTORDER,
    BREADTH_FIRST
  }
}
