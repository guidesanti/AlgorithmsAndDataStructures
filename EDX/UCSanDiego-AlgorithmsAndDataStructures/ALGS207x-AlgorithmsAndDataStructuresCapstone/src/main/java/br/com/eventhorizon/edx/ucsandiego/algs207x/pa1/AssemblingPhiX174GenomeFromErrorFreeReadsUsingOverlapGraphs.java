package br.com.eventhorizon.edx.ucsandiego.algs207x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

public class AssemblingPhiX174GenomeFromErrorFreeReadsUsingOverlapGraphs implements PA {

  private static List<String> reads;

  private static List<List<Edge>> adjacencies;

  private static List<Edge> path;

  private static void init() {
    reads = new ArrayList<>();
    adjacencies = new ArrayList<>();
    path = new ArrayList<>();
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read reads
    String str = scanner.next();
    while (str != null) {
      reads.add(str);
      str = scanner.next();
    }
  }

  private static void writeOutput() {
    StringBuilder string = new StringBuilder();
    string.append(reads.get(path.get(0).from));
    for (Edge edge : path) {
      String label = reads.get(edge.to);
      string.append(label, edge.weight, label.length());
    }
    for (int i = reads.get(0).length(); i > 1; i--) {
      if (string.substring(0, i).equals(string.substring(string.length() - i))) {
        string.delete(string.length() - i, string.length());
        break;
      }
    }
    System.out.println(string.toString());
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    finalSolutionImpl();
    writeOutput();
  }

  private static void finalSolutionImpl() {
    buildOverlapGraph();
    findHamiltonianPath();
  }

  private static void buildOverlapGraph() {
    for (int i = 0; i < reads.size(); i++) {
      String read1 = reads.get(i);
      List<Edge> read1Adjacencies = new ArrayList<>();
      for (int j = 0; j < reads.size(); j++) {
        if (i == j) {
          continue;
        }
        String read2 = reads.get(j);
        int weight = findMaximumOverlap(read1, read2);
        if (weight > 0) {
          read1Adjacencies.add(new Edge(i, j, weight));
        }
      }
      adjacencies.add(read1Adjacencies);
    }
  }

  private static void findHamiltonianPath() {
    boolean[] marked = new boolean[adjacencies.size()];
    adjacencies.forEach(edges -> edges.sort(Comparator.comparingInt(o -> o.weight)));
    marked[0] = true;
    findHamiltonianPathRecursive(0, marked);
  }

  private static boolean findHamiltonianPathRecursive(int curr, boolean[] marked) {
    if (isHamiltonianPath(marked)) {
      return true;
    }
    List<Edge> edges = adjacencies.get(curr);
    for (int i = edges.size() - 1; i >= 0; i--) {
      Edge edge = edges.get(i);
      if (marked[edge.to]) {
        continue;
      }
      marked[edge.to] = true;
      path.add(edge);
      if (findHamiltonianPathRecursive(edge.to, marked)) {
        return true;
      }
      marked[edge.to] = false;
      path.remove(path.size() - 1);
    }
    return false;
  }

  private static boolean isHamiltonianPath(boolean[] marked) {
    for (boolean b : marked) {
      if (!b) {
        return false;
      }
    }
    return true;
  }

  private static int findMaximumOverlap(String read1, String read2) {
    int overlapLength = 0;
    for (int i = 1; i <= read1.length(); i++) {
      if (compare(read1, read2, read1.length() - i)) {
        overlapLength = i;
      }
    }
    return overlapLength;
  }

  private static boolean compare(String string1, String string2, int start) {
    int index1 = start;
    int index2 = 0;
    while (index1 < string1.length()) {
      if (string1.charAt(index1) != string2.charAt(index2)) {
        return false;
      }
      index1++;
      index2++;
    }
    return true;
  }

  private static class Edge {

    final int from;

    final int to;

    final int weight;

    Edge(int from, int to, int weight) {
      this.from = from;
      this.to = to;
      this.weight = weight;
    }

    @Override
    public String toString() {
      return from + "->" + to + ":" + weight;
    }
  }
}
