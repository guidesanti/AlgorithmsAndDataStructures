package br.com.eventhorizon.edx.ucsandiego.algs207x.pa3;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindCirculationInNetwork implements PA {

  private static int verticesCount;

  private static int edgesCount;

  private static Map<Integer, List<Edge>> adjacencies;

  private static void init() {
    verticesCount = 0;
    edgesCount = 0;
    adjacencies = new HashMap<>();
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read number of vertices and number of edges
    verticesCount = scanner.nextInt();
    edgesCount = scanner.nextInt();

    // Read edges
    for (int i = 0; i < edgesCount; i++) {
      int from = scanner.nextInt();
      List<Edge> adj = adjacencies.getOrDefault(from, new ArrayList<>());
      adj.add(new Edge(from, scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
      adjacencies.put(from, adj);
    }
  }

  private static void writeOutput() {
    // TODO
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    finalSolutionImpl();
    writeOutput();
  }

  private static void finalSolutionImpl() {
    // TODO
  }

  private static class Edge {

    final int from;

    final int to;

    final int lowerBound;

    final int capacity;

    int flow;

    Edge(int from, int to, int lowerBound, int capacity) {
      this.from = from;
      this.to = to;
      this.lowerBound = lowerBound;
      this.capacity = capacity;
    }

    @Override
    public String toString() {
      return from + "->" + to + ":" + lowerBound + "," + capacity;
    }
  }
}
