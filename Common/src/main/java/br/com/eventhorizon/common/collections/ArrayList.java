package br.com.eventhorizon.common.collections;

import java.util.Arrays;

public class ArrayList implements List {

  private static int DEFAULT_INITIAL_CAPACITY = 1024;

  private long[] values;

  private int size;

  public ArrayList() {
    this.values = new long[DEFAULT_INITIAL_CAPACITY];
    this.size = 0;
  }

  public ArrayList(int initialCapacity) {
    this.values = new long[initialCapacity];
    this.size = 0;
  }

  public ArrayList(long[] values) {
    this.values = Arrays.copyOf(values, values.length << 1);
    this.size = values.length;
  }

  @Override
  public void add(int index, long value) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException();
    }
    if (index == values.length) {
      increaseCapacity();
    }
    for (int i = size; i > index; i--) {
      values[i] = values[i - 1];
    }
    values[index] = value;
    size++;
  }

  @Override
  public long get(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    return values[index];
  }

  @Override
  public long remove(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    size--;
    return values[index];
  }

  @Override
  public void replace(int index, long value) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    values[index] = value;
  }

  @Override
  public List subList(int fromIndex, int toIndex) {
    if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) {
      throw new IndexOutOfBoundsException();
    }
    return new ArrayList(Arrays.copyOfRange(values, fromIndex, toIndex));
  }

  @Override
  public void clear() {
    size = 0;
  }

  @Override
  public boolean contains(long value) {
    for (int index = 0; index < size; index++) {
      if (values[index] == value) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public long[] toArray() {
    return values;
  }

  private void increaseCapacity() {
    this.values = Arrays.copyOf(values, values.length << 1);
  }
}
