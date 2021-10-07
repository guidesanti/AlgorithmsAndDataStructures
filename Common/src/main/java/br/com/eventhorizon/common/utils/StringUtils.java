package br.com.eventhorizon.common.utils;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.List;

public final class StringUtils {

  public static List<String> generatePrefixes(String text) {
    List<String> prefixes = new ArrayList<>();
    for (int i = 0; i < text.length(); i++) {
      prefixes.add(text.substring(0, i + 1));
    }
    return prefixes;
  }

  public static List<String> generateSuffixes(String text) {
    List<String> suffixes = new ArrayList<>();
    for (int i = 0; i < text.length(); i++) {
      suffixes.add(text.substring(i));
    }
    return suffixes;
  }

  public static List<String> generateKMers(String text, int k) {
    if (k > text.length()) {
      throw new IllegalArgumentException("Value of 'k' cannot be greater than text length");
    }
    List<String> kMers = new ArrayList<>();
    for (int i = 0; i <= text.length() - k; i++) {
      kMers.add(text.substring(i, i + k));
    }
    for (int i = text.length() - k + 1; i < text.length(); i++) {
      int overflow = k - (text.length() - i);
      kMers.add(text.substring(i) + text.substring(0, overflow));
    }
    return kMers;
  }
}
