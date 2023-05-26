package br.com.eventhorizon.problems;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.v2.PA;

import java.util.HashMap;
import java.util.Map;

public class OddOccurrencesInArray implements PA {

  private static int n;

  private static int[] array;

  private static int oddOccurrence;

  private static void init() {
    oddOccurrence = 0;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read N
    n = scanner.nextInt();
    if (n == 0) {
      return;
    }

    // Read the original array
    array = new int[n];
    for (int i = 0; i < n; i++) {
      array[i] = scanner.nextInt();
    }
  }

  private static void writeOutput() {
    System.out.print(oddOccurrence);
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
    Map<Integer, Integer>  numbers = new HashMap<>();
    for (int number : array) {
      int count = numbers.getOrDefault(number, 0) + 1;
      if (count % 2 == 0) {
        numbers.remove(number);
      } else {
        numbers.put(number, count);
      }
    }
    oddOccurrence = numbers.keySet().stream().findFirst().get();
  }

  private static void finalSolutionImpl() {
    trivialSolutionImpl();
  }
}
