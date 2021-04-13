package br.com.eventhorizon.common.datastructures.queues;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class UnorderedArrayPriorityQueue<T> {

  private final Type type;

  private final Comparator<? super T> comparator;

  private Object[] values;

  private int size;

  public UnorderedArrayPriorityQueue(Type type) {
    this.type = type;
    this.comparator = null;
    this.values = new Object[0];
  }

  public UnorderedArrayPriorityQueue(Type type, Comparator<? super T> comparator) {
    this.type = type;
    this.comparator = comparator;
    values = new Object[0];
  }

  public UnorderedArrayPriorityQueue(Type type, int initialCapacity) {
    this.type = type;
    this.comparator = null;
    this.values = new Object[initialCapacity];
  }

  public UnorderedArrayPriorityQueue(Type type, int initialCapacity, Comparator<? super T> comparator) {
    this.type = type;
    this.comparator = comparator;
    this.values = new Object[initialCapacity];
  }

  public UnorderedArrayPriorityQueue(Type type, T[] values) {
    this.type = type;
    this.comparator = null;
    this.values = Arrays.copyOf(values, values.length);
    this.size = values.length;
  }

  public UnorderedArrayPriorityQueue(Type type, T[] values, Comparator<? super T> comparator) {
    this.type = type;
    this.comparator = comparator;
    this.values = Arrays.copyOf(values, values.length);
    this.size = values.length;
  }

  public void add(T value) {
    if (size == values.length) {
      increaseCapacity();
    }
    values[size++] = value;
  }

  @SuppressWarnings("unchecked")
  public T peek() {
    if (size == 0 ) {
      throw new NoSuchElementException("Queue is empty");
    }
    return (T) values[type == Type.MIN ? findMin() : findMax()];
  }

  @SuppressWarnings("unchecked")
  public T poll() {
    if (size == 0 ) {
      throw new NoSuchElementException("Queue is empty");
    }
    int index = type == Type.MIN ? findMin() : findMax();
    T result = (T) values[index];
    System.arraycopy(values, index + 1, values, index, size - 1 - index);
    size--;
    return result;
  }

  @SuppressWarnings("unchecked")
  public T remove(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    T result = (T) values[index];
    System.arraycopy(values, index + 1, values, index, size - 1 - index);
    size--;
    return result;
  }

  @SuppressWarnings("unchecked")
  public T remove(T value) {
    int index = find(value);
    if (index == -1) {
      throw new NoSuchElementException();
    }
    T result = (T) values[index];
    System.arraycopy(values, index + 1, values, index, size - 1 - index);
    size--;
    return result;
  }

  public void replace(T oldValue, T newValue) {
    int i = find(oldValue);
    if (i >= 0) {
      values[i] = newValue;
    } else {
      throw new NoSuchElementException("oldValue is not in the queue");
    }
  }

  public void replace(int index, T value) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    values[index] = value;
  }

  public boolean contains(T value) {
    return find(value) >= 0;
  }

  private int find(T value) {
    for (int i = 0; i < size; i++) {
      if (value.equals(values[i])) {
        return i;
      }
    }
    return -1;
  }

  public void clear() {
    values = new Object[0];
    size = 0;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  @SuppressWarnings("unchecked")
  private int findMin() {
    int minIndex = 0;
    for (int i = 1; i < size; i++) {
      int compareResult;
      if (comparator != null) {
        compareResult = comparator.compare((T) values[i], (T) values[minIndex]);
      } else {
        compareResult = ((Comparable<? super T>)values[i]).compareTo((T) values[minIndex]);
      }
      if (compareResult < 0) {
        minIndex = i;
      }
    }
    return minIndex;
  }

  @SuppressWarnings("unchecked")
  private int findMax() {
    int maxIndex = 0;
    for (int i = 1; i < size; i++) {
      int compareResult;
      if (comparator != null) {
        compareResult = comparator.compare((T) values[i], (T) values[maxIndex]);
      } else {
        compareResult = ((Comparable<? super T>)values[i]).compareTo((T) values[maxIndex]);
      }
      if (compareResult > 0) {
        maxIndex = i;
      }
    }
    return maxIndex;
  }

  private void increaseCapacity() {
    if (values.length == 0) {
      values = new Object[1];
    } else {
      values = Arrays.copyOf(values, values.length << 1);
    }
  }

  public enum Type {
    MIN,
    MAX
  }
}
