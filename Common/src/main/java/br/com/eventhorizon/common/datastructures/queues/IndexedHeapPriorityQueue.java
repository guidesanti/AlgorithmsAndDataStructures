package br.com.eventhorizon.common.datastructures.queues;

import br.com.eventhorizon.common.datastructures.ArrayHeap;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class IndexedHeapPriorityQueue<T> {

  private final Node[] nodes;

  private final ArrayHeap<Node> heap;

  @SuppressWarnings("unchecked")
  public IndexedHeapPriorityQueue(Type type, int maxIndex) {
    nodes = new IndexedHeapPriorityQueue.Node[maxIndex];
    heap = type == Type.MIN ?
        new ArrayHeap<>(ArrayHeap.Type.MIN) :
        new ArrayHeap<>(ArrayHeap.Type.MAX);
  }

  @SuppressWarnings("unchecked")
  public IndexedHeapPriorityQueue(Type type, T[] keys) {
    nodes = new IndexedHeapPriorityQueue.Node[keys.length];
    for (int i = 0; i < keys.length; i++) {
      nodes[i] = new Node(i, keys[i]);
    }
    heap = type == Type.MIN ?
        new ArrayHeap<>(ArrayHeap.Type.MIN, nodes) :
        new ArrayHeap<>(ArrayHeap.Type.MAX, nodes);
  }

  public void add(int index, T key) {
    if (index < 0 || index >= nodes.length) {
      throw new IndexOutOfBoundsException();
    }
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
    if (index < 0 || index >= nodes.length) {
      throw new IndexOutOfBoundsException();
    }
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

  public enum Type {
    MIN,
    MAX
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
