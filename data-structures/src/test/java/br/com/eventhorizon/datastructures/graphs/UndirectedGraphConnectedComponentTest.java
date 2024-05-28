package br.com.eventhorizon.datastructures.graphs;

import br.com.eventhorizon.common.utils.converters.StringToIntegerArrayConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UndirectedGraphConnectedComponentTest {

  private static final String UNDIRECTED_GRAPH_COMPONENTS = "/graphs/undirected-graph-components.csv";

  @ParameterizedTest
  @CsvFileSource(resources = UNDIRECTED_GRAPH_COMPONENTS, numLinesToSkip = 1)
  public void testUndirectedGraphComponent(
      String graphFileName,
      int sourceVertex,
      int connectedComponentSize,
      boolean hasCycle,
      @ConvertWith(StringToIntegerArrayConverter.class) int[] vertices) {
    UndirectedGraph graph = GraphUtils.readUndirectedGraphFromCsvFile("src/test/resources/graphs/" + graphFileName);
    UndirectedGraphConnectedComponent connectedComponent = new UndirectedGraphConnectedComponent(graph, sourceVertex);
    assertEquals(connectedComponentSize, connectedComponent.size());
    assertEquals(hasCycle, connectedComponent.hasCycle());
    Set<Integer> verticesNotInConnectedComponent = new HashSet<>();
    for (int i = 0; i < graph.numberOfVertices(); i++) {
      verticesNotInConnectedComponent.add(i);
    }
    Set<Integer> verticesInConnectedComponent = new HashSet<>();
    for (int vertex : vertices) {
      verticesInConnectedComponent.add(vertex);
    }
    verticesNotInConnectedComponent.removeAll(verticesInConnectedComponent);
    for (int vertex : verticesInConnectedComponent) {
      assertTrue(connectedComponent.contains(vertex));
    }
    for (int vertex : verticesNotInConnectedComponent) {
      assertFalse(connectedComponent.contains(vertex));
    }
  }
}
