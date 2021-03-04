package br.com.eventhorizon.common.datastructures;

import br.com.eventhorizon.common.Utils;

import java.util.Arrays;

public class ArrayList {

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

  public void add(long value) {
    if (size == values.length) {
      increaseCapacity();
    }
    values[size] = value;
    size++;
  }

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

  public long get(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    return values[index];
  }

  public long remove(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    long removingElement = values[index];
    for (int i = index; i < size - 1; i++) {
      values[i] = values[i + 1];
    }
    size--;
    return removingElement;
  }

  public void replace(int index, long value) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    values[index] = value;
  }

  public ArrayList subList(int fromIndex, int toIndex) {
    if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) {
      throw new IndexOutOfBoundsException();
    }
    return new ArrayList(Arrays.copyOfRange(values, fromIndex, toIndex));
  }

  public void clear() {
    size = 0;
  }

  public boolean contains(long value) {
    for (int index = 0; index < size; index++) {
      if (values[index] == value) {
        return true;
      }
    }
    return false;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public long[] toArray() {
    return Arrays.copyOf(values, values.length);
  }

  public void shuffle() {
    for (int i = 0; i < size; i++) {
      int j = Utils.getRandomInteger(0, size - 1);
      int k = Utils.getRandomInteger(0, size - 1);
      long temp = values[j];
      values[j] = values[k];
      values[k] = temp;
    }
  }

  private void increaseCapacity() {
    this.values = Arrays.copyOf(values, values.length << 1);
  }
}
