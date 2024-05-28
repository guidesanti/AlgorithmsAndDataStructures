package br.com.eventhorizon.problems;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class FrogJump implements PA {

  private static int x;

  private static int y;

  private static int d;

  private static int jumps;

  private static void init() {
    jumps = 0;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read x, y and d
    x = scanner.nextInt();
    y = scanner.nextInt();
    d = scanner.nextInt();
  }

  private static void writeOutput() {
    System.out.print(jumps);
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
    while (x < y) {
      x += d;
      jumps++;
    }
  }

  private static void finalSolutionImpl() {
    int distance = y - x;
    jumps = (distance / d);
    if (distance % d > 0) {
      jumps++;
    }
  }
}
