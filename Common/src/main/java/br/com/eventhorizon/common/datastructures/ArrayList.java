package br.com.eventhorizon.common.datastructures;

import br.com.eventhorizon.common.utils.Utils;

import java.util.Arrays;
import java.util.Iterator;

public class ArrayList<T> implements Iterable<T> {

  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  private static final Object[] EMPTY_VALUES = {};

  private Object[] values;

  private int size;

  public ArrayList() {
    this.values = new Object[DEFAULT_INITIAL_CAPACITY];
    this.size = 0;
  }

  public ArrayList(int initialCapacity) {
    this.values = new Object[initialCapacity];
    this.size = 0;
  }

  public ArrayList(T[] values) {
    if (values == null) {
      throw new IllegalArgumentException("Argument 'values' cannot be null");
    }
    this.values = Arrays.copyOf(values, values.length);
    this.size = values.length;
  }

  public void add(T value) {
    if (size == values.length) {
      increaseCapacity();
    }
    values[size] = value;
    size++;
  }

  public void add(int index, T value) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException();
    }
    if (index == values.length) {
      increaseCapacity();
    }
    System.arraycopy(values, index, values, index + 1, size - index);
    values[index] = value;
    size++;
  }

  @SuppressWarnings("unchecked")
  public T get(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    return (T) values[index];
  }

  @SuppressWarnings("unchecked")
  public T remove(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    T removingElement = (T) values[index];
    System.arraycopy(values, index + 1, values, index, size - 1 - index);
    size--;
    return removingElement;
  }

  public void replace(int index, T value) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    values[index] = value;
  }

  public ArrayList<T> subList(int fromIndex, int toIndex) {
    if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) {
      throw new IndexOutOfBoundsException();
    }
    ArrayList<T> subList = new ArrayList<>();
    subList.values = Arrays.copyOfRange(values, fromIndex, toIndex + 1);
    subList.size = toIndex - fromIndex + 1;
    return subList;
  }

  public void clear() {
    values = EMPTY_VALUES;
    size = 0;
  }

  public boolean contains(T value) {
    for (int index = 0; index < size; index++) {
      if (values[index].equals(value)) {
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

  public void shuffle() {
    for (int i = 0; i < size; i++) {
      int j = Utils.getRandomInteger(0, size - 1);
      int k = Utils.getRandomInteger(0, size - 1);
      Object temp = values[j];
      values[j] = values[k];
      values[k] = temp;
    }
  }

  public void invert() {
    for (int i = 0; i < values.length / 2; i++) {
      Object temp = values[i];
      values[i] = values[values.length - 1 - i];
      values[values.length - 1 - i] = temp;
    }
  }

  @Override
  public Iterator<T> iterator() {
    return new ArrayListIterator();
  }

  private void increaseCapacity() {
    this.values = Arrays.copyOf(values, values.length << 1);
  }

  public class ArrayListIterator implements Iterator<T> {

    private int index;

    private ArrayListIterator() {
      this.index = 0;
    }

    @Override
    public boolean hasNext() {
      return this.index < size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T next() {
      return (T) values[index++];
    }
  }
}
