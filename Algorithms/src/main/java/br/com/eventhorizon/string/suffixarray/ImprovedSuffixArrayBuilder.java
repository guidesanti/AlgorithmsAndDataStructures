package br.com.eventhorizon.string.suffixarray;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ImprovedSuffixArrayBuilder implements SuffixArrayBuilder {

  private static final char EOF = 0;

  @Override
  public int[] buildSuffixArray(String text) {
    int[] order = sortCharacters(text);
    int[] classes = computeCharacterClasses(text, order);
    for (int length = 1; length <= text.length(); length *= 2) {
      order = sortDoubledShifts(text, length, order, classes);
      classes = updateClasses(order, classes, length);
    }
    return Arrays.copyOfRange(order, 1, order.length);
  }

  private int[] sortCharacters(String text) {
    int[] order = new int[text.length() + 1];
    Map<Character, Integer> count = new HashMap<>();

    // Discover all possible symbols in the text and count the occurrence of each one
    for (int i = 0; i <= text.length(); i++) {
      char symbol = charAt(text, i);
      count.put(symbol, count.getOrDefault(symbol, 0) + 1);
    }

    // For each possible symbol, calculate the position in the sorted array of all the symbols
    // of the text right after the last such symbol
    List<Character> sortedSymbols = count.keySet().stream().sorted().collect(Collectors.toList());
    for (int i = 1; i < sortedSymbols.size(); i++) {
      char prevSymbol = sortedSymbols.get(i - 1);
      char symbol = sortedSymbols.get(i);
      count.put(symbol, count.get(symbol) + count.get(prevSymbol));
    }

    // Calculate the order of the characters within text
    for (int i = text.length(); i >= 0; i--) {
      char symbol = charAt(text, i);
      int position = count.get(symbol) - 1;
      count.put(symbol, position);
      order[position] = i;
    }

    return order;
  }

  private int[] computeCharacterClasses(String text, int[] order) {
    int[] classes = new int[order.length];
    classes[order[0]] = 0;
    for (int i = 1; i < order.length; i++) {
      if (charAt(text, order[i]) == charAt(text, order[i - 1])) {
        classes[order[i]] = classes[order[i - 1]];
      } else {
        classes[order[i]] = classes[order[i - 1]] + 1;
      }
    }
    return classes;
  }

  private int[] sortDoubledShifts(String text, int length, int[] order, int[] classes) {
    int[] count = new int[order.length];
    int[] newOrder = new int[order.length];

    // Count the number of times each class occurs in the text
    for (int i = 0; i <= text.length(); i++) {
      count[classes[i]] = count[classes[i]] + 1;
    }

    // For each class, calculate the position in the sorted array of the last such class
    for (int i = 1; i <= text.length(); i++) {
      count[i] = count[i] + count[i - 1];
    }

    // Calculate the order of the doubled shifts
    for (int i = text.length(); i >= 0; i--) {
      int doubledShiftStart = (order[i] - length + text.length() + 1) % (text.length() + 1);
      int clazz = classes[doubledShiftStart];
      count[clazz] = count[clazz] - 1;
      newOrder[count[clazz]] = doubledShiftStart;
    }

    return newOrder;
  }

  private int[] updateClasses(int[] order, int[] classes, int length) {
    int[] newClasses = new int[order.length];
    newClasses[order[0]] = 0;
    for (int i = 1; i < order.length; i++) {
      int curr = order[i];
      int prev = order[i - 1];
      int midCurr = (curr + length) % order.length;
      int midPrev = (prev + length) % order.length;
      if (classes[curr] != classes[prev] ||
          classes[midCurr] != classes[midPrev]) {
        newClasses[curr] = newClasses[prev] + 1;
      } else {
        newClasses[curr] = newClasses[prev];
      }
    }
    return newClasses;
  }

  private char charAt(String text, int index) {
    return index < text.length() ? text.charAt(index) : EOF;
  }
}
