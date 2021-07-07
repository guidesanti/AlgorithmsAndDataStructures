package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class OverlapAlignment implements PA {

  private static int matchScore;

  private static int mismatchScore;

  private static int gapScore;

  private static String string1;

  private static String string2;

  private static int score;

  private static String alignment1;

  private static String alignment2;

  @Override
  public void finalSolution() {
    init();
    readInput();
    finalFittingAlignment();
    writeOutput();
  }

  private static void finalFittingAlignment() {
    if (string1 == null || string1.isEmpty() || string2 == null || string2.isEmpty()) {
      return;
    }

    int maxI = 0;
    int maxJ = 0;
    int[][] scores = new int[string1.length() + 1][string2.length() + 1];
    for (int i = 1; i < scores[0].length; i++) {
      scores[0][i] = scores[0][i - 1] + gapScore;
    }
    for (int i = 1; i <= string1.length(); i++) {
      for (int j = 1; j <= string2.length(); j++) {
        if (string1.charAt(i - 1) == string2.charAt(j - 1)) {
          scores[i][j] = scores[i - 1][j - 1] + matchScore;
        } else {
          scores[i][j] = scores[i - 1][j - 1] + mismatchScore;
        }
        scores[i][j] = Math.max(scores[i][j], Math.max(scores[i - 1][j] + gapScore, scores[i][j - 1] + gapScore));
        if (i == string1.length() && scores[i][j] > score) {
          score = scores[i][j];
          maxI = i;
          maxJ = j;
        }
      }
    }

    StringBuilder s1 = new StringBuilder();
    StringBuilder s2 = new StringBuilder();
    score = scores[maxI][maxJ];
    while (maxJ > 0) {
      if (maxI == 0) {
        s1.insert(0, "-");
        s2.insert(0, string2.charAt(maxJ - 1));
        maxJ--;
      } else {
        int a = string1.charAt(maxI - 1) == string2.charAt(maxJ - 1) ? matchScore : mismatchScore;
        if (scores[maxI][maxJ] == scores[maxI - 1][maxJ - 1] + a) {
          s1.insert(0, string1.charAt(maxI - 1));
          s2.insert(0, string2.charAt(maxJ - 1));
          maxI--;
          maxJ--;
        } else if (scores[maxI][maxJ] == scores[maxI - 1][maxJ] + gapScore) {
          s1.insert(0, string1.charAt(maxI - 1));
          s2.insert(0, "-");
          maxI--;
        } else {
          s1.insert(0, "-");
          s2.insert(0, string2.charAt(maxJ - 1));
          maxJ--;
        }
      }
    }
    alignment1 = s1.toString();
    alignment2 = s2.toString();
  }

  private static void init() {
    score = Integer.MIN_VALUE;
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
}
