package br.com.eventhorizon.common.datastructures;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

public class ArrayHeap<T> {

  private final Type type;

  private Object[] values;

  private int size;

  private final BiFunction<T, T, Boolean> compareUp;

  private final BiFunction<T, T, Boolean> compareDown;

  @SuppressWarnings("unchecked")
  public ArrayHeap(Type type) {
    this.type = type;
    this.values = new Object[0];
    this.size = 0;
    switch (type) {
      case MIN:
        compareUp = (a, b) -> ((Comparable<? super T>)a).compareTo(b) < 0;
        compareDown = (a, b) -> ((Comparable<? super T>)a).compareTo(b) > 0;
        break;
      case MAX:
      default:
        compareUp = (a, b) -> ((Comparable<? super T>)a).compareTo(b) > 0;
        compareDown = (a, b) -> ((Comparable<? super T>)a).compareTo(b) < 0;
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
  public T remove(T key) {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    int index = find(key, 0);
    if (index == -1) {
      throw new NoSuchElementException();
    }
    T removedKey = (T) values[index];
    values[index] = values[size - 1];
    values[size - 1] = null;
    size--;
    if (index == 0) {
      siftDown(index);
    } else if (index < size) {
      if (compareUp.apply((T) values[index], (T) values[parent(index)])) {
        siftUp(index);
      } else {
        siftDown(index);
      }
    }
    return removedKey;
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
      if (type == Type.MIN) {
        if (compare(newValue, (T) values[parent(index)]) < 0) {
          siftUp(index);
        } else {
          siftDown(index);
        }
      } else {
        if (compare(newValue, (T) values[parent(index)]) > 0) {
          siftUp(index);
        } else {
          siftDown(index);
        }
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

  public Object[] toArray() {
    return Arrays.copyOf(values, size);
  }

  @SuppressWarnings("unchecked")
  public T[] toArray(T[] array) {
    if (array.length < size) {
      return (T[]) Arrays.copyOf(values, size, array.getClass());
    }
    System.arraycopy(values, 0, array, 0, size);
    if (array.length > size) {
      array[size] = null;
    }
    return array;
  }

  @SuppressWarnings("unchecked")
  private int find(T key, int index) {
    if (index >= size) {
      return -1;
    }
    if (compareUp.apply(key, (T) values[index])) {
      return -1;
    }
    if (key.equals(values[index])) {
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
    if (compareUp.apply((T) values[i], (T) values[parent])) {
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

  @SuppressWarnings("unchecked")
  private int compare(T key1, T key2) {
    return ((Comparable<? super T>)key1).compareTo(key2);
  }

  public enum Type {
    MIN,
    MAX
  }
}
