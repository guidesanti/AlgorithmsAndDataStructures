package br.com.eventhorizon.edx.ucsandiego.algs207x.pa3;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;
import java.util.stream.Collectors;

public class FindCirculationInNetwork implements PA {

  private static int verticesCount;

  private static int edgesCount;

  private static Map<Integer, Vertex> vertices;

  private static List<Edge> edges;

  private static Queue<Vertex> residualFlow;

  private static Vertex source;

  private static Vertex sink;

  private static void init() {
    verticesCount = 0;
    edgesCount = 0;
    vertices = new HashMap<>();
    edges = new ArrayList<>();
    residualFlow = new LinkedList<>();
    source = null;
    sink = null;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read number of vertices and number of edges
    verticesCount = scanner.nextInt();
    edgesCount = scanner.nextInt();

    // Read edges
    for (int i = 0; i < edgesCount; i++) {
      int from = scanner.nextInt();
      Vertex fromVertex = vertices.getOrDefault(from, new Vertex(from));
      Edge edge = new Edge(from, scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
      fromVertex.adjacencies.add(edge);
      fromVertex.outDegree++;
      edges.add(edge);
      vertices.put(from, fromVertex);
      Vertex toVertex = vertices.getOrDefault(edge.to, new Vertex(edge.to));
      toVertex.inDegree++;
      vertices.put(edge.to, toVertex);
    }
  }

  private static void writeOutput() {
    // TODO
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    finalSolutionImpl();
    writeOutput();
  }

  private static void finalSolutionImpl() {
    findSourceAndSink();
    sendFlow();
  }

  private static void findSourceAndSink() {
    for (Vertex vertex : vertices.values()) {
      if (vertex.inDegree == 0) {
        source = vertex;
      }
      if (vertex.outDegree == 0) {
        sink = vertex;
      }
      if (source != null && sink != null) {
        break;
      }
    }
  }

  private static void sendMinimumFlow() {
    // Send all minimum flow
    for (Vertex vertex : vertices.values()) {
      List<Edge> edgesToRemove = new ArrayList<>();
      for (Edge edge : vertex.adjacencies) {
        vertex.flow -= edge.lowerBound;
        edge.flow = edge.lowerBound;
        vertices.get(edge.to).flow += edge.lowerBound;
        if (edge.flow == edge.capacity) {
          edgesToRemove.add(edge);
        }
      }
      vertex.adjacencies.removeAll(edgesToRemove);
    }

    // Set residual flow
    Queue<Vertex> residualFlow = vertices.values().stream().filter(vertex -> vertex.flow > 0).collect(Collectors.toCollection(LinkedList::new));
  }

  private static void sendFlow() {
    while (true) {
      List<Edge> path = new ArrayList<>();
      int flow = findPath(source, path, Integer.MAX_VALUE);
      if (flow == 0) {
        break;
      }
      for (Edge edge : path) {
        edge.flow += flow;
      }
    }
  }

  private static int findPath(Vertex curr, List<Edge> path, int flow) {
    if (curr == sink) {
      return flow;
    }
    for (Edge edge : curr.adjacencies) {
      if (edge.flow < edge.capacity) {
        path.add(edge);
        int newFlow = findPath(vertices.get(edge.to), path, Math.min(flow, edge.capacity - edge.flow));
        if (newFlow > 0) {
          return newFlow;
        }
        path.remove(path.size() - 1);
      }
    }
    return 0;
  }

  private static class Vertex {

    final int label;

    final List<Edge> adjacencies;

    int flow;

    int inDegree;

    int outDegree;

    public Vertex(int label) {
      this.label = label;
      this.adjacencies = new ArrayList<>();
      this.flow = 0;
    }

    @Override
    public String toString() {
      return label + ":" + flow;
    }
  }

  private static class Edge {

    final int from;

    final int to;

    final int lowerBound;

    final int capacity;

    int flow;

    Edge(int from, int to, int lowerBound, int capacity) {
      this.from = from;
      this.to = to;
      this.lowerBound = lowerBound;
      this.capacity = capacity;
    }

    @Override
    public String toString() {
      return from + "->" + to + ":" + lowerBound + "," + capacity + "," + flow;
    }
  }
}
