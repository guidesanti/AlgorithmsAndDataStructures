package br.com.eventhorizon.datastructures.graphs;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DirectedGraphCycleTest {

  private static final String DIRECTED_GRAPH_CYCLES = "/graphs/directed-graph-cycles.csv";

  @ParameterizedTest
  @CsvFileSource(resources = DIRECTED_GRAPH_CYCLES, numLinesToSkip = 1)
  public void testDirectedGraphCycle(
      String graphFileName,
      boolean hasCycle) {
    DirectedGraph graph = GraphUtils.readDirectedGraphFromCsvFile("src/test/resources/graphs/" + graphFileName);
    DirectedGraphCycle directedGraphCycle = new DirectedGraphCycle(graph);
    assertEquals(hasCycle, directedGraphCycle.hasCycle());
    if (hasCycle) {
      Iterator<Integer> cycle = directedGraphCycle.cycle().iterator();
      int curr = cycle.next();
      int next = cycle.next();
      int first = curr;
      assertTrue(graph.adjacencies(curr).contains(next));
      while (cycle.hasNext()) {
        curr = next;
        next = cycle.next();
        assertTrue(graph.adjacencies(curr).contains(next));
      }
      assertEquals(first, next);
    }
  }
}
