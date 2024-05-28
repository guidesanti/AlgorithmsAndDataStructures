package br.com.eventhorizon.edx.ucsandiego.algs202x.pa3;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MinimumNumberOfFlightSegments implements PA {

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    UndirectedGraph graph = readGraph(scanner);
    System.out.println(breadthFirst(graph, scanner.nextInt() - 1, scanner.nextInt() - 1));
  }

  private int breadthFirst(UndirectedGraph graph, int sourceVertex, int destinationVertex) {
    boolean[] marked = new boolean[graph.numberOfVertices];
    int[] edgeTo = new int[graph.numberOfVertices];
    Queue<Integer> queue = new LinkedList<>();
    queue.add(sourceVertex);
    marked[sourceVertex] = true;
    while (!queue.isEmpty()) {
      Integer vertex = queue.remove();
      List<Integer> adjVertices = graph.adjacencies(vertex);
      adjVertices.forEach(adjVertex -> {
        if (!marked[adjVertex]) {
          queue.add(adjVertex);
          marked[adjVertex] = true;
          edgeTo[adjVertex] = vertex;
        }
      });
      if (marked[destinationVertex]) {
        break;
      }
    }
    if (!marked[destinationVertex]) {
      return -1;
    }
    int count = 0;
    for (int i = destinationVertex; i != sourceVertex; i = edgeTo[i]) {
      count++;
    }
    return count;
  }

  private static UndirectedGraph readGraph(FastScanner scanner) {
    int numberOfVertices = scanner.nextInt();
    int numberOfEdges = scanner.nextInt();
    UndirectedGraph graph = new UndirectedGraph(numberOfVertices);
    for (int i = 0; i < numberOfEdges; i++) {
      graph.addEdge(scanner.nextInt() - 1, scanner.nextInt() - 1);
    }
    return graph;
  }

  private static class UndirectedGraph {

    private final int numberOfVertices;

    private int numberOfEdges;

    private final List<Integer>[] adjacencies;

    UndirectedGraph(int numberOfVertices) {
      this.numberOfVertices = numberOfVertices;
      this.adjacencies = new List[numberOfVertices];
      for (int i = 0; i < numberOfVertices; i++) {
        this.adjacencies[i] = new ArrayList<>();
      }
    }

    void addEdge(int vertex1, int vertex2) {
      if (vertex1 < 0 || vertex1 >= numberOfVertices ||
          vertex2 < 0 || vertex2 >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      adjacencies[vertex1].add(vertex2);
      adjacencies[vertex2].add(vertex1);
      numberOfEdges++;
    }

    List<Integer> adjacencies(int vertex) {
      if (vertex < 0 || vertex >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      return adjacencies[vertex];
    }
  }
}
