package br.com.eventhorizon.edx.ucsandiego.algs202x.pa2;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class CheckingConsistencyOfCSCurriculum implements PA {

  @Override
  public void naiveSolution() {
    finalSolution();
  }

  @Override
  public void finalSolution() {
    DirectedGraph graph = readGraph();
    DirectedGraphCycle graphCycle = new DirectedGraphCycle(graph);
    System.out.println(graphCycle.hasCycle ? 1 : 0);
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

  private static class DirectedGraphCycle {

    private final boolean[] marked;

    private final boolean[] onPath;

    private boolean hasCycle;

    public DirectedGraphCycle(DirectedGraph graph) {
      marked = new boolean[graph.numberOfVertices()];
      onPath = new boolean[graph.numberOfVertices()];
      for (int vertex = 0; vertex < graph.numberOfVertices() && !hasCycle; vertex++) {
        if (!marked[vertex]) {
          depthFirstSearch(graph, vertex);
        }
      }
    }

    private void depthFirstSearch(DirectedGraph graph, int sourceVertex) {
      Stack<Integer> stack = new Stack<>();
      stack.push(sourceVertex);
      while (!stack.isEmpty()) {
        while (true) {
          Integer vertex = stack.peek();
          List<Integer> adjVertices = graph.adjacencies(vertex);
          onPath[vertex] = true;
          boolean stop = true;
          for (int i = 0; i < adjVertices.size(); i++) {
            int adjVertex = adjVertices.get(i);
            if (!marked[adjVertex]) {
              stack.push(adjVertex);
              marked[adjVertex] = true;
              stop = false;
            } else if (onPath[adjVertex]) {
              hasCycle = true;
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
}
