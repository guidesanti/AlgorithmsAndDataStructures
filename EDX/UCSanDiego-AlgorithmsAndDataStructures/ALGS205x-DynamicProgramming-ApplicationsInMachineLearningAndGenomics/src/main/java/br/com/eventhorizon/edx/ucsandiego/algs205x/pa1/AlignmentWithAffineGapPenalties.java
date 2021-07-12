package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
      edgeTo[0][i].add(new Vertex(0, i - 1));
    }
    for (int i = 1; i < scores.length; i++) {
      if (i == 1) {
        scores[i][0] = scores[i - 1][0] + gapOpeningScore;
      } else {
        scores[i][0] = scores[i - 1][0] + gapExtensionScore;
      }
      edgeTo[i][0].add(new Vertex(i - 1, 0));
    }
    for (int i = 1; i <= string1.length(); i++) {
      for (int j = 1; j <= string2.length(); j++) {
        int middleScore = scores[i - 1][j - 1];
        if (string1.charAt(i - 1) == string2.charAt(j - 1)) {
          middleScore += matchScore;
        } else {
          middleScore += mismatchScore;
        }
        int downScore = scores[i - 1][j];
        int finalJ = j;
        if (edgeTo[i - 1][j].stream().anyMatch(vertex -> vertex.j == finalJ)) {
          downScore += gapExtensionScore;
        } else {
          downScore += gapOpeningScore;
        }
        int rightScore = scores[i][j - 1];
        int finalI = i;
        if (edgeTo[i][j - 1].stream().anyMatch(vertex -> vertex.i == finalI)) {
          rightScore += gapExtensionScore;
        } else {
          rightScore += gapOpeningScore;
        }
        scores[i][j] = Math.max(middleScore, Math.max(downScore, rightScore));
        if (scores[i][j] == middleScore) {
          edgeTo[i][j].add(new Vertex(i - 1, j - 1));
        }
        if (scores[i][j] == downScore) {
          edgeTo[i][j].add(new Vertex(i - 1, j));
        }
        if (scores[i][j] == rightScore) {
          edgeTo[i][j].add(new Vertex(i, j - 1));
        }
      }
    }

    score = scores[string1.length()][string2.length()];
    Queue<Alignment> queue = new LinkedList<>();
    queue.add(new Alignment(string1.length(), string2.length()));
    while (!queue.isEmpty()) {
      Alignment alignment = queue.poll();
      int i = alignment.i;
      int j = alignment.j;
      while (i > 0 || j > 0) {
        Vertex vertex;
        for (int k = 1; k < edgeTo[i][j].size(); k++) {
          Alignment newAlignment = new Alignment(alignment);
          vertex = edgeTo[i][j].get(k);
          if (vertex.i == i -1 && vertex.j == j - 1) {
            newAlignment.s1.insert(0, string1.charAt(i - 1));
            newAlignment.s2.insert(0, string2.charAt(j - 1));
          } else if (vertex.i == i - 1 && vertex.j == j) {
            newAlignment.s1.insert(0, string1.charAt(i - 1));
            newAlignment.s2.insert(0, "-");
          } else {
            newAlignment.s1.insert(0, "-");
            newAlignment.s2.insert(0, string2.charAt(j - 1));
          }
          newAlignment.i = vertex.i;
          newAlignment.j = vertex.j;
          queue.add(newAlignment);
        }
        vertex = edgeTo[i][j].get(0);
        if (vertex.i == i -1 && vertex.j == j - 1) {
          alignment.s1.insert(0, string1.charAt(i - 1));
          alignment.s2.insert(0, string2.charAt(j - 1));
        } else if (vertex.i == i - 1 && vertex.j == j) {
          alignment.s1.insert(0, string1.charAt(i - 1));
          alignment.s2.insert(0, "-");
        } else {
          alignment.s1.insert(0, "-");
          alignment.s2.insert(0, string2.charAt(j - 1));
        }
        i = vertex.i;
        j = vertex.j;
      }
      if (score(alignment.s1.toString(), alignment.s2.toString()) == score) {
        alignment1 = alignment.s1.toString();
        alignment2 = alignment.s2.toString();
        break;
      }
    }
  }

  private static int score(String alignment1, String alignment2) {
    int score = 0;
    for (int i = 0; i < alignment1.length(); i++) {
      if ((charAt(alignment1, i - 1) != '-' && charAt(alignment1, i) == '-') ||
          (charAt(alignment2, i - 1) != '-' && charAt(alignment2, i) == '-')) {
        score += gapOpeningScore;
      } else if ((charAt(alignment1, i - 1) == '-' && charAt(alignment1, i) == '-') ||
          (charAt(alignment2, i - 1) == '-' && charAt(alignment2, i) == '-')) {
        score += gapExtensionScore;
      } else if (alignment1.charAt(i) != alignment2.charAt(i)) {
        score += mismatchScore;
      } else {
        score += matchScore;
      }
    }
    return score;
  }

  private static char charAt(String string, int index) {
    if (index < 0) {
      return ' ';
    }
    return string.charAt(index);
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

    public Vertex(int i, int j) {
      this.i = i;
      this.j = j;
    }
  }

  private static class Alignment {

    StringBuilder s1 = new StringBuilder();

    StringBuilder s2 = new StringBuilder();

    int i;

    int j;

    int score;

    public Alignment(int i, int j) {
      this.i = i;
      this.j = j;
    }

    public Alignment(Alignment alignment) {
      this.s1 = new StringBuilder(alignment.s1);
      this.s2 = new StringBuilder(alignment.s2);
      this.i = alignment.i;
      this.j = alignment.j;
      this.score = alignment.score;
    }
  }
}
