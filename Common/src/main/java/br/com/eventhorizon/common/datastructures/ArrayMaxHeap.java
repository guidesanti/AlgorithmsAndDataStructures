package br.com.eventhorizon.common.datastructures;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class ArrayMaxHeap {

  private long[] values;

  private int size;

  public ArrayMaxHeap() {
    this.values = new long[0];
    this.size = 0;
  }

  public ArrayMaxHeap(int initialCapacity) {
    this.values = new long[initialCapacity];
    this.size = 0;
  }

  public ArrayMaxHeap(long[] values) {
    this.values = Arrays.copyOf(values, values.length << 1);
    this.size = values.length;
    buildMaxHeap();
  }

  public void add(long value) {
    if (size == values.length) {
      increaseCapacity();
    }
    values[size] = value;
    siftUp(size);
    size++;
  }

  public long getMax() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    return values[0];
  }

  public long removeMax() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    long max = values[0];
    values[0] = values[size - 1];
    size--;
    siftDown(0);
    return max;
  }

//  public long remove(long value) {
//    // TODO
//    return 0;
//  }
//
//  public void replace(long value, long newValue) {
//    // TODO
//  }

  public void clear() {
    values = new long[1];
    size = 0;
  }

  public boolean contains(long value) {
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

  public long[] toArray() {
    return Arrays.copyOf(values, size);
  }

  private boolean contains(long value, int i) {
    if (i >= size) {
      return false;
    }
    if (value > values[i]) {
      return false;
    }
    if (value == values[i]) {
      return true;
    }
    if (contains(value, leftChild(i))) {
      return true;
    }
    return contains(value, rightChild(i));
  }

  private void buildMaxHeap() {
    for (int i = values.length - 1; i >= 0; i--) {
      siftDown(i);
    }
  }

  private void siftUp(int i) {
    if (i == 0) {
      return;
    }
    int parent = parent(i);
    if (values[i] > values[parent]) {
      swap(i, parent);
      siftUp(parent);
    }
  }

  private void siftDown(int i) {
    int leftChild = leftChild(i);
    int rightChild = rightChild(i);
    if (leftChild >= size) {
      return;
    }
    int maxChild = leftChild;
    if (rightChild < size) {
      if (values[leftChild] < values[rightChild]) {
        maxChild = rightChild;
      }
    }
    if (values[i] < values[maxChild]) {
      swap(i, maxChild);
      siftDown(maxChild);
    }
  }

  private int parent(int i) {
    return (i - 1) / 2;
  }

  private int leftChild(int i) {
    return (i << 1) + 1;
  }

  private int rightChild(int i) {
    return (i << 1) + 2;
  }

  private void swap(int i, int j) {
    long temp = values[i];
    values[i] = values[j];
    values[j] = temp;
  }

  private void increaseCapacity() {
    int newCapacity = values.length == 0 ? 1 : values.length << 1;
    this.values = Arrays.copyOf(values, newCapacity);
  }

//  private void decreaseCapacity() {
//    this.values = Arrays.copyOf(values, values.length >> 1);
//  }
}
