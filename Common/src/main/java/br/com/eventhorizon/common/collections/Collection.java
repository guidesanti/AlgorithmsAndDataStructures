package br.com.eventhorizon.common.collections;

import javax.naming.OperationNotSupportedException;

public interface Collection {

  default boolean add(long value) throws OperationNotSupportedException {
    throw new OperationNotSupportedException();
  }

  void clear();

  boolean contains(long value);

  boolean isEmpty();

  default boolean remove(long value) throws OperationNotSupportedException {
    throw new OperationNotSupportedException();
  }

  int size();

  long[] toArray();
}
