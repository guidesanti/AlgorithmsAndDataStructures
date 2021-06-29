package br.com.eventhorizon.string.matching;

import br.com.eventhorizon.string.suffixarray.ImprovedSuffixArrayBuilder;

import java.util.*;

public class SuffixArrayPatternMatcher implements PatternMatcher {

  @Override
  public Collection<Integer> match(String text, String pattern) {
    return match(text, Collections.singletonList(pattern));
  }

  @Override
  public Collection<Integer> match(String text, Collection<String> patterns) {
    Set<Integer> matches = new HashSet<>();
    int[] suffixArray = new ImprovedSuffixArrayBuilder().buildSuffixArray(text);
    for (String pattern : patterns) {
      int start = 0;
      int end = text.length();
      int minIndex = start;
      int maxIndex = end;
      while (minIndex < maxIndex) {
        int middleIndex = (minIndex + maxIndex) / 2;
        if (compareDown(text, pattern, suffixArray[middleIndex]) > 0) {
          minIndex = middleIndex + 1;
        } else {
          maxIndex = middleIndex;
        }
      }
      start = minIndex;
      maxIndex = text.length();
      while (minIndex < maxIndex) {
        int middleIndex = (minIndex + maxIndex) / 2;
        if (compareUp(text, pattern, suffixArray[middleIndex]) < 0) {
          maxIndex = middleIndex;
        } else {
          minIndex = middleIndex + 1;
        }
      }
      end = maxIndex;
      while (start < end) {
        matches.add(suffixArray[start++]);
      }
    }
    return matches;
  }

  private int compareDown(String text, String pattern, int suffixIndex) {
    int patternIndex = 0;
    while (patternIndex < pattern.length() && suffixIndex < text.length()) {
      if (pattern.charAt(patternIndex) != text.charAt(suffixIndex)) {
        return pattern.charAt(patternIndex) - text.charAt(suffixIndex);
      }
      patternIndex++;
      suffixIndex++;
    }
    return pattern.length() - patternIndex - text.length() + suffixIndex;
  }

  private int compareUp(String text, String pattern, int suffixIndex) {
    int patternIndex = 0;
    while (patternIndex < pattern.length() && suffixIndex < text.length()) {
      if (pattern.charAt(patternIndex) != text.charAt(suffixIndex)) {
        return pattern.charAt(patternIndex) - text.charAt(suffixIndex);
      }
      patternIndex++;
      suffixIndex++;
    }
    return text.length() - suffixIndex - pattern.length() + patternIndex;
  }
}
