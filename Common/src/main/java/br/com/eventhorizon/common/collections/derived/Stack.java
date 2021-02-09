package br.com.eventhorizon.common.collections.derived;

import br.com.eventhorizon.common.collections.elementary.LinkedList;

public class Stack {

  private LinkedList list = new LinkedList();

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

  public long peek() {
    return list.getFirst();
  }

  public long pop() {
    return list.removeFirst();
  }

  public void push(long value) {
    list.addFirst(value);
  }
}
