package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.List;

public class MultipleAlignment implements PA {

  private static String string1;

  private static String string2;

  private static String string3;

  private static int score;

  private static String alignment1;

  private static String alignment2;

  private static String alignment3;

  private static void init() {
    score = 0;
    alignment1 = string1;
    alignment2 = string2;
    alignment3 = string3;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);
    string1 = scanner.next();
    string2 = scanner.next();
    string3 = scanner.next();
  }

  private static void writeOutput() {
    System.out.println(score);
    System.out.println(alignment1);
    System.out.println(alignment2);
    System.out.println(alignment3);
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

    List<Vertex>[][][] edgeTo = new ArrayList[string1.length() + 1][string2.length() + 1][string3.length() + 1];
    int[][][] scores = new int[string1.length() + 1][string2.length() + 1][string3.length() + 1];
    for (int i = 0; i <= string1.length(); i++) {
      for (int j = 0; j <= string2.length(); j++) {
        for (int k = 0; k <= string3.length(); k++) {
          edgeTo[i][j][k] = new ArrayList<>();
        }
      }
    }
    for (int i = 0; i <= string1.length(); i++) {
      for (int j = 0; j <= string2.length(); j++) {
        if (i > 0) {
          edgeTo[i][j][0].add(new Vertex(i - 1, j, 0));
        }
        if (j > 0) {
          edgeTo[i][j][0].add(new Vertex(i, j - 1, 0));
        }
      }
    }
    for (int j = 0; j <= string2.length(); j++) {
      for (int k = 0; k <= string3.length(); k++) {
        if (j > 0) {
          edgeTo[0][j][k].add(new Vertex(0, j - 1, k));
        }
        if (k > 0) {
          edgeTo[0][j][k].add(new Vertex(0, j, k - 1));
        }
      }
    }
    for (int k = 1; k <= string3.length(); k++) {
      for (int i = 1; i <= string1.length(); i++) {
        if (k > 0) {
          edgeTo[i][0][k].add(new Vertex(i, 0, k - 1));
        }
        if (i > 0) {
          edgeTo[i][0][k].add(new Vertex(i - 1, 0, k));
        }
      }
    }
    for (int i = 1; i <= string1.length(); i++) {
      for (int j = 1; j <= string2.length(); j++) {
        for (int k = 1; k <= string3.length(); k++) {
          int s = (string1.charAt(i - 1) == string2.charAt(j - 1)) && (string1.charAt(i - 1) == string3.charAt(k - 1)) ? 1 : 0;
          int max =
              Math.max(scores[i - 1][j - 1][k - 1] + s,
                  Math.max(scores[i - 1][j][k],
                      Math.max(scores[i][j - 1][k],
                          Math.max(scores[i - 1][j - 1][k],
                              Math.max(scores[i][j][k - 1],
                                  Math.max(scores[i - 1][j][k - 1], scores[i][j - 1][k - 1]))))));
          scores[i][j][k] = max;
          if (scores[i - 1][j - 1][k - 1] + s == max) {
            edgeTo[i][j][k].add(new Vertex(i - 1, j - 1, k - 1));
          }
          if (scores[i - 1][j][k] == max) {
            edgeTo[i][j][k].add(new Vertex(i - 1, j, k));
          }
          if (scores[i][j - 1][k] == max) {
            edgeTo[i][j][k].add(new Vertex(i, j - 1, k));
          }
          if (scores[i - 1][j - 1][k] == max) {
            edgeTo[i][j][k].add(new Vertex(i - 1, j - 1, k));
          }
          if (scores[i][j][k - 1] == max) {
            edgeTo[i][j][k].add(new Vertex(i, j, k - 1));
          }
          if (scores[i - 1][j][k - 1] == max) {
            edgeTo[i][j][k].add(new Vertex(i - 1, j, k - 1));
          }
          if (scores[i][j - 1][k - 1] == max) {
            edgeTo[i][j][k].add(new Vertex(i, j - 1, k - 1));
          }
        }
      }
    }
    score = scores[string1.length()][string2.length()][string3.length()];
    buildAlignment(edgeTo);
  }

  private static void buildAlignment(List<Vertex>[][][] edgeTo) {
    StringBuilder s1 = new StringBuilder();
    StringBuilder s2 = new StringBuilder();
    StringBuilder s3 = new StringBuilder();

    int i = string1.length();
    int j = string2.length();
    int k = string3.length();
    while (i > 0 || j > 0 || k > 0) {
      Vertex fromVertex = edgeTo[i][j][k].get(0);
      if (fromVertex.i == i - 1 && fromVertex.j == j - 1 && fromVertex.k == k - 1) {
        s1.insert(0, string1.charAt(i - 1));
        s2.insert(0, string2.charAt(j - 1));
        s3.insert(0, string3.charAt(k - 1));
      } else if (fromVertex.k == k) {
        if (fromVertex.i == i - 1 && fromVertex.j == j) {
          s1.insert(0, string1.charAt(i - 1));
          s2.insert(0, "-");
          s3.insert(0, "-");
        } else if (fromVertex.i == i && fromVertex.j == j - 1) {
          s1.insert(0, "-");
          s2.insert(0, string2.charAt(j - 1));
          s3.insert(0, "-");
        } else {
          s1.insert(0, string1.charAt(i - 1));
          s2.insert(0, string2.charAt(j - 1));
          s3.insert(0, "-");
        }
      } else if (fromVertex.i == i - 1) {
        s1.insert(0, string1.charAt(i - 1));
        s2.insert(0, "-");
        s3.insert(0, string3.charAt(k - 1));
      } else if (fromVertex.j == j - 1) {
        s1.insert(0, "-");
        s2.insert(0, string2.charAt(j - 1));
        s3.insert(0, string3.charAt(k - 1));
      } else {
        s1.insert(0, "-");
        s2.insert(0, "-");
        s3.insert(0, string3.charAt(k - 1));
      }
      i = fromVertex.i;
      j = fromVertex.j;
      k = fromVertex.k;
    }

    alignment1 = s1.toString();
    alignment2 = s2.toString();
    alignment3 = s3.toString();
  }

  private static class Vertex {

    final int i;

    final int j;

    final int k;

    public Vertex(int i, int j, int k) {
      this.i = i;
      this.j = j;
      this.k = k;
    }

    @Override
    public String toString() {
      return "Vertex{" + i + "," + j + "," + k + "}";
    }
  }
}
