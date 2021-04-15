package br.com.eventhorizon.common.datastructures.graphs;

import br.com.eventhorizon.common.utils.StringToDoubleConverter;
import br.com.eventhorizon.common.utils.StringToIntegerArrayConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class WeightedDirectedGraphShortestPathTest {

  private static final String WEIGHTED_DIRECTED_GRAPH_SHORTEST_PATHS = "/graphs/weighted-directed-graph-shortest-paths.csv";

  @ParameterizedTest
  @CsvFileSource(resources = WEIGHTED_DIRECTED_GRAPH_SHORTEST_PATHS, numLinesToSkip = 1)
  public void testUndirectedGraphComponent(
      String graphFileName,
      int sourceVertex,
      int destinationVertex,
      @ConvertWith(StringToDoubleConverter.class) double distance,
      @ConvertWith(StringToIntegerArrayConverter.class) int[] path) {
    WeightedDirectedGraph graph = GraphUtils.readWeightedDirectedGraphFromCsvFile("src/test/resources/graphs/" + graphFileName);
    WeightedDirectedGraphShortestPath weightedDirectedGraphShortestPath = new WeightedDirectedGraphShortestPath(graph, sourceVertex);
    if (distance < Double.POSITIVE_INFINITY) {
      assertTrue(weightedDirectedGraphShortestPath.hasPathTo(destinationVertex));
      Iterator<WeightedDirectedGraph.WeightedDirectEdge> iterator = weightedDirectedGraphShortestPath.pathTo(destinationVertex).iterator();
      int index = 0;
      int last = 0;
      double calculatedDistance = 0.0;
      while (iterator.hasNext()) {
        WeightedDirectedGraph.WeightedDirectEdge edge = iterator.next();
        int from = edge.from();
        last = edge.to();
        calculatedDistance += edge.weight();
        assertEquals(path[index++], from);
      }
      assertEquals(path[index], last);
      assertEquals(distance, calculatedDistance);
    } else {
      assertFalse(weightedDirectedGraphShortestPath.hasPathTo(destinationVertex));
      assertNull(weightedDirectedGraphShortestPath.pathTo(destinationVertex));
    }
    assertEquals(distance, weightedDirectedGraphShortestPath.distanceTo(destinationVertex));
  }
}
