package br.com.eventhorizon.common.datastructures.graphs;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTraverserTest {

  private static final Integer[] DEPTH_FIRST = { 0, 6, 5, 4, 2, 7, 3, 1 };

  private static final Integer[] BREADTH_FIRST = { 0, 1, 2, 5, 6, 3, 7, 4 };

  @Test
  public void testDepthFirstTraverse() {
    Graph graph = buildGraph();
    GraphTraverser traverser = new GraphTraverser(graph, 0, GraphTraverser.Type.DEPTH_FIRST);
    int i = 0;
    while (traverser.hasNext()) {
      assertEquals(DEPTH_FIRST[i++], traverser.next());
    }
    assertThrows(NoSuchElementException.class, traverser::next);
  }

  @Test
  public void testBreadthFirstTraverse() {
    Graph graph = buildGraph();
    GraphTraverser traverser = new GraphTraverser(graph, 0, GraphTraverser.Type.BREADTH_FIRST);
    int i = 0;
    while (traverser.hasNext()) {
      assertEquals(BREADTH_FIRST[i++], traverser.next());
    }
    assertThrows(NoSuchElementException.class, traverser::next);
  }

  private Graph buildGraph() {
    Graph graph = new Graph(8);
    assertEquals(8, graph.numberOfVertices());
    assertEquals(0, graph.numberOfEdges());
    for (int i = 0; i < 8; i++) {
      assertEquals(0, graph.degree(i));
      assertTrue(graph.adjacencies(i).isEmpty());
    }
    assertEquals(0, graph.maxDegree());

    graph.addEdge(0, 1);
    assertEquals(8, graph.numberOfVertices());
    assertEquals(1, graph.numberOfEdges());
    assertEquals(1, graph.maxDegree());

    graph.addEdge(0, 2);
    assertEquals(8, graph.numberOfVertices());
    assertEquals(2, graph.numberOfEdges());
    assertEquals(2, graph.maxDegree());

    graph.addEdge(0, 5);
    assertEquals(8, graph.numberOfVertices());
    assertEquals(3, graph.numberOfEdges());
    assertEquals(3, graph.maxDegree());

    graph.addEdge(0, 6);
    assertEquals(8, graph.numberOfVertices());
    assertEquals(4, graph.numberOfEdges());
    assertEquals(4, graph.maxDegree());

    graph.addEdge(2, 3);
    assertEquals(8, graph.numberOfVertices());
    assertEquals(5, graph.numberOfEdges());
    assertEquals(4, graph.maxDegree());

    graph.addEdge(2, 7);
    assertEquals(8, graph.numberOfVertices());
    assertEquals(6, graph.numberOfEdges());
    assertEquals(4, graph.maxDegree());

    graph.addEdge(4, 5);
    assertEquals(8, graph.numberOfVertices());
    assertEquals(7, graph.numberOfEdges());
    assertEquals(4, graph.maxDegree());

    return graph;
  }
}
