package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class FittingAlignment implements PA {

  private static int matchScore;

  private static int mismatchScore;

  private static int gapScore;

  private static String string1;

  private static String string2;

  private static int score;

  private static String alignment1;

  private static String alingment2;

  @Override
  public void naiveSolution() {
    init();
    readInput();
    naiveFittingAlignment();
    writeOutput();
  }

  private static void naiveFittingAlignment() {
    for (int length = 1; length <= string1.length(); length++) {
      for (int index = 0; index < string1.length() - length + 1; index++) {
        String source = string1.substring(index, index + length);
        score = Math.max(score, naiveRecursiveFittingAlignment(source, string2, 0, source.length() + string2.length()));
      }
    }
  }

  private static int naiveRecursiveFittingAlignment(String source, String target, int index, int max) {
    int gap1 = Integer.MIN_VALUE;
    int gap2 = Integer.MIN_VALUE;
    int mis = Integer.MIN_VALUE;
    int mat = Integer.MIN_VALUE;

    if (source.length() == target.length()) {
      //      return score(source, target);
      return 0;
    }
    if (index < source.length() && index < target.length()) {
      if (source.charAt(index) == target.charAt(index)) {
        mat = naiveRecursiveFittingAlignment(source, target, index + 1, max) + matchScore;
      } else {
        mis = naiveRecursiveFittingAlignment(source, target, index + 1, max) + mismatchScore;
      }
    }
    if (source.length() < max) {
      if (index < source.length()) {
        gap1 = naiveRecursiveFittingAlignment(source.substring(0, index) + "-" + source.substring(index), target, index + 1, max) + gapScore;
      } else {
        gap1 = naiveRecursiveFittingAlignment(source + "-", target, index + 1, max) + gapScore;
      }
    }
    if (target.length() < max) {
      if (index < target.length()) {
        gap2 = naiveRecursiveFittingAlignment(source, target.substring(0, index) + "-" + target.substring(index), index + 1, max) + gapScore;
      } else {
        gap2 = naiveRecursiveFittingAlignment(source, target + "-", index + 1, max) + gapScore;
      }
    }
    return Math.max(Math.max(gap1, gap2), Math.max(mat, mis));
  }

  private static int score(String string1, String string2) {
    int score = 0;
    for (int index = 0; index < string1.length(); index++) {
      if (string1.charAt(index) == '-' || string2.charAt(index) == '-') {
        score += gapScore;
      } else if (string1.charAt(index) == string2.charAt(index)) {
        score += matchScore;
      } else {
        score += mismatchScore;
      }
    }
    return score;
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    finalFittingAlignment();
    writeOutput();
  }

  private static void finalFittingAlignment() {
    // TODO
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
    System.out.println(alingment2);
  }
}
