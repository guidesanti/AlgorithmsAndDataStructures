package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class EditDistance implements PA {

  private static String string1;

  private static String string2;

  private static int editDistance;

  @Override
  public void naiveSolution() {
    init();
    readInput();
    naiveEditDistance();
    writeOutput();
  }

  private static void naiveEditDistance() {
    editDistance = naiveRecursiveEditDistance(string1, string2, 0);
  }

  private static int naiveRecursiveEditDistance(String source, String target, int index) {
    if (source.equals(target)) {
      return 0;
    }
    int del = Integer.MAX_VALUE;
    int ins = Integer.MAX_VALUE;
    int sub = Integer.MAX_VALUE;

    if (index < source.length()) {
      del = naiveRecursiveEditDistance(source.substring(0, index) + source.substring(index + 1), target, index) + 1;
    }
    if (index < target.length()) {
      ins = naiveRecursiveEditDistance(source.substring(0, index) + target.charAt(index) + source.substring(index), target, index + 1) + 1;
    }
    if (index < target.length() && index < source.length()) {
      sub = source.charAt(index) == target.charAt(index) ?
          naiveRecursiveEditDistance(source, target, index + 1) :
          naiveRecursiveEditDistance(source.substring(0, index) + target.charAt(index) + source.substring(index + 1), target, index + 1) + 1;
    }
    return Math.min(del, Math.min(ins, sub));
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    finalEditDistance();
    writeOutput();
  }

  private static void finalEditDistance() {
    if (string1 == null || string1.isEmpty() || string2 == null || string2.isEmpty()) {
      return;
    }

    int[][] editDistances = new int[string1.length() + 1][string2.length() + 1];
    for (int i = 1; i < editDistances[0].length; i++) {
      editDistances[0][i] = i;
    }
    for (int i = 1; i < editDistances.length; i++) {
      editDistances[i][0] = i;
    }
    for (int i = 1; i <= string1.length(); i++) {
      for (int j = 1; j <= string2.length(); j++) {
        editDistances[i][j] = string1.charAt(i - 1) == string2.charAt(j - 1) ?
            editDistances[i - 1][j - 1] :
            editDistances[i - 1][j - 1] + 1;
        editDistances[i][j] = Math.min(editDistances[i][j], Math.min(editDistances[i - 1][j] + 1, editDistances[i][j - 1] + 1));
      }
    }
    editDistance = editDistances[string1.length()][string2.length()];
  }

  private static void init() {
    editDistance = 0;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);
    string1 = scanner.next();
    string2 = scanner.next();
  }

  private static void writeOutput() {
    System.out.println(editDistance);
  }
}
