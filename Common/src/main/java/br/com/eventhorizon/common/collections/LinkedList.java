package br.com.eventhorizon.common.collections;

import java.util.NoSuchElementException;

public class LinkedList implements List {

  private LinkedListNode first;

  private LinkedListNode last;

  private int size;

  public LinkedList() {
    this.first = null;
    this.last = null;
    this.size = 0;
  }

  @Override
  public void add(int index, long value) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException();
    }
    LinkedListNode newNode = new LinkedListNode(value);
    if (index == 0) {
      newNode.next = first;
      first = newNode;
    } else {
      LinkedListNode previous = first;
      LinkedListNode current = first.next;
      for (int i = 0; i < index; i++) {
        previous = current;
        current = current.next;
      }
      newNode.next = current;
      previous.next = newNode;
    }
    if (index == size) {
      last = newNode;
    }
    size++;
  }

  public void addFirst(long value) {
    first = new LinkedListNode(value, first);
    if (size == 0) {
      last = first;
    }
    size++;
  }

  public void addLast(long value) {
    if (size == 0) {
      first = new LinkedListNode(value, null);
      last = first;
    } else {
      LinkedListNode newNode = new LinkedListNode(value, null);
      last.next = newNode;
      last = newNode;
    }
    size++;
  }

  @Override
  public long get(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    LinkedListNode current = first;
    for (int i = 0; i < index; i++) {
      current = current.next;
    }
    return current.value;
  }

  @Override
  public long remove(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    long value;
    if (index == 0) {
      if (first == last) {
        first = null;
        last = null;
      } else {
        first = first.next;
      }
      value = first.value;
    } else {
      LinkedListNode previous = first;
      LinkedListNode current = first.next;
      for (int i = 1; i < index; i++) {
        previous = current;
        current = current.next;
      }
      previous.next = current.next;
      if (current == last) {
        last = previous;
      }
      value = current.value;
    }
    size--;
    return value;
  }

  public long removeFirst() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    long value = first.value;
    if (size == 1) {
      first = null;
      last = null;
    } else {
      first = first.next;
    }
    size--;
    return value;
  }

  public long removeLast() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    long value;
    if (size == 1) {
      value = first.value;
      first = null;
      last = null;
    } else {
      LinkedListNode previous = null;
      LinkedListNode current = first;
      while (current != last) {
        previous = current;
        current = current.next;
      }
      previous = current.next;
      last = previous;
      value = current.value;
    }
    size--;
    return value;
  }

  @Override
  public void replace(int index, long value) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    LinkedListNode current = first;
    for (int i = 0; i < index; i++) {
      current = current.next;
    }
    current.value = value;
  }

  @Override
  public List subList(int fromIndex, int toIndex) {
    if (fromIndex < 0 || toIndex >= size || fromIndex > toIndex) {
      throw new IndexOutOfBoundsException();
    }
    LinkedList newList = new LinkedList();
    LinkedListNode current = first;
    for (int i = 0; i < fromIndex; i++) {
      current = current.next;
    }
    for (int i = fromIndex; i <= toIndex; i++) {
      newList.addFirst(current.value);
      current = current.next;
    }
    return newList;
  }

  @Override
  public void clear() {
    first = null;
    last = null;
    size = 0;
  }

  @Override
  public boolean contains(long value) {
    LinkedListNode current = first;
    while (current != null) {
      if (current.value == value) {
        return true;
      }
      current = current.next;
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
    long[] values = new long[size];
    LinkedListNode current = first;
    for (int i = 0; i < size; i++) {
      values[i] = current.value;
      current = current.next;
    }
    return values;
  }

  private static class LinkedListNode {

    long value;

    LinkedListNode next;

    public LinkedListNode(long value) {
      this.value = value;
      this.next = null;
    }

    public LinkedListNode(long value, LinkedListNode next) {
      this.value = value;
      this.next = next;
    }
  }
}
