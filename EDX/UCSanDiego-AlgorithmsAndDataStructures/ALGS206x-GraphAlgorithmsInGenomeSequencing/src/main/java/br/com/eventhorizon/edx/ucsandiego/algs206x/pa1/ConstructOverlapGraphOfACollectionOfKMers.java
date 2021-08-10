package br.com.eventhorizon.edx.ucsandiego.algs206x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstructOverlapGraphOfACollectionOfKMers implements PA {

  private static List<String> kMers;

  private static Map<String, Edge> adjacencies;

  private static void init() {
    kMers = new ArrayList<>();
    adjacencies = new HashMap<>();
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read K-mers
    String str = scanner.next();
    while (str != null) {
      kMers.add(str);
      str = scanner.next();
    }
  }

  private static void postReadInputInit() {
    // Do nothing
  }

  private static void writeOutput() {
    for (Edge edge : adjacencies.values()) {
      System.out.print(edge.from + "->" + edge.to.get(0));
      for (int i = 1; i < edge.to.size(); i++) {
        System.out.print("," + edge.to.get(i));
      }
      System.out.println();
    }
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    postReadInputInit();
    finalSolutionImpl();
    writeOutput();
  }

  private static void finalSolutionImpl() {
    for (int i = 0; i < kMers.size(); i++) {
      String str1 = kMers.get(i);
      Edge edge = adjacencies.getOrDefault(str1, new Edge(str1));
      for (String str2 : kMers) {
        if (compareSuffixAndPrefix(str1, str2)) {
          if (!edge.to.contains(str2)) {
            edge.to.add(str2);
            adjacencies.put(str1, edge);
          }
        }
      }
    }
  }

  private static boolean compareSuffixAndPrefix(String str1, String str2) {
    int index = 0;
    while (index < str1.length() - 1) {
      if (str1.charAt(index + 1) != str2.charAt(index)) {
        return false;
      }
      index++;
    }
    return true;
  }

  private static class Edge {

    final String from;

    final List<String> to;

    public Edge(String from) {
      this.from = from;
      this.to = new ArrayList<>();
    }

    @Override
    public String toString() {
      return from + "->" + to;
    }
  }
}
