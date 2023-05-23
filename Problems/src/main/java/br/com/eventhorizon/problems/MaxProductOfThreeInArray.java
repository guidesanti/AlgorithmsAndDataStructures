package br.com.eventhorizon.problems;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.v2.PA;

import java.util.*;

public class MaxProductOfThreeInArray implements PA {

  private static int n;

  private static Integer[] array;

  private static int maxProductOfThree;

  private static void init() {
    n = 0;
    array = null;
    maxProductOfThree = 0;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read N
    n = scanner.nextInt();
    if (n == 0) {
      return;
    }

    // Read the original array
    array = new Integer[n];
    for (int i = 0; i < n; i++) {
      array[i] = scanner.nextInt();
    }
  }

  private static void writeOutput() {
    System.out.print(maxProductOfThree);
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
    maxProductOfThree = Integer.MIN_VALUE;
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        for (int k = j + 1; k < n; k++) {
          int max = array[i] * array[j] * array[k];
          maxProductOfThree = Math.max(maxProductOfThree, max);
        }
      }
    }
  }

  private static void finalSolutionImpl() {
    int min1 = Integer.MAX_VALUE;
    int min2 = Integer.MAX_VALUE;
    int max1 = Integer.MIN_VALUE;
    int max2 = Integer.MIN_VALUE;
    int max3 = Integer.MIN_VALUE;
    for (int i = 0; i < n; i++) {
      int value = array[i];
      if (value > max3) {
        max1 = max2;
        max2 = max3;
        max3 = value;
      } else if (value > max2) {
        max1 = max2;
        max2 = value;
      } else if (value > max1) {
        max1 = value;
      }
      if (value < min1) {
        min2 = min1;
        min1 = value;
      } else if (value < min2) {
        min2 = value;
      }
    }
    maxProductOfThree = Math.max(max1 * max2 * max3, min1 * min2 * max3);
  }
}
