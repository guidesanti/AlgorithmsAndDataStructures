package br.com.eventhorizon.datastructures.graphs;

import br.com.eventhorizon.datastructures.LinkedList;
import br.com.eventhorizon.datastructures.Stack;
import br.com.eventhorizon.datastructures.queues.Queue;

import java.util.NoSuchElementException;
import java.util.function.Function;

public class UndirectedGraphTraverser {

  private final UndirectedGraph graph;

  private final int sourceVertex;

  private final Type type;

  private final Function<Void, Integer> traverser;

  private final Stack<Integer> stack;

  private final Queue<Integer> queue;

  private final int[] edgeTo;

  private final boolean[] marked;

  private int markedCount;

  public UndirectedGraphTraverser(UndirectedGraph graph, int sourceVertex, Type type) {
    this.graph = graph;
    this.sourceVertex = sourceVertex;
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
    this.stack.push(sourceVertex);
    this.queue = new Queue<>();
    this.queue.enqueue(sourceVertex);
    this.edgeTo = new int[graph.numberOfVertices()];
    this.marked = new boolean[graph.numberOfVertices()];
    this.marked[sourceVertex] = true;
    this.markedCount++;
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
        edgeTo[adjVertex] = vertex;
        marked[adjVertex] = true;
        markedCount++;
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
          edgeTo[adjVertex] = vertex;
          marked[adjVertex] = true;
          markedCount++;
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
        edgeTo[adjVertex] = vertex;
        marked[adjVertex] = true;
        markedCount++;
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

  public int markedCount() {
    return markedCount;
  }

  public boolean hasPathTo(int destinationVertex) {
    if (destinationVertex < 0 || destinationVertex >= graph.numberOfVertices()) {
      throw new IndexOutOfBoundsException();
    }
    return marked[destinationVertex];
  }

  public Iterable<Integer> pathTo(int destinationVertex) {
    if (destinationVertex < 0 || destinationVertex >= graph.numberOfVertices()) {
      throw new IndexOutOfBoundsException();
    }
    if (!hasPathTo(destinationVertex)) {
      return null;
    }
    LinkedList<Integer> path = new LinkedList<>();
    for (int vertex = destinationVertex; vertex != sourceVertex; vertex = edgeTo[vertex]) {
      path.addFirst(vertex);
    }
    path.addFirst(sourceVertex);
    return path;
  }

  public enum Type {
    DEPTH_FIRST_PREORDER,
    DEPTH_FIRST_POSTORDER,
    BREADTH_FIRST
  }
}
