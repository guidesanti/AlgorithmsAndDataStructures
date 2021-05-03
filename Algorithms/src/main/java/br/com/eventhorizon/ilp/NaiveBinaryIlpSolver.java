package br.com.eventhorizon.ilp;

public class NaiveBinaryIlpSolver implements BinaryIlpSolver {

  private int count = 0;

  @Override
  public int[] solve(int[][] coefficients, int[] b) {
    if (coefficients == null) {
      throw new IllegalArgumentException("coefficients cannot be null");
    }
    if (b == null) {
      throw new IllegalArgumentException("b cannot be null");
    }
    if (coefficients.length != b.length) {
      throw new IllegalArgumentException("coefficients length should be equals to b length");
    }
    int[] solution = new int[coefficients[0].length];
    do {
      if (BinaryIlpVerifier.verify(coefficients, b, solution)) {
        return solution;
      }
    } while (next(solution));
    return null;
  }

  private boolean next(int[] solution) {
    count++;
    if (count >= Math.pow(2, solution.length)) {
      return false;
    }
    int index = solution.length - 1;
    while (index > 0 && solution[index] > 0) {
      solution[index] = solution[index] == 0 ? 1 : 0;
      index--;
    }
    solution[index] = solution[index] == 0 ? 1 : 0;
    return true;
  }
}
