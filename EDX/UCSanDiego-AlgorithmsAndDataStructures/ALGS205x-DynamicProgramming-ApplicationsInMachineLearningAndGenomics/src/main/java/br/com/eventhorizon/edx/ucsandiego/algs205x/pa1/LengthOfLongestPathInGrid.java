package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class LengthOfLongestPathInGrid implements PA {

  private static int numberOfRows;

  private static int numberOfColumns;

  private static int[][] down;

  private static int[][] right;

  @Override
  public void trivialSolution() {
    readInput();
    System.out.println(naive(numberOfRows, numberOfColumns));
  }

  private static int naive(int i, int j) {
    if (i == 0 && j == 0) {
      return 0;
    }
    if (i == 0) {
      return naive(0, j - 1) + right[i][j - 1];
    }
    if (j == 0) {
      return naive(i - 1, 0) + down[i - 1][j];
    }
    int left = naive(i, j - 1);
    return Math.max(naive(i - 1, j) + down[i - 1][j], left + right[i][j - 1]);
  }

  @Override
  public void finalSolution() {
    readInput();
    int[][] distances = new int[numberOfRows + 1][numberOfColumns + 1];
    for (int i = 1; i <= numberOfColumns; i++) {
      distances[0][i] = distances[0][i - 1] + right[0][i - 1];
    }
    for (int i = 1; i <= numberOfRows; i++) {
      distances[i][0] = distances[i - 1][0] + down[i - 1][0];
    }
    for (int i = 1; i <= numberOfRows; i++) {
      for (int j = 1; j <= numberOfColumns; j++) {
        distances[i][j] = Math.max(distances[i - 1][j] + down[i - 1][j], distances[i][j - 1] + right[i][j - 1]);
      }
    }
    System.out.println(distances[numberOfRows][numberOfColumns]);
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);
    numberOfRows = scanner.nextInt();
    numberOfColumns = scanner.nextInt();
    down = new int[numberOfRows][numberOfColumns + 1];
    for (int i = 0; i < numberOfRows; i++) {
      for (int j = 0; j <= numberOfColumns; j++) {
        down[i][j] = scanner.nextInt();
      }
    }
    if (scanner.nextChar() != '-') {
      throw new RuntimeException("Invalid input");
    }
    right = new int[numberOfRows + 1][numberOfColumns];
    for (int i = 0; i <= numberOfRows; i++) {
      for (int j = 0; j < numberOfColumns; j++) {
        right[i][j] = scanner.nextInt();
      }
    }
  }
}
