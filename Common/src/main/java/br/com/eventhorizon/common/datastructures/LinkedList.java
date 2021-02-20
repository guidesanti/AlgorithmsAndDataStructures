package br.com.eventhorizon.common.collections.elementary;

import java.util.NoSuchElementException;

public class LinkedList {

  private final Node NULL = new Node(0);

  private int size;

  public LinkedList() {
    NULL.previous = NULL;
    NULL.next = NULL;
  }

  public LinkedList(long[] values) {
    this();
    for (int i = 0; i < values.length; i++) {
      addLast(values[i]);
    }
  }

  public void add(int index, long value) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException();
    }
    Node newNode = new Node(value);
    Node current = first();
    for (int i = 0; i < index; i++) {
      current = current.next;
    }
    newNode.previous = current.previous;
    newNode.next = current;
    current.previous.next = newNode;
    current.previous = newNode;
    size++;
  }

  public void addFirst(long value) {
    Node first = first();
    Node newNode = new Node(value, first.previous, first);
    first.previous.next = newNode;
    first.previous = newNode;
    size++;
  }

  public void addLast(long value) {
    Node last = last();
    Node newNode = new Node(value, last, last.next);
    last.next.previous = newNode;
    last.next = newNode;
    size++;
  }

  public long get(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    Node current = first();
    for (int i = 0; i < index; i++) {
      current = current.next;
    }
    return current.value;
  }

  public long getFirst() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    return first().value;
  }

  public long getLast() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    return last().value;
  }

  public long remove(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    Node current = first();
    for (int i = 0; i < index; i++) {
      current = current.next;
    }
    current.previous.next = current.next;
    current.next.previous = current.previous;
    size--;
    return current.value;
  }

  public long removeFirst() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    Node first = first();
    first.previous.next = first.next;
    first.next.previous = first.previous;
    size--;
    return first.value;
  }

  public long removeLast() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    Node last = last();
    last.previous.next = last.next;
    last.next.previous = last.previous;
    size--;
    return last.value;
  }

  public void replace(int index, long value) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    Node current = first();
    for (int i = 0; i < index; i++) {
      current = current.next;
    }
    current.value = value;
  }

  public LinkedList subList(int fromIndex, int toIndex) {
    if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
      throw new IndexOutOfBoundsException();
    }
    LinkedList newList = new LinkedList();
    Node current = first();
    for (int i = 0; i < fromIndex; i++) {
      current = current.next;
    }
    for (int i = fromIndex; i < toIndex; i++) {
      newList.addLast(current.value);
      current = current.next;
    }
    return newList;
  }

  public void clear() {
    NULL.previous = NULL;
    NULL.next = NULL;
    size = 0;
  }

  public boolean contains(long value) {
    Node current = first();
    while (current != NULL) {
      if (current.value == value) {
        return true;
      }
      current = current.next;
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
    long[] values = new long[size];
    Node current = first();
    for (int i = 0; i < size; i++) {
      values[i] = current.value;
      current = current.next;
    }
    return values;
  }

  private Node first() {
    return NULL.next;
  }

  private Node last() {
    return NULL.previous;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append("DoublyLinkedList{size=").append(size).append(", values = { ");
    Node current = first();
    while (current.next != NULL) {
      str.append(current.value).append(", ");
      current = current.next;
    }
    str.append(size > 0 ? current.value : "").append(" }}");
    return str.toString();
  }

  private static class Node {

    long value;

    Node previous;

    Node next;

    public Node(long value) {
      this.value = value;
      this.previous = null;
      this.next = null;
    }

    public Node(long value, Node previous, Node next) {
      this.value = value;
      this.previous = previous;
      this.next = next;
    }
  }
}
