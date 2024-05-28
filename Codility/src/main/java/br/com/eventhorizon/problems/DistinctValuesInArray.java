package br.com.eventhorizon.problems;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;
import java.util.*;

public class DistinctValuesInArray implements PA {

  private static int n;

  private static int[] numbers;

  private static int distinctValuesCount;

  private static void init() {
    n = 0;
    numbers = null;
    distinctValuesCount = 0;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read N
    n = scanner.nextInt();

    // Read the original array
    numbers = new int[n];
    for (int i = 0; i < n; i++) {
      numbers[i] = scanner.nextInt();
    }
  }

  private static void writeOutput() {
    System.out.print(distinctValuesCount);
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
    Map<Integer, Object> distinct = new HashMap<>();
    Object obj = new Object();
    for (int i = 0; i < n; i++) {
      distinct.put(numbers[i], obj);
    }
    distinctValuesCount = distinct.keySet().size();
  }

  private static void finalSolutionImpl() {
    trivialSolutionImpl();
  }
}
