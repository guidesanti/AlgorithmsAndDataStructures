package br.com.eventhorizon.common.pa;

public interface PA {

  default void reset() { }

  default void trivialSolution() {
    throw new PAMethodNotImplemented("Method not implemented: trivialSolution()");
  }

  void finalSolution();
}
