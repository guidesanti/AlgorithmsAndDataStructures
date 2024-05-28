package br.com.eventhorizon.edx.ucsandiego.algs201x.pa3;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.List;

public class FindPatterInText implements PA {

  private static final long PRIME = 1000000009L;

  private static long X = 73;

  @Override
  public void trivialSolution() {
    FastScanner in = new FastScanner(System.in);
    String pattern = in.next();
    String text = in.next();
    naiveFindPattern(text, pattern).forEach(i -> System.out.print(i + " "));
  }

  private static List<Integer> naiveFindPattern(String text, String pattern) {
    List<Integer> list = new ArrayList<>();
    for (int i = 0; i + pattern.length() <= text.length(); i++) {
      boolean find = true;
      for (int j = 0; j < pattern.length(); j++) {
        if (text.charAt(i + j) != pattern.charAt(j)) {
          find = false;
          break;
        }
      }
      if (find) {
        list.add(i);
      }
    }
    return list;
  }

  @Override
  public void finalSolution() {
    FastScanner in = new FastScanner(System.in);
    String pattern = in.next();
    String text = in.next();
    finalFindPattern(text, pattern).forEach(i -> System.out.print(i + " "));
  }

  private static List<Integer> finalFindPattern(String text, String pattern) {
    int n = text.length();
    int m = pattern.length();
    long h = 1;
    long patternHash = 0;
    long hash = 0;

    for (int i = 0; i < m; i++) {
      if (i < m - 1) {
        h = (h * X) % PRIME;
      }
      patternHash = ((patternHash * X) + pattern.charAt(i)) % PRIME;
      hash = ((hash * X) + text.charAt(i)) % PRIME;
    }

    List<Integer> list = new ArrayList<>();
    for (int i = 0; i <= n - m; i++) {
      if (hash == patternHash) {
        boolean find = true;
        for (int j = 0; j < pattern.length(); j++) {
          if (text.charAt(i + j) != pattern.charAt(j)) {
            find = false;
            break;
          }
        }
        if (find) {
          list.add(i);
        }
      }
      if (i < n - m) {
        hash = (X * (hash - h * text.charAt(i)) + text.charAt(i + m)) % PRIME;
        if (hash < 0) {
          hash += PRIME;
        }
      }
    }
    return list;
  }
}
