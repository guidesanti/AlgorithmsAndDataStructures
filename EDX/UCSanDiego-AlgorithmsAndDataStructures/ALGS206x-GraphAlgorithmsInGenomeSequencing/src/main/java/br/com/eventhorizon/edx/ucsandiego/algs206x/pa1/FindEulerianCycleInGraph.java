package br.com.eventhorizon.edx.ucsandiego.algs206x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

public class FindEulerianCycleInGraph implements PA {

  private static Map<String, List<String>> adjacencies;

  private static Vertex start;

  private static Vertex end;

  private static void init() {
    adjacencies = new HashMap<>();
    start = null;
    end = null;
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
    start = new Vertex(startVertex);
    start.next = start;
    end = start;
  }

  private static void writeOutput() {
    Vertex curr = start;
    System.out.print(curr.label);
    curr = curr.next;
    while (curr != start) {
      System.out.print("->" + curr.label);
      curr = curr.next;
    }
    System.out.print("->" + curr.label);
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
    findCycle();
    while (!adjacencies.isEmpty()) {
      selectNewCycleStart();
      findCycle();
    }
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
