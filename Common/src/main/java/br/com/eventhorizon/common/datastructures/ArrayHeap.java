package br.com.eventhorizon.common.datastructures;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

public class ArrayHeap<T extends Comparable<T>> {

  private Object[] values;

  private int size;

  private final BiFunction<T, T, Boolean> compareUp;

  private final BiFunction<T, T, Boolean> compareDown;

  public ArrayHeap(Type type) {
    this.values = new Object[0];
    this.size = 0;
    switch (type) {
      case MIN:
        compareUp = (a, b) -> a.compareTo(b) < 0;
        compareDown = (a, b) -> a.compareTo(b) > 0;
        break;
      case MAX:
      default:
        compareUp = (a, b) -> a.compareTo(b) > 0;
        compareDown = (a, b) -> a.compareTo(b) < 0;
        break;
    }
  }

  public ArrayHeap(Type type, int initialCapacity) {
    this(type);
    this.values = new Object[initialCapacity];
  }

  public ArrayHeap(Type type, T[] values) {
    this(type);
    this.values = Arrays.copyOf(values, values.length << 1);
    this.size = values.length;
    buildHeap();
  }

  public int add(T key) {
    if (size == values.length) {
      increaseCapacity();
    }
    values[size] = key;
    siftUp(size);
    return size++;
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
    T max = (T) values[0];
    values[0] = values[size - 1];
    size--;
    siftDown(0);
    return max;
  }

  public T remove(T key) {
    // TODO
    return null;
  }

  public T remove(int index) {
    // TODO
    return null;
  }

  public void replace(T key, T newKey) {
    // TODO
  }

  public void replace(int index, T key) {
    // TODO
  }

  public void clear() {
    values = new Object[0];
    size = 0;
  }

  public boolean contains(T value) {
    if (size == 0) {
      return false;
    }
    return contains(value, 0);
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public Object[] toArray() {
    return Arrays.copyOf(values, size);
  }

  @SuppressWarnings("unchecked")
  private boolean contains(T key, int i) {
    if (i >= size) {
      return false;
    }
    if (compareUp.apply(key, (T) values[i])) {
      return false;
    }
    if (key.equals(values[i])) {
      return true;
    }
    if (contains(key, leftChild(i))) {
      return true;
    }
    return contains(key, rightChild(i));
  }

  private void buildHeap() {
    for (int i = values.length - 1; i >= 0; i--) {
      siftDown(i);
    }
  }

  @SuppressWarnings("unchecked")
  private void siftUp(int i) {
    if (i == 0) {
      return;
    }
    int parent = parent(i);
    if (compareUp.apply((T) values[i], (T) values[parent])) {
      swap(i, parent);
      siftUp(parent);
    }
  }

  @SuppressWarnings("unchecked")
  private void siftDown(int i) {
    int leftChild = leftChild(i);
    int rightChild = rightChild(i);
    if (leftChild >= size) {
      return;
    }
    int selectedChild = leftChild;
    if (rightChild < size) {
      if (compareDown.apply((T) values[leftChild], (T) values[rightChild])) {
        selectedChild = rightChild;
      }
    }
    if (compareDown.apply((T) values[i], (T) values[selectedChild])) {
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

  public enum Type {
    MIN,
    MAX
  }
}
