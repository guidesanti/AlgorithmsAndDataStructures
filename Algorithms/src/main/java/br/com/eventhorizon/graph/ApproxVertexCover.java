package br.com.eventhorizon.graph;

import br.com.eventhorizon.datastructures.graphs.UndirectedGraphV2;

import java.util.ArrayList;
import java.util.List;

/**
 * This graph processing class finds a approximation vertex cover of an undirected graph.
 * It is an approximation because it is not guarantee that the vertex cover found will be the
 * optimum one, that means it may not be the minimum vertex cover.
 */
public class ApproxVertexCover {

  public List<Integer> findApproxVertexCover(UndirectedGraphV2 graph) {
    boolean[] covered = new boolean[graph.numberOfVertices()];
    List<Integer> vertexCover = new ArrayList<>();
    for (UndirectedGraphV2.Edge edge : graph.edges()) {
      if (covered[edge.vertex1()] || covered[edge.vertex2()]) {
        continue;
      }
      vertexCover.add(edge.vertex1());
      vertexCover.add(edge.vertex2());
      covered[edge.vertex1()] = true;
      covered[edge.vertex2()] = true;
      graph.adjacencies(edge.vertex1()).forEach(adjVertex -> covered[adjVertex] = true);
      graph.adjacencies(edge.vertex2()).forEach(adjVertex -> covered[adjVertex] = true);
    }
    return vertexCover;
  }
}
