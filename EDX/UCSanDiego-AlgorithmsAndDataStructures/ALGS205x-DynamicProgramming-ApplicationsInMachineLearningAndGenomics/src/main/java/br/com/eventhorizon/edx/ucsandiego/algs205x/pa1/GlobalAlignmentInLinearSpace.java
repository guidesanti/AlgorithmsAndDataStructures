package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

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

    // TODO: put the largest string in the X edge

    int[] path = new int[string1.length() + 1];
    finalGlobalAlignment1(path, 0, 0, string2.length(), string1.length());
    path[path.length - 1] = string2.length();
    buildAlignment(path);
  }

  private static void finalGlobalAlignment1(int[] path, int fromI, int fromJ, int toI, int toJ) {
    if (((toI - fromI)*(toI - fromI) + (toJ - fromJ)*(toJ - fromJ)) <= 2) {
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
    for (int j = 1; j <= middleJ; j++) {
      tempJ1 = j % 2;
      int tempJJ1 = tempJ1 == 0 ? 1 : 0;
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
    for (int j = numberOfColumns - 2; j >= middleJ; j--) {
      tempJ2 = j % 2;
      int tempJJ2 = tempJ2 == 0 ? 1 : 0;
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
    int middleI = 0;
    for (int i = 0; i < numberOfRows; i++) {
      int temp = scores1[i][tempJ1] + scores2[i][tempJ2];
      if (temp > max) {
        max = temp;
        middleI = i;
      }
    }
    path[fromJ + middleJ] = fromI + middleI;

    if (fromI == 0 && fromJ == 0 && toI == string2.length() && toJ == string1.length()) {
      score = max;
    }

    finalGlobalAlignment1(path, fromI, fromJ, fromI + middleI, fromJ + middleJ);
    finalGlobalAlignment1(path, fromI + middleI, fromJ + middleJ, toI, toJ);
  }

  private static void buildAlignment(int[] path) {
    StringBuilder s1 = new StringBuilder();
    StringBuilder s2 = new StringBuilder();

    int prevI = 0;
    int prevJ = 0;
    for (int j = 1; j < path.length; j++) {
      int i = path[j];
      if (prevI == i - 1 && prevJ == j - 1) {
        s1.append(string1.charAt(j - 1));
        s2.append(string2.charAt(i - 1));
      } else if (prevI == i) {
        s1.append(string1.charAt(j - 1));
        s2.append('-');
      } else {
        s1.append('-');
        s2.append(string2.charAt(i - 1));
      }
      prevI = i;
      prevJ = j;
    }

    alignment1 = s1.toString();
    alignment2 = s2.toString();
  }
}
