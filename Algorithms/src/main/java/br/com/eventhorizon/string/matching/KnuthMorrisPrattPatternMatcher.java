package br.com.eventhorizon.string.matching;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class KnuthMorrisPrattPatternMatcher implements PatternMatcher {

  private String pattern;

  private String text;

  @Override
  public Collection<Integer> match(String text, String pattern) {
    Set<Integer> matches = new HashSet<>();
    this.pattern = pattern;
    this.text = text;
    int length = pattern.length() + text.length() + 1;
    int[] longestBorder = new int[length];
    longestBorder[0] = 0;
    int border = 0;
    for (int i = 1; i < length; i++) {
      while (border > 0 && !equals(i, border)) {
        border = longestBorder[border - 1];
      }
      if (equals(i, border)) {
        border++;
      } else {
        border = 0;
      }
      longestBorder[i] = border;
      if (i > pattern.length() && border == pattern.length()) {
        matches.add(i - (2 * pattern.length()));
      }
    }
    return matches;
  }

  @Override
  public Collection<Integer> match(String text, Collection<String> patterns) {
    Set<Integer> matches = new HashSet<>();
    for (String pattern : patterns) {
      matches.addAll(match(text, pattern));
    }
    return matches;
  }

  private boolean equals(int index1, int index2) {
    if (index1 == pattern.length() || index2 == pattern.length()) {
      return false;
    }
    char char1 = index1 < pattern.length() ? pattern.charAt(index1) : text.charAt(index1 - pattern.length() - 1);
    char char2 = index2 < pattern.length() ? pattern.charAt(index2) : text.charAt(index2 - pattern.length() - 1);
    return char1 == char2;
  }
}
