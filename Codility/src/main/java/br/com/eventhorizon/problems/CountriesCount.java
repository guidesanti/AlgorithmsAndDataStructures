package br.com.eventhorizon.problems;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;
import java.util.Stack;

public class CountriesCount implements PA {

  private static int numRows;

  private static int numCols;

  private static int[][] colors;

  private static boolean[][] visited;

  private static Stack<Pair> stack;

  private static int countriesCount;

  private static void init() {
    numRows = 0;
    numCols = 0;
    colors = null;
    visited = null;
    stack = new Stack<>();
    countriesCount = 0;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read number of rows and number of columns
    numRows = scanner.nextInt();
    numCols = scanner.nextInt();

    // Read the colors matrix
    colors = new int[numRows][numCols];
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        colors[i][j] = scanner.nextInt();
      }
    }
    visited = new boolean[numRows][numCols];
  }

  private static void writeOutput() {
    System.out.print(countriesCount);
  }

  @Override
  public void trivialSolution() {
    init();
    readInput();
    trivialSolutionImpl();
    writeOutput();
  }

  private static class Pair {
    private final int i;

    private final int j;

    public Pair(int i, int j) {
      this.i = i;
      this.j = j;
    }
  }

  private static void trivialSolutionImpl() {
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        if (visited[i][j]) {
          continue;
        }

        stack.push(new Pair(i, j));
        while (!stack.isEmpty()) {
          Pair pair = stack.pop();
          visit(pair.i, pair.j);
        }
        countriesCount++;
      }
    }
  }

  private static void visit(int i, int j) {
    if (visited[i][j]) {
      return;
    }
    if (j + 1 < numCols && !visited[i][j + 1] && colors[i][j] == colors[i][j + 1]) {
      stack.push(new Pair(i, j + 1));
    }
    if (i + 1 < numRows && !visited[i + 1][j] && colors[i][j] == colors[i + 1][j]) {
      stack.push(new Pair(i + 1, j));
    }
    if (j - 1 >= 0 && !visited[i][j - 1] && colors[i][j] == colors[i][j - 1]) {
      stack.push(new Pair(i, j - 1));
    }
    if (i - 1 >= 0 && !visited[i - 1][j] && colors[i][j] == colors[i - 1][j]) {
      stack.push(new Pair(i - 1, j));
    }
    visited[i][j] = true;
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    finalSolutionImpl();
    writeOutput();
  }

  private static void finalSolutionImpl() {
    trivialSolutionImpl();
  }
}
