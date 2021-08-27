package br.com.eventhorizon.edx.ucsandiego.algs207x.pa2;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

public class FindingEulerianCycleInDirectedGraph implements PA {

  private static int verticesCount;

  private static int edgesCount;

  private static Map<Integer, List<Integer>> adjacencies;

  private static Vertex start;

  private static Vertex end;

  private static Map<Integer, Degree> inputOutputDegrees;

  private static boolean hasEulerianCycle;

  private static void init() {
    verticesCount = 0;
    edgesCount = 0;
    adjacencies = new HashMap<>();
    start = null;
    end = null;
    inputOutputDegrees = new HashMap<>();
    hasEulerianCycle = true;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read number of vertices and number of edges
    verticesCount = scanner.nextInt();
    edgesCount = scanner.nextInt();
    
    // Read edges
    for (int i = 0; i < edgesCount; i++) {
      int from = scanner.nextInt();
      int to = scanner.nextInt();
      List<Integer> adj = adjacencies.getOrDefault(from, new ArrayList<>());
      adj.add(to);
      adjacencies.put(from, adj);
    }

    // Initialize start vertex
    int startVertex = adjacencies.keySet().stream().findAny().get();
    start = new Vertex(startVertex);
    start.next = start;
    end = start;
  }

  private static void writeOutput() {
    if (hasEulerianCycle) {
      System.out.println(1);
      Vertex curr = start;
      System.out.print(curr.label);
      curr = curr.next;
      while (curr != start) {
        System.out.print(" " + curr.label);
        curr = curr.next;
      }
      // We will not print last vertex in this case, since it is the start vertex
      // System.out.print(" " + curr.label);
    } else {
      System.out.println(0);
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
    computeInputAndOutputDegrees();
    verifyEulerianCycle();
    if (!hasEulerianCycle) {
      return;
    }
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
      for (Integer to : tos) {
        degree = inputOutputDegrees.getOrDefault(to, new Degree(to));
        degree.input++;
        inputOutputDegrees.put(to, degree);
      }
    });
  }

  private static void findCycle() {
    Vertex curr = end;
    while (true) {
      List<Integer> cycleEndAdjacencies = adjacencies.get(curr.label);
      Integer next = cycleEndAdjacencies.remove(0);
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

  private static void verifyEulerianCycle() {
    for (Degree degree : inputOutputDegrees.values()) {
      if (degree.input != degree.output) {
        hasEulerianCycle = false;
        break;
      }
    }
  }

  private static class Vertex {

    final int label;

    Vertex next;

    public Vertex(int label) {
      this.label = label;
    }

    @Override
    public String toString() {
      return "" + label;
    }
  }

  private static class Degree {

    final int label;

    int input;

    int output;

    public Degree(int label) {
      this.label = label;
    }
  }
}
