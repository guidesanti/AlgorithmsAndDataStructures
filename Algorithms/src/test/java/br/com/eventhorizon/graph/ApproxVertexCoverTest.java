package br.com.eventhorizon.graph;

import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.datastructures.graphs.GraphUtils;
import br.com.eventhorizon.common.datastructures.graphs.UndirectedGraphV2;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApproxVertexCoverTest {

  private static int NUMBER_OF_TESTS = 100;

  @Test
  public void test() {
    ApproxVertexCover approxVertexCover = new ApproxVertexCover();
    for (int test = 0; test < NUMBER_OF_TESTS; test++) {
      int numberOfVertices = Utils.getRandomInteger(0, 500);
      int numberOfEdges = Utils.getRandomInteger(0, (numberOfVertices * (numberOfVertices - 1)) / 2);
      UndirectedGraphV2 graph = GraphUtils.generateRandomGraph(numberOfVertices, numberOfEdges, UndirectedGraphV2.class);
      List<Integer> vertexCover = approxVertexCover.findApproxVertexCover(graph);
      assertTrue(GraphUtils.isVertexCover(graph, vertexCover));
    }
  }
}
