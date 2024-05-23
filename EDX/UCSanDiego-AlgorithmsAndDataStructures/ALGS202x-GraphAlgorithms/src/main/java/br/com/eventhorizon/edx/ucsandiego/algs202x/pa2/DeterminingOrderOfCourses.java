package br.com.eventhorizon.edx.ucsandiego.algs202x.pa2;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.v2.PA;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class DeterminingOrderOfCourses implements PA {

  @Override
  public void trivialSolution() {
    DirectedGraph graph = readGraph();
    naiveDepthFirstReversePostorder(graph).forEach(vertex -> System.out.println(vertex + 1));
  }

  private List<Integer> naiveDepthFirstReversePostorder(DirectedGraph graph) {
    boolean[] marked = new boolean[graph.numberOfVertices()];
    Stack<Integer> reversePost = new Stack<>();
    for (int vertex = 0; vertex < graph.numberOfVertices(); vertex++) {
      if (!marked[vertex]) {
        recursiveDepthFirstReversePostorder(graph, vertex, marked, reversePost);
      }
    }
    return reversePost;
  }

  private void recursiveDepthFirstReversePostorder(DirectedGraph graph, int vertex, boolean[] marked, Stack<Integer> reversePost) {
    marked[vertex] = true;
    for (int w : graph.adjacencies(vertex)) {
      if (!marked[w]) {
        recursiveDepthFirstReversePostorder(graph, w, marked, reversePost);
      }
    }
    reversePost.push(vertex);
  }

  @Override
  public void finalSolution() {
    DirectedGraph graph = readGraph();
    depthFirstReversePostorder(graph).forEach(vertex -> System.out.println(vertex + 1));
  }

  private List<Integer> depthFirstReversePostorder(DirectedGraph graph) {
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
            List<Integer> adjVertices = graph.adjacencies(vertex);
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

  private static DirectedGraph readGraph() {
    FastScanner scanner = new FastScanner(System.in);
    int numberOfVertices = scanner.nextInt();
    int numberOfEdges = scanner.nextInt();
    DirectedGraph graph = new DirectedGraph(numberOfVertices);
    for (int i = 0; i < numberOfEdges; i++) {
      graph.addEdge(scanner.nextInt() - 1, scanner.nextInt() - 1);
    }
    return graph;
  }

  private static class DirectedGraph {

    private final int numberOfVertices;

    private int numberOfEdges;

    private final List<Integer>[] adjacencies;

    public DirectedGraph(int numberOfVertices) {
      this.numberOfVertices = numberOfVertices;
      this.adjacencies = new List[numberOfVertices];
      for (int i = 0; i < numberOfVertices; i++) {
        this.adjacencies[i] = new LinkedList<>();
      }
    }

    public void addEdge(int fromVertex, int toVertex) {
      if (fromVertex < 0 || fromVertex >= numberOfVertices ||
          toVertex < 0 || toVertex >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      adjacencies[fromVertex].add(toVertex);
      numberOfEdges++;
    }

    public int numberOfVertices() {
      return numberOfVertices;
    }

    public int numberOfEdges() {
      return numberOfEdges;
    }

    public List<Integer> adjacencies(int vertex) {
      if (vertex < 0 || vertex >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      return adjacencies[vertex];
    }
  }
}
