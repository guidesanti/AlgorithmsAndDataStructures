package br.com.eventhorizon.string.matching;

import java.util.*;

public class RabinKarpPatternMatcher implements PatternMatcher {

  private static final long PRIME = 1000000009L;

  private static final long X = 94;

  @Override
  public Collection<Integer> match(String text, String pattern) {
    if (pattern.length() == 0) {
      return Collections.emptyList();
    }

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

    List<Integer> shifts = new ArrayList<>();
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
          shifts.add(i);
        }
      }
      if (i < n - m) {
        hash = (X * (hash - h * text.charAt(i)) + text.charAt(i + m)) % PRIME;
        if (hash < 0) {
          hash += PRIME;
        }
      }
    }
    return shifts;
  }

  @Override
  public Collection<Integer> match(String text, Collection<String> patterns) {
    Set<Integer> shifts = new HashSet<>();
    for (String pattern : patterns) {
      shifts.addAll(match(text, pattern));
    }
    return shifts;
  }
}
