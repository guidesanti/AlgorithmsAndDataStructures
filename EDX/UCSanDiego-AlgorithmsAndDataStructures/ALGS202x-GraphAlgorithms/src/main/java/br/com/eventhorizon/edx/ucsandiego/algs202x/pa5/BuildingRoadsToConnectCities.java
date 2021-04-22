package br.com.eventhorizon.edx.ucsandiego.algs202x.pa5;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

public class BuildingRoadsToConnectCities implements PA {

  @Override
  public void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);
    WeightedUndirectedGraph graph = readGraph(scanner);
    WeightedUndirectedGraphKruskalMinimumSpanningTree mst = new WeightedUndirectedGraphKruskalMinimumSpanningTree(graph);
    System.out.printf("%.9f", mst.weight());
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    WeightedUndirectedGraph graph = readGraph(scanner);
    WeightedUndirectedGraphPrimMinimumSpanningTree mst = new WeightedUndirectedGraphPrimMinimumSpanningTree(graph);
    System.out.printf("%.9f", mst.weight());
  }

  private static WeightedUndirectedGraph readGraph(FastScanner scanner) {
    int numberOfVertices = scanner.nextInt();
    WeightedUndirectedGraph graph = new WeightedUndirectedGraph(numberOfVertices);
    int[] x = new int[numberOfVertices];
    int[] y = new int[numberOfVertices];
    for (int i = 0; i < numberOfVertices; i++) {
      x[i] = scanner.nextInt();
      y[i] = scanner.nextInt();
    }
    for (int i = 0; i < numberOfVertices; i++) {
      for (int j = i + 1; j < numberOfVertices; j++) {
        double distance = Math.sqrt(
            ((x[i] - x[j]) * (x[i] - x[j])) +
            ((y[i] - y[j]) * (y[i] - y[j])));
        graph.addEdge(i, j, distance);
      }
    }
    return graph;
  }

  private static class WeightedUndirectedEdge implements Comparable<WeightedUndirectedEdge> {

    private final int hashCode;

    protected final int vertex1;

    protected final int vertex2;

    protected final double weight;

    public WeightedUndirectedEdge(int vertex1, int vertex2, double weight) {
      String str = vertex1 < vertex2 ? vertex1 + ":" + vertex2 : vertex2 + ":" + vertex1;
      str += ":" + weight;
      hashCode = str.hashCode();
      this.vertex1 = vertex1;
      this.vertex2 = vertex2;
      this.weight = weight;
    }

    public int vertex1() {
      return vertex1;
    }

    public int vertex2() {
      return vertex2;
    }

    public int either() {
      return vertex1;
    }

    public int other(int vertex) {
      if (vertex == vertex1) {
        return vertex2;
      } else if (vertex == vertex2) {
        return vertex1;
      } else {
        throw new IllegalArgumentException("Invalid vertex for this edge");
      }
    }

    public double weight() {
      return weight;
    }

    @Override
    public String toString() {
      return "WeightedDirectEdge {(" + weight + ") " + vertex1 + " - " + vertex2 + "}";
    }

    @Override
    public int hashCode() {
      return hashCode;
    }

    @Override
    public boolean equals(Object object) {
      if (object == null) {
        return false;
      }
      if (!(object instanceof WeightedUndirectedEdge)) {
        return false;
      }
      WeightedUndirectedEdge other = (WeightedUndirectedEdge) object;
      return (this.vertex1 == other.vertex1 && this.vertex2 == other.vertex2
          && this.weight == other.weight) || (this.vertex1 == other.vertex2
          && this.vertex2 == other.vertex1 && this.weight == other.weight);
    }

    @Override
    public int compareTo(WeightedUndirectedEdge other) {
      return Double.compare(this.weight, other.weight);
    }
  }

  private static class WeightedUndirectedGraph {

    private final int numberOfVertices;

    private final LinkedList<WeightedUndirectedEdge>[] adjacencies;

    private final LinkedList<WeightedUndirectedEdge> edges;

    public WeightedUndirectedGraph(int numberOfVertices) {
      this.numberOfVertices = numberOfVertices;
      this.adjacencies = new LinkedList[numberOfVertices];
      for (int i = 0; i < numberOfVertices; i++) {
        this.adjacencies[i] = new LinkedList<>();
      }
      this.edges = new LinkedList<>();
    }

    public void addEdge(int vertex1, int vertex2, double weight) {
      if (vertex1 < 0 || vertex1 >= numberOfVertices ||
          vertex2 < 0 || vertex2 >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      WeightedUndirectedEdge
          edge = new WeightedUndirectedEdge(vertex1, vertex2, weight);
      adjacencies[vertex1].addLast(edge);
      adjacencies[vertex2].addLast(edge);
      edges.addLast(edge);
    }

    public void addEdge(WeightedUndirectedEdge edge) {
      if (edge == null) {
        throw new IllegalArgumentException("edge cannot be null");
      }
      int vertex1 = edge.vertex1;
      int vertex2 = edge.vertex2;
      if (vertex1 < 0 || vertex1 >= numberOfVertices ||
          vertex2 < 0 || vertex2 >= numberOfVertices) {
        throw new IllegalArgumentException("Invalid edge, vertex1 and/or vertex2 cannot be less than 0 or greater than or equal number of vertices in the graph");
      }
      adjacencies[vertex1].addLast(edge);
      adjacencies[vertex2].addLast(edge);
      edges.addLast(edge);
    }

    public int numberOfVertices() {
      return numberOfVertices;
    }

    public int numberOfEdges() {
      return edges.size();
    }

    public LinkedList<WeightedUndirectedEdge> adjacencies(int vertex) {
      if (vertex < 0 || vertex >= numberOfVertices) {
        throw new IndexOutOfBoundsException();
      }
      return adjacencies[vertex];
    }

    public LinkedList<WeightedUndirectedEdge> edges() {
      return edges;
    }
  }

  private static class WeightedUndirectedGraphKruskalMinimumSpanningTree {

    private final LinkedList<WeightedUndirectedEdge> minimumSpanningTree;

    private double minimumSpanningTreeWeight;

    public WeightedUndirectedGraphKruskalMinimumSpanningTree(WeightedUndirectedGraph graph) {
      minimumSpanningTree = new LinkedList<>();
      RootedTreeDisjointSets.Node[] disjointSets = new RootedTreeDisjointSets.Node[graph.numberOfVertices()];
      PriorityQueue<WeightedUndirectedEdge> queue = new PriorityQueue<>();
      graph.edges().forEach(queue::add);
      for (int vertex = 0; vertex < graph.numberOfVertices(); vertex++) {
        disjointSets[vertex] = RootedTreeDisjointSets.build(vertex);
      }
      while (!queue.isEmpty() && minimumSpanningTree.size() < graph.numberOfVertices() - 1) {
        WeightedUndirectedEdge edge = queue.poll();
        RootedTreeDisjointSets.Node node1 = RootedTreeDisjointSets.find(disjointSets[edge.vertex1()]);
        RootedTreeDisjointSets.Node node2 = RootedTreeDisjointSets.find(disjointSets[edge.vertex2()]);
        if (node1.equals(node2)) {
          continue;
        }
        RootedTreeDisjointSets.union(node1, node2);
        minimumSpanningTree.addLast(edge);
        minimumSpanningTreeWeight += edge.weight;
      }
    }

    public double weight() {
      return minimumSpanningTreeWeight;
    }

    public Iterable<WeightedUndirectedEdge> edges() {
      return minimumSpanningTree;
    }
  }

  private static class RootedTreeDisjointSets {

    private RootedTreeDisjointSets() { }

    public static <T> RootedTreeDisjointSets.Node<T> build(T object) {
      if (object == null) {
        throw new IllegalArgumentException("object cannot be null");
      }
      return new RootedTreeDisjointSets.Node<>(object);
    }

    public static <T> RootedTreeDisjointSets.Node<T> union(RootedTreeDisjointSets.Node<T> node1, RootedTreeDisjointSets.Node<T> node2) {
      Node<T> root1 = findRoot(node1);
      RootedTreeDisjointSets.Node<T> root2 = findRoot(node2);
      if (root1 == root2) {
        return root1;
      }
      if (root1.rank < root2.rank) {
        root1.parent = root2;
        root2.rank++;
        return root2;
      } else {
        root2.parent = root1;
        root1.rank++;
        return root1;
      }
    }

    public static <T> RootedTreeDisjointSets.Node<T> find(RootedTreeDisjointSets.Node<T> node) {
      if (node == null) {
        return null;
      }
      return findRoot(node);
    }

    private static <T> RootedTreeDisjointSets.Node<T> findRoot(RootedTreeDisjointSets.Node<T> node) {
      if (node.parent != node) {
        node.parent = findRoot(node.parent);
      }
      return node.parent;
    }

    public static class Node<T> {

      protected Node<T> parent;

      protected int rank;

      private final T object;

      public Node(T object) {
        this.parent = this;
        this.rank = 0;
        this.object = object;
      }

      public T getObject() {
        return object;
      }
    }
  }

  private static class WeightedUndirectedGraphPrimMinimumSpanningTree {

    private final WeightedUndirectedEdge[] edgeTo;

    private double minimumSpanningTreeWeight;

    public WeightedUndirectedGraphPrimMinimumSpanningTree(WeightedUndirectedGraph graph) {
      boolean[] marked = new boolean[graph.numberOfVertices()];
      double[] distanceTo = new double[graph.numberOfVertices()];
      edgeTo = new WeightedUndirectedEdge[graph.numberOfVertices()];
      IndexedHeapPriorityQueue queue = new IndexedHeapPriorityQueue(graph.numberOfVertices());

      marked[0] = true;
      for (int vertex = 0; vertex < graph.numberOfVertices(); vertex++) {
        distanceTo[vertex] = Double.POSITIVE_INFINITY;
      }
      distanceTo[0] = 0.0;
      queue.add(0, 0.0);

      while (!queue.isEmpty()) {
        IndexedHeapPriorityQueue.Node node = queue.poll();
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

  private static class IndexedHeapPriorityQueue {

    private final IndexedHeapPriorityQueue.Node[] nodes;

    private final MinHeap<IndexedHeapPriorityQueue.Node> heap;

    public IndexedHeapPriorityQueue(int maxIndex) {
      nodes = new IndexedHeapPriorityQueue.Node[maxIndex];
      heap = new MinHeap<>(maxIndex);
    }

    public void add(int index, double key) {
      if (index < 0 || index >= nodes.length) {
        throw new IndexOutOfBoundsException();
      }
      if (nodes[index] != null) {
        throw new IllegalArgumentException("index is already added");
      }
      IndexedHeapPriorityQueue.Node node = new Node(index, key);
      nodes[index] = node;
      heap.add(node);
    }

    public IndexedHeapPriorityQueue.Node peek() {
      return heap.peek();
    }

    public IndexedHeapPriorityQueue.Node poll() {
      IndexedHeapPriorityQueue.Node node = heap.poll();
      nodes[node.index] = null;
      return node;
    }

    public void replace(int index, double key) {
      if (index < 0 || index >= nodes.length) {
        throw new IndexOutOfBoundsException();
      }
      if (nodes[index] == null) {
        throw new NoSuchElementException();
      }
      IndexedHeapPriorityQueue.Node oldNode = nodes[index];
      IndexedHeapPriorityQueue.Node newNode = new Node(index, key);
      nodes[index] = newNode;
      heap.replace(oldNode, newNode);
    }

    public boolean contains(int index) {
      return nodes[index] != null;
    }

    public boolean contains(double key) {
      return heap.contains(new Node(0, key));
    }

    public void clear() {
      Arrays.fill(nodes, null);
      heap.clear();
    }

    public boolean isEmpty() {
      return heap.isEmpty();
    }

    public int size() {
      return heap.size();
    }

    public static class Node implements Comparable<Node> {

      private final int index;

      private final double key;

      public Node(int index, double key) {
        this.index = index;
        this.key = key;
      }

      public int index() {
        return index;
      }

      public double key() {
        return key;
      }

      @Override
      public String toString() {
        return "Node{" + "index=" + index + ", key=" + key + '}';
      }

      @Override
      public int compareTo(IndexedHeapPriorityQueue.Node node) {
        return ((Comparable<? super Double>)this.key).compareTo(node.key);
      }
    }
  }

  private static class MinHeap<T> {

    private Object[] values;

    private int size;

    public MinHeap(int initialCapacity) {
      this.values = new Object[initialCapacity];
    }

    public void add(T key) {
      if (size == values.length) {
        increaseCapacity();
      }
      values[size] = key;
      siftUp(size);
      size++;
    }

    @SuppressWarnings("unchecked")
    public T peek() {
      if (size == 0) {
        throw new NoSuchElementException();
      }
      return (T) values[0];
    }

    @SuppressWarnings("unchecked")
    public T poll() {
      if (size == 0) {
        throw new NoSuchElementException();
      }
      T root = (T) values[0];
      values[0] = values[size - 1];
      size--;
      siftDown(0);
      return root;
    }

    @SuppressWarnings("unchecked")
    public void replace(T oldValue, T newValue) {
      int index = find(oldValue);
      if (index < 0) {
        throw new NoSuchElementException();
      }
      values[index] = newValue;
      if (index == 0) {
        siftDown(index);
      } else {
        if (compare(newValue, (T) values[parent(index)]) < 0) {
          siftUp(index);
        } else {
          siftDown(index);
        }
      }
    }

    public void clear() {
      values = new Object[0];
      size = 0;
    }

    public boolean contains(T key) {
      if (size == 0) {
        return false;
      }
      return find(key, 0) >= 0;
    }

    public int find(T key) {
      if (size == 0) {
        throw new NoSuchElementException();
      }
      int found = find(key, 0);
      if (found >= 0) {
        return found;
      }
      throw new NoSuchElementException();
    }

    public boolean isEmpty() {
      return size == 0;
    }

    public int size() {
      return size;
    }

    @SuppressWarnings("unchecked")
    private int find(T key, int index) {
      if (index >= size) {
        return -1;
      }
      if (compare(key, (T) values[index]) < 0) {
        return -1;
      }
      if (compare(key, (T) values[index]) == 0) {
        if (key.equals(values[index])) {
          return index;
        }
      }
      int found = find(key, leftChild(index));
      if (found >= 0) {
        return found;
      }
      return find(key, rightChild(index));
    }

    private void buildHeap() {
      for (int i = values.length - 1; i >= 0; i--) {
        siftDown(i);
      }
    }

    @SuppressWarnings("unchecked")
    private void siftUp(int i) {
      if (i <= 0) {
        return;
      }
      int parent = parent(i);
      if (parent < 0) {
        return;
      }
      if (compare((T) values[i], (T) values[parent]) < 0) {
        swap(i, parent);
        siftUp(parent);
      }
    }

    @SuppressWarnings("unchecked")
    private void siftDown(int i) {
      if (i >= size) {
        return;
      }
      int leftChild = leftChild(i);
      int rightChild = rightChild(i);
      if (leftChild >= size) {
        return;
      }
      int selectedChild = leftChild;
      if (rightChild < size) {
        if (compare((T) values[leftChild], (T) values[rightChild]) > 0) {
          selectedChild = rightChild;
        }
      }
      if (compare((T) values[i], (T) values[selectedChild]) > 0) {
        swap(i, selectedChild);
        siftDown(selectedChild);
      }
    }

    private int parent(int i) {
      return (i - 1) >> 1;
    }

    private int leftChild(int i) {
      return (i << 1) + 1;
    }

    private int rightChild(int i) {
      return (i << 1) + 2;
    }

    private void swap(int i, int j) {
      Object temp = values[i];
      values[i] = values[j];
      values[j] = temp;
    }

    private void increaseCapacity() {
      int newCapacity = values.length == 0 ? 1 : values.length << 1;
      this.values = Arrays.copyOf(values, newCapacity);
    }

    @SuppressWarnings("unchecked")
    private int compare(T key1, T key2) {
      return ((Comparable<? super T>)key1).compareTo(key2);
    }

    @Override
    public String toString() {
      StringJoiner str = new StringJoiner(", ", "MinHeap {", "}");
      for (int i = 0; i < size; i++) {
        str.add(values[i].toString());
      }
      return str.toString();
    }
  }
}
