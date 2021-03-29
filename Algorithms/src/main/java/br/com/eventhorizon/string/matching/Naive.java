package br.com.eventhorizon.string.matching;

import br.com.eventhorizon.common.Utils;

import java.util.ArrayList;
import java.util.List;

public class Naive implements StringMatchingAlgorithm {

  @Override
  public int[] match(char[] text, char[] pattern) {
    if (pattern.length == 0) {
      int[] shifts = new int[text.length];
      for (int i = 0; i < shifts.length; i++) {
        shifts[i] = i;
      }
      return shifts;
    }
    List<Integer> shifts = new ArrayList<>();
    int n = text.length - pattern.length;
    for (int i = 0; i <= n; i++) {
      boolean match = true;
      for (int j = 0; j < pattern.length; j++) {
        if (pattern[j] != text[i + j]) {
          match = false;
          break;
        }
      }
      if (match) {
        shifts.add(i);
      }
    }
    return Utils.listOfIntegersToArray(shifts);
  }
}
