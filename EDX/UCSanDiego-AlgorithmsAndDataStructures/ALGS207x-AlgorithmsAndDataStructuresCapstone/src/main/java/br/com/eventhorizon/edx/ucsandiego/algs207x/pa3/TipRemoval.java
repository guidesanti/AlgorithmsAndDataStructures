package br.com.eventhorizon.edx.ucsandiego.algs207x.pa3;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TipRemoval implements PA {

  private static final int K = 3;

  private static List<String> reads;

  private static Vertex[] graph;

  private static Map<String, Integer> count;

  private static int readLength;

  private static int removedEdgesCount;

  private static void init() {
    reads = new ArrayList<>();
    graph = null;
    count = new HashMap<>();
    readLength = 0;
    removedEdgesCount = 0;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);
    String kmer = scanner.next();
    while (kmer != null) {
      reads.add(kmer);
      kmer = scanner.next();
    }
    readLength = reads.get(0).length();
  }

  private static void writeOutput() {
    System.out.print("" + removedEdgesCount);
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    finalSolutionImpl();
    writeOutput();
  }

  private static void finalSolutionImpl() {
    buildDeBruijnGraph();
    findTips();
  }

  private static void buildDeBruijnGraph() {
    int index = 0;
    Map<String, Integer> indices = new HashMap<>();
    List<Edge> edges = new ArrayList<>();
    for (String read : reads) {
      for (int i = 0; i <= readLength - K; i++) {
        String kmer = read.substring(i, i + K);

        if (count.containsKey(kmer)) {
          count.put(kmer, count.get(kmer) + 1);
          continue;
        }
        count.put(kmer, 1);

        String fromLabel = kmer.substring(0, kmer.length() - 1);
        String toLabel = kmer.substring(1);

        if(!indices.containsKey(fromLabel)){
          indices.put(fromLabel, index);
          index++;
        }
        int fromIndex = indices.get(fromLabel);

        if(!indices.containsKey(toLabel)){
          indices.put(toLabel, index);
          index++;
        }
        int toIndex = indices.get(toLabel);

        edges.add(new Edge(fromIndex, toIndex));
      }
    }

    graph = new Vertex[indices.size()];
    indices.forEach((l, i) -> graph[i] = new Vertex(i, l));
    edges.forEach(edge -> {
      graph[edge.from].outEdges.add(edge);
      graph[edge.to].inEdges.add(edge);
    });
  }

  private static void findTips() {
    boolean[] visited = new boolean[graph.length];
    for (Vertex vertex : graph) {
      if (visited[vertex.index]) {
        continue;
      }
      findTipsForward(vertex.index, visited);
    }
    visited = new boolean[graph.length];
    for (Vertex vertex : graph) {
      if (visited[vertex.index]) {
        continue;
      }
      findTipsBackward(vertex.index, visited);
    }
  }

  private static boolean findTipsForward(int curr, boolean[] visited) {
    visited[curr] = true;
    if (graph[curr].outEdges.isEmpty()) {
      return true;
    }
    int count = 0;
    for (Edge edge : graph[curr].outEdges) {
      if (visited[edge.to]) {
        continue;
      }
      if (findTipsForward(edge.to, visited)) {
        removedEdgesCount++;
        count++;
      }
    }
    return count == graph[curr].outEdges.size();
  }

  private static boolean findTipsBackward(int curr, boolean[] visited) {
    visited[curr] = true;
    if (graph[curr].inEdges.isEmpty()) {
      return true;
    }
    int count = 0;
    for (Edge edge : graph[curr].inEdges) {
      if (visited[edge.from]) {
        continue;
      }
      if (findTipsBackward(edge.from, visited)) {
        removedEdgesCount++;
        count++;
      }
    }
    return count == graph[curr].inEdges.size();
  }

  private static class Vertex {

    final int index;

    final String label;

    final List<Edge> outEdges;

    final List<Edge> inEdges;

    Vertex(int index, String label) {
      this.index = index;
      this.label = label;
      this.outEdges = new ArrayList<>();
      this.inEdges = new ArrayList<>();
    }

    @Override
    public String toString() {
      return index + "(" + label + ")";
    }
  }

  private static class Edge {

    final int from;

    final int to;

    boolean removed;

    public Edge(int from, int to) {
      this.from = from;
      this.to = to;
    }

    @Override
    public String toString() {
      return from + "->" + to;
    }
  }
}
