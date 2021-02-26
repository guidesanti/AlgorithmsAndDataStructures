package br.com.eventhorizon.common.datastructures;

import java.util.NoSuchElementException;

public class LinkedList<T> {

  private final Node<T> NULL = new Node<>(null);

  private int size;

  public LinkedList() {
    NULL.previous = NULL;
    NULL.next = NULL;
  }

  public LinkedList(T[] values) {
    this();
    for (T value : values) {
      addLast(value);
    }
  }

  public void add(int index, T value) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException();
    }
    Node<T> newNode = new Node<>(value);
    Node<T> current = first();
    for (int i = 0; i < index; i++) {
      current = current.next;
    }
    newNode.previous = current.previous;
    newNode.next = current;
    current.previous.next = newNode;
    current.previous = newNode;
    size++;
  }

  public void addFirst(T value) {
    Node<T> first = first();
    Node<T> newNode = new Node<>(value, first.previous, first);
    first.previous.next = newNode;
    first.previous = newNode;
    size++;
  }

  public void addLast(T value) {
    Node<T> last = last();
    Node<T> newNode = new Node<T>(value, last, last.next);
    last.next.previous = newNode;
    last.next = newNode;
    size++;
  }

  public T get(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    Node<T> current = first();
    for (int i = 0; i < index; i++) {
      current = current.next;
    }
    return current.key;
  }

  public T getFirst() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    return first().key;
  }

  public T getLast() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    return last().key;
  }

  public T remove(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    Node<T> current = first();
    for (int i = 0; i < index; i++) {
      current = current.next;
    }
    current.previous.next = current.next;
    current.next.previous = current.previous;
    size--;
    return current.key;
  }

  public T removeFirst() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    Node<T> first = first();
    first.previous.next = first.next;
    first.next.previous = first.previous;
    size--;
    return first.key;
  }

  public T removeLast() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    Node<T> last = last();
    last.previous.next = last.next;
    last.next.previous = last.previous;
    size--;
    return last.key;
  }

  public void replace(int index, T value) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException();
    }
    Node<T> current = first();
    for (int i = 0; i < index; i++) {
      current = current.next;
    }
    current.key = value;
  }

  public LinkedList<T> subList(int fromIndex, int toIndex) {
    if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
      throw new IndexOutOfBoundsException();
    }
    LinkedList<T> newList = new LinkedList<>();
    Node<T> current = first();
    for (int i = 0; i < fromIndex; i++) {
      current = current.next;
    }
    for (int i = fromIndex; i < toIndex; i++) {
      newList.addLast(current.key);
      current = current.next;
    }
    return newList;
  }

  public void clear() {
    NULL.previous = NULL;
    NULL.next = NULL;
    size = 0;
  }

  public boolean contains(T value) {
    Node current = first();
    while (current != NULL) {
      if (current.key == value) {
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

  public Object[] toArray() {
    Object[] values = new Object[size];
    Node<T> current = first();
    for (int i = 0; i < size; i++) {
      values[i] = current.key;
      current = current.next;
    }
    return values;
  }

  private Node<T> first() {
    return NULL.next;
  }

  private Node<T> last() {
    return NULL.previous;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    str.append("DoublyLinkedList{size=").append(size).append(", values = { ");
    Node<T> current = first();
    while (current.next != NULL) {
      str.append(current.key).append(", ");
      current = current.next;
    }
    str.append(size > 0 ? current.key : "").append(" }}");
    return str.toString();
  }

  private static class Node<T> {

    T key;

    Node<T> previous;

    Node<T> next;

    public Node(T key) {
      this.key = key;
      this.previous = null;
      this.next = null;
    }

    public Node(T key, Node<T> previous, Node<T> next) {
      this.key = key;
      this.previous = previous;
      this.next = next;
    }
  }
}
