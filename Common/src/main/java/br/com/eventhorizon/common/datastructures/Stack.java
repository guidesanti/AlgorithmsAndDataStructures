package br.com.eventhorizon.common.datastructures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class Stack<T> implements Iterable<T> {

  private Node top;

  private int size;

  public Stack() { }

  public void push(T data) {
    top = new Node(data, top);
    size++;
  }

  public T peek() {
    if (size == 0) {
      throw new NoSuchElementException("Stack is empty");
    }
    return top.data;
  }

  public T pop() {
    if (size == 0) {
      throw new NoSuchElementException("Stack is empty");
    }
    T data = top.data;
    top = top.next;
    size--;
    return data;
  }

  public void clear() {
    top = null;
    size = 0;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  @Override
  public String toString() {
    StringJoiner str = new StringJoiner(", ", "Stack {", "}");
    Node node = top;
    while (node != null) {
      str.add("" + node.data);
      node = node.next;
    }
    return str.toString();
  }

  @Override
  public Iterator<T> iterator() {
    return new StackIterator(top);
  }

  public class StackIterator implements Iterator<T> {

    private Node next;

    private StackIterator(Node top) {
      next = top;
    }

    @Override
    public boolean hasNext() {
      return next != null;
    }

    @Override
    public T next() {
      T data = next.data;
      next = next.next;
      return data;
    }
  }

  private class Node {

    private T data;

    private Node next;

    public Node(T data, Node next) {
      this.data = data;
      this.next = next;
    }
  }
}
