package br.com.eventhorizon.problems;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class CyclicRotation implements PA {

  private static int n;

  private static int k;

  private static int[] originalArray;

  private static int[] rotatedArray;

  private static void init() {
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read N and K
    n = scanner.nextInt();
    if (n == 0) {
      return;
    }
    k = scanner.nextInt() % n;

    // Read the original array
    originalArray = new int[n];
    for (int i = 0; i < n; i++) {
      originalArray[i] = scanner.nextInt();
    }
  }

  private static void writeOutput() {
    for (int i = 0; i < n; i++) {
      System.out.print(rotatedArray[i] + " ");
    }
  }

  @Override
  public void trivialSolution() {
    init();
    readInput();
    trivialSolutionImpl();
    writeOutput();
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    finalSolutionImpl();
    writeOutput();
  }

  private static void trivialSolutionImpl() {
    if (n == 0) {
      return;
    }
    rotatedArray = originalArray;
    for (int i = 0; i < k; i++) {
      int aux = rotatedArray[n - 1];
      rotatedArray[n - 1] = rotatedArray[n - 2];
      for (int j = n - 2; j > 0; j--) {
        rotatedArray[j] = rotatedArray[j - 1];
      }
      rotatedArray[0] = aux;
    }
  }

  private static void finalSolutionImpl() {
    if (n == 0) {
      return;
    }
    k %= n;
    rotatedArray = new int[n];
    for (int i = 0; i < n; i++) {
      rotatedArray[(i + k) % n] = originalArray[i];
    }
  }
}
