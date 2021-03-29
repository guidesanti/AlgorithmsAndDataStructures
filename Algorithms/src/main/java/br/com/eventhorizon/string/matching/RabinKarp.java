package br.com.eventhorizon.string.matching;

import br.com.eventhorizon.common.Utils;

import java.util.ArrayList;
import java.util.List;

public class RabinKarp implements StringMatchingAlgorithm {

  private static final long PRIME = 1000000009L;

  private static final long X = 94;

  @Override
  public int[] match(char[] text, char[] pattern) {
    if (pattern.length == 0) {
      int[] shifts = new int[text.length];
      for (int i = 0; i < shifts.length; i++) {
        shifts[i] = i;
      }
      return shifts;
    }

    int n = text.length;
    int m = pattern.length;
    long h = 1;
    long patternHash = 0;
    long hash = 0;

    for (int i = 0; i < m; i++) {
      if (i < m - 1) {
        h = (h * X) % PRIME;
      }
      patternHash = ((patternHash * X) + pattern[i]) % PRIME;
      hash = ((hash * X) + text[i]) % PRIME;
    }

    List<Integer> list = new ArrayList<>();
    for (int i = 0; i <= n - m; i++) {
      if (hash == patternHash) {
        boolean find = true;
        for (int j = 0; j < pattern.length; j++) {
          if (text[i + j] != pattern[j]) {
            find = false;
            break;
          }
        }
        if (find) {
          list.add(i);
        }
      }
      if (i < n - m) {
        hash = (X * (hash - h * text[i]) + text[i + m]) % PRIME;
        if (hash < 0) {
          hash += PRIME;
        }
      }
    }
    return Utils.listOfIntegersToArray(list);
  }
}
