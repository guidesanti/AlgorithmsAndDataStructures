package br.com.eventhorizon.common.datastructures.graphs;

import br.com.eventhorizon.common.datastructures.Stack;
import br.com.eventhorizon.common.datastructures.queues.IndexedHeapPriorityQueue;

import java.util.Arrays;

/**
 * This weighted directed graph processing class computes the shortest paths from a given source
 * vertex to all reachable vertices from this source vertex using the Dijkstra's algorithm.
 */
public class WeightedDirectedGraphShortestPath {

  private final int sourceVertex;

  private final WeightedDirectedGraph graph;

  private final WeightedDirectedGraph.WeightedDirectEdge[] edgeTo;

  private final double[] distanceTo;

  public WeightedDirectedGraphShortestPath(WeightedDirectedGraph graph, int sourceVertex) {
    this.sourceVertex = sourceVertex;
    this.graph = graph;
    this.edgeTo = new WeightedDirectedGraph.WeightedDirectEdge[graph.numberOfVertices()];
    this.distanceTo = new double[graph.numberOfVertices()];
    init();
    processGraph();
  }

  public double distanceTo(int destinationVertex) {
    return distanceTo[destinationVertex];
  }

  public boolean hasPathTo(int destinationVertex) {
    return distanceTo[destinationVertex] < Double.POSITIVE_INFINITY;
  }

  public Iterable<WeightedDirectedGraph.WeightedDirectEdge> pathTo(int destinationVertex) {
    if (destinationVertex < 0 || destinationVertex >= graph.numberOfVertices()) {
      throw new IndexOutOfBoundsException();
    }
    if (!hasPathTo(destinationVertex)) {
      return null;
    }
    Stack<WeightedDirectedGraph.WeightedDirectEdge> path = new Stack<>();
    for (WeightedDirectedGraph.WeightedDirectEdge edge = edgeTo[destinationVertex]; edge != null; edge = edgeTo[edge.from()]) {
      path.push(edge);
    }
    return path;
  }

  private void init() {
    Arrays.fill(distanceTo, Double.POSITIVE_INFINITY);
    distanceTo[sourceVertex] = 0;
  }

  private void processGraph() {
    IndexedHeapPriorityQueue<Double> queue = new IndexedHeapPriorityQueue<>(IndexedHeapPriorityQueue.Type.MIN, graph.numberOfVertices());
    queue.add(sourceVertex, 0.0);
    while (!queue.isEmpty()) {
      int vertex = queue.poll().index();
      graph.adjacencies(vertex).forEach(edge -> relax(edge, queue));
    }
  }

  private void relax(WeightedDirectedGraph.WeightedDirectEdge edge, IndexedHeapPriorityQueue<Double> priorityQueue) {
    int from = edge.from();
    int to = edge.to();
    double weight = edge.weight();
    if (distanceTo[to] > distanceTo[from] + weight) {
      distanceTo[to] = distanceTo[from] + weight;
      edgeTo[to] = edge;
      if (priorityQueue.contains(to)) {
        priorityQueue.replace(to, distanceTo[to]);
      } else {
        priorityQueue.add(to, distanceTo[to]);
      }
    }
  }
}
