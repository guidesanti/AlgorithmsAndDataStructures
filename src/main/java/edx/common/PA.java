package edx.common;

import javax.naming.OperationNotSupportedException;

public interface PA {

  void naiveSolution();

  default void intermediateSolution1() throws OperationNotSupportedException {
    throw new OperationNotSupportedException();
  }

  default void intermediateSolution2() throws OperationNotSupportedException {
    throw new OperationNotSupportedException();
  }

  void finalSolution();
}
