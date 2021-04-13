package br.com.eventhorizon.common.datastructures.queues;

import br.com.eventhorizon.common.datastructures.ArrayHeap;

import java.util.Comparator;

public class HeapPriorityQueue<T> {

  private final ArrayHeap<T> heap;

  public HeapPriorityQueue(Type type) {
    if (type == Type.MIN) {
      heap = new ArrayHeap(ArrayHeap.Type.MIN);
    } else {
      heap = new ArrayHeap(ArrayHeap.Type.MAX);
    }
  }

  public HeapPriorityQueue(Type type, Comparator<? super T> comparator) {
    if (type == Type.MIN) {
      heap = new ArrayHeap(ArrayHeap.Type.MIN, comparator);
    } else {
      heap = new ArrayHeap(ArrayHeap.Type.MAX, comparator);
    }
  }

  public HeapPriorityQueue(Type type, int initialCapacity) {
    if (type == Type.MIN) {
      heap = new ArrayHeap(ArrayHeap.Type.MIN, initialCapacity);
    } else {
      heap = new ArrayHeap(ArrayHeap.Type.MAX, initialCapacity);
    }
  }

  public HeapPriorityQueue(Type type, int initialCapacity, Comparator<? super T> comparator) {
    if (type == Type.MIN) {
      heap = new ArrayHeap(ArrayHeap.Type.MIN, initialCapacity, comparator);
    } else {
      heap = new ArrayHeap(ArrayHeap.Type.MAX, initialCapacity, comparator);
    }
  }

  public HeapPriorityQueue(Type type, T[] values) {
    if (type == Type.MIN) {
      heap = new ArrayHeap(ArrayHeap.Type.MIN, values);
    } else {
      heap = new ArrayHeap(ArrayHeap.Type.MAX, values);
    }
  }

  public HeapPriorityQueue(Type type, T[] values, Comparator<? super T> comparator) {
    if (type == Type.MIN) {
      heap = new ArrayHeap(ArrayHeap.Type.MIN, values, comparator);
    } else {
      heap = new ArrayHeap(ArrayHeap.Type.MAX, values, comparator);
    }
  }

  public void add(T value) {
    heap.add(value);
  }

  public T peek() {
    return heap.peek();
  }

  public T poll() {
    return heap.poll();
  }

  public T remove(int index) {
    return heap.remove(index);
  }

  public T remove(T value) {
    return heap.remove(value);
  }

  public void replace(T oldValue, T newValue) {
    heap.replace(oldValue, newValue);
  }

  public void replace(int index, T value) {
    heap.replace(index, value);
  }

  public boolean contains(T value) {
    return heap.contains(value);
  }

  public void clear() {
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
}
