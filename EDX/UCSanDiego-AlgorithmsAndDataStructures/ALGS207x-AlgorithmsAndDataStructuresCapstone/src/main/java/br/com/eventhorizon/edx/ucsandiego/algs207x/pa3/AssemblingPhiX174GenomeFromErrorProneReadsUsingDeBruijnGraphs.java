package br.com.eventhorizon.edx.ucsandiego.algs207x.pa3;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;
import java.util.stream.Collectors;

public class AssemblingPhiX174GenomeFromErrorProneReadsUsingDeBruijnGraphs implements PA {

  private static final int MISMATCH_LIMIT = 2;

  private static final int K = 15;

  private static List<String> reads;

  private static Vertex[] vertices;

  private static List<Edge> edges;

  private static Map<String, Integer> kmerCount;

  private static Map<String, List<KMerInfo>> kmerInfo;

  private static Node cycleStart;

  private static Node cycleEnd;

  private static Node pathStart;

  private static int readLength;

  private static void init() {
    reads = new ArrayList<>();
    vertices = null;
    edges = new ArrayList<>();
    kmerCount = new HashMap<>();
    kmerInfo = new HashMap<>();
    cycleStart = null;
    cycleEnd = null;
    pathStart = null;
    readLength = 0;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);
    String read = scanner.next();
    while (read != null) {
      reads.add(read);
      read = scanner.next();
    }
    readLength = reads.get(0).length();
  }

  private static void writeOutput() {
    StringBuilder genome = new StringBuilder();
    Node curr = pathStart;
    genome.append(curr.vertex.label);
    curr = curr.next;
    while (curr != pathStart) {
      genome.append(curr.vertex.label.charAt(curr.vertex.label.length() - 1));
      curr = curr.next;
    }
    for (int i = readLength; i > 1; i--) {
      if (genome.substring(0, i).equals(genome.substring(genome.length() - i))) {
        genome.delete(genome.length() - i, genome.length());
        break;
      }
    }
    System.out.print(genome);
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    finalSolutionImpl();
    writeOutput();
  }

  private static void finalSolutionImpl() {
    buildDeBruijnGraph();
    removeTips();
    findBubbles();
    removeEdges();
    removeTips();
    buildCycle();
    findEulerianCycle();
  }

  private static void buildDeBruijnGraph() {
    int index = 0;
    Map<String, Integer> indices = new HashMap<>();
    for (int r = 0; r < reads.size(); r++) {
      String read1 = reads.get(r);
      for (int i = 0; i <= readLength - K; i++) {
        String kmer = read1.substring(i, i + K);

        if (kmerCount.containsKey(kmer)) {
          boolean sameKMer = false;
          kmerCount.put(kmer, kmerCount.get(kmer) + 1);
          List<KMerInfo> info = kmerInfo.get(kmer);
          for (KMerInfo kMerInfo : info) {
            String read2 = reads.get(kMerInfo.read);
            boolean overlap = kMerInfo.offset < i ?
                overlap(read1, read2, i - kMerInfo.offset) :
                overlap(read2, read1, kMerInfo.offset - i);
            if (overlap) {
              sameKMer = true;
              break;
            }
          }
          if (sameKMer) {
            continue;
          } else {
            int a = 10;
          }
          info.add(new KMerInfo(r, i));
        } else {
          kmerCount.put(kmer, 1);
          List<KMerInfo> info = new ArrayList<>();
          info.add(new KMerInfo(r, i));
          kmerInfo.put(kmer, info);
        }

        String fromLabel = kmer.substring(0, kmer.length() - 1);
        String toLabel = kmer.substring(1);

        if(!indices.containsKey(fromLabel)){
          indices.put(fromLabel, index);
          index++;
        }
        int fromIndex = indices.get(fromLabel);

        if(!indices.containsKey(toLabel)){
          indices.put(toLabel, index);
          index++;
        }
        int toIndex = indices.get(toLabel);

        edges.add(new Edge(fromIndex, toIndex));
      }
    }

    vertices = new Vertex[indices.size()];
    indices.forEach((l, i) -> vertices[i] = new Vertex(i, l));
    edges.forEach(edge -> {
      vertices[edge.from].outEdges.add(edge);
      vertices[edge.to].inEdges.add(edge);
    });
  }

  private static boolean overlap(String read1, String read2, int offset) {
    int mismatchCount = 0;
    for (int i = 0; i < readLength - offset; i++) {
      if (read1.charAt(offset + i) != read2.charAt(i)) {
        mismatchCount++;
      }
      if (mismatchCount > MISMATCH_LIMIT) {
        return false;
      }
    }
    return true;
  }

  private static void removeTips() {
    boolean run = true;
    while (run) {
      run = false;
      for (Vertex vertex : vertices) {
        if (vertex.inEdges.isEmpty() && vertex.outEdges.size() == 1) {
          List<Edge> edgesToRemove = findTipsForward(vertex);
          if (!edgesToRemove.isEmpty()) {
            run = true;
            removeEdges(edgesToRemove);
          }
        }
        if (vertex.inEdges.size() == 1 && vertex.outEdges.isEmpty()) {
          List<Edge> edgesToRemove = findTipsBackward(vertex);
          if (!edgesToRemove.isEmpty()) {
            run = true;
            removeEdges(edgesToRemove);
          }
        }
      }
    }
  }

  private static List<Edge> findTipsForward(Vertex curr) {
    List<Edge> edgesToRemove = new ArrayList<>();
    while (curr.outEdges.size() == 1) {
      Edge edge = curr.outEdges.get(0);
      edgesToRemove.add(edge);
      curr = vertices[edge.to];
      if (curr.inEdges.size() != 1) {
        break;
      }
      if (edgesToRemove.size() >= K) {
        edgesToRemove.clear();
        break;
      }
    }
    return edgesToRemove;
  }

  private static List<Edge> findTipsBackward(Vertex curr) {
    List<Edge> edgesToRemove = new ArrayList<>();
    while (curr.inEdges.size() == 1) {
      Edge edge = curr.inEdges.get(0);
      edgesToRemove.add(edge);
      curr = vertices[edge.from];
      if (curr.outEdges.size() != 1) {
        break;
      }
      if (edgesToRemove.size() >= K) {
        edgesToRemove.clear();
        break;
      }
    }
    return edgesToRemove;
  }

  private static void buildCycle() {
    // Find source
    List<Vertex> sources = new ArrayList<>();
    List<Vertex> sinks = new ArrayList<>();
    for (Vertex vertex : vertices) {
      if (vertex.inEdges.size() < vertex.outEdges.size()) {
        sources.add(vertex);
      }
      if (vertex.inEdges.size() > vertex.outEdges.size()) {
        sinks.add(vertex);
      }
    }
    if (sources.size() > 1) {
      throw new RuntimeException("More than one source found");
    }
    if (sinks.size() > 1) {
      throw new RuntimeException("More than one sink found");
    }
    if (sources.size() == 1 && sinks.size() == 1) {
      Vertex source = sources.get(0);
      Vertex sink = sinks.get(0);
      Edge edge = new Edge(sink.index, source.index);
      sink.outEdges.add(edge);
      source.inEdges.add(edge);
      edges.add(edge);
    } else if (!sources.isEmpty() || !sinks.isEmpty()) {
      throw new RuntimeException("Inconsistent graph");
    }
  }

  private static void findBubbles() {
    for (Vertex vertex : vertices) {
      if (vertex.outEdges.size() < 2) {
        continue;
      }
      findBubblesForward(vertex.index, vertex.index, 0, new boolean[vertices.length], new Stack<>());
    }
  }

  private static void findBubblesForward(int start, int curr, int count, boolean[] visited, Stack<Integer> forwardPath) {
    count++;

    if (count - 1 > K) {
      return;
    }

    visited[curr] = true;
    forwardPath.push(curr);

    for (Edge edge : vertices[curr].outEdges) {
      if (edge.blocked || edge.removed || visited[edge.to]) {
        continue;
      }
      edge.blocked = true;
      findBubblesForward(start, edge.to, count, visited, forwardPath);
      edge.blocked = false;
    }

    if (curr != start) {
      findBubblesBackward(start, curr, 0, visited, forwardPath, new Stack<>());
    }

    visited[curr] = false;
    forwardPath.pop();
  }

  private static void findBubblesBackward(int start, int curr, int count, boolean[] visited, Stack<Integer> forwardPath, Stack<Integer> backwardPath) {
    count++;

    if (count - 1 >= K) {
      return;
    }

    visited[curr] = true;
    backwardPath.push(curr);

    for (Edge edge : vertices[curr].inEdges) {
      if (edge.blocked || edge.removed) {
        continue;
      }
      if (edge.from == start) {
        backwardPath.push(start);
        handleBubble(forwardPath, backwardPath);
        backwardPath.pop();
      }
      if (visited[edge.from]) {
        continue;
      }
      edge.blocked = true;
      findBubblesBackward(start, edge.from, count, visited, forwardPath, backwardPath);
      edge.blocked = false;
    }

    visited[curr] = false;
    backwardPath.pop();
  }

  private static void handleBubble(Stack<Integer> forwardPath, Stack<Integer> backwardPath) {
    double sum = 0;
    double count = 0;

    // Compute coverage of forward path
    Vertex prev = vertices[forwardPath.get(0)];
    for (int i = 1; i < forwardPath.size(); i++) {
      Vertex curr = vertices[forwardPath.get(i)];
      String kmer = prev.label + curr.label.charAt(curr.label.length() - 1);
      sum += kmerCount.get(kmer);
      count++;
      prev = curr;
    }
    double forwardPathCoverage = sum / count;

    // Compute coverage of backward path
    prev = vertices[backwardPath.get(backwardPath.size() - 1)];
    for (int i = backwardPath.size() - 2; i >= 0; i--) {
      Vertex curr = vertices[backwardPath.get(i)];
      String kmer = prev.label + curr.label.charAt(curr.label.length() - 1);
      sum += kmerCount.get(kmer);
      count++;
      prev = curr;
    }
    double backwardPathCoverage = sum / count;

    // Mark edges to be removed
    Stack<Integer> verticesToRemove = forwardPathCoverage >= backwardPathCoverage ? backwardPath : forwardPath;
    if (forwardPathCoverage >= backwardPathCoverage) {
      int prevVertex = verticesToRemove.get(verticesToRemove.size() - 1);
      for (int i = verticesToRemove.size() - 2; i >= 0; i--) {
        int currVertex = verticesToRemove.get(i);
        Edge edgeToRemove = vertices[prevVertex].outEdges.stream().filter(edge -> edge.to == currVertex).findFirst().get();
        edgeToRemove.removed = true;
        prevVertex = currVertex;
      }
    } else {
      int prevVertex = verticesToRemove.get(0);
      for (int i = 1; i < verticesToRemove.size() - 1; i++) {
        int currVertex = verticesToRemove.get(i);
        Edge edgeToRemove = vertices[prevVertex].outEdges.stream().filter(edge -> edge.to == currVertex).findFirst().get();
        edgeToRemove.removed = true;
        prevVertex = currVertex;
      }
    }
  }

  private static void removeEdges() {
    List<Edge> edgesToRemove = edges.stream().filter(edge -> edge.removed).collect(Collectors.toList());
    for (Edge edge : edgesToRemove) {
      vertices[edge.from].outEdges.remove(edge);
      vertices[edge.to].inEdges.remove(edge);
    }
    edges.removeAll(edgesToRemove);
  }

  private static void removeEdges(List<Edge> edgesToRemove) {
    for (Edge edge : edgesToRemove) {
      vertices[edge.from].outEdges.remove(edge);
      vertices[edge.to].inEdges.remove(edge);
    }
    edges.removeAll(edgesToRemove);
  }

  private static void findEulerianCycle() {
    List<Vertex> remainingVertices =
        Arrays.stream(vertices)
            .filter(vertex -> !vertex.inEdges.isEmpty() && !vertex.outEdges.isEmpty())
            .collect(Collectors.toList());
    cycleStart = new Node(remainingVertices.get(0));
    cycleStart.next = cycleStart;
    cycleEnd = cycleStart;
    pathStart = cycleStart;
    findCycle(remainingVertices);
    while (!remainingVertices.isEmpty()) {
      selectNewCycleStart(remainingVertices);
      findCycle(remainingVertices);
    }
  }

  private static void findCycle(List<Vertex> remainingVertices) {
    Node curr = cycleEnd;
    while (true) {
      Node next = new Node(vertices[curr.vertex.outEdges.remove(0).to]);
      if (curr.vertex.outEdges.isEmpty()) {
        remainingVertices.remove(curr.vertex);
      }
      if (next.vertex == cycleStart.vertex) {
        break;
      }
      curr.next = next;
      curr = curr.next;
      curr.next = cycleStart;
    }
  }

  private static void selectNewCycleStart(List<Vertex> remainingVertices) {
    while (!remainingVertices.contains(cycleStart.vertex)) {
      cycleStart = cycleStart.next;
    }
    Node newStart = new Node(cycleStart.vertex);
    newStart.next = cycleStart.next;
    cycleEnd = cycleStart;
    cycleEnd.next = newStart;
    cycleStart = newStart;
  }

  private static class Vertex {

    final int index;

    final String label;

    final List<Edge> outEdges;

    final List<Edge> inEdges;

    Vertex(int index, String label) {
      this.index = index;
      this.label = label;
      this.outEdges = new ArrayList<>();
      this.inEdges = new ArrayList<>();
    }

    @Override
    public String toString() {
      return index + " (" + label + ")";
    }
  }

  private static class Edge {

    final int from;

    final int to;

    boolean blocked;

    boolean removed;

    public Edge(int from, int to) {
      this.from = from;
      this.to = to;
    }

    @Override
    public String toString() {
      return from + "->" + to;
    }
  }

  private static class Node {

    final Vertex vertex;

    Node next;

    public Node(Vertex vertex) {
      this.vertex = vertex;
    }

    @Override
    public String toString() {
      return vertex.toString();
    }
  }

  private static class KMerInfo {

    final int read;

    final int offset;

    KMerInfo(int read, int offset) {
      this.read = read;
      this.offset = offset;
    }
  }
}
