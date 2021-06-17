package br.com.eventhorizon.string;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NaiveSuffixArrayBuilder implements SuffixArrayBuilder {

  @Override
  public int[] buildSuffixArray(String text) {
    List<String> suffixes = new ArrayList<>();
    for (int index = 0; index < text.length(); index++) {
      suffixes.add(text.substring(index));
    }
    Collections.sort(suffixes);
    int[] suffixArray = new int[text.length()];
    for (int i = 0; i < suffixes.size(); i++) {
      String suffix = suffixes.get(i);
      suffixArray[i] = text.length() - suffix.length();
    }
    return suffixArray;
  }
}
