package br.com.eventhorizon.common.pa.v2;

import org.apache.commons.lang3.NotImplementedException;

public interface PA {

  default void reset() { }

  default void trivialSolution() {
    throw new NotImplementedException("Method not implemented: trivialSolution()");
  }

  void finalSolution();
}
