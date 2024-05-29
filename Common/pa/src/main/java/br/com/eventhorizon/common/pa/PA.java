package br.com.eventhorizon.common.pa;

public interface PA {

  default void reset() { }

  default void trivialSolution() {
    throw new PAMethodNotImplementedException("trivialSolution()");
  }

  void finalSolution();
}
