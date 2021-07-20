package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.List;

public class AlignmentWithAffineGapPenalties implements PA {

  private static int matchScore;

  private static int mismatchScore;

  private static int gapOpeningScore;

  private static int gapExtensionScore;

  private static String string1;

  private static String string2;

  private static int score;

  private static String alignment1;

  private static String alignment2;

  @Override
  public void naiveSolution() {
    init();
    readInput();
    naiveGlobalAlignment();
    writeOutput();
  }

  private static void naiveGlobalAlignment() {
    if (string1 == null || string1.isEmpty() || string2 == null || string2.isEmpty()) {
      return;
    }

    List<Vertex1>[][] edgeTo = new ArrayList[string1.length() + 1][string2.length() + 1];
    for (int i = 0; i <= string1.length(); i++) {
      for (int j = 0; j <= string2.length(); j++) {
        edgeTo[i][j] = new ArrayList<>();
      }
    }
    int[][] scores = new int[string1.length() + 1][string2.length() + 1];
    for (int i = 1; i < scores[0].length; i++) {
      int weight = gapOpeningScore + ((i - 1) * gapExtensionScore);
      scores[0][i] = weight;
      edgeTo[0][i].add(new Vertex1(0, 0));
    }
    for (int i = 1; i < scores.length; i++) {
      int weight = gapOpeningScore + ((i - 1) * gapExtensionScore);
      scores[i][0] = weight;
      edgeTo[i][0].add(new Vertex1(0, 0));
    }
    for (int i = 1; i <= string1.length(); i++) {
      for (int j = 1; j <= string2.length(); j++) {
        List<Vertex1> vertices = new ArrayList<>();
        int edgeScore = (string1.charAt(i - 1) == string2.charAt(j - 1) ? matchScore : mismatchScore);
        int maxScore = scores[i - 1][j - 1] + edgeScore;
        vertices.add(new Vertex1(i - 1, j - 1));

        for (Vertex1 vertex : edgeTo[i - 1][j]) {
          int newI = i - 1;
          int downScore = scores[i - 1][j];
          if (vertex.j == j) {
            newI = vertex.i;
            edgeScore = gapExtensionScore;
          } else {
            edgeScore = gapOpeningScore;
          }
          downScore += edgeScore;
          if (downScore == maxScore) {
            vertices.add(new Vertex1(newI, j));
          } else if (downScore > maxScore) {
            maxScore = downScore;
            vertices.clear();
            vertices.add(new Vertex1(newI, j));
          }
        }

        for (Vertex1 vertex : edgeTo[i][j - 1]) {
          int newJ = j - 1;
          int rightScore = scores[i][j - 1];
          if (vertex.i == i) {
            newJ = vertex.j;
            edgeScore = gapExtensionScore;
          } else {
            edgeScore = gapOpeningScore;
          }
          rightScore += edgeScore;
          if (rightScore == maxScore) {
            vertices.add(new Vertex1(i, newJ));
          } else if (rightScore > maxScore) {
            maxScore = rightScore;
            vertices.clear();
            vertices.add(new Vertex1(i, newJ));
          }
        }

        scores[i][j] = maxScore;
        for (Vertex1 vertex : vertices) {
          edgeTo[i][j].add(vertex);
        }
      }
    }

    StringBuilder s1 = new StringBuilder();
    StringBuilder s2 = new StringBuilder();
    int i = string1.length();
    int j = string2.length();
    score = scores[string1.length()][string2.length()];
    while (i > 0 || j > 0) {
      Vertex1 vertex = edgeTo[i][j].get(0);
      if (i == 0) {
        for (int k = 0; k < j - vertex.j; k++) {
          s1.insert(0, '-');
          s2.insert(0, string2.charAt(j - k - 1));
        }
      } else if (j == 0) {
        for (int k = 0; k < i - vertex.i; k++) {
          s1.insert(0, string1.charAt(i - k - 1));
          s2.insert(0, '-');
        }
      } else {
        if (vertex.i != i && vertex.j != j) {
          s1.insert(0, string1.charAt(i - 1));
          s2.insert(0, string2.charAt(j - 1));
        } else if (vertex.i == i) {
          for (int k = 0; k < j - vertex.j; k++) {
            s1.insert(0, '-');
            s2.insert(0, string2.charAt(j - k - 1));
          }
        } else {
          for (int k = 0; k < i - vertex.i; k++) {
            s1.insert(0, string1.charAt(i - k - 1));
            s2.insert(0, '-');
          }
        }
      }
      i = vertex.i;
      j = vertex.j;
    }
    alignment1 = s1.toString();
    alignment2 = s2.toString();
  }

  private static class Vertex1 {

    int i;

    int j;

    public Vertex1(int i, int j) {
      this.i = i;
      this.j = j;
    }
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    finalGlobalAlignment2();
    writeOutput();
  }

  private static final int MM = 0;

  private static final int X = 1;

  private static final int Y = 2;

  private static void finalGlobalAlignment2() {
    if (string1 == null || string1.isEmpty() || string2 == null || string2.isEmpty()) {
      return;
    }

    int[][] M = new int[string1.length() + 1][string2.length() + 1];
    int[][] Ix = new int[string1.length() + 1][string2.length() + 1];
    int[][] Iy = new int[string1.length() + 1][string2.length() + 1];
    List<Vertex2>[][][] edgeTo = new ArrayList[3][string1.length() + 1][string2.length() + 1];
    for (int i = 0; i < edgeTo.length; i++) {
      for (int j = 0; j < edgeTo[0].length; j++) {
        for (int k = 0; k < edgeTo[0][0].length; k++) {
          edgeTo[i][j][k] = new ArrayList<>();
        }
      }
    }

    M[0][0] = 0;
    Ix[0][0] = 0;
    Iy[0][0] = 0;
    for (int j = 1; j < M[0].length; j++) {
      M[0][j] = Integer.MIN_VALUE;
      Ix[0][j] = Integer.MIN_VALUE;
      Iy[0][j] = gapOpeningScore + ((j - 1) * gapExtensionScore);
      edgeTo[Y][0][j].add(new Vertex2(Y, 0, j - 1));
    }
    for (int i = 1; i < M.length; i++) {
      M[i][0] = Integer.MIN_VALUE;
      Ix[i][0] = gapOpeningScore + ((i - 1) * gapExtensionScore);
      Iy[i][0] = Integer.MIN_VALUE;
      edgeTo[X][i][0].add(new Vertex2(X, i - 1, 0));
    }
    for (int i = 1; i <= string1.length(); i++) {
      for (int j = 1; j <= string2.length(); j++) {
        int Ix0 = M[i - 1][j] == Integer.MIN_VALUE ? Integer.MIN_VALUE : (M[i - 1][j] + gapOpeningScore);
        int Ix1 = Ix[i - 1][j] == Integer.MIN_VALUE ? Integer.MIN_VALUE : (Ix[i - 1][j] + gapExtensionScore);
        int Ix2 = Iy[i - 1][j] == Integer.MIN_VALUE ? Integer.MIN_VALUE : (Iy[i - 1][j] + gapOpeningScore);
        Ix[i][j] = Math.max(Ix0, Math.max(Ix1, Ix2));
        if (Ix[i][j] != Integer.MIN_VALUE) {
          if (Ix0 == Ix[i][j]) {
            edgeTo[X][i][j].add(new Vertex2(MM, i - 1, j));
          }
          if (Ix1 == Ix[i][j]) {
            edgeTo[X][i][j].add(new Vertex2(X, i - 1, j));
          }
          if (Ix2 == Ix[i][j]) {
            edgeTo[X][i][j].add(new Vertex2(Y, i - 1, j));
          }
        }

        int Iy0 = M[i][j - 1] == Integer.MIN_VALUE ? Integer.MIN_VALUE : (M[i][j - 1] + gapOpeningScore);
        int Iy1 = Iy[i][j - 1] == Integer.MIN_VALUE ? Integer.MIN_VALUE : (Iy[i][j - 1] + gapExtensionScore);
        int Iy2 = Ix[i][j - 1] == Integer.MIN_VALUE ? Integer.MIN_VALUE : (Ix[i][j - 1] + gapOpeningScore);
        Iy[i][j] = Math.max(Iy0, Math.max(Iy1, Iy2));
        if (Iy[i][j] != Integer.MIN_VALUE) {
          if (Iy0 == Iy[i][j]) {
            edgeTo[Y][i][j].add(new Vertex2(MM, i, j - 1));
          }
          if (Iy1 == Iy[i][j]) {
            edgeTo[Y][i][j].add(new Vertex2(Y, i, j - 1));
          }
          if (Iy2 == Iy[i][j]) {
            edgeTo[Y][i][j].add(new Vertex2(X, i, j - 1));
          }
        }

        int s = string1.charAt(i - 1) == string2.charAt(j - 1) ? matchScore : mismatchScore;
        int m0 = M[i - 1][j - 1] == Integer.MIN_VALUE ? Integer.MIN_VALUE : (M[i - 1][j - 1] + s);
        int m1 = Ix[i - 1][j - 1] == Integer.MIN_VALUE ? Integer.MIN_VALUE : (Ix[i - 1][j - 1] + s);
        int m2 = Iy[i - 1][j - 1] == Integer.MIN_VALUE ? Integer.MIN_VALUE : (Iy[i - 1][j - 1] + s);
        M[i][j] = Math.max(m0, Math.max(m1, m2));
        if (M[i][j] != Integer.MIN_VALUE) {
          if (m0 == M[i][j]) {
            edgeTo[MM][i][j].add(new Vertex2(MM, i - 1, j - 1));
          }
          if (m1 == M[i][j]) {
            edgeTo[MM][i][j].add(new Vertex2(X, i - 1, j - 1));
          }
          if (m2 == M[i][j]) {
            edgeTo[MM][i][j].add(new Vertex2(Y, i - 1, j - 1));
          }
        }
      }
    }

    int k;
    int i = string1.length();
    int j = string2.length();
    Vertex2 curr;
    if (M[i][j] >= Ix[i][j] && M[i][j] >= Iy[i][j]) {
      score = M[i][j];
      k = MM;
    } else if (Ix[i][j] >= M[i][j] && Ix[i][j] >= Iy[i][j]) {
      score = Ix[i][j];
      k = X;
    } else {
      score = Iy[i][j];
      k = Y;
    }
    StringBuilder s1 = new StringBuilder();
    StringBuilder s2 = new StringBuilder();
    while (i > 0 || j > 0) {
      curr = edgeTo[k][i][j].get(0);
      if (curr.i == i - 1 && curr.j == j - 1) {
        s1.insert(0, string1.charAt(curr.i));
        s2.insert(0, string2.charAt(curr.j));
      } else if (curr.i == i) {
        s1.insert(0, '-');
        s2.insert(0, string2.charAt(curr.j));
      } else {
        s1.insert(0, string1.charAt(curr.i));
        s2.insert(0, '-');
      }
      k = curr.k;
      i = curr.i;
      j = curr.j;
    }
    alignment1 = s1.toString();
    alignment2 = s2.toString();
  }

  private static void init() {
    score = 0;
    alignment1 = "";
    alignment2 = "";
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);
    matchScore = scanner.nextInt();
    mismatchScore = -scanner.nextInt();
    gapOpeningScore = -scanner.nextInt();
    gapExtensionScore = -scanner.nextInt();
    string1 = scanner.next();
    string2 = scanner.next();
  }

  private static void writeOutput() {
    System.out.println(score);
    System.out.println(alignment1);
    System.out.println(alignment2);
  }

  private static class Vertex2 {

    final int k;

    final int i;

    final int j;

    Vertex2(int k, int i, int j) {
      this.k = k;
      this.i = i;
      this.j = j;
    }

    @Override
    public String toString() {
      return "Vertex{" + k + ":" + i + ":" + j + "}";
    }
  }
}
