package br.com.eventhorizon.edx.ucsandiego.algs207x.pa3;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;
import java.util.stream.Collectors;

public class FindCirculationInNetwork implements PA {

  private static final int SOURCE = -1;

  private static final int SINK = -2;

  private static final int SUPER_SOURCE = -3;

  private static final int SUPER_SINK = -4;

  private static int verticesCount;

  private static int edgesCount;

  private static Vertex[] vertices;

  private static List<Edge> edges;

  private static List<Edge> auxEdges;

  private static Vertex source;

  private static Vertex sink;

  private static Vertex superSource;

  private static Vertex superSink;

  private static boolean existCirculation;

  private static void init() {
    verticesCount = 0;
    edgesCount = 0;
    vertices = null;
    edges = new ArrayList<>();
    auxEdges = new ArrayList<>();
    source = null;
    sink = new Vertex(SINK);
    superSource = new Vertex(SUPER_SOURCE);
    superSink = new Vertex(SUPER_SINK);
    existCirculation = false;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read number of vertices and number of edges
    verticesCount = scanner.nextInt();
    edgesCount = scanner.nextInt();

    // Initialize vertices
    vertices = new Vertex[verticesCount];
    for (int i = 0; i < verticesCount; i++) {
      vertices[i] = new Vertex(i + 1);
    }

    // Read edges
    for (int i = 0; i < edgesCount; i++) {
      int from = scanner.nextInt();
      int to = scanner.nextInt();
      Vertex fromVertex = getVertex(from);
      Vertex toVertex = getVertex(to);
      Edge edge = new Edge(from, to, scanner.nextInt(), scanner.nextInt());
      fromVertex.edges.add(edge);
      toVertex.edges.add(edge.reverse);
      edges.add(edge);
    }
  }

  private static void writeOutput() {
    if (existCirculation) {
      System.out.println("YES");
      for (Edge edge : edges) {
        System.out.println(edge.flow);
      }
    } else {
      System.out.print("NO");
    }
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    finalSolutionImpl();
    writeOutput();
  }

  private static void finalSolutionImpl() {
    removeLowerBounds();
    sendFlow(superSource, superSink);
    if (verifyAdmissibleFlow()) {
      findSource();
      removeCycles();
      sendFlow(source, sink);
      verifyCirculation();
    }
  }

  private static void findSource() {
    boolean[] visited = new boolean[verticesCount];
    for (Vertex vertex : vertices) {
      if (visited[vertex.label - 1]) {
        continue;
      }
      source = vertex;
      findSource(vertex, visited);
    }
  }

  private static void findSource(Vertex curr, boolean[] visited) {
    if (visited[curr.label - 1]) {
      return;
    }
    visited[curr.label - 1] = true;
    for (Edge edge : curr.edges) {
      findSource(getVertex(curr.label), visited);
    }
  }

  private static void removeCycles() {
    Set<Edge> list = new HashSet<>();
    findCycle(source, new ArrayList<>(), new boolean[verticesCount], new boolean[verticesCount], list);
    addSourceAndSink(list);
  }

  private static boolean findCycle(Vertex curr, List<Edge> path, boolean[] onPath, boolean[] visited, Set<Edge> list) {
    if (!path.isEmpty() && onPath[curr.label - 1]) {
      if (path.size() > 1) {
        list.add(path.get(path.size() - 1));
      }
      return true;
    }
    onPath[curr.label - 1] = true;
    visited[curr.label - 1] = true;
    for (Edge edge : curr.edges.stream().filter(edge -> !edge.isReverse).collect(Collectors.toList())) {
      Vertex next = getVertex(edge.to);
      if (visited[next.label - 1]) {
        continue;
      }
      path.add(edge);
      if (!findCycle(next, path, onPath, visited, list)) {
        onPath[edge.to - 1] = false;
      }
      path.remove(path.size() - 1);
    }
    return false;
  }

  private static void addSourceAndSink(Set<Edge> list) {
    for (Edge edge : list) {
      if (source.edges.stream().noneMatch(edge1 -> edge1.to == edge.to)) {
        Vertex toVertex = getVertex(edge.to);
        toVertex.edges.remove(edge.reverse);
        edge.to = sink.label;
        edge.reverse.from = sink.label;
        sink.edges.add(edge.reverse);
      }
    }
  }

  private static void removeLowerBounds() {
    edges.stream().filter(edge -> edge.lowerBound > 0 && !edge.isReverse).forEach(edge -> {
      Vertex from = getVertex(edge.from);
      Edge edgeToSink = new Edge(edge.from, SUPER_SINK, 0, edge.lowerBound);
      from.edges.add(edgeToSink);
      superSink.edges.add(edgeToSink.reverse);

      Vertex to = getVertex(edge.to);
      Edge edgeFromSource = new Edge(SUPER_SOURCE, edge.to, 0, edge.lowerBound);
      superSource.edges.add(edgeFromSource);
      to.edges.add(edgeFromSource.reverse);

      auxEdges.add(edgeFromSource);
      auxEdges.add(edgeToSink);

      edge.capacity -= edge.lowerBound;
      edge.reverse.flow -= edge.lowerBound;
      edge.reverse.capacity -= edge.lowerBound;

    });
  }

  private static void sendFlow(Vertex source, Vertex sink) {
    while (true) {
      List<Edge> path = new ArrayList<>();
      int flow = findPath(source, sink, path, Integer.MAX_VALUE, new HashSet<>());
      if (flow == 0) {
        break;
      }
      incrementFlow(path, flow);
    }
  }

  private static int findPath(Vertex curr, Vertex sink, List<Edge> path, int flow, Set<Integer> visited) {
    if (curr == sink) {
      return flow;
    }
    if (flow == 0) {
      return 0;
    }
    visited.add(curr.label);
    for (Edge edge : curr.edges) {
      if (!visited.contains(edge.to) && edge.residualCapacity() > 0) {
        Vertex next = getVertex(edge.to);
        path.add(edge);
        int newFlow = findPath(next, sink, path, Math.min(flow, edge.residualCapacity()), visited);
        if (newFlow > 0) {
          return newFlow;
        }
        path.remove(path.size() - 1);
      }
    }
    return 0;
  }

  private static void incrementFlow(List<Edge> path, int flow) {
    for (Edge edge : path) {
      edge.flow += flow;
      edge.reverse.flow -= flow;
    }
  }

  private static boolean verifyAdmissibleFlow() {
    boolean admissibleFlow = true;

    // Verify admissible flow
    for (Edge edge : auxEdges) {
      if (edge.flow != edge.capacity) {
        admissibleFlow = false;
        break;
      }
    }

    // Remove auxiliary edges
    if (admissibleFlow) {
      // Remove edges from super source
      superSource.edges.forEach(edge -> {
        Vertex vertex = getVertex(edge.to);
        vertex.edges.remove(edge.reverse);
      });
      superSource.edges.clear();
      superSource = null;

      // Remove edges to super sink
      superSink.edges.forEach(edge -> {
        Vertex vertex = getVertex(edge.to);
        vertex.edges.remove(edge.reverse);
      });
      superSink.edges.clear();
      superSink = null;

      // Fix edges
      edges.forEach(edge -> {
        edge.flow += edge.lowerBound;
        edge.capacity += edge.lowerBound;
      });
    }

    return admissibleFlow;
  }

  private static void verifyCirculation() {
    existCirculation = true;
    for (Edge edge : edges) {
      if (edge.flow < edge.lowerBound) {
        existCirculation = false;
        break;
      }
    }
  }

  private static Vertex getVertex(int label) {
    if (label >= 0) {
      return vertices[label - 1];
    } else if (label == SOURCE) {
      return source;
    } else if (label == SINK) {
      return sink;
    } else if (label == SUPER_SOURCE) {
      return superSource;
    } else if (label == SUPER_SINK) {
      return superSink;
    } else {
      throw new RuntimeException("Invalid vertex label " + label);
    }
  }

  protected static class Vertex {

    final int label;

    final List<Edge> edges;

    int flow;

    public Vertex(int label) {
      this.label = label;
      this.edges = new ArrayList<>();
      this.flow = 0;
    }

    @Override
    public String toString() {
      return "" + label;
    }
  }

  protected static class Edge {

    int from;

    int to;

    final int lowerBound;

    int capacity;

    int flow;

    final Edge reverse;

    final boolean isReverse;

    Edge(int from, int to, int lowerBound, int capacity) {
      this.from = from;
      this.to = to;
      this.lowerBound = lowerBound;
      this.capacity = capacity;
      this.flow = 0;
      this.reverse = new Edge(to, from, lowerBound, capacity, this);
      this.isReverse = false;
    }

    Edge(int from, int to, int lowerBound, int capacity, Edge reverse) {
      this.from = from;
      this.to = to;
      this.lowerBound = lowerBound;
      this.capacity = capacity;
      this.flow = capacity;
      this.reverse = reverse;
      this.isReverse = true;
    }

    @Override
    public String toString() {
      return from + "->" + to + ":" + lowerBound + "," + capacity + "," + flow;
    }

    int residualCapacity() {
      return capacity - flow;
    }
  }
}
