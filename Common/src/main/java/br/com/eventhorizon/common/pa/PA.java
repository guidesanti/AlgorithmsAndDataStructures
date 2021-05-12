package br.com.eventhorizon.common.pa;

import javax.naming.OperationNotSupportedException;

public interface PA {

  default void naiveSolution() {
    finalSolution();
  }

  default void intermediateSolution1() throws OperationNotSupportedException {
    throw new OperationNotSupportedException();
  }

  default void intermediateSolution2() throws OperationNotSupportedException {
    throw new OperationNotSupportedException();
  }

  void finalSolution();
}
