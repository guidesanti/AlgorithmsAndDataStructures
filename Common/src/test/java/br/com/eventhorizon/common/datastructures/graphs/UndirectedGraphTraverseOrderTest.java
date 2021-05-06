package br.com.eventhorizon.common.datastructures.graphs;

import br.com.eventhorizon.common.utils.StringToIntegerArrayConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class UndirectedGraphTraverseOrderTest {

  private static final String UNDIRECTED_GRAPH_TRAVERSE_ORDER = "/graphs/undirected-graph-traverse-order.csv";

  @ParameterizedTest
  @CsvFileSource(resources = UNDIRECTED_GRAPH_TRAVERSE_ORDER, numLinesToSkip = 1)
  public void testUndirectedGraphComponent(
      String graphFileName,
      UndirectedGraphTraverseOrder.Type type,
      @ConvertWith(StringToIntegerArrayConverter.class) int[] vertices) {
    UndirectedGraph graph = GraphUtils.readUndirectedGraphFromCsvFile("src/test/resources/graphs/" + graphFileName);
    Iterator<Integer> iterator = UndirectedGraphTraverseOrder.order(graph, type).iterator();
    for (int vertex : vertices) {
      assertTrue(iterator.hasNext());
      assertEquals(vertex, iterator.next());
    }
    assertFalse(iterator.hasNext());
  }
}
