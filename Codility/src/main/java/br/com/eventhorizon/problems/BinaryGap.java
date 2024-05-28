package br.com.eventhorizon.problems;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class BinaryGap implements PA {

  private static long number;

  private static int binaryGapLength;

  private static void init() {
    binaryGapLength = 0;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read number
    number = scanner.nextLong();
  }

  private static void writeOutput() {
    System.out.println(binaryGapLength);
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
    if (number == 0) {
      return;
    }

    // Find first 1
    while ((number & 0x1) == 0) {
      number >>= 1;
    }
    number >>= 1;

    OUTER:
    while (true) {
      // Count 0s until we find the next 1
      int count = 0;
      while ((number & 0x1) == 0) {
        number >>= 1;
        if (number == 0) {
          break OUTER;
        }
        count++;
      }

      // Set the count
      binaryGapLength = Math.max(binaryGapLength, count);

      // Find next 0
      number >>= 1;
      while ((number & 0x1) == 1) {
        number >>= 1;
        if (number == 0) {
          break OUTER;
        }
      }
    }
  }

  private static void finalSolutionImpl() {
    trivialSolutionImpl();
  }
}
