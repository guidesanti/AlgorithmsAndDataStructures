package br.com.eventhorizon.common.datastructures.graphs;

import br.com.eventhorizon.common.utils.StringToDoubleConverter;
import br.com.eventhorizon.common.utils.StringToIntegerArrayConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class WeightedDirectedGraphBellmanFordShortestPathTest {

  private static final String WEIGHTED_DIRECTED_GRAPH_SHORTEST_PATHS = "/graphs/weighted-directed-graph-bellman-ford-shortest-paths.csv";

  private static final String WEIGHTED_DIRECTED_GRAPH_NEGATIVE_CYCLES = "/graphs/weighted-directed-graph-bellman-ford-negative-cycles.csv";

  @ParameterizedTest
  @CsvFileSource(resources = WEIGHTED_DIRECTED_GRAPH_SHORTEST_PATHS, numLinesToSkip = 1)
  public void testUndirectedGraphComponent(
      String graphFileName,
      int sourceVertex,
      int destinationVertex,
      @ConvertWith(StringToDoubleConverter.class) double distance,
      @ConvertWith(StringToIntegerArrayConverter.class) int[] path) {
    WeightedDirectedGraph graph = GraphUtils.readWeightedDirectedGraphFromCsvFile("src/test/resources/graphs/" + graphFileName);
    WeightedDirectedGraphBellmanFordShortestPath shortestPath = new WeightedDirectedGraphBellmanFordShortestPath(graph, sourceVertex);
    if (distance < Double.POSITIVE_INFINITY) {
      assertTrue(shortestPath.hasPathTo(destinationVertex));
      Iterator<WeightedDirectedGraph.WeightedDirectEdge> iterator = shortestPath.pathTo(destinationVertex).iterator();
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
      assertFalse(shortestPath.hasPathTo(destinationVertex));
      assertNull(shortestPath.pathTo(destinationVertex));
    }
    assertEquals(distance, shortestPath.distanceTo(destinationVertex));
  }

  @ParameterizedTest
  @CsvFileSource(resources = WEIGHTED_DIRECTED_GRAPH_NEGATIVE_CYCLES, numLinesToSkip = 1)
  public void testUndirectedGraphComponentNegativeCycles(
      String graphFileName,
      int sourceVertex,
      boolean hasNegativeCycle,
      @ConvertWith(StringToIntegerArrayConverter.class) int[] cycle) {
    WeightedDirectedGraph graph = GraphUtils.readWeightedDirectedGraphFromCsvFile("src/test/resources/graphs/" + graphFileName);
    WeightedDirectedGraphBellmanFordShortestPath shortestPath = new WeightedDirectedGraphBellmanFordShortestPath(graph, sourceVertex);
    assertEquals(hasNegativeCycle, shortestPath.hasNegativeCycle());
    if (hasNegativeCycle) {
      Iterator<WeightedDirectedGraph.WeightedDirectEdge> iterator = shortestPath.negativeCycle().iterator();
      int index = 0;
      while (iterator.hasNext()) {
        WeightedDirectedGraph.WeightedDirectEdge edge = iterator.next();
        assertEquals(cycle[index++], edge.from());
        assertEquals(cycle[index], edge.to());
      }
    }
  }
}
