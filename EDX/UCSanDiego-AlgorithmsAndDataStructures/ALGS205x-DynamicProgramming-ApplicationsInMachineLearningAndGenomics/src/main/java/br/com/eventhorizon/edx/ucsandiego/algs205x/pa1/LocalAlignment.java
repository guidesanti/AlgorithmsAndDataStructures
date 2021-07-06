package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class LocalAlignment implements PA {

  private static int matchScore;

  private static int mismatchScore;

  private static int gapScore;

  private static String string1;

  private static String string2;

  private static int localScore;

  private static String alignedString1;

  private static String alignedString2;

  @Override
  public void finalSolution() {
    init();
    readInput();
    finalLocalAlignment();
    writeOutput();
  }

  private static void finalLocalAlignment() {
    if (string1 == null || string1.isEmpty() || string2 == null || string2.isEmpty()) {
      return;
    }

    int maxI = 0;
    int maxJ = 0;
    int[][] scores = new int[string1.length() + 1][string2.length() + 1];
    for (int i = 1; i <= string1.length(); i++) {
      for (int j = 1; j <= string2.length(); j++) {
        if (string1.charAt(i - 1) == string2.charAt(j - 1)) {
          scores[i][j] = scores[i - 1][j - 1] + matchScore;
        } else {
          scores[i][j] = scores[i - 1][j - 1] + mismatchScore;
        }
        scores[i][j] = Math.max(Math.max(0, scores[i][j]), Math.max(scores[i - 1][j] + gapScore, scores[i][j - 1] + gapScore));
        if (scores[i][j] > localScore) {
          localScore = scores[i][j];
          maxI = i;
          maxJ = j;
        }
      }
    }

    StringBuilder s1 = new StringBuilder();
    StringBuilder s2 = new StringBuilder();
    int score = 0;
    while (score != localScore && (maxI > 0 || maxJ > 0)) {
      if (maxI == 0) {
        s1.insert(0, "-");
        s2.insert(0, string2.charAt(maxJ - 1));
        maxJ--;
        score += gapScore;
      } else if (maxJ == 0) {
        s1.insert(0, string1.charAt(maxI - 1));
        s2.insert(0, "-");
        maxI--;
        score += gapScore;
      } else {
        int a = string1.charAt(maxI - 1) == string2.charAt(maxJ - 1) ? matchScore : mismatchScore;
        if (scores[maxI][maxJ] == scores[maxI - 1][maxJ - 1] + a) {
          s1.insert(0, string1.charAt(maxI - 1));
          s2.insert(0, string2.charAt(maxJ - 1));
          maxI--;
          maxJ--;
          score += a;
        } else if (scores[maxI][maxJ] == scores[maxI - 1][maxJ] + gapScore) {
          s1.insert(0, string1.charAt(maxI - 1));
          s2.insert(0, "-");
          maxI--;
          score += gapScore;
        } else {
          s1.insert(0, "-");
          s2.insert(0, string2.charAt(maxJ - 1));
          maxJ--;
          score += gapScore;
        }
      }
    }
    alignedString1 = s1.toString();
    alignedString2 = s2.toString();
  }

  private static void init() {
    localScore = 0;
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
    System.out.println(localScore);
    System.out.println(alignedString1);
    System.out.println(alignedString2);
  }
}
