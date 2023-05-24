package br.com.eventhorizon.problems;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.v2.PA;

public class MaxProfit implements PA {

  private static int n;

  private static int[] array;

  private static int maxProfit;

  private static void init() {
    n = 0;
    array = null;
    maxProfit = Integer.MIN_VALUE;
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
    System.out.print(maxProfit);
  }

  @Override
  public void trivialSolution() {
    init();
    readInput();
    trivialSolutionImpl();
    writeOutput();
  }

  private static void trivialSolutionImpl() {
    for (int i = 0; i < n; i++) {
      for (int j = i; j < n; j++) {
        int profit = array[j] - array[i];
        maxProfit = Math.max(profit, maxProfit);
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
    int currentBuy = array[0];
    maxProfit = 0;
    for (int i = 1; i < array.length; i++) {
      int currentProfit = array[i] - currentBuy;

      if (currentProfit > maxProfit) {
        maxProfit = currentProfit;
      }

      if (currentBuy > array[i]) {
        currentBuy = array[i];
      }
    }
  }
}
