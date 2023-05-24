package br.com.eventhorizon.problems;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.v2.PA;

import java.util.*;

public class Triangle implements PA {

  private static int n;

  private static Long[] array;

  private static int hasTriangle;

  private static void init() {
    n = 0;
    array = null;
    hasTriangle = 0;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read N
    n = scanner.nextInt();
    if (n == 0) {
      return;
    }

    // Read the original array
    array = new Long[n];
    for (int i = 0; i < n; i++) {
      array[i] = (long)scanner.nextInt();
    }
  }

  private static void writeOutput() {
    System.out.print(hasTriangle);
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
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        for (int k = j + 1; k < n; k++) {
          if ((array[i] >= 0 && array[j] >= 0 && array[k] >= 0) &&
              (array[i] + array[j] > array[k]) &&
              (array[i] + array[k] > array[j]) &&
              (array[k] + array[j] > array[i])) {
            hasTriangle = 1;
            break;
          }
        }
      }
    }
  }

  private static void finalSolutionImpl() {
    List<Long> list = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      if (array[i] > 0) {
        list.add(array[i]);
      }
    }
    Collections.sort(list);
    for (int i = 0; i < list.size() - 2; i++) {
      long a = list.get(i);
      long b = list.get(i + 1);
      long c = list.get(i + 2);
      if ((a + b > c) && (c + a > b) && (b + c > a)) {
        hasTriangle = 1;
        break;
      }
    }
  }
}
