package br.com.eventhorizon.common.datastructures.graphs;

import br.com.eventhorizon.common.utils.StringToIntegerArrayConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class UndirectedUndirectedGraphTraverserTest {

  private static final String UNDIRECTED_ACYCLIC_SINGLE_COMPONENT_GRAPH = "src/test/resources/graphs/undirected-acyclic-single-component-graph.csv";

  private static final String UNDIRECTED_ACYCLIC_SINGLE_COMPONENT_GRAPH_TRAVERSE = "/graphs/undirected-acyclic-single-component-graph-traverse.csv";

  private static final String UNDIRECTED_ACYCLIC_MULTI_COMPONENT_GRAPH = "src/test/resources/graphs/undirected-acyclic-multi-component-graph.csv";

  private static final String UNDIRECTED_ACYCLIC_MULTI_COMPONENT_GRAPH_TRAVERSE = "/graphs/undirected-acyclic-multi-component-graph-traverse.csv";

  private static final String UNDIRECTED_CYCLIC_SINGLE_COMPONENT_GRAPH = "src/test/resources/graphs/undirected-cyclic-single-component-graph.csv";

  private static final String UNDIRECTED_CYCLIC_SINGLE_COMPONENT_GRAPH_TRAVERSE = "/graphs/undirected-cyclic-single-component-graph-traverse.csv";

  private static final String UNDIRECTED_CYCLIC_MULTI_COMPONENT_GRAPH = "src/test/resources/graphs/undirected-cyclic-multi-component-graph.csv";

  private static final String UNDIRECTED_CYCLIC_MULTI_COMPONENT_GRAPH_TRAVERSE = "/graphs/undirected-cyclic-multi-component-graph-traverse.csv";

  @ParameterizedTest
  @CsvFileSource(resources = UNDIRECTED_ACYCLIC_SINGLE_COMPONENT_GRAPH_TRAVERSE, numLinesToSkip = 1)
  public void testUndirectedAcyclicSingleComponentGraph(
      UndirectedGraphTraverser.Type type,
      int sourceVertex,
      @ConvertWith(StringToIntegerArrayConverter.class) int[] expectedOrder) {
    UndirectedGraph graph = GraphUtils.readUndirectedGraphFromCsvFile(UNDIRECTED_ACYCLIC_SINGLE_COMPONENT_GRAPH);
    assertEquals(expectedOrder.length, graph.numberOfVertices());
    UndirectedGraphTraverser traverser = new UndirectedGraphTraverser(graph, sourceVertex, type);
    int[] actualOrder = new int[expectedOrder.length];
    int i = 0;
    while (traverser.hasNext()) {
      actualOrder[i++] = traverser.next();
    }
    assertEquals(i, graph.numberOfVertices());
    assertArrayEquals(expectedOrder, actualOrder);
    assertThrows(NoSuchElementException.class, traverser::next);
    assertEquals(traverser.markedCount(), actualOrder.length);
  }

  @ParameterizedTest
  @CsvFileSource(resources = UNDIRECTED_ACYCLIC_MULTI_COMPONENT_GRAPH_TRAVERSE, numLinesToSkip = 1)
  public void testUndirectedAcyclicMultiComponentGraph(
      UndirectedGraphTraverser.Type type,
      int sourceVertex,
      @ConvertWith(StringToIntegerArrayConverter.class) int[] expectedOrder) {
    UndirectedGraph graph = GraphUtils.readUndirectedGraphFromCsvFile(UNDIRECTED_ACYCLIC_MULTI_COMPONENT_GRAPH);
    UndirectedGraphTraverser traverser = new UndirectedGraphTraverser(graph, sourceVertex, type);
    int[] actualOrder = new int[expectedOrder.length];
    int i = 0;
    while (traverser.hasNext()) {
      actualOrder[i++] = traverser.next();
    }
    assertArrayEquals(expectedOrder, actualOrder);
    assertThrows(NoSuchElementException.class, traverser::next);
    assertEquals(traverser.markedCount(), actualOrder.length);
  }

  @ParameterizedTest
  @CsvFileSource(resources = UNDIRECTED_CYCLIC_SINGLE_COMPONENT_GRAPH_TRAVERSE, numLinesToSkip = 1)
  public void testUndirectedCyclicSingleComponentGraph(
      UndirectedGraphTraverser.Type type,
      int sourceVertex,
      @ConvertWith(StringToIntegerArrayConverter.class) int[] expectedOrder) {
    UndirectedGraph graph = GraphUtils.readUndirectedGraphFromCsvFile(UNDIRECTED_CYCLIC_SINGLE_COMPONENT_GRAPH);
    assertEquals(expectedOrder.length, graph.numberOfVertices());
    UndirectedGraphTraverser traverser = new UndirectedGraphTraverser(graph, sourceVertex, type);
    int[] actualOrder = new int[expectedOrder.length];
    int i = 0;
    while (traverser.hasNext()) {
      actualOrder[i++] = traverser.next();
    }
    assertEquals(i, graph.numberOfVertices());
    assertArrayEquals(expectedOrder, actualOrder);
    assertThrows(NoSuchElementException.class, traverser::next);
    assertEquals(traverser.markedCount(), actualOrder.length);
  }

  @ParameterizedTest
  @CsvFileSource(resources = UNDIRECTED_CYCLIC_MULTI_COMPONENT_GRAPH_TRAVERSE, numLinesToSkip = 1)
  public void testUndirectedCyclicMultiComponentGraph(
      UndirectedGraphTraverser.Type type,
      int sourceVertex,
      @ConvertWith(StringToIntegerArrayConverter.class) int[] expectedOrder) {
    UndirectedGraph graph = GraphUtils.readUndirectedGraphFromCsvFile(UNDIRECTED_CYCLIC_MULTI_COMPONENT_GRAPH);
    UndirectedGraphTraverser traverser = new UndirectedGraphTraverser(graph, sourceVertex, type);
    int[] actualOrder = new int[expectedOrder.length];
    int i = 0;
    while (traverser.hasNext()) {
      actualOrder[i++] = traverser.next();
    }
    assertArrayEquals(expectedOrder, actualOrder);
    assertThrows(NoSuchElementException.class, traverser::next);
    assertEquals(traverser.markedCount(), actualOrder.length);
  }
}
