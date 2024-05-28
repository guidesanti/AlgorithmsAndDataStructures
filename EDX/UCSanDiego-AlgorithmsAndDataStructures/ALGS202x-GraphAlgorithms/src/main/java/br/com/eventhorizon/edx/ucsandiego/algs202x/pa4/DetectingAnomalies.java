package br.com.eventhorizon.edx.ucsandiego.algs202x.pa4;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class DetectingAnomalies implements PA {

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    WeightedDirectedGraph graph = readGraph(scanner);
    for (int i = 0; i < graph.numberOfVertices; i++) {
      WeightedDirectedGraphBellmanFordShortestPath shortestPath = new WeightedDirectedGraphBellmanFordShortestPath(graph, i);
      if (shortestPath.hasNegativeCycle()) {
        System.out.println(1);
        return;
      }
    }
    System.out.println(0);
  }

  private static WeightedDirectedGraph readGraph(FastScanner scanner) {
    int numberOfVertices = scanner.nextInt();
    int numberOfEdges = scanner.nextInt();
    WeightedDirectedGraph graph = new WeightedDirectedGraph(numberOfVertices);
    for (int i = 0; i < numberOfEdges; i++) {
      graph.addEdge(scanner.nextInt() - 1, scanner.nextInt() - 1, scanner.nextInt());
    }
    return graph;
  }

  private static class WeightedDirectEdge {

    private final int from;

    private final int to;

    private final int weight;

    private WeightedDirectEdge(int from, int to, int weight) {
      this.from = from;
      this.to = to;
      this.weight = weight;
    }

    public int from() {
      return from;
    }

    public int to() {
      return to;
    }

    public int weight() {
      return weight;
    }

    @Override
    public String toString() {
      return "WeightedDirectEdge {(" + weight + ")" + from + " -> " + to + "}";
    }
  }

  private static class WeightedDirectedGraph {

    private final int numberOfVertices;

    private final LinkedList<WeightedDirectEdge>[] adjacencies;

    private final LinkedList<WeightedDirectEdge> edges;

    public WeightedDirectedGraph(int numberOfVertices) {
      this.numberOfVertices = numberOfVertices;
      this.adjacencies = new LinkedList[numberOfVertices];
      for (int i = 0; i < numberOfVertices; i++) {
        this.adjacencies[i] = new LinkedList<>();
      }
      this.edges = new LinkedList<>();
    }

    public void addEdge(int fromVertex, int toVertex, int weight) {
      if (fromVertex < 0 || fromVertex >= numberOfVertices ||
          toVertex < 0 || toVertex >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      WeightedDirectEdge edge = new WeightedDirectEdge(fromVertex, toVertex, weight);
      adjacencies[fromVertex].addLast(edge);
      edges.addLast(edge);
    }

    public int numberOfVertices() {
      return numberOfVertices;
    }

    public int numberOfEdges() {
      return edges.size();
    }

    public LinkedList<WeightedDirectEdge> adjacencies(int vertex) {
      if (vertex < 0 || vertex >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      return adjacencies[vertex];
    }

    public LinkedList<WeightedDirectEdge> edges() {
      return edges;
    }
  }

  private static class WeightedDirectedGraphBellmanFordShortestPath {

    private final int sourceVertex;

    private final WeightedDirectedGraph graph;

    private final WeightedDirectEdge[] edgeTo;

    private final double[] distanceTo;

    private final Queue<Integer> queue;

    private final boolean[] onQueue;

    private Iterable<WeightedDirectEdge> negativeCycle;

    private int count;

    public WeightedDirectedGraphBellmanFordShortestPath(WeightedDirectedGraph graph, int sourceVertex) {
      this.sourceVertex = sourceVertex;
      this.graph = graph;
      this.edgeTo = new WeightedDirectEdge[graph.numberOfVertices()];
      this.distanceTo = new double[graph.numberOfVertices()];
      this.queue = new LinkedList<>();
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

    public Iterable<WeightedDirectEdge> pathTo(int destinationVertex) {
      if (destinationVertex < 0 || destinationVertex >= graph.numberOfVertices()) {
        throw new IndexOutOfBoundsException();
      }
      if (!hasPathTo(destinationVertex)) {
        return null;
      }
      Stack<WeightedDirectEdge> path = new Stack<>();
      for (WeightedDirectEdge edge = edgeTo[destinationVertex]; edge != null; edge = edgeTo[edge.from()]) {
        path.push(edge);
      }
      return path;
    }

    public boolean hasNegativeCycle() {
      return negativeCycle != null;
    }

    public Iterable<WeightedDirectEdge> negativeCycle() {
      return negativeCycle;
    }

    private void init() {
      Arrays.fill(distanceTo, Double.POSITIVE_INFINITY);
      distanceTo[sourceVertex] = 0;
    }

    private void processGraph() {
      queue.add(sourceVertex);
      onQueue[sourceVertex] = true;
      while (!queue.isEmpty() && !hasNegativeCycle()) {
        int vertex = queue.remove();
        onQueue[vertex] = false;
        graph.adjacencies(vertex).forEach(this::relax);
      }
    }

    private void relax(WeightedDirectEdge edge) {
      int from = edge.from();
      int to = edge.to();
      double weight = edge.weight();
      if (distanceTo[to] > distanceTo[from] + weight) {
        distanceTo[to] = distanceTo[from] + weight;
        edgeTo[to] = edge;
        if (!onQueue[to]) {
          queue.add(to);
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
          WeightedDirectEdge edge = edgeTo[vertex];
          auxGraph.addEdge(edge.from(), edge.to(), edge.weight());
        }
      }
      WeightedDirectedGraphCycle graphCycle = new WeightedDirectedGraphCycle(auxGraph);
      if (graphCycle.hasCycle()) {
        negativeCycle = graphCycle.cycle();
      }
    }
  }

  private static class WeightedDirectedGraphCycle {

    private final boolean[] marked;

    private final WeightedDirectEdge[] edgeTo;

    private final boolean[] onPath;

    private final Stack<WeightedDirectEdge> cycle;

    public WeightedDirectedGraphCycle(WeightedDirectedGraph graph) {
      if (graph == null) {
        throw new IllegalArgumentException("graph cannot be null");
      }
      marked = new boolean[graph.numberOfVertices()];
      edgeTo = new WeightedDirectEdge[graph.numberOfVertices()];
      onPath = new boolean[graph.numberOfVertices()];
      cycle = new Stack<>();
      for (int vertex = 0; vertex < graph.numberOfVertices() && cycle.isEmpty(); vertex++) {
        if (!marked[vertex]) {
          depthFirstSearch(graph, vertex);
        }
      }
    }

    public boolean hasCycle() {
      return !cycle.isEmpty();
    }

    public Iterable<WeightedDirectEdge> cycle() {
      return cycle;
    }

    private void depthFirstSearch(WeightedDirectedGraph graph, int sourceVertex) {
      Stack<Integer> stack = new Stack<>();
      stack.push(sourceVertex);
      marked[sourceVertex] = true;
      while (!stack.isEmpty()) {
        while (true) {
          Integer vertex = stack.peek();
          LinkedList<WeightedDirectEdge> adjEdges = graph.adjacencies(vertex);
          onPath[vertex] = true;
          boolean stop = true;
          for (int i = 0; i < adjEdges.size(); i++) {
            WeightedDirectEdge adjEdge = adjEdges.get(i);
            int adjVertex = adjEdge.to();
            if (!marked[adjVertex]) {
              stack.push(adjVertex);
              edgeTo[adjVertex] = adjEdge;
              marked[adjVertex] = true;
              stop = false;
            } else if (onPath[adjVertex]) {
              cycle.push(adjEdge);
              for (WeightedDirectEdge edge = edgeTo[vertex]; edge != null; edge = edgeTo[edge.from()]) {
                cycle.push(edge);
              }
              return;
            }
          }
          if (stop) {
            break;
          }
        }
        onPath[stack.pop()] = false;
      }
    }
  }
}
