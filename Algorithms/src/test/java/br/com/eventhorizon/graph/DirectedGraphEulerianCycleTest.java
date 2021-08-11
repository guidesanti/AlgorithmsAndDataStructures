package br.com.eventhorizon.graph;

import br.com.eventhorizon.common.datastructures.LinkedList;
import br.com.eventhorizon.common.datastructures.graphs.DirectedGraph;
import br.com.eventhorizon.common.datastructures.graphs.GraphUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectedGraphEulerianCycleTest {

  private static final String DATA = "/graph/directed-graph-eulerian-cycle.csv";

  @ParameterizedTest
  @CsvFileSource(resources = DATA, numLinesToSkip = 1)
  public void test(String graphFileName) {
    DirectedGraph graph = GraphUtils.readDirectedGraphFromCsvFile("src/test/resources/graph/" + graphFileName);
    Map<Integer, List<Integer>> adjacendies = new HashMap<>();
    for (int i = 0; i < graph.numberOfVertices(); i++) {
      List<Integer> vertexAdj = new ArrayList<>();
      LinkedList<Integer> vertexAdjTemp = graph.adjacencies(i);
      for (int j = 0; j < vertexAdjTemp.size(); j++) {
        vertexAdj.add(vertexAdjTemp.get(j));
      }
      adjacendies.put(i, vertexAdj);
    }

    DirectedGraphEulerianCycle directedGraphEulerianCycle = new DirectedGraphEulerianCycle();
    List<Integer> cycle = directedGraphEulerianCycle.findEulerianCycle(graph);

    assertEquals(graph.numberOfEdges(), cycle.size() - 1);
    int prev = cycle.get(0);
    for (int i = 1; i < cycle.size(); i++) {
      int curr = cycle.get(i);
      List<Integer> prevAdjacencies = adjacendies.get(prev);
      prevAdjacencies.remove(Integer.valueOf(curr));
      if (prevAdjacencies.isEmpty()) {
        adjacendies.remove(prev);
      }
      prev = curr;
    }
    assertEquals(0, adjacendies.size());
  }
}
