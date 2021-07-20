package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.List;

public class MiddleEdgeInAlignmentGraph implements PA {

  private static int matchScore;

  private static int mismatchScore;

  private static int gapScore;

  private static String string1;

  private static String string2;

  private static int fromI;

  private static int fromJ;

  private static int toI;

  private static int toJ;

  @Override
  public void naiveSolution() {
    readInput();
    naiveGlobalAlignment();
    writeOutput();
  }

  private static void naiveGlobalAlignment() {
    if (string1 == null || string1.isEmpty() || string2 == null || string2.isEmpty()) {
      return;
    }

    int numberOfRows = string2.length() + 1;
    int numberOfColumns = string1.length() + 1;
    List<Vertex>[][] edgeTo = new List[numberOfRows][numberOfColumns];
    for (int i = 0; i < numberOfRows; i++) {
      for (int j = 0; j < numberOfColumns; j++) {
        edgeTo[i][j] = new ArrayList<>();
      }
    }
    for (int i = 1; i < edgeTo[0].length; i++) {
      edgeTo[0][i].add(new Vertex(0, i -1));
    }
    for (int i = 1; i < edgeTo.length; i++) {
      edgeTo[i][0].add(new Vertex(i - 1, 0));
    }
    int[][] scores = new int[2][numberOfColumns];
    for (int i = 0; i < scores[0].length; i++) {
      scores[0][i] = i * gapScore;
    }
    for (int i = 1; i < numberOfRows; i++) {
      int tempI = i % 2;
      int tempII = tempI == 0 ? 1 : 0;
      scores[tempI][0] = i * gapScore;
      for (int j = 1; j < numberOfColumns; j++) {
        if (string1.charAt(j - 1) == string2.charAt(i - 1)) {
          scores[tempI][j] = scores[tempII][j - 1] + matchScore;
        } else {
          scores[tempI][j] = scores[tempII][j - 1] + mismatchScore;
        }
        int s1 = scores[tempI][j];
        int s2 = scores[tempI][j - 1] + gapScore;
        int s3 = scores[tempII][j] + gapScore;
        scores[tempI][j] = Math.max(s1, Math.max(s2, s3));
        if (s1 == scores[tempI][j]) {
          edgeTo[i][j].add(new Vertex(i - 1, j - 1));
        }
        if (s2 == scores[tempI][j]) {
          edgeTo[i][j].add(new Vertex(i, j - 1));
        }
        if (s3 == scores[tempI][j]) {
          edgeTo[i][j].add(new Vertex(i - 1, j));
        }
      }
    }

    int middle = (numberOfColumns - 1) / 2;
    int i = numberOfRows - 1;
    int j = numberOfColumns - 1;
    while (true) {
      Vertex from = edgeTo[i][j].get(0);
      if (from.j == middle && from.j < j) {
        fromI = from.i;
        fromJ = from.j;
        toI = i;
        toJ = j;
        break;
      }
      i = from.i;
      j = from.j;
    }
  }

  private static class Vertex {

    final int i;

    final int j;

    public Vertex(int i, int j) {
      this.i = i;
      this.j = j;
    }
  }

  @Override
  public void finalSolution() {
    readInput();
    finalGlobalAlignment();
    writeOutput();
  }

  private static void finalGlobalAlignment() {
    if (string1 == null || string1.isEmpty() || string2 == null || string2.isEmpty()) {
      return;
    }

    int numberOfRows = string2.length() + 1;
    int numberOfColumns = string1.length() + 1;
    int middle = (numberOfColumns - 1) / 2;
    int[][] scores1 = new int[numberOfRows][2];
    for (int i = 0; i < scores1.length; i++) {
      scores1[i][0] = i * gapScore;
    }
    int tempJ1 = 0;
    int tempJJ1 = 0;
    for (int j = 1; j <= middle + 1; j++) {
      tempJ1 = j % 2;
      tempJJ1 = tempJ1 == 0 ? 1 : 0;
      scores1[0][tempJ1] = j * gapScore;
      for (int i = 1; i < numberOfRows; i++) {
        if (string1.charAt(j - 1) == string2.charAt(i - 1)) {
          scores1[i][tempJ1] = scores1[i - 1][tempJJ1] + matchScore;
        } else {
          scores1[i][tempJ1] = scores1[i - 1][tempJJ1] + mismatchScore;
        }
        int s1 = scores1[i][tempJ1];
        int s2 = scores1[i - 1][tempJ1] + gapScore;
        int s3 = scores1[i][tempJJ1] + gapScore;
        scores1[i][tempJ1] = Math.max(s1, Math.max(s2, s3));
      }
    }
    int[][] scores2 = new int[numberOfRows][2];
    for (int i = 0; i < scores2.length; i++) {
      scores2[i][(numberOfColumns - 1) % 2] = (scores2.length - i - 1) * gapScore;
    }
    int tempJ2 = 0;
    int tempJJ2 = 0;
    for (int j = numberOfColumns - 2; j >= middle; j--) {
      tempJ2 = j % 2;
      tempJJ2 = tempJ2 == 0 ? 1 : 0;
      scores2[numberOfRows - 1][tempJ2] = (numberOfColumns - 1 - j) * gapScore;
      for (int i = numberOfRows - 2; i >= 0; i--) {
        if (string1.charAt(j) == string2.charAt(i)) {
          scores2[i][tempJ2] = scores2[i + 1][tempJJ2] + matchScore;
        } else {
          scores2[i][tempJ2] = scores2[i + 1][tempJJ2] + mismatchScore;
        }
        int s1 = scores2[i][tempJ2];
        int s2 = scores2[i + 1][tempJ2] + gapScore;
        int s3 = scores2[i][tempJJ2] + gapScore;
        scores2[i][tempJ2] = Math.max(s1, Math.max(s2, s3));
      }
    }
    int maxFrom = Integer.MIN_VALUE;
    fromJ = middle;
    for (int i = 0; i < numberOfRows; i++) {
      int temp = scores1[i][tempJJ1] + scores2[i][tempJ2];
      if (temp >= maxFrom) {
        maxFrom = temp;
        fromI = i;
      }
    }
    if (fromI == numberOfRows - 1) {
      toI = fromI;
    } else {
      int a = scores1[fromI][tempJ1] + scores2[fromI][tempJJ2];
      int b = scores1[fromI + 1][tempJ1] + scores2[fromI + 1][tempJJ2];
      if (a >= b) {
        toI = fromI;
      } else {
        toI = fromI + 1;
      }
    }
    toJ = middle + 1;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);
    matchScore = scanner.nextInt();
    mismatchScore = -scanner.nextInt();
    gapScore = -scanner.nextInt();
    string1 = scanner.next();
    string2 = scanner.next();
  }

  private static void writeOutput() {
    System.out.println("(" + fromI + ", " + fromJ + ") (" + toI + ", " + toJ + ")");
  }
}
