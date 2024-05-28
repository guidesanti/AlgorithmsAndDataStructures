package br.com.eventhorizon.problems;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class MaxSliceSum implements PA {

  private static int n;

  private static int[] array;

  private static long maxSliceSum;

  private static void init() {
    n = 0;
    array = null;
    maxSliceSum = Long.MIN_VALUE;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read N
    n = scanner.nextInt();

    // Read the original array
    array = new int[n];
    for (int i = 0; i < n; i++) {
      array[i] = scanner.nextInt();
    }
  }

  private static void writeOutput() {
    System.out.print(maxSliceSum);
  }

  @Override
  public void trivialSolution() {
    init();
    readInput();
    trivialSolutionImpl();
    writeOutput();
  }

  private static void trivialSolutionImpl() {
    for (int i = 0; i < array.length; i++) {
      for (int j = i; j < array.length; j++) {
        int sliceSum = 0;
        for (int k = i; k <= j; k++) {
          sliceSum += array[k];
        }
        maxSliceSum = Math.max(sliceSum, maxSliceSum);
      }
    }
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    finalSolutionImpl();
    writeOutput();
  }

  private static void finalSolutionImpl() {
    if (array.length == 0) {
      return;
    }
    int currentSum = 0;
    maxSliceSum = Long.MIN_VALUE;
    for (int value : array) {
      currentSum += value;

      if (currentSum > maxSliceSum) {
        maxSliceSum = currentSum;
      }

      if (currentSum < 0) {
        currentSum = 0;
      }
    }
  }
}
