package br.com.eventhorizon.edx.ucsandiego.algs202x.pa4;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Stack;

public class MinimumCostOfFlight implements PA {

  @Override
  public void naiveSolution() {
    finalSolution();
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    WeightedDirectedGraph graph = readGraph(scanner);
    int sourceVertex = scanner.nextInt() - 1;
    int destinationVertex = scanner.nextInt() - 1;
    WeightedDirectedGraphShortestPath shortestPaths = new WeightedDirectedGraphShortestPath(graph, sourceVertex);
    System.out.println("" + (shortestPaths.hasPathTo(destinationVertex) ? shortestPaths.distanceTo(destinationVertex) : -1));
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
        return index;
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
  }

  private static class IndexedMinPriorityQueue<T> {

    private final Node[] nodes;

    private final MinHeap<Node> heap;

    @SuppressWarnings("unchecked")
    public IndexedMinPriorityQueue(int maxIndex) {
      nodes = new IndexedMinPriorityQueue.Node[maxIndex];
      heap = new MinHeap<>(maxIndex);
    }

    public void add(int index, T key) {
      if (nodes[index] != null) {
        throw new IllegalArgumentException("index is already added");
      }
      Node node = new Node(index, key);
      nodes[index] = node;
      heap.add(node);
    }

    public Node peek() {
      return heap.peek();
    }

    public Node poll() {
      Node node = heap.poll();
      nodes[node.index] = null;
      return node;
    }

    public void replace(int index, T key) {
      if (nodes[index] == null) {
        throw new NoSuchElementException();
      }
      Node oldNode = nodes[index];
      Node newNode = new Node(index, key);
      nodes[index] = newNode;
      heap.replace(oldNode, newNode);
    }

    public boolean contains(int index) {
      return nodes[index] != null;
    }

    public boolean contains(T key) {
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

    public class Node implements Comparable<Node> {

      private final int index;

      private final T key;

      public Node(int index, T key) {
        this.index = index;
        this.key = key;
      }

      public int index() {
        return index;
      }

      public T key() {
        return key;
      }

      @Override
      @SuppressWarnings("unchecked")
      public int compareTo(Node node) {
        return ((Comparable<? super T>)this.key).compareTo(node.key);
      }
    }
  }

  private static class WeightedDirectedGraphShortestPath {

    private final int sourceVertex;

    private final WeightedDirectedGraph graph;

    private final WeightedDirectEdge[] edgeTo;

    private final int[] distanceTo;

    public WeightedDirectedGraphShortestPath(WeightedDirectedGraph graph, int sourceVertex) {
      this.sourceVertex = sourceVertex;
      this.graph = graph;
      this.edgeTo = new WeightedDirectEdge[graph.numberOfVertices()];
      this.distanceTo = new int[graph.numberOfVertices()];
      init();
      processGraph();
    }

    public int distanceTo(int destinationVertex) {
      return distanceTo[destinationVertex];
    }

    public boolean hasPathTo(int destinationVertex) {
      return distanceTo[destinationVertex] != Integer.MAX_VALUE;
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

    private void init() {
      Arrays.fill(distanceTo, Integer.MAX_VALUE);
      distanceTo[sourceVertex] = 0;
    }

    private void processGraph() {
      IndexedMinPriorityQueue<Integer> queue = new IndexedMinPriorityQueue<>(graph.numberOfVertices());
      queue.add(sourceVertex, 0);
      while (!queue.isEmpty()) {
        int vertex = queue.poll().index();
        graph.adjacencies(vertex).forEach(edge -> relax(edge, queue));
      }
    }

    private void relax(WeightedDirectEdge edge, IndexedMinPriorityQueue<Integer> priorityQueue) {
      int from = edge.from();
      int to = edge.to();
      int weight = edge.weight();
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
}
