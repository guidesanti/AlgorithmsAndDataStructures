package br.com.eventhorizon.common.datastructures.graphs;

import br.com.eventhorizon.common.utils.StringToIntegerArrayConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeightedDirectedGraphCycleTest {

  private static final String WEIGHTED_DIRECTED_GRAPH_CYCLES = "/graphs/weighted-directed-graph-cycles.csv";

  @ParameterizedTest
  @CsvFileSource(resources = WEIGHTED_DIRECTED_GRAPH_CYCLES, numLinesToSkip = 1)
  public void testWeightedDirectedGraphCycle(
      String graphFileName,
      boolean hasCycle,
      @ConvertWith(StringToIntegerArrayConverter.class) int[] cycle) {
    WeightedDirectedGraph graph = GraphUtils.readWeightedDirectedGraphFromCsvFile("src/test/resources/graphs/" + graphFileName);
    WeightedDirectedGraphCycle graphCycle = new WeightedDirectedGraphCycle(graph);
    assertEquals(hasCycle, graphCycle.hasCycle());
    if (hasCycle) {
      Iterator<WeightedDirectedGraph.WeightedDirectEdge> iterator = graphCycle.cycle().iterator();
      int index = 0;
      while (iterator.hasNext()) {
        WeightedDirectedGraph.WeightedDirectEdge edge = iterator.next();
        assertTrue(graph.adjacencies(edge.from()).contains(edge));
        assertEquals(cycle[index++], edge.from());
        assertEquals(cycle[index], edge.to());
      }
    }
  }
}
