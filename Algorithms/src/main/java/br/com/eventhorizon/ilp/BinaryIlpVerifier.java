package br.com.eventhorizon.ilp;

public final class BinaryIlpVerifier {

  public static boolean verify(int[][] coefficients, int[]b, int[] solution) {
    if (coefficients.length != b.length) {
      throw new RuntimeException("Invalid input, coefficients length should be equals to b length");
    }
    if (coefficients[0].length != solution.length) {
      throw new RuntimeException("Invalid input, coefficients length should be equals to solution length");
    }
    for (int i = 0; i < coefficients.length; i++) {
      int accumulator = 0;
      for (int j = 0; j < coefficients[i].length; j++) {
        accumulator += coefficients[i][j] * solution[j];
      }
      if (accumulator > b[i]) {
        return false;
      }
    }
    return true;
  }
}
