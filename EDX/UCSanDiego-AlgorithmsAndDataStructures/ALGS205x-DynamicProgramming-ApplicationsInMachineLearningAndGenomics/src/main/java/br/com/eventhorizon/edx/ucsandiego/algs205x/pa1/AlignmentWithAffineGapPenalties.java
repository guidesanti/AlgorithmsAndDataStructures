package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  private static void finalGlobalAlignment1() {
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

  private static void finalGlobalAlignment2() {
    if (string1 == null || string1.isEmpty() || string2 == null || string2.isEmpty()) {
      return;
    }

    Map<String, Vertex2> vertices = new HashMap<>();
    int[][] M = new int[string1.length() + 1][string2.length() + 1];
    int[][] Ix = new int[string1.length() + 1][string2.length() + 1];
    int[][] Iy = new int[string1.length() + 1][string2.length() + 1];

    M[0][0] = 0;
    Ix[0][0] = gapOpeningScore;
    Iy[0][0] = gapOpeningScore;
    Vertex2 vertex = new Vertex2('M', 0, 0);
    vertices.put(vertex.id, vertex);
    vertex = new Vertex2('X', 0, 0);
    vertices.put(vertex.id, vertex);
    vertex = new Vertex2('Y', 0, 0);
    vertices.put(vertex.id, vertex);
    for (int i = 1; i < M[0].length; i++) {
      M[0][i] = Integer.MIN_VALUE;
      Ix[0][i] = Integer.MIN_VALUE;
      Iy[0][i] = gapOpeningScore + (i * gapExtensionScore);
      Vertex2 to = vertices.get(id('Y', 0, i - 1));
      Vertex2 from = new Vertex2('Y', 0, i);
      from.next.add(to);
      vertices.put(from.id, from);
    }
    for (int i = 1; i < M.length; i++) {
      M[i][0] = Integer.MIN_VALUE;
      Ix[i][0] = gapOpeningScore + (i * gapExtensionScore);
      Iy[i][0] = Integer.MIN_VALUE;
      Vertex2 to = vertices.get(id('X', i - 1, 0));
      Vertex2 from = new Vertex2('X', i, 0);
      from.next.add(to);
      vertices.put(from.id, from);
    }
    for (int i = 1; i <= string1.length(); i++) {
      for (int j = 1; j <= string2.length(); j++) {
        int Ix0 = M[i - 1][j] == Integer.MIN_VALUE ? Integer.MIN_VALUE : (M[i - 1][j] + gapOpeningScore);
        int Ix1 = Ix[i - 1][j] == Integer.MIN_VALUE ? Integer.MIN_VALUE : (Ix[i - 1][j] + gapExtensionScore);
        Ix[i][j] = Math.max(Ix0, Ix1);
        if (Ix[i][j] != Integer.MIN_VALUE) {
          Vertex2 from = new Vertex2('X', i, j);
          vertices.put(from.id, from);
          if (Ix0 == Ix[i][j]) {
            String id = id('M', i - 1, j);
            Vertex2 to = vertices.getOrDefault(id, new Vertex2('M', i - 1, j));
            vertices.put(to.id, to);
            from.next.add(to);
          }
          if (Ix1 == Ix[i][j]) {
            String id = id('X', i - 1, j);
            Vertex2 to = vertices.getOrDefault(id, new Vertex2('X', i - 1, j));
            vertices.put(to.id, to);
            from.next.add(to);
          }
        }

        int Iy0 = M[i][j - 1] == Integer.MIN_VALUE ? Integer.MIN_VALUE : (M[i][j - 1] + gapOpeningScore);
        int Iy1 = Iy[i][j - 1] == Integer.MIN_VALUE ? Integer.MIN_VALUE : (Iy[i][j - 1] + gapExtensionScore);
        Iy[i][j] = Math.max(Iy0, Iy1);
        if (Iy[i][j] != Integer.MIN_VALUE) {
          Vertex2 from = new Vertex2('Y', i, j);
          vertices.put(from.id, from);
          if (Iy0 == Iy[i][j]) {
            String id = id('M', i, j - 1);
            Vertex2 to = vertices.getOrDefault(id, new Vertex2('M', i, j - 1));
            vertices.put(to.id, to);
            from.next.add(to);
          }
          if (Iy1 == Iy[i][j]) {
            String id = id('Y', i, j - 1);
            Vertex2 to = vertices.getOrDefault(id, new Vertex2('Y', i, j - 1));
            vertices.put(to.id, to);
            from.next.add(to);
          }
        }

        int s = string1.charAt(i - 1) == string2.charAt(j - 1) ? matchScore : mismatchScore;
        int m0 = M[i - 1][j - 1] == Integer.MIN_VALUE ? Integer.MIN_VALUE : (M[i - 1][j - 1] + s);
        int m1 = Ix[i - 1][j - 1] == Integer.MIN_VALUE ? Integer.MIN_VALUE : (Ix[i - 1][j - 1] + s);
        int m2 = Iy[i - 1][j - 1] == Integer.MIN_VALUE ? Integer.MIN_VALUE : (Iy[i - 1][j - 1] + s);
        M[i][j] = Math.max(m0, Math.max(m1, m2));
        if (M[i][j] != Integer.MIN_VALUE) {
          Vertex2 from = new Vertex2('M', i, j);
          vertices.put(from.id, from);
          if (m0 == M[i][j]) {
            String id = id('M', i - 1, j - 1);
            Vertex2 to = vertices.getOrDefault(id, new Vertex2('M', i - 1, j - 1));
            vertices.put(to.id, to);
            from.next.add(to);
          }
          if (m1 == M[i][j]) {
            String id = id('X', i - 1, j - 1);
            Vertex2 to = vertices.getOrDefault(id, new Vertex2('X', i - 1, j - 1));
            vertices.put(to.id, to);
            from.next.add(to);
          }
          if (m2 == M[i][j]) {
            String id = id('Y', i - 1, j - 1);
            Vertex2 to = vertices.getOrDefault(id, new Vertex2('Y', i - 1, j - 1));
            vertices.put(to.id, to);
            from.next.add(to);
          }
        }
      }
    }

    int i = string1.length();
    int j = string2.length();
    Vertex2 curr;
    if (M[i][j] >= Ix[i][j] && M[i][j] >= Iy[i][j]) {
      score = M[i][j];
      curr = vertices.get(id('M', i, j));
    } else if (Ix[i][j] >= M[i][j] && Ix[i][j] >= Iy[i][j]) {
      score = Ix[i][j];
      curr = vertices.get(id('X', i, j));
    } else {
      score = Iy[i][j];
      curr = vertices.get(id('Y', i, j));
    }
    StringBuilder s1 = new StringBuilder();
    StringBuilder s2 = new StringBuilder();
    while (!curr.next.isEmpty()) {
      Vertex2 next = curr.next.get(0);
      if (next.i == curr.i - 1 && next.j == curr.j - 1) {
        s1.insert(0, string1.charAt(curr.i - 1));
        s2.insert(0, string2.charAt(curr.j - 1));
      } else if (next.i == curr.i) {
        s1.insert(0, '-');
        s2.insert(0, string2.charAt(curr.j - 1));
      } else {
        s1.insert(0, string1.charAt(curr.i - 1));
        s2.insert(0, '-');
      }
      curr = next;
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

  private static String id(char table, int i, int j) {
    return table + ":" + i + ":" + j;
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

  private static class Vertex2 {

    final char table;

    final int i;

    final int j;

    final String id;

    final List<Vertex2> next;

    Vertex2(char table, int i, int j) {
      this.table = table;
      this.i = i;
      this.j = j;
      this.id = id(table, i, j);
      this.next = new ArrayList<>();
    }
  }
}
