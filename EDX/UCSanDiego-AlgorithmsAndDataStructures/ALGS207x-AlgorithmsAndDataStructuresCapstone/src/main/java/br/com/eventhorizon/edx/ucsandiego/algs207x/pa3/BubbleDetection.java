package br.com.eventhorizon.edx.ucsandiego.algs207x.pa3;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

public class BubbleDetection implements PA {

  private static int k;

  private static int t;

  private static List<String> reads;

  private static Vertex[] graph;

  private static Map<String, Integer> count;

  private static int readLength;

  private static int bubbleCount;

  private static void init() {
    k = 0;
    t = 0;
    reads = new ArrayList<>();
    graph = null;
    count = new HashMap<>();
    readLength = 0;
    bubbleCount = 0;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    k = scanner.nextInt();
    t = scanner.nextInt();

    String kmer = scanner.next();
    while (kmer != null) {
      reads.add(kmer);
      kmer = scanner.next();
    }
    readLength = reads.get(0).length();
  }

  private static void writeOutput() {
    System.out.print("" + bubbleCount);
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
    findBubbles();
  }

  private static void buildDeBruijnGraph() {
    int index = 0;
    Map<String, Integer> indices = new HashMap<>();
    List<Edge> edges = new ArrayList<>();
    for (String read : reads) {
      for (int i = 0; i <= readLength - k; i++) {
        String kmer = read.substring(i, i + k);

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
      graph[edge.from].outEdges2.add(edge);
      graph[edge.to].inEdges2.add(edge);
    });
  }

  private static void findBubbles() {
    for (Vertex vertex : graph) {
      if (vertex.outEdges2.size() < 2) {
        continue;
      }
      forward(vertex.index, vertex.index, 0, new boolean[graph.length]);
    }
    bubbleCount /= 2;
  }

  private static void forward(int start, int curr, int count, boolean[] visited) {
    count++;
    visited[curr] = true;

    if (count - 1 > t) {
      visited[curr] = false;
      return;
    }

    for (Edge edge : graph[curr].outEdges2) {
      if (edge.blocked || visited[edge.to]) {
        continue;
      }
      edge.blocked = true;
      forward(start, edge.to, count, visited);
      edge.blocked = false;
    }

    if (curr != start) {
      backward(start, curr, 0, visited);
    }

    visited[curr] = false;
  }

  private static void backward(int start, int curr, int count, boolean[] visited) {
    count++;
    visited[curr] = true;

    if (count - 1 > t) {
      visited[curr] = false;
      return;
    }

    for (Edge edge : graph[curr].inEdges2) {
      if (edge.blocked) {
        continue;
      }
      if (edge.from == start) {
        bubbleCount++;
      }
      if (visited[edge.from]) {
        continue;
      }
      edge.blocked = true;
      backward(start, edge.from, count, visited);
      edge.blocked = false;
    }
    visited[curr] = false;
  }

  private static class Vertex {

    final int index;

    final String label;

    final List<Edge> outEdges2;

    final List<Edge> inEdges2;

    Vertex(int index, String label) {
      this.index = index;
      this.label = label;
      this.outEdges2 = new ArrayList<>();
      this.inEdges2 = new ArrayList<>();
    }

    @Override
    public String toString() {
      return index + "(" + label + ")";
    }
  }

  private static class Edge {

    final int from;

    final int to;

    boolean blocked;

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
