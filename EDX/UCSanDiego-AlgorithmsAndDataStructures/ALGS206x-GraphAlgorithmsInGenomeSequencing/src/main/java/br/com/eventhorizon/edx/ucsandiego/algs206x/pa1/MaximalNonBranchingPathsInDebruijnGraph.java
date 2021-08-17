package br.com.eventhorizon.edx.ucsandiego.algs206x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;
import java.util.stream.Collectors;

public class MaximalNonBranchingPathsInDebruijnGraph implements PA {

  private static List<String> kMers;

  private static Map<String, List<String>> adjacencies;

  private static Map<String, Degree> inputOutputDegrees;

  private static List<Vertex> paths;

  private static void init() {
    kMers = new ArrayList<>();
    adjacencies = new HashMap<>();
    inputOutputDegrees = new HashMap<>();
    paths = new ArrayList<>();
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in, "\n\r");

    // Read K-mers
    String str = scanner.next();
    while (str != null) {
      kMers.add(str);
      str = scanner.next();
    }
  }

  private static void writeOutput() {
    for (Vertex vertex : paths) {
      Vertex curr = vertex;
      System.out.print(curr.label);
      curr = curr.next;
      while (curr != null) {
        System.out.print(curr.label.charAt(curr.label.length() - 1));
        curr = curr.next;
      }
      System.out.print(" ");
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
    buildDebruijnGraph();
    computeInputAndOutputDegrees();
    findPathsFromBranchingVertices();
    findIsolatedCycles();
  }

  private static void buildDebruijnGraph() {
    for (String kmers : kMers) {
      String from = kmers.substring(0, kmers.length() - 1);
      String to = kmers.substring(1);
      List<String> fromVertexAdjacencies = adjacencies.getOrDefault(from, new ArrayList<>());
      fromVertexAdjacencies.add(to);
      adjacencies.put(from, fromVertexAdjacencies);
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

  private static void findPathsFromBranchingVertices() {
    List<String> branchingVertices = inputOutputDegrees.values().stream()
        .filter(degree -> !degree.isOneToOne())
        .filter(degree -> degree.output > 0)
        .map(degree -> degree.label)
        .collect(Collectors.toList());
    for (String branchingVertex : branchingVertices) {
      List<String> branchingVertexAdjacencies = adjacencies.get(branchingVertex);
      for (String adjacentVertex : branchingVertexAdjacencies) {
        Vertex path = new Vertex(branchingVertex);
        path.next = new Vertex(adjacentVertex);
        Vertex curr = path.next;
        while (inputOutputDegrees.get(curr.label).isOneToOne()) {
          curr.next = new Vertex(adjacencies.get(curr.label).get(0));
          curr = curr.next;
        }
        paths.add(path);
      }
    }
  }

  private static void findIsolatedCycles() {
    List<String> oneToOneVertices = inputOutputDegrees.values().stream()
        .filter(Degree::isOneToOne)
        .sorted(Comparator.comparingInt(o -> o.output))
        .map(degree -> degree.label)
        .collect(Collectors.toList());
    while (!oneToOneVertices.isEmpty()) {
      Vertex currVertex = new Vertex(oneToOneVertices.get(0));
      Vertex cycleStartVertex = currVertex;
      while (!oneToOneVertices.isEmpty()) {
        oneToOneVertices.remove(currVertex.label);
        List<String> currVertexAdjacencies = adjacencies.getOrDefault(currVertex.label, Collections.emptyList());
        if (currVertexAdjacencies.size() != 1) {
          break;
        }
        currVertex.next = new Vertex(currVertexAdjacencies.get(0));
        currVertex = currVertex.next;
        if (currVertex.label.equals(cycleStartVertex.label)) {
          paths.add(cycleStartVertex);
          break;
        }
      }
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

  private static class Degree {

    final String label;

    int input;

    int output;

    Degree(String label) {
      this.label = label;
    }

    boolean isOneToOne() {
      return input == 1 && output == 1;
    }
  }
}
