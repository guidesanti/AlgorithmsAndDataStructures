package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalAlignmentInLinearSpace implements PA {

  private static int matchScore;

  private static int mismatchScore;

  private static int gapScore;

  private static String string1;

  private static String string2;

  private static int score;

  private static String alignment1;

  private static String alignment2;

  private static void init() {
    score = Integer.MIN_VALUE;
    alignment1 = "";
    alignment2 = "";
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
    System.out.println(score);
    System.out.println(alignment1);
    System.out.println(alignment2);
  }

  @Override
  public void trivialSolution() {
    init();
    readInput();
    naiveGlobalAlignment();
    writeOutput();
  }

  private static void naiveGlobalAlignment() {
    if (string1 == null || string1.isEmpty() || string2 == null || string2.isEmpty()) {
      return;
    }

    int[][] scores = new int[string1.length() + 1][string2.length() + 1];
    for (int i = 1; i < scores[0].length; i++) {
      scores[0][i] = scores[0][i - 1] + gapScore;
    }
    for (int i = 1; i < scores.length; i++) {
      scores[i][0] = scores[i - 1][0] + gapScore;
    }
    for (int i = 1; i <= string1.length(); i++) {
      for (int j = 1; j <= string2.length(); j++) {
        if (string1.charAt(i - 1) == string2.charAt(j - 1)) {
          scores[i][j] = scores[i - 1][j - 1] + matchScore;
        } else {
          scores[i][j] = scores[i - 1][j - 1] + mismatchScore;
        }
        scores[i][j] = Math.max(scores[i][j], Math.max(scores[i - 1][j] + gapScore, scores[i][j - 1] + gapScore));
      }
    }

    StringBuilder s1 = new StringBuilder();
    StringBuilder s2 = new StringBuilder();
    int i = string1.length();
    int j = string2.length();
    score = scores[i][j];
    while (i > 0 || j > 0) {
      if (i == 0) {
        s1.insert(0, "-");
        s2.insert(0, string2.charAt(j - 1));
        j--;
      } else if (j == 0) {
        s1.insert(0, string1.charAt(i - 1));
        s2.insert(0, "-");
        i--;
      } else {
        if (scores[i][j] == scores[i - 1][j - 1] + (string1.charAt(i - 1) == string2.charAt(j - 1) ? matchScore : mismatchScore)) {
          s1.insert(0, string1.charAt(i - 1));
          s2.insert(0, string2.charAt(j - 1));
          i--;
          j--;
        } else if (scores[i][j] == scores[i - 1][j] + gapScore) {
          s1.insert(0, string1.charAt(i - 1));
          s2.insert(0, "-");
          i--;
        } else {
          s1.insert(0, "-");
          s2.insert(0, string2.charAt(j - 1));
          j--;
        }
      }
    }
    alignment1 = s1.toString();
    alignment2 = s2.toString();
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    finalGlobalAlignment();
    writeOutput();
  }

  private static void finalGlobalAlignment() {
    if (string1 == null || string1.isEmpty() || string2 == null || string2.isEmpty()) {
      return;
    }

    Map<String, Vertex> edgeTo = new HashMap<>();
    finalGlobalAlignment1(edgeTo, 0, 0, string2.length(), string1.length());
    buildAlignment(edgeTo);
  }

  private static void finalGlobalAlignment1(Map<String, Vertex> edgeTo, int fromI, int fromJ, int toI, int toJ) {
    int d = ((toI - fromI)*(toI - fromI) + (toJ - fromJ)*(toJ - fromJ));
    if (d == 0) {
      return;
    }
    if (d == 1) {
      edgeTo.putIfAbsent(id(toI, toJ), new Vertex(fromI, fromJ));
      return;
    }
    if (d == 2) {
      int s1 = string1.charAt(fromJ) == string2.charAt(fromI) ? matchScore : mismatchScore;
      int s2 = 2 * gapScore;
      if (s1 > s2) {
        edgeTo.putIfAbsent(id(toI, toJ), new Vertex(fromI, fromJ));
        if (score == Integer.MIN_VALUE) {
          score = s1;
        }
      } else {
        edgeTo.putIfAbsent(id(fromI, fromJ + 1), new Vertex(fromI, fromJ));
        edgeTo.putIfAbsent(id(toI, toJ), new Vertex(fromI, fromJ + 1));
        if (score == Integer.MIN_VALUE) {
          score = s2;
        }
      }
      return;
    }
    if (fromI == toI) {
      for (int j = fromJ; j <= toJ; j++) {
        edgeTo.putIfAbsent(id(fromI, j + 1), new Vertex(fromI, j));
      }
      return;
    }
    if (fromJ == toJ) {
      for (int i = fromI; i <= toI; i++) {
        edgeTo.putIfAbsent(id(i + 1, fromJ), new Vertex(i, fromJ));
      }
      return;
    }

    int numberOfRows = toI - fromI + 1;
    int numberOfColumns = toJ - fromJ + 1;
    int middleJ = (numberOfColumns - 1) / 2;

    int[][] scores1 = new int[numberOfRows][2];
    for (int i = 0; i < scores1.length; i++) {
      scores1[i][0] = i * gapScore;
    }
    int tempJ1 = 0;
    int tempJJ1 = 0;
    for (int j = 1; j <= middleJ; j++) {
      tempJ1 = j % 2;
      tempJJ1 = tempJ1 == 0 ? 1 : 0;
      scores1[0][tempJ1] = j * gapScore;
      for (int i = 1; i < numberOfRows; i++) {
        if (string1.charAt(fromJ + j - 1) == string2.charAt(fromI + i - 1)) {
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
    for (int j = numberOfColumns - 2; j >= middleJ; j--) {
      tempJ2 = j % 2;
      tempJJ2 = tempJ2 == 0 ? 1 : 0;
      scores2[numberOfRows - 1][tempJ2] = (numberOfColumns - 1 - j) * gapScore;
      for (int i = numberOfRows - 2; i >= 0; i--) {
        if (string1.charAt(fromJ + j) == string2.charAt(fromI + i)) {
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

    int max = Integer.MIN_VALUE;
    List<Vertex> vertices = new ArrayList<>();
    for (int i = 0; i < numberOfRows; i++) {
      int temp = scores1[i][tempJ1] + scores2[i][tempJ2];
      if (temp == max) {
        vertices.add(new Vertex(fromI + i, fromJ + middleJ));
      } else if (temp > max) {
        vertices.clear();
        vertices.add(new Vertex(fromI + i, fromJ + middleJ));
        max = temp;
      }
    }

    Vertex firstVertex = vertices.get(0);
    Vertex lastVertex = firstVertex;
    for (Vertex vertex : vertices) {
      int i = vertex.i;
      int j = vertex.j;
      int auxI = i - fromI;
      int auxJ = j - fromJ;

      Vertex fromVertex;
      if (auxI == 0) {
        fromVertex = new Vertex(i, j - 1);
      } else if (auxJ == 0) {
        fromVertex = new Vertex(i - 1, j);
      } else {
        int s = string1.charAt(j - 1) == string2.charAt(i - 1) ? matchScore : mismatchScore;
        if (scores1[auxI][tempJ1] == scores1[auxI - 1][tempJ1] + gapScore) {
          fromVertex = new Vertex(i - 1, j);
        } else if (scores1[auxI][tempJ1] == scores1[auxI - 1][tempJJ1] + s) {
          fromVertex = new Vertex(i - 1, j - 1);
        } else {
          fromVertex = new Vertex(i, j - 1);
        }
      }

      Vertex toVertex;
      if (i == string2.length()) {
        toVertex = new Vertex(i, j + 1);
      } else if (j == string1.length()) {
        toVertex = new Vertex(i + 1, j);
      } else {
        int s = string1.charAt(j) == string2.charAt(i) ? matchScore : mismatchScore;
        if (scores2[auxI][tempJ2] == scores2[auxI][tempJJ2] + gapScore) {
          toVertex = new Vertex(i, j + 1);
        } else if (scores2[auxI][tempJ2] == scores2[auxI + 1][tempJJ2] + s) {
          toVertex = new Vertex(i + 1, j + 1);
        } else {
          toVertex = new Vertex(i + 1, j);
        }
      }
      edgeTo.putIfAbsent(id(i, j), fromVertex);

      if (toVertex.j == j + 1) {
        edgeTo.putIfAbsent(toVertex.id, vertex);
        lastVertex = toVertex;
        break;
      }
    }

    if (fromI == 0 && fromJ == 0 && toI == string2.length() && toJ == string1.length()) {
      score = max;
    }

    finalGlobalAlignment1(edgeTo, fromI, fromJ, firstVertex.i, firstVertex.j);
    finalGlobalAlignment1(edgeTo, lastVertex.i, lastVertex.j, toI, toJ);
  }

  private static void buildAlignment(Map<String, Vertex> edgeTo) {
    StringBuilder s1 = new StringBuilder();
    StringBuilder s2 = new StringBuilder();

    int i = string2.length();
    int j = string1.length();
    while (i > 0 || j > 0) {
      Vertex fromVertex = edgeTo.get(id(i, j));
      if (fromVertex.i == i - 1 && fromVertex.j == j - 1) {
        s1.insert(0, string1.charAt(j - 1));
        s2.insert(0, string2.charAt(i - 1));
      } else if (fromVertex.i == i) {
        s1.insert(0, string1.charAt(j - 1));
        s2.insert(0, '-');
      } else {
        s1.insert(0, '-');
        s2.insert(0, string2.charAt(i - 1));
      }
      i = fromVertex.i;
      j = fromVertex.j;
    }

    alignment1 = s1.toString();
    alignment2 = s2.toString();
  }

  private static String id(int i, int j) {
    return i + ":" + j;
  }

  private static class Vertex {

    final int i;

    final int j;

    final String id;

    Vertex(int i, int j) {
      this.i = i;
      this.j = j;
      this.id = id(i, j);
    }

    @Override
    public String toString() {
      return "Vertex{" + i + "," + j + "}";
    }
  }
}
