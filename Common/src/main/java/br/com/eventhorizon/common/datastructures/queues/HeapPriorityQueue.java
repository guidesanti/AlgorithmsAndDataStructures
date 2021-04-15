package br.com.eventhorizon.common.datastructures.queues;

import br.com.eventhorizon.common.datastructures.ArrayHeap;

import java.util.Comparator;

public class HeapPriorityQueue<T> {

  private final ArrayHeap<T> heap;

  public HeapPriorityQueue(Type type) {
    heap = type == Type.MIN ?
        new ArrayHeap<>(ArrayHeap.Type.MIN) :
        new ArrayHeap<>(ArrayHeap.Type.MAX);
  }

  public HeapPriorityQueue(Type type, int initialCapacity) {
    heap = type == Type.MIN ?
        new ArrayHeap<>(ArrayHeap.Type.MIN, initialCapacity) :
        new ArrayHeap<>(ArrayHeap.Type.MAX, initialCapacity);
  }

  public HeapPriorityQueue(Type type, T[] values) {
    heap = type == Type.MIN ?
        new ArrayHeap<>(ArrayHeap.Type.MIN, values) :
        new ArrayHeap<>(ArrayHeap.Type.MAX, values);
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

  public T remove(T value) {
    return heap.remove(value);
  }

  public void replace(T oldValue, T newValue) {
    heap.replace(oldValue, newValue);
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
