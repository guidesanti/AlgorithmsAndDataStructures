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
    List<List<Edge>> paths = new ArrayList<>();
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
        if (j == middle + 1) {
          if (s1 == scores[tempI][j]) {
            List<Edge> path = new ArrayList<>();
            path.add(new Edge(i - 1, j - 1, i, j));
            paths.add(path);
          }
          if (s2 == scores[tempI][j]) {
            List<Edge> path = new ArrayList<>();
            path.add(new Edge(i, j - 1, i, j));
            paths.add(path);
          }
          if (s3 == scores[tempI][j]) {
            addEdge(paths, i - 1, j, i, j);
          }
        } else if (j > middle + 1) {
          if (s1 == scores[tempI][j]) {
            addEdge(paths, i - 1, j - 1, i, j);
          }
          if (s2 == scores[tempI][j]) {
            addEdge(paths, i, j - 1, i, j);
          }
          if (s3 == scores[tempI][j]) {
            addEdge(paths, i - 1, j, i, j);
          }
        }
      }
    }

    for (List<Edge> path : paths) {
      Edge lastEdge = path.get(path.size() - 1);
      if (lastEdge.toI == numberOfRows - 1 && lastEdge.toJ == numberOfColumns - 1) {
        Edge firstEdge = path.get(0);
        fromI = firstEdge.fromI;
        fromJ = firstEdge.fromJ;
        toI = firstEdge.toI;
        toJ = firstEdge.toJ;
        break;
      }
    }
  }

  private static void addEdge(List<List<Edge>> paths, int fromI, int fromJ, int toI, int toJ) {
    for (List<Edge> path : paths) {
      Edge lastEdge = path.get(path.size() - 1);
      if (lastEdge.toI == fromI && lastEdge.toJ == fromJ) {
        path.add(new Edge(fromI, fromJ, toI, toJ));
      }
    }
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

  private static class Edge {

    final int fromI;

    final int fromJ;

    final int toI;

    final int toJ;

    public Edge(int fromI, int fromJ, int toI, int toJ) {
      this.fromI = fromI;
      this.fromJ = fromJ;
      this.toI = toI;
      this.toJ = toJ;
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
}
