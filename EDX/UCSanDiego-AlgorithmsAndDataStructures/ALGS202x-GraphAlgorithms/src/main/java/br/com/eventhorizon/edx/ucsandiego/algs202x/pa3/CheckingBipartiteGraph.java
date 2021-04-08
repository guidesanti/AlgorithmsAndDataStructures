package br.com.eventhorizon.edx.ucsandiego.algs202x.pa3;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CheckingBipartiteGraph implements PA {

  private static final byte UNKNOWN = 0;

  private static final byte BLACK = 1;

  private static final byte WHITE = 2;

  @Override
  public void naiveSolution() {
    finalSolution();
  }

  @Override
  public void finalSolution() {
    UndirectedGraph graph = readGraph();
    System.out.println(breadthFirst(graph) ? 1 : 0);
  }

  private boolean breadthFirst(UndirectedGraph graph) {
    byte[] color = new byte[graph.numberOfVertices];
    Queue<Integer> queue = new LinkedList<>();
    for (int sourceVertex = 0; sourceVertex < graph.numberOfVertices; sourceVertex++) {
      if (color[sourceVertex] == UNKNOWN) {
        queue.add(sourceVertex);
        color[sourceVertex] = BLACK;
        while (!queue.isEmpty()) {
          Integer vertex = queue.remove();
          byte nextColor = (byte) (color[vertex] ^ 0x03);
          List<Integer> adjVertices = graph.adjacencies(vertex);
          for (int adjVertex : adjVertices) {
            if (color[adjVertex] == UNKNOWN) {
              queue.add(adjVertex);
              color[adjVertex] = nextColor;
            } else if (color[adjVertex] == color[vertex]) {
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  private static UndirectedGraph readGraph() {
    FastScanner scanner = new FastScanner(System.in);
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
    }

    List<Integer> adjacencies(int vertex) {
      if (vertex < 0 || vertex >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      return adjacencies[vertex];
    }
  }
}
