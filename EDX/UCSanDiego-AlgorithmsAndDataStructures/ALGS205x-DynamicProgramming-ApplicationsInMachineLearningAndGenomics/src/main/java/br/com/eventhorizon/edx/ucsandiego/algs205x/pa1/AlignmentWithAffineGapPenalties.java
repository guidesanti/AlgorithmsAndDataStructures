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
    finalGlobalAlignment();
    writeOutput();
  }

  private static void finalGlobalAlignment() {
    if (string1 == null || string1.isEmpty() || string2 == null || string2.isEmpty()) {
      return;
    }

    List<Vertex>[][] edgeTo = new ArrayList[string1.length() + 1][string2.length() + 1];
    for (int i = 0; i <= string1.length(); i++) {
      for (int j = 0; j <= string2.length(); j++) {
        edgeTo[i][j] = new ArrayList<>();
      }
    }
    int[][] scores = new int[string1.length() + 1][string2.length() + 1];
    for (int i = 1; i < scores[0].length; i++) {
      if (i == 1) {
        scores[0][i] = scores[0][i - 1] + gapOpeningScore;
      } else {
        scores[0][i] = scores[0][i - 1] + gapExtensionScore;
      }
      edgeTo[0][i].add(new Vertex(0, i - 1, i != 1));
    }
    for (int i = 1; i < scores.length; i++) {
      if (i == 1) {
        scores[i][0] = scores[i - 1][0] + gapOpeningScore;
      } else {
        scores[i][0] = scores[i - 1][0] + gapExtensionScore;
      }
      edgeTo[i][0].add(new Vertex(i - 1, 0, i != 1));
    }
    for (int i = 1; i <= string1.length(); i++) {
      for (int j = 1; j <= string2.length(); j++) {
        int middleScore = scores[i - 1][j - 1];
        boolean downGapExtension = false;
        boolean rightGapExtension = false;
        if (string1.charAt(i - 1) == string2.charAt(j - 1)) {
          middleScore += matchScore;
        } else {
          middleScore += mismatchScore;
        }
        int downScore = scores[i - 1][j];
        int finalI = i;
        int finalJ = j;
        if (edgeTo[i - 1][j].stream().anyMatch(vertex -> vertex.j == finalJ)) {
          downScore += gapExtensionScore;
          downGapExtension = true;
        } else {
          downScore += gapOpeningScore;
        }
        int rightScore = scores[i][j - 1];
        if (edgeTo[i][j - 1].stream().anyMatch(vertex -> vertex.i == finalI)) {
          rightScore += gapExtensionScore;
          rightGapExtension = true;
        } else {
          rightScore += gapOpeningScore;
        }
        scores[i][j] = Math.max(middleScore, Math.max(downScore, rightScore));
        if (scores[i][j] == middleScore) {
          edgeTo[i][j].add(new Vertex(i - 1, j - 1, false));
        }
        if (scores[i][j] == downScore) {
          edgeTo[i][j].add(new Vertex(i - 1, j, downGapExtension));
        }
        if (scores[i][j] == rightScore) {
          edgeTo[i][j].add(new Vertex(i, j - 1, rightGapExtension));
        }
      }
    }

    score = scores[string1.length()][string2.length()];
    StringBuilder s1 = new StringBuilder();
    StringBuilder s2 = new StringBuilder();
    int i = string1.length();
    int j = string2.length();
    boolean isLastGapExtension = false;
    while (i > 0 || j > 0) {
      for (Vertex fromVertex : edgeTo[i][j]) {
        char s1Char;
        char s2Char;
        if (fromVertex.i == i - 1 && fromVertex.j == j - 1) {
          if (isLastGapExtension) {
            continue;
          }
          s1Char = string1.charAt(i - 1);
          s2Char = string2.charAt(j - 1);
        } else if (fromVertex.i == i - 1 && fromVertex.j == j) {
          s1Char = string1.charAt(i - 1);
          s2Char = '-';
        } else {
          s1Char = '-';
          s2Char = string2.charAt(j - 1);
        }
        s1.insert(0, s1Char);
        s2.insert(0, s2Char);
        i = fromVertex.i;
        j = fromVertex.j;
        isLastGapExtension = fromVertex.gapExtension;
        break;
      }
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

  private static class Vertex {

    int i;

    int j;

    boolean gapExtension;

    public Vertex(int i, int j, boolean gapExtension) {
      this.i = i;
      this.j = j;
      this.gapExtension = gapExtension;
    }
  }
}
