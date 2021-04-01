package br.com.eventhorizon.common.datastructures.graphs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UndirectedGraphTest {

  @Test
  public void testGraph() {
    UndirectedGraph graph = new UndirectedGraph(8);
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
  }
}
