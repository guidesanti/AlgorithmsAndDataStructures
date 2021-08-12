package br.com.eventhorizon.edx.ucsandiego.algs206x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

public class FindEulerianPathInGraph implements PA {

  private static Map<String, List<String>> adjacencies;

  private static Map<String, Degree> inputOutputDegrees;

  private static Vertex cycleStart;

  private static Vertex cycleEnd;

  private static Vertex pathStart;

  private static String source;

  private static String sink;

  private static void init() {
    adjacencies = new HashMap<>();
    inputOutputDegrees = new HashMap<>();
    cycleStart = null;
    cycleEnd = null;
    pathStart = null;
    source = null;
    sink = null;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in, "\n\r");

    // Read graph
    String line = scanner.next();
    while (line != null) {
      line = line.trim().replace(" ", "");
      String[] values = line.split("->");
      String from = values[0];
      List<String> adj = adjacencies.getOrDefault(from, new ArrayList<>());
      values = values[1].split(",");
      Collections.addAll(adj, values);
      adjacencies.put(from, adj);
      line = scanner.next();
    }
  }

  private static void postReadInputInit() {
    String startVertex = adjacencies.keySet().stream().findAny().get();
    cycleStart = new Vertex(startVertex);
    cycleStart.next = cycleStart;
    cycleEnd = cycleStart;
  }

  private static void writeOutput() {
    Vertex curr = pathStart;
    System.out.print(curr.label);
    curr = curr.next;
    while (curr != pathStart) {
      System.out.print("->" + curr.label);
      curr = curr.next;
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
    computeInputAndOutputDegrees();
    findSourceAndSink();
    createEulerianCycle();
    findCycle();
    while (!adjacencies.isEmpty()) {
      selectNewCycleStart();
      findCycle();
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
  }

  private static void createEulerianCycle() {
    Degree sourceDegree = inputOutputDegrees.get(source);
    Degree sinkDegree = inputOutputDegrees.get(sink);
    List<String> sinkAdjacencies = adjacencies.getOrDefault(sink, new ArrayList<>());
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
