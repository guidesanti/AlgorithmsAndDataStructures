package br.com.eventhorizon.uri.paradigms;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.v2.PA;

public class P1084 implements PA {

  private static int n;

  private static int d;

  private static String inputNumber;

  private static StringBuilder outputNumber;

  private static void init() {
    n = 0;
    d = 0;
    inputNumber = null;
    outputNumber = new StringBuilder();
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read n, d and number
    n = scanner.nextInt();
    d = scanner.nextInt();
    inputNumber = scanner.next();

    if (n != inputNumber.length()) {
      throw new RuntimeException("Invalid input, n is different from number length");
    }
  }

  private static void writeOutput() {
    System.out.println(outputNumber);
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    finalSolutionImpl();
    writeOutput();
  }

  private static void finalSolutionImpl() {
    outputNumber.append(inputNumber);
    int index = 0;
    while (d > 0 && index < outputNumber.length() - 1) {
      if (outputNumber.charAt(index) < outputNumber.charAt(index + 1)) {
        outputNumber.deleteCharAt(index);
        d--;
        if (index > 0) {
          index--;
        }
      } else {
        index++;
      }
    }

    char symbol = '0';
    while (d > 0) {
      index = 0;
      while (d > 0 && index < outputNumber.length()) {
        if (outputNumber.charAt(index) == symbol) {
          outputNumber.deleteCharAt(index);
          d--;
        } else {
          index++;
        }
      }
      symbol++;
    }
  }
}
