package br.com.eventhorizon.common.datastructures.sets;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class ArraySet<T> implements Iterable<T> {

  private Object[] keys;

  private int size;

  public ArraySet() {
    keys = new Object[0];
    size = 0;
  }

  public ArraySet(int initialCapacity) {
    keys = new Object[initialCapacity];
    size = 0;
  }

  public ArraySet(T[] keys) {
    this.keys = new Object[keys.length];
    for (T key : keys) {
      add(key);
    }
  }

  public boolean add(T key) {
    if (contains(key)) {
      return false;
    }
    if (size == keys.length) {
      increaseCapacity();
    }
    keys[size++] = key;
    return true;
  }

  public boolean remove(T key) {
    int index = find(key);
    if (index < 0) {
      return false;
    }
    System.arraycopy(keys, index + 1, keys, index, size - index - 1);
    size--;
    keys[size] = null;
    return true;
  }

  public boolean contains(T key) {
    return find(key) >= 0;
  }

  public void clear() {
    keys = new Object[0];
    size = 0;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public Object[] toArray() {
    return Arrays.copyOf(keys, size);
  }

  @SuppressWarnings("unchecked")
  public T[] toArray(T[] array) {
    if (array.length < size) {
      return (T[]) Arrays.copyOf(keys, size, array.getClass());
    }
    System.arraycopy(keys, 0, array, 0, size);
    if (array.length > size) {
      array[size] = null;
    }
    return array;
  }

  @Override
  public String toString() {
    StringJoiner str = new StringJoiner(", ", "ArraySet{size: " + size + ", keys: [", "]}");
    for (int i = 0; i < size; i++) {
      str.add(keys[i].toString());
    }
    return str.toString();
  }

  @Override
  public Iterator<T> iterator() {
    return new ArraySetIterator();
  }

  private int find(T key) {
    for (int i = 0; i < size; i++) {
      if (key.equals(keys[i])) {
        return i;
      }
    }
    return -1;
  }

  private void increaseCapacity() {
    keys = keys.length == 0 ? new Object[1] : Arrays.copyOf(keys, keys.length << 1);
  }

  private class ArraySetIterator implements Iterator<T> {

    private int index;

    @Override
    public boolean hasNext() {
      return index < size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T next() {
      if (index < size) {
        return (T) keys[index++];
      }
      throw new NoSuchElementException();
    }
  }
}
