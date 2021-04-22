package br.com.eventhorizon.common.datastructures.queues;


import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class IndexedHeapPriorityQueue<T> {

  private final Type type;

  private final int capacity;

  private final Node[] nodes;

  private final Node[] heap;

  private int size;

  public IndexedHeapPriorityQueue(Type type, int capacity) {
    this.type = type;
    this.capacity = capacity;
    nodes = new IndexedHeapPriorityQueue.Node[capacity];
    heap = new IndexedHeapPriorityQueue.Node[capacity];
  }

  public IndexedHeapPriorityQueue(Type type, T[] keys) {
    this.type = type;
    this.capacity = keys.length;
    nodes = new IndexedHeapPriorityQueue.Node[capacity];
    this.heap = new IndexedHeapPriorityQueue.Node[capacity];
    for (int i = 0; i < capacity; i++) {
      nodes[i] = new Node(i, keys[i]);
      heap[i] =nodes[i];
    }
    size = capacity;
    buildHeap();
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
    heap[size] = node;
    siftUp(size);
    size++;
  }

  public Node peek() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    return heap[0];
  }

  public Node poll() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    Node node = heap[0];
    heap[0] = heap[size - 1];
    size--;
    siftDown(0);
    nodes[node.index] = null;
    return node;
  }

  public void replace(int index, T key) {
    if (index < 0 || index >= capacity) {
      throw new IndexOutOfBoundsException();
    }
    if (nodes[index] == null) {
      throw new NoSuchElementException();
    }
    int heapIndex = findByIndex(index);
    if (heapIndex < 0) {
      throw new NoSuchElementException();
    }
    Node newNode = new Node(index, key);
    nodes[index] = newNode;
    heap[heapIndex] = newNode;
    if (heapIndex == 0) {
      siftDown(heapIndex);
    } else {
      if (type == Type.MIN) {
        if (compare(key, heap[parent(heapIndex)].key) < 0) {
          siftUp(heapIndex);
        } else {
          siftDown(heapIndex);
        }
      } else {
        if (compare(key, heap[parent(heapIndex)].key) > 0) {
          siftUp(heapIndex);
        } else {
          siftDown(heapIndex);
        }
      }
    }
  }

  public boolean contains(int index) {
    return nodes[index] != null;
  }

  public boolean contains(T key) {
    if (size == 0) {
      return false;
    }
    return findByKey(key, 0) >= 0;
  }

  public void clear() {
    Arrays.fill(nodes, null);
    Arrays.fill(heap, null);
    size = 0;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  @Override
  public String toString() {
    StringJoiner str = new StringJoiner(",", "IndexedHeapPriorityQueue{size:" + size + " elements:[", "]}");
    for (int i = 0; i < size; i++) {
      str.add(heap[i].toString());
    }
    return str.toString();
  }

  private int findByKey(T key, int startIndex) {
    if (startIndex >= size) {
      return -1;
    }
    if (type == Type.MIN) {
      if (compare(key, heap[startIndex].key) < 0) {
        return -1;
      }
    } else {
      if (compare(key, heap[startIndex].key) > 0) {
        return -1;
      }
    }
    if (key.equals(heap[startIndex].key)) {
      return startIndex;
    }
    int found = findByKey(key, leftChild(startIndex));
    if (found >= 0) {
      return found;
    }
    return findByKey(key, rightChild(startIndex));
  }

  private int findByIndex(int index) {
    for (int heapIndex = 0; heapIndex < size; heapIndex++) {
      if (heap[heapIndex].index == index) {
        return heapIndex;
      }
    }
    return -1;
  }

  private void buildHeap() {
    for (int i = size - 1; i >= 0; i--) {
      siftDown(i);
    }
  }

  private void siftUp(int i) {
    if (i <= 0) {
      return;
    }
    int parent = parent(i);
    if (parent < 0) {
      return;
    }
    if (type == Type.MIN) {
      if (compare(heap[i].key, heap[parent].key) < 0) {
        swap(i, parent);
        siftUp(parent);
      }
    } else {
      if (compare(heap[i].key, heap[parent].key) > 0) {
        swap(i, parent);
        siftUp(parent);
      }
    }
  }

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
    if (type == Type.MIN) {
      if (rightChild < size) {
        if (compare(heap[leftChild].key, heap[rightChild].key) > 0) {
          selectedChild = rightChild;
        }
      }
      if (compare(heap[i].key, heap[selectedChild].key) > 0) {
        swap(i, selectedChild);
        siftDown(selectedChild);
      }
    } else {
      if (rightChild < size) {
        if (compare(heap[leftChild].key, heap[rightChild].key) < 0) {
          selectedChild = rightChild;
        }
      }
      if (compare(heap[i].key, heap[selectedChild].key) < 0) {
        swap(i, selectedChild);
        siftDown(selectedChild);
      }
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
    Node temp = heap[i];
    heap[i] = heap[j];
    heap[j] = temp;
  }

  private int compare(T key1, T key2) {
    return ((Comparable<? super T>)key1).compareTo(key2);
  }

  public enum Type {
    MIN,
    MAX
  }

  public class Node {

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
    public String toString() {
      return "Node{index: " + index + ", key: " + key + "}";
    }
  }
}
