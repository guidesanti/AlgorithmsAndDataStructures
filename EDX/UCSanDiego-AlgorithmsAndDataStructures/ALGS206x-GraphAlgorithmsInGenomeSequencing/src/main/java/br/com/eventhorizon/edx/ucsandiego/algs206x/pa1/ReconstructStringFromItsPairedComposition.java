package br.com.eventhorizon.edx.ucsandiego.algs206x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReconstructStringFromItsPairedComposition implements PA {

  private static int k;

  private static int d;

  private static boolean a;

  private static List<String> pairedKMers;

  private static Map<String, List<String>> adjacencies;

  private static Map<String, Degree> inputOutputDegrees;

  private static Vertex cycleStart;

  private static Vertex cycleEnd;

  private static Vertex pathStart;

  private static String source;

  private static String sink;

  private static void init() {
    k = 0;
    d = 0;
    a = false;
    pairedKMers = new ArrayList<>();
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

    // Read k
    k = scanner.nextInt();

    // Read d
    d = scanner.nextInt();

    // Read paired K-mers
    String str = scanner.next();
    while (str != null) {
      pairedKMers.add(str);
      str = scanner.next();
    }
  }

  private static void writeOutput() {
    StringBuilder part1;
    StringBuilder part2;
    do {
      Vertex curr = pathStart;
      part1 = new StringBuilder();
      part2 = new StringBuilder();
      part1.append(curr.label, 0, k - 1);
      part2.append(curr.label, k - 1, curr.label.length());
      curr = curr.next;
      while (curr != pathStart) {
        part1.append(curr.label.charAt(k - 2));
        part2.append(curr.label.charAt(curr.label.length() - 1));
        curr = curr.next;
      }
      if (a) {
        part1.append(curr.label.charAt(k - 2));
        part2.append(curr.label.charAt(curr.label.length() - 1));
      }
      pathStart = pathStart.next;
    } while (!compatible(part1, part2));
    part1.replace(k + d, part1.length(), part2.toString());
    System.out.print(part1.toString());
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
    for (String kmers : pairedKMers) {
      String[] pair = kmers.split("\\|");
      String from = pair[0].substring(0, k - 1) + pair[1].substring(0, k - 1);
      String to = pair[0].substring(1) + pair[1].substring(1);
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
      sink = adjacencies.keySet().stream().findAny().get();
      source = adjacencies.get(sink).get(0);
      a = true;
    }
  }

  private static void createEulerianCycle() {
    List<String> sinkAdjacencies = adjacencies.getOrDefault(sink, new ArrayList<>());
    Degree sourceDegree = inputOutputDegrees.get(source);
    Degree sinkDegree = inputOutputDegrees.get(sink);
    while (sourceDegree.input != sourceDegree.output && sinkDegree.input != sinkDegree.output) {
      sinkAdjacencies.add(source);
      sourceDegree.input++;
      sinkDegree.output++;
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

  private static boolean compatible(StringBuilder part1, StringBuilder part2) {
    String a1 = part1.substring(k + d);
    String a2 = part2.substring(0, part1.length() - (k + d));
    return a1.equals(a2);
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
