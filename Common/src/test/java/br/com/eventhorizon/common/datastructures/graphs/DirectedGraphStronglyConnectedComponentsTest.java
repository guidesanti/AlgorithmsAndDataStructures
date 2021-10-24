package br.com.eventhorizon.common.datastructures.graphs;

import br.com.eventhorizon.common.utils.converters.StringToListOfListOfIntegers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DirectedGraphStronglyConnectedComponentsTest {

  private static final String DIRECT_GRAPH_STRONGLY_CONNECTED_COMPONENTS = "/graphs/directed-graph-strongly-connected-components.csv";

  @ParameterizedTest
  @CsvFileSource(resources = DIRECT_GRAPH_STRONGLY_CONNECTED_COMPONENTS, numLinesToSkip = 1)
  public void testUndirectedGraphComponent(
      String graphFileName,
      @ConvertWith(StringToListOfListOfIntegers.class)List<List<Integer>> components) {
    DirectedGraph graph = GraphUtils.readDirectedGraphFromCsvFile("src/test/resources/graphs/" + graphFileName);
    DirectedGraphStronglyConnectedComponents stronglyConnectedComponents = new DirectedGraphStronglyConnectedComponents(graph);
    assertEquals(components.size(), stronglyConnectedComponents.count());
    components.forEach(vertices -> {
      // Verify the id
      int id = stronglyConnectedComponents.id(vertices.get(0));
      vertices.forEach(vertex -> {
        assertEquals(id, stronglyConnectedComponents.id(vertex));
      });
      // verify the connection
      for (int i = 0; i < vertices.size(); i++) {
        for (int j = i + 1; j < vertices.size(); j++) {
          int vertex1 = vertices.get(i);
          int vertex2 = vertices.get(j);
          assertTrue(stronglyConnectedComponents.areStronglyConnected(vertex1, vertex2));
        }
      }
    });
  }
}
