package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class GlobalAlignment implements PA {

  private static int matchScore;

  private static int mismatchScore;

  private static int gapScore;

  private static String string1;

  private static String string2;

  private static int globalScore;

  private static String alignedString1;

  private static String alignedString2;

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
    globalScore = scores[i][j];
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
    alignedString1 = s1.toString();
    alignedString2 = s2.toString();
  }

  private static void init() {
    globalScore = 0;
    alignedString1 = "";
    alignedString2 = "";
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
    System.out.println(globalScore);
    System.out.println(alignedString1);
    System.out.println(alignedString2);
  }
}
