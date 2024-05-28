package br.com.eventhorizon.datastructures.graphs;

import br.com.eventhorizon.datastructures.LinkedList;
import br.com.eventhorizon.datastructures.queues.IndexedHeapPriorityQueue;

public class WeightedUndirectedGraphPrimMinimumSpanningTree {

  private final WeightedUndirectedEdge[] edgeTo;

  private double minimumSpanningTreeWeight;

  public WeightedUndirectedGraphPrimMinimumSpanningTree(WeightedUndirectedGraph graph) {
    boolean[] marked = new boolean[graph.numberOfVertices()];
    double[] distanceTo = new double[graph.numberOfVertices()];
    edgeTo = new WeightedUndirectedEdge[graph.numberOfVertices()];
    IndexedHeapPriorityQueue<Double> queue =
        new IndexedHeapPriorityQueue<>(IndexedHeapPriorityQueue.Type.MIN, graph.numberOfVertices());

    marked[0] = true;
    for (int vertex = 0; vertex < graph.numberOfVertices(); vertex++) {
      distanceTo[vertex] = Double.POSITIVE_INFINITY;
    }
    distanceTo[0] = 0.0;
    queue.add(0, 0.0);

    while (!queue.isEmpty()) {
      IndexedHeapPriorityQueue<Double>.Node node = queue.poll();
      int vertex = node.index();
      marked[vertex] = true;
      minimumSpanningTreeWeight += node.key();
      for (WeightedUndirectedEdge adjEdge : graph.adjacencies(vertex)) {
        int adjVertex = adjEdge.other(vertex);
        if (marked[adjVertex]) {
          continue;
        }
        double weight = adjEdge.weight();
        if (weight < distanceTo[adjVertex]) {
          edgeTo[adjVertex] = adjEdge;
          distanceTo[adjVertex] = weight;
          if (queue.contains(adjVertex)) {
            queue.replace(adjVertex, weight);
          } else {
            queue.add(adjVertex, weight);
          }
        }
      }
    }
  }

  public double weight() {
    return minimumSpanningTreeWeight;
  }

  public Iterable<WeightedUndirectedEdge> edges() {
    LinkedList<WeightedUndirectedEdge> edges = new LinkedList<>();
    for (int i = 1; i < edgeTo.length; i++) {
      edges.addLast(edgeTo[i]);
    }
    return edges;
  }
}
