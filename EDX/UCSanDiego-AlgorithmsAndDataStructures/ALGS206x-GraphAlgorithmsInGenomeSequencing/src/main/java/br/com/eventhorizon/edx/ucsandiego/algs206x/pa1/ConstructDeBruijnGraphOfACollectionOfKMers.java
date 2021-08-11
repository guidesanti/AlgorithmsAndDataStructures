package br.com.eventhorizon.edx.ucsandiego.algs206x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstructDeBruijnGraphOfACollectionOfKMers implements PA {

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
    for (String kmers : kMers) {
      String from = kmers.substring(0, kmers.length() - 1);
      String to = kmers.substring(1);
      Edge edge = adjacencies.getOrDefault(from, new Edge(from));
      edge.to.add(to);
      adjacencies.put(from, edge);
    }
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
