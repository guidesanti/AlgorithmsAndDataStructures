package br.com.eventhorizon.edx.ucsandiego.algs206x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

public class FindKUniversalCircularString implements PA {

  static private int k;

  private static Map<String, List<String>> adjacencies;

  private static Vertex start;

  private static Vertex end;

  private static void init() {
    k = 0;
    adjacencies = new HashMap<>();
    start = null;
    end = null;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in, "\n\r");

    // Read k
    k = scanner.nextInt();
  }

  private static void writeOutput() {
    Vertex curr = start;
    int count = (int) Math.pow(2, k) - k + 1;
    System.out.print(curr.label);
    curr = curr.next;
    while (curr != start && count > 0) {
      System.out.print(curr.label.charAt(curr.label.length() - 1));
      curr = curr.next;
      count--;
    }
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
    initializeStartVertex();
    findCycle();
    while (!adjacencies.isEmpty()) {
      selectNewCycleStart();
      findCycle();
    }
  }

  private static void buildDeBruijnGraph() {
    char[] kmer = new char[k];
    Arrays.fill(kmer, '0');
    int count = (int) Math.pow(2, k);
    for (int i = 0; i < count; i++) {
      String kmerString = new String(kmer);
      String from = kmerString.substring(0, k - 1);
      String to = kmerString.substring(1);
      List<String> adj = adjacencies.getOrDefault(from, new ArrayList<>());
      adj.add(to);
      adjacencies.put(from, adj);
      nextKMer(kmer);
    }
  }

  private static void initializeStartVertex() {
    String startVertex = adjacencies.keySet().stream().findAny().get();
    start = new Vertex(startVertex);
    start.next = start;
    end = start;
  }

  private static void findCycle() {
    Vertex curr = end;
    while (true) {
      List<String> cycleEndAdjacencies = adjacencies.get(curr.label);
      String next = cycleEndAdjacencies.remove(0);
      if (cycleEndAdjacencies.isEmpty()) {
        adjacencies.remove(curr.label);
      }
      if (next.equals(start.label)) {
        curr.next = start;
        break;
      }
      curr.next = new Vertex(next);
      curr = curr.next;
    }
  }

  private static void selectNewCycleStart() {
    while (!adjacencies.containsKey(start.label)) {
      start = start.next;
    }
    Vertex newCycle = new Vertex(start.label);
    newCycle.next = start.next;
    end = start;
    start = newCycle;
  }

  private static void nextKMer(char[] kmer) {
    int index = kmer.length - 1;
    while (index >= 0 && kmer[index] == '1') {
      kmer[index] = '0';
      index--;
    }
    if (index >= 0) {
      kmer[index] = '1';
    }
  }

  private static class Vertex {

    final String label;

    Vertex next;

    public Vertex(String label) {
      this.label = label;
    }

    @Override
    public String toString() {
      return label;
    }
  }
}
