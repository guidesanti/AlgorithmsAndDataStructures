package br.com.eventhorizon.graph;

import br.com.eventhorizon.common.datastructures.graphs.GraphUtils;
import br.com.eventhorizon.common.datastructures.graphs.VertexWeightedUndirectedGraph;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxWeightedIndependentSetInTreeTest {

  private static final String DATA = "/graph/max-weighted-independent-set-int-tree.csv";

  @ParameterizedTest
  @CsvFileSource(resources = DATA, numLinesToSkip = 1)
  public void test(String graphFileName, int expectedSize, int expectedWeight) {
    VertexWeightedUndirectedGraph graph = GraphUtils.readVertexWeightedUndirectedGraphFromCsvFile("src/test/resources/graph/" + graphFileName);
    MaxWeightedIndependentSetInTree maxWeightedIndependentSetInTree = new MaxWeightedIndependentSetInTree(graph);
//    assertEquals(expectedSize, maxWeightedIndependentSetInTree.size());
    assertEquals(expectedWeight, maxWeightedIndependentSetInTree.weight());
    // TODO: verify the set elements
  }
}
