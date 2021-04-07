package br.com.eventhorizon.edx.ucsandiego.algs202x.pa2;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class CheckingCityIntersections implements PA {

  @Override
  public void naiveSolution() {
    finalSolution();
  }

  @Override
  public void finalSolution() {
    DirectedGraph graph = readGraph();
    DirectedGraphStronglyConnectedComponents scc = new DirectedGraphStronglyConnectedComponents(graph);
    System.out.println(scc.count);
  }

  private static DirectedGraph readGraph() {
    FastScanner scanner = new FastScanner(System.in);
    int numberOfVertices = scanner.nextInt();
    int numberOfEdges = scanner.nextInt();
    DirectedGraph
        graph = new DirectedGraph(numberOfVertices);
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

    public DirectedGraph reverse() {
      DirectedGraph graph = new DirectedGraph(numberOfVertices);
      for (int i = 0; i < numberOfVertices; i++) {
        List<Integer> adjacencies = this.adjacencies[i];
        for (int j = 0; j < adjacencies.size(); j++) {
          graph.addEdge(adjacencies.get(j), i);
        }
      }
      return graph;
    }
  }

  private static class DirectedGraphStronglyConnectedComponents {

    private final boolean[] marked;

    private int count;

    public DirectedGraphStronglyConnectedComponents(DirectedGraph graph) {
      marked = new boolean[graph.numberOfVertices()];
      processGraph(graph);
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
        List<Integer> adjVertices = graph.adjacencies(vertex);
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
  }
}
