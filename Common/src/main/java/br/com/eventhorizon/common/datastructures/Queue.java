package br.com.eventhorizon.common.datastructures;

public class Queue<T> {

  private LinkedList<T> list = new LinkedList<>();

  public Queue() { }

  public void enqueue(T value) {
    list.addLast(value);
  }

  public T dequeue() {
    return list.removeFirst();
  }

  public T peek() {
    return list.getFirst();
  }

  public void clear() {
    list.clear();
  }

  public boolean isEmpty() {
    return list.isEmpty();
  }

  public int size() {
    return list.size();
  }
}
