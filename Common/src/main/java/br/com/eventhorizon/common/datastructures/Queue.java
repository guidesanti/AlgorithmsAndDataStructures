package br.com.eventhorizon.common.collections.derived;

import br.com.eventhorizon.common.collections.elementary.LinkedList;

public class Queue {

  private LinkedList list = new LinkedList();

  public Queue() { }

  public void clear() {
    list.clear();
  }

  public boolean isEmpty() {
    return list.isEmpty();
  }

  public int size() {
    return list.size();
  }

  public void enqueue(long value) {
    list.addLast(value);
  }

  public long dequeue() {
    return list.removeFirst();
  }

  public long peek() {
    return list.getFirst();
  }
}
