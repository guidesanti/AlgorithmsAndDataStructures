package br.com.eventhorizon.common.datastructures.graphs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DirectedGraphTest {

  @Test
  public void testGraph() {
    DirectedGraph graph = new DirectedGraph(8);
    assertEquals(8, graph.numberOfVertices());
    assertEquals(0, graph.numberOfEdges());
    for (int i = 0; i < 8; i++) {
      assertEquals(0, graph.outDegree(i));
      assertTrue(graph.adjacencies(i).isEmpty());
    }

    // TODO
  }
}
