package br.com.eventhorizon.edx.ucsandiego.algs202x.pa4;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

public class ExchangingMoneyOptimally implements PA {

  @Override
  public void naiveSolution() {
    finalSolution();
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    WeightedDirectedGraph graph = readGraph(scanner);
    int sourceVertex = scanner.nextInt() - 1;
    WeightedDirectedGraphBellmanFordShortestPath shortestPath = new WeightedDirectedGraphBellmanFordShortestPath(graph, sourceVertex);
    for (int destinationVertex = 0; destinationVertex < graph.numberOfVertices; destinationVertex++) {
      if (shortestPath.distanceTo[destinationVertex] == Integer.MAX_VALUE) {
        System.out.println("*");
      } else if (shortestPath.distanceTo[destinationVertex] == Integer.MIN_VALUE) {
        System.out.println("-");
      } else {
        System.out.println(shortestPath.distanceTo[destinationVertex]);
      }
    }
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

    private final long[] distanceTo;

    private final Queue<Integer> queue;

    private final boolean[] onQueue;

    private final Set<Integer> relaxedVertices;

    private int pass;

    private int count;

    public WeightedDirectedGraphBellmanFordShortestPath(WeightedDirectedGraph graph, int sourceVertex) {
      this.sourceVertex = sourceVertex;
      this.graph = graph;
      this.distanceTo = new long[graph.numberOfVertices()];
      this.queue = new LinkedList<>();
      this.onQueue = new boolean[graph.numberOfVertices];
      this.relaxedVertices = new HashSet<>();
      init();
      processGraph();
    }

    private void init() {
      Arrays.fill(distanceTo, Integer.MAX_VALUE);
      distanceTo[sourceVertex] = 0;
    }

    private void processGraph() {
      queue.add(sourceVertex);
      onQueue[sourceVertex] = true;
      while (!queue.isEmpty() && pass < graph.numberOfVertices) {
        int vertex = queue.remove();
        onQueue[vertex] = false;
        graph.adjacencies(vertex).forEach(edge -> relax(edge, null));
      }
      while (!queue.isEmpty() && pass <= graph.numberOfVertices) {
        int vertex = queue.remove();
        onQueue[vertex] = false;
        graph.adjacencies(vertex).forEach(edge -> relax(edge, relaxedVertices));
      }
      for (int sourceVertex : relaxedVertices) {
        depthFirstPostorder(graph, sourceVertex);
      }
    }

    private void relax(WeightedDirectEdge edge, Set<Integer> relaxedVertices) {
      int from = edge.from();
      int to = edge.to();
      int weight = edge.weight();
      if (distanceTo[to] > distanceTo[from] + weight) {
        distanceTo[to] = distanceTo[from] + weight;
        if (!onQueue[to]) {
          queue.add(to);
        }
        if (relaxedVertices != null) {
          relaxedVertices.add(to);
        }
      }
      if (++count % graph.numberOfVertices() == 0) {
        pass++;
      }
    }

    private void depthFirstPostorder(WeightedDirectedGraph graph, int sourceVertex) {
      Stack<Integer> stack = new Stack<>();
      if (distanceTo[sourceVertex] != Integer.MIN_VALUE) {
        stack.push(sourceVertex);
        distanceTo[sourceVertex] = Integer.MIN_VALUE;
        while (!stack.isEmpty()) {
          while (true) {
            Integer vertex = stack.peek();
            LinkedList<WeightedDirectEdge> adjVertices = graph.adjacencies(vertex);
            boolean stop = true;
            for (WeightedDirectEdge edge : adjVertices) {
              if (distanceTo[edge.to] != Integer.MIN_VALUE) {
                stack.push(edge.to);
                distanceTo[edge.to] = Integer.MIN_VALUE;
                stop = false;
                break;
              }
            }
            if (stop) {
              break;
            }
          }
          stack.pop();
        }
      }
    }
  }
}
