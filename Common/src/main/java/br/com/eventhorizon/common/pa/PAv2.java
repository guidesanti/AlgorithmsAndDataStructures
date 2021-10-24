package br.com.eventhorizon.common.pa;

import org.apache.commons.lang3.NotImplementedException;

public interface PAv2 {

  default void reset() { }

  default void trivialSolution() {
    throw new NotImplementedException("Method not implemented: " + getClass().getEnclosingMethod());
  }

  void finalSolution();
}
