package br.com.eventhorizon.datastructures.queues;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class Queue<T> implements Iterable<T> {

  private Node first;

  private Node last;

  private int size;

  public Queue() { }

  public void enqueue(T data) {
    if (size == 0) {
      last = new Node(data);
      first = last;
    } else {
      last.next = new Node(data);
      last = last.next;
    }
    size++;
  }

  public T dequeue() {
    if (size == 0) {
      throw new NoSuchElementException("Queue is empty");
    }
    T data = first.data;
    first = first.next;
    size--;
    return data;
  }

  public T peek() {
    if (size == 0) {
      throw new NoSuchElementException("Queue is empty");
    }
    return first.data;
  }

  public void clear() {
    first = null;
    last = null;
    size = 0;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  @Override
  public Iterator<T> iterator() {
    return new QueueIterator(first);
  }

  @Override
  public String toString() {
    StringJoiner str = new StringJoiner(", ", "Queue {", "}");
    Node node = first;
    while (node != null) {
      str.add("" + node.data);
      node = node.next;
    }
    return str.toString();
  }

  public class QueueIterator implements Iterator<T> {

    private Node next;

    private QueueIterator(Node first) {
      next = first;
    }

    @Override
    public boolean hasNext() {
      return next != null;
    }

    @Override
    public T next() {
      if (next == null) {
        throw new NoSuchElementException();
      }
      T data = next.data;
      next = next.next;
      return data;
    }
  }

  private class Node {

    private final T data;

    private Node next;

    public Node(T data) {
      this.data = data;
    }
  }
}
