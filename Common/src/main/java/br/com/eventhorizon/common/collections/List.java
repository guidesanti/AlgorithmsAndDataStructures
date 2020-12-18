package br.com.eventhorizon.common.collections;

public interface List extends Collection {

  void add(int index, long value);

  long get(int index);

  long remove(int index);

  void replace(int index, long value);

  List subList(int fromIndex, int toIndex);
}
