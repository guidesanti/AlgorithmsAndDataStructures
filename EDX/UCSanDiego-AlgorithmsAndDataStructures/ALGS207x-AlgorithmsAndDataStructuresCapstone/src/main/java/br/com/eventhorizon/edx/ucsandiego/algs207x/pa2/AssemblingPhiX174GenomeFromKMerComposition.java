package br.com.eventhorizon.edx.ucsandiego.algs207x.pa2;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssemblingPhiX174GenomeFromKMerComposition implements PA {

  private static int k;

  private static List<String> kMers;

  private static Map<String, List<String>> adjacencies;

  private static Map<String, Degree> inputOutputDegrees;

  private static Vertex cycleStart;

  private static Vertex cycleEnd;

  private static Vertex pathStart;

  private static String source;

  private static String sink;

  private static void init() {
    k = 0;
    kMers = new ArrayList<>();
    adjacencies = new HashMap<>();
    inputOutputDegrees = new HashMap<>();
    cycleStart = null;
    cycleEnd = null;
    pathStart = null;
    source = null;
    sink = null;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read K-mers
    String str = scanner.next();
    while (str != null) {
      kMers.add(str);
      str = scanner.next();
    }

    // Initialize k
    k = kMers.get(0).length();
  }

  private static void writeOutput() {
    StringBuilder text = new StringBuilder();
    Vertex curr = pathStart;
    text.append(curr.label);
    curr = curr.next;
    while (curr != pathStart) {
      text.append(curr.label.charAt(curr.label.length() - 1));
      curr = curr.next;
    }
    int maxI = 0;
    for (int i = 1; i <= k; i++) {
      if (text.substring(0, i).equals(text.substring(text.length() - i))) {
        maxI = i;
      }
    }
    text.delete(text.length() - maxI, text.length());
    System.out.print(text.toString());
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
    computeInputAndOutputDegrees();
    findSourceAndSink();
    createEulerianCycle();
    String startVertex = adjacencies.keySet().stream().findAny().get();
    cycleStart = new Vertex(startVertex);
    cycleStart.next = cycleStart;
    cycleEnd = cycleStart;
    findCycle();
    while (!adjacencies.isEmpty()) {
      selectNewCycleStart();
      findCycle();
    }
  }

  private static void buildDeBruijnGraph() {
    for (String kmers : kMers) {
      String from = kmers.substring(0, k - 1);
      String to = kmers.substring(1);
      List<String> adj = adjacencies.getOrDefault(from, new ArrayList<>());
      adj.add(to);
      adjacencies.put(from, adj);
    }
  }

  private static void computeInputAndOutputDegrees() {
    adjacencies.forEach((from, tos) -> {
      // Output degree
      Degree degree = inputOutputDegrees.getOrDefault(from, new Degree(from));
      degree.output += tos.size();
      inputOutputDegrees.put(from, degree);
      // Input degree
      for (String to : tos) {
        degree = inputOutputDegrees.getOrDefault(to, new Degree(to));
        degree.input++;
        inputOutputDegrees.put(to, degree);
      }
    });
  }

  private static void findSourceAndSink() {
    for (Degree degree : inputOutputDegrees.values()) {
      if (degree.output == degree.input) {
        continue;
      }
      if (degree.output > degree.input) {
        if (source != null) {
          throw new RuntimeException("More than one source detected!");
        }
        source = degree.label;
      }
      if (degree.output < degree.input) {
        if (sink != null) {
          throw new RuntimeException("More than one sink detected!");
        }
        sink = degree.label;
      }
      if (source != null && sink != null) {
        break;
      }
    }
    if (source == null && sink == null) {
      source = adjacencies.keySet().stream().findAny().get();
      sink = source;
    }
  }

  private static void createEulerianCycle() {
    List<String> sinkAdjacencies = adjacencies.getOrDefault(sink, new ArrayList<>());
    if (source.equals(sink)) {
      sinkAdjacencies.add(source);
    } else {
      Degree sourceDegree = inputOutputDegrees.get(source);
      Degree sinkDegree = inputOutputDegrees.get(sink);
      while (sourceDegree.input != sourceDegree.output && sinkDegree.input != sinkDegree.output) {
        sinkAdjacencies.add(source);
        sourceDegree.input++;
        sinkDegree.output++;
      }
    }
    adjacencies.put(sink, sinkAdjacencies);
  }

  private static void findCycle() {
    Vertex curr = cycleEnd;
    while (true) {
      List<String> cycleEndAdjacencies = adjacencies.get(curr.label);
      String nextLabel = cycleEndAdjacencies.remove(0);
      if (cycleEndAdjacencies.isEmpty()) {
        adjacencies.remove(curr.label);
      }
      if (nextLabel.equals(cycleStart.label)) {
        if (curr.label.equals(sink) && curr.next.label.equals(source)) {
          pathStart = curr.next;
        }
        break;
      }
      curr.next = new Vertex(nextLabel);
      if (curr.label.equals(sink) && curr.next.label.equals(source)) {
        pathStart = curr.next;
      }
      curr = curr.next;
      curr.next = cycleStart;
    }
  }

  private static void selectNewCycleStart() {
    while (!adjacencies.containsKey(cycleStart.label)) {
      cycleStart = cycleStart.next;
    }
    Vertex newStart = new Vertex(cycleStart.label);
    newStart.next = cycleStart.next;
    cycleEnd = cycleStart;
    cycleEnd.next = newStart;
    cycleStart = newStart;
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

  private static class Degree {

    final String label;

    int input;

    int output;

    public Degree(String label) {
      this.label = label;
    }
  }
}
