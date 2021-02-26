package br.com.eventhorizon.common.datastructures;

import java.util.StringJoiner;

public class Stack<T> {

  private LinkedList<T> list = new LinkedList<>();

  public Stack() { }

  public void clear() {
    list.clear();
  }

  public boolean isEmpty() {
    return list.isEmpty();
  }

  public int size() {
    return list.size();
  }

  public T peek() {
    return list.getFirst();
  }

  public T pop() {
    return list.removeFirst();
  }

  public void push(T value) {
    list.addFirst(value);
  }

  @Override
  public String toString() {
    StringJoiner str = new StringJoiner(" ", "Stack { ", " }");
    for (int i = 0; i < list.size(); i++) {
      str.add("" + list.get(i));
    }
    return str.toString();
  }
}
