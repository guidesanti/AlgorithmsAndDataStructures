package br.com.eventhorizon.graph;

import br.com.eventhorizon.datastructures.LinkedList;
import br.com.eventhorizon.datastructures.graphs.DirectedGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This graph processing class finds an Eulerian cycle within a directed graph.
 * Eulerian directed graphs are those who are strongly connected and balanced.
 * Balanced means that every vertex within the graph has input degree equals to output degree.
 * This graph processing class assumes the input graph is an Eulerian directed graph and
 * it will not check if the graph is or not Eulerian, which can return an unpredictable result if
 * a non Eulerian graph is passed as an argument.
 */
public class DirectedGraphEulerianCycle {

  private Vertex start;

  private Vertex end;

  private Map<Integer, List<Integer>> adjacendies;

  public List<Integer> findEulerianCycle(DirectedGraph graph) {
    init(graph);
    findCycle();
    while (!adjacendies.isEmpty()) {
      selectNewCycleStart();
      findCycle();
    }
    return buildCycle();
  }

  private void init(DirectedGraph graph) {
    start = new Vertex(0);
    start.next = start;
    end = start;
    adjacendies = new HashMap<>();
    for (int i = 0; i < graph.numberOfVertices(); i++) {
      List<Integer> vertexAdj = new ArrayList<>();
      LinkedList<Integer> vertexAdjTemp = graph.adjacencies(i);
      for (int j = 0; j < vertexAdjTemp.size(); j++) {
        vertexAdj.add(vertexAdjTemp.get(j));
      }
      adjacendies.put(i, vertexAdj);
    }
  }

  private void findCycle() {
    Vertex oldStart = start;
    Vertex curr = end;
    while (true) {
      List<Integer> cycleEndAdjacencies = adjacendies.get(curr.number);
      int next = cycleEndAdjacencies.remove(0);
      if (cycleEndAdjacencies.isEmpty()) {
        adjacendies.remove(curr.number);
      } else {
        start = curr;
      }
      if (next == oldStart.number) {
        curr.next = oldStart;
        end = start;
        break;
      }
      curr.next = new Vertex(next);
      curr = curr.next;
    }
  }

  private void selectNewCycleStart() {
    Vertex newCycle = new Vertex(start.number);
    newCycle.next = start.next;
    start = newCycle;
  }

  private List<Integer> buildCycle() {
    List<Integer> cycle = new ArrayList<>();
    Vertex curr = start;
    do {
      cycle.add(curr.number);
      curr = curr.next;
    } while (curr != start);
    cycle.add(start.number);
    return cycle;
  }

  private static class Vertex {

    final int number;

    Vertex next;

    public Vertex(int number) {
      this.number = number;
    }

    @Override
    public String toString() {
      return "" + number;
    }
  }
}
