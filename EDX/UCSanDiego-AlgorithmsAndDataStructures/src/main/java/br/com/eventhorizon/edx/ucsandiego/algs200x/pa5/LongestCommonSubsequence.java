package br.com.eventhorizon.edx.ucsandiego.algs200x.pa5;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.List;

public class LongestCommonSubsequence implements PA {

  @Override
  public void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int m = scanner.nextInt();
    List<Integer> s1 = new ArrayList<>();
    for (int i = 0; i < m; i++) {
      s1.add(scanner.nextInt());
    }

    int n = scanner.nextInt();
    List<Integer> s2 = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      s2.add(scanner.nextInt());
    }

    System.out.println(naiveLongestCommonSubsequence(s1, s2));
  }

  private static int naiveLongestCommonSubsequence(List<Integer> s1, List<Integer>s2) {
    if (s1.isEmpty() || s2.isEmpty()) {
      return 0;
    }
    if (s1.get(0).equals(s2.get(0))) {
      return 1 + naiveLongestCommonSubsequence(s1.subList(1, s1.size()), s2.subList(1, s2.size()));
    } else {
      int count1 = naiveLongestCommonSubsequence(s1.subList(1, s1.size()), s2);
      int count2 = naiveLongestCommonSubsequence(s1, s2.subList(1, s2.size()));
      return Math.max(count1, count2);
    }
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int m = scanner.nextInt();
    int[] s1 = new int[m];
    for (int i = 0; i < m; i++) {
      s1[i] = scanner.nextInt();
    }

    int n = scanner.nextInt();
    int[] s2 = new int[n];
    for (int i = 0; i < n; i++) {
      s2[i] = scanner.nextInt();
    }

    System.out.println(finalLongestCommonSubsequence(s1, s2));
  }

  private static int finalLongestCommonSubsequence(int[] s1, int[] s2) {
    int [][]l = new int[s1.length + 1][s2.length + 1];

    for (int i = 0; i < l.length; i++) {
      for (int j = 0; j < l[0].length; j++) {
        if (i == 0 || j == 0) {
          l[i][j] = 0;
        } else if (s1[i - 1] == s2[j - 1]) {
          l[i][j] = 1 + l[i - 1][j - 1];
        } else {
          l[i][j] = Math.max(l[i - 1][j], l[i][j - 1]);
        }
      }
    }

    return l[s1.length][s2.length];
  }
}
