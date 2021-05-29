package br.com.eventhorizon.string.matching;

import java.util.*;

public class NaivePatternMatcher implements PatternMatcher {

  @Override
  public Collection<Integer> match(String text, String pattern) {
    if (pattern.length() == 0) {
      return Collections.emptyList();
    }
    List<Integer> shifts = new ArrayList<>();
    int n = text.length() - pattern.length();
    for (int i = 0; i <= n; i++) {
      boolean match = true;
      for (int j = 0; j < pattern.length(); j++) {
        if (pattern.charAt(j) != text.charAt(i + j)) {
          match = false;
          break;
        }
      }
      if (match) {
        shifts.add(i);
      }
    }
    return shifts;
  }

  @Override
  public Collection<Integer> match(String text, Collection<String> patterns) {
    Set<Integer> shifts = new HashSet<>();
    // TODO: handle cases where patterns == null or empty
    for (String pattern : patterns) {
      int n = text.length() - pattern.length();
      for (int i = 0; i <= n; i++) {
        boolean match = true;
        for (int j = 0; j < pattern.length(); j++) {
          if (pattern.charAt(j) != text.charAt(i + j)) {
            match = false;
            break;
          }
        }
        if (match) {
          shifts.add(i);
        }
      }
    }
    return shifts;
  }
}
