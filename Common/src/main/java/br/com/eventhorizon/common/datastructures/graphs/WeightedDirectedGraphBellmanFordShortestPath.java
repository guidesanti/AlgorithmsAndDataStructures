package br.com.eventhorizon.common.datastructures.graphs;

import br.com.eventhorizon.common.datastructures.Stack;
import br.com.eventhorizon.common.datastructures.queues.Queue;

import java.util.Arrays;

/**
 * This weighted directed graph processing class computes the shortest paths from a given source
 * vertex to all reachable vertices from this source vertex using the Bellman-Ford's algorithm.
 */
public class WeightedDirectedGraphBellmanFordShortestPath {

  private final int sourceVertex;

  private final WeightedDirectedGraph graph;

  private final WeightedDirectedGraph.WeightedDirectEdge[] edgeTo;

  private final double[] distanceTo;

  private final Queue<Integer> queue;

  private final boolean[] onQueue;

  private Iterable<WeightedDirectedGraph.WeightedDirectEdge> negativeCycle;

  private int count;

  public WeightedDirectedGraphBellmanFordShortestPath(WeightedDirectedGraph graph, int sourceVertex) {
    this.sourceVertex = sourceVertex;
    this.graph = graph;
    this.edgeTo = new WeightedDirectedGraph.WeightedDirectEdge[graph.numberOfVertices()];
    this.distanceTo = new double[graph.numberOfVertices()];
    this.queue = new Queue<>();
    this.onQueue = new boolean[graph.numberOfVertices()];
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

  public boolean hasNegativeCycle() {
    return negativeCycle != null;
  }

  public Iterable<WeightedDirectedGraph.WeightedDirectEdge> negativeCycle() {
    return negativeCycle;
  }

  private void init() {
    Arrays.fill(distanceTo, Double.POSITIVE_INFINITY);
    distanceTo[sourceVertex] = 0;
  }

  private void processGraph() {
    queue.enqueue(sourceVertex);
    onQueue[sourceVertex] = true;
    while (!queue.isEmpty() && !hasNegativeCycle()) {
      int vertex = queue.dequeue();
      onQueue[vertex] = false;
      graph.adjacencies(vertex).forEach(this::relax);
    }
  }

  private void relax(WeightedDirectedGraph.WeightedDirectEdge edge) {
    int from = edge.from();
    int to = edge.to();
    double weight = edge.weight();
    if (distanceTo[to] > distanceTo[from] + weight) {
      distanceTo[to] = distanceTo[from] + weight;
      edgeTo[to] = edge;
      if (!onQueue[to]) {
        queue.enqueue(to);
      }
    }
    if (count++ % graph.numberOfVertices() == 0) {
      findNegativeCycle();
    }
  }

  private void findNegativeCycle() {
    WeightedDirectedGraph auxGraph = new WeightedDirectedGraph(graph.numberOfVertices());
    for (int vertex = 0; vertex < graph.numberOfVertices(); vertex++) {
      if (edgeTo[vertex] != null) {
        WeightedDirectedGraph.WeightedDirectEdge edge = edgeTo[vertex];
        auxGraph.addEdge(edge.from(), edge.to(), edge.weight());
      }
    }
    WeightedDirectedGraphCycle graphCycle = new WeightedDirectedGraphCycle(auxGraph);
    if (graphCycle.hasCycle()) {
      negativeCycle = graphCycle.cycle();
    }
  }
}
