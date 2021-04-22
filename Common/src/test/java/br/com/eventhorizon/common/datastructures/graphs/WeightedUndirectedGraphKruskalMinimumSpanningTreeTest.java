package br.com.eventhorizon.common.datastructures.graphs;

import br.com.eventhorizon.common.utils.StringToSetOfWeightedUndirectedEdges;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WeightedUndirectedGraphKruskalMinimumSpanningTreeTest {

  private static final String WEIGHTED_UNDIRECTED_GRAPH_PRIM = "/graphs/weighted-undirected-graph-prim.csv";

  @ParameterizedTest
  @CsvFileSource(resources = WEIGHTED_UNDIRECTED_GRAPH_PRIM, numLinesToSkip = 1)
  public void testKruskalMinimumSpanningTree(
      String graphFileName,
      double weight,
      @ConvertWith(StringToSetOfWeightedUndirectedEdges.class) Set<WeightedUndirectedEdge> edges) {
    WeightedUndirectedGraph graph = GraphUtils.readWeightedUndirectedGraphFromCsvFile("src/test/resources/graphs/" + graphFileName);
    WeightedUndirectedGraphKruskalMinimumSpanningTree mst = new WeightedUndirectedGraphKruskalMinimumSpanningTree(graph);
    assertEquals(weight, mst.weight());
    assertEquals(graph.numberOfVertices() - 1, edges.size());
    for (WeightedUndirectedEdge edge : mst.edges()) {
      assertTrue(edges.contains(edge));
    }
  }
}
