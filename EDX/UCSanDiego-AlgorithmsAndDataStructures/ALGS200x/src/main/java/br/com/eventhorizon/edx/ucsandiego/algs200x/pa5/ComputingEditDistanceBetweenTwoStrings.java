package br.com.eventhorizon.edx.ucsandiego.algs200x.pa5;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class ComputingEditDistanceBetweenTwoStrings implements PA {

  @Override
  public void naiveSolution() {
    FastScanner scan = new FastScanner(System.in);
    String s = scan.next();
    String t = scan.next();
    System.out.println(naiveEditDistance(s, t));
  }

  public static int naiveEditDistance(String s1, String s2) {
    int count = 0;
    if (s1.isEmpty() && s2.isEmpty()) {
      return 0;
    }
    if (s1.isEmpty()) {
      return s2.length();
    }
    if (s2.isEmpty()) {
      return s1.length();
    }
    if (s1.charAt(0) != s2.charAt(0)) {
      count += 1;
      int d1 = naiveEditDistance(s1.substring(1), s2);
      int d2 = naiveEditDistance(s1, s2.substring(1));
      int d3 = naiveEditDistance(s1.substring(1), s2.substring(1));
      count += Math.min(Math.min(d1, d2), d3);
    } else {
      count += naiveEditDistance(s1.substring(1), s2.substring(1));
    }
    return count;
  }

  @Override
  public void finalSolution() {
    FastScanner scan = new FastScanner(System.in);
    String s = scan.next();
    String t = scan.next();
    System.out.println(finalEditDistance(s, t));
  }

  public static int finalEditDistance(String s1, String s2) {
    int[][] m = new int[s1.length() + 1][s2.length() + 1];

    for (int i = 1; i < m[0].length; i++) {
      m[0][i] = i;
    }
    for (int i = 1; i < m.length; i++) {
      m[i][0] = i;
    }
    for (int i = 1; i < m.length; i++) {
      for (int j = 1; j < m[0].length; j++) {
        int insertions = m[i][j - 1] + 1;
        int deletions = m[i - 1][j] + 1;
        int matches = m[i - 1][j - 1];
        int mismatches = m[i - 1][j - 1] + 1;
        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
          m[i][j] = Math.min(Math.min(insertions, deletions), matches);
        } else {
          m[i][j] = Math.min(Math.min(insertions, deletions), mismatches);
        }
      }
    }

    return m[m.length - 1][m[0].length - 1];
  }
}
