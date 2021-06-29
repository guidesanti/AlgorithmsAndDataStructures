package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class LongestCommonSubsequenceOfTwoStrings implements PA {

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    String string1 = scanner.next();
    String string2 = scanner.next();
    System.out.println(find(string1, string2));
  }

  private static String find(String s1, String s2) {
    if (s1 == null || s1.isEmpty() || s2 == null || s2.isEmpty()) {
      return "";
    }
    int[][] paths = new int[s1.length() + 1][s2.length() + 1];
    for (int i = 1; i <= s1.length(); i++) {
      for (int j = 1; j <= s2.length(); j++) {
        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
          paths[i][j] = paths[i - 1][j - 1] + 1;
        } else {
          paths[i][j] = Math.max(paths[i][j - 1], paths[i - 1][j]);
        }
      }
    }
    StringBuilder subsequence = new StringBuilder();
    int i = s1.length();
    int j = s2.length();
    while (i > 0 && j > 0) {
      if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
        subsequence.append(s1.charAt(i - 1));
        i--;
        j--;
      } else if (paths[i][j - 1] > paths[i - 1][j]) {
        j--;
      } else {
        i--;
      }
    }
    return subsequence.reverse().toString();
  }
}
