package br.com.eventhorizon.edx.ucsandiego.algs207x.pa2;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

public class PuzzleAssembly implements PA {

  private static final String BLACK = "black";

  private static List<Square> squares;

  private static int n;

  private static Square[][] grid;

  private static void init() {
    squares = new ArrayList<>();
    n = 0;
    grid = null;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read squares
    int index = 0;
    String str = scanner.next();
    Square upLeft = null;
    Square leftDown = null;
    Square downRight = null;
    Square rightUp = null;
    while (str != null) {
      String[] colors = str.replace("(", "").replace(")", "").split(",");
      Square square = new Square(index++, colors);
      if (square.upColor().equals(BLACK) && square.leftColor().equals(BLACK)) {
        upLeft = square;
      } else if (square.leftColor().equals(BLACK) && square.downColor().equals(BLACK)) {
        leftDown = square;
      } else if (square.downColor().equals(BLACK) && square.rightColor().equals(BLACK)) {
        downRight = square;
      } else if (square.rightColor().equals(BLACK) && square.upColor().equals(BLACK)) {
        rightUp = square;
      }
      squares.add(square);
      str = scanner.next();
    }

    // Compute n
    n = (int) Math.sqrt(squares.size());

    // Initialize grid
    grid = new Square[n][n];
    grid[0][0] = upLeft;
    grid[n - 1][0] = leftDown;
    grid[n - 1][n - 1] = downRight;
    grid[0][n - 1] = rightUp;
  }

  private static void writeOutput() {
    for (int i = 0; i < n; i++) {
      StringJoiner output = new StringJoiner(",");
      for (int j = 0; j < n; j++) {
        output.add(grid[i][j].toString());
      }
      System.out.println(output);
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
    buildConnections();
    fixBorders();
    buildGrid();
  }

  private static void buildConnections() {
    for (int i = 0; i < squares.size(); i++) {
      Square from = squares.get(i);
      for (int j = 0; j < squares.size(); j++) {
        if (i == j) {
          continue;
        }
        Square to = squares.get(j);
        if (!from.upColor().equals(BLACK) && from.upColor().equals(to.downColor())) {
          from.up.add(to);
        }
        if (!from.leftColor().equals(BLACK) && from.leftColor().equals(to.rightColor())) {
          from.left.add(to);
        }
        if (!from.downColor().equals(BLACK) && from.downColor().equals(to.upColor())) {
          from.down.add(to);
        }
        if (!from.rightColor().equals(BLACK) && from.rightColor().equals(to.leftColor())) {
          from.right.add(to);
        }
      }
    }
  }

  private static void fixBorders() {
    squares.forEach(square -> {
      // Top border
      if (square.upColor().equals(BLACK)) {
        square.left.removeIf(leftSquare -> !leftSquare.upColor().equals(BLACK));
        square.right.removeIf(rightSquare -> !rightSquare.upColor().equals(BLACK));
      } else {
        square.left.removeIf(leftSquare -> leftSquare.upColor().equals(BLACK));
        square.right.removeIf(rightSquare -> rightSquare.upColor().equals(BLACK));
      }
      // Left border
      if (square.leftColor().equals(BLACK)) {
        square.up.removeIf(upSquare -> !upSquare.leftColor().equals(BLACK));
        square.down.removeIf(downSquare -> !downSquare.leftColor().equals(BLACK));
      } else {
        square.up.removeIf(upSquare -> upSquare.leftColor().equals(BLACK));
        square.down.removeIf(downSquare -> downSquare.leftColor().equals(BLACK));
      }
      // Bottom border
      if (square.downColor().equals(BLACK)) {
        square.left.removeIf(leftSquare -> !leftSquare.downColor().equals(BLACK));
        square.right.removeIf(rightSquare -> !rightSquare.downColor().equals(BLACK));
      } else {
        square.left.removeIf(leftSquare -> leftSquare.downColor().equals(BLACK));
        square.right.removeIf(rightSquare -> rightSquare.downColor().equals(BLACK));
      }
      // Right border
      if (square.rightColor().equals(BLACK)) {
        square.up.removeIf(upSquare -> !upSquare.rightColor().equals(BLACK));
        square.down.removeIf(downSquare -> !downSquare.rightColor().equals(BLACK));
      } else {
        square.up.removeIf(upSquare -> upSquare.rightColor().equals(BLACK));
        square.down.removeIf(downSquare -> downSquare.rightColor().equals(BLACK));
      }
    });
  }

  private static void buildGrid() {
    for (Square square : grid[0][0].down) {
      grid[1][0] = square;
      if (extendSolution(1, 1, 0)) {
        break;
      }
    }
  }

  private static boolean extendSolution(int k, int i, int j) {
    Square curr = grid[i][j];
    if (i == j) {
      for (Square square : curr.up) {
        grid[i - 1][j] = square;
        if (extendSolution(k, i - 1, j)) {
          return true;
        }
      }
    } else if (j < k) {
      if (curr.up.contains(grid[i - 1][j])) {
        for (Square square : curr.right) {
          grid[i][j + 1] = square;
          if (extendSolution(k, i, j + 1)) {
            return true;
          }
        }
      }
    } else if (i == 0) {
      if (curr.left.contains(grid[i][j - 1])) {
        if (j == n - 1) {
          return true;
        }
        for (Square square : grid[k][0].down) {
          grid[k + 1][0] = square;
          if (extendSolution(k + 1, k + 1, 0)) {
            return true;
          }
        }
      }
    } else if (i < k) {
      if (curr.left.contains(grid[i][j - 1])) {
        for (Square square : curr.up) {
          grid[i - 1][j] = square;
          if (extendSolution(k, i - 1, j)) {
            return true;
          }
        }
      }
    } else {
      throw new RuntimeException("Should not reach here");
    }
    return false;
  }

  private static class Square {

    static final int UP = 0;

    static final int LEFT = 1;

    static final int DOWN = 2;

    static final int RIGHT = 3;

    final int index;

    final String[] colors;

    final List<Square> up;

    final List<Square> left;

    final List<Square> down;

    final List<Square> right;

    Square(int index, String[] colors) {
      this.index = index;
      this.colors = new String[4];
      System.arraycopy(colors, 0, this.colors, 0, 4);
      this.up = new ArrayList<>();
      this.left = new ArrayList<>();
      this.down = new ArrayList<>();
      this.right = new ArrayList<>();
    }

    @Override
    public String toString() {
      return "(" + colors[UP] + "," + colors[LEFT] + "," + colors[DOWN] + "," + colors[RIGHT] + ")";
    }

    String upColor() {
      return colors[UP];
    }

    String leftColor() {
      return colors[LEFT];
    }

    String downColor() {
      return colors[DOWN];
    }

    String rightColor() {
      return colors[RIGHT];
    }
  }
}
