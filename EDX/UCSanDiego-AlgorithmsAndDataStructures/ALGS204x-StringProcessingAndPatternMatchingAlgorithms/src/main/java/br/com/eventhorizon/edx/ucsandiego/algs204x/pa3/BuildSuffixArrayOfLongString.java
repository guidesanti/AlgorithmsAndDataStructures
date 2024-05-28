package br.com.eventhorizon.edx.ucsandiego.algs204x.pa3;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;
import java.util.stream.Collectors;

public class BuildSuffixArrayOfLongString implements PA {

  @Override
  public void trivialSolution() {
    FastScanner scanner = new FastScanner(System.in);
    String text = scanner.next();
    List<String> suffixes = new ArrayList<>();
    for (int index = 0; index < text.length(); index++) {
      suffixes.add(text.substring(index));
    }
    Collections.sort(suffixes);
    suffixes.forEach(suffix -> System.out.print(text.length() - suffix.length() + " "));
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    String text = scanner.next();
    int[] suffixArray = buildSuffixArray(text);
    for (int suffixIndex : suffixArray) {
      System.out.print(suffixIndex + " ");
    }
  }

  private static int[] buildSuffixArray(String text) {
    int[] order = sortCharacters(text);
    int[] classes = computeCharacterClasses(text, order);
    for (int length = 1; length < text.length(); length *= 2) {
      order = sortDoubledShifts(text, length, order, classes);
      classes = updateClasses(order, classes, length);
    }
    return order;
  }

  private static int[] sortCharacters(String text) {
    int[] order = new int[text.length()];
    Map<Character, Integer> count = new HashMap<>();

    // Discover all possible symbols in the text and count the occurrence of each one
    for (int i = 0; i < text.length(); i++) {
      char symbol = text.charAt(i);
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
    for (int i = text.length() - 1; i >= 0; i--) {
      char symbol = text.charAt(i);
      int position = count.get(symbol) - 1;
      count.put(symbol, position);
      order[position] = i;
    }

    return order;
  }

  private static int[] computeCharacterClasses(String text, int[] order) {
    int[] classes = new int[order.length];
    classes[order[0]] = 0;
    for (int i = 1; i < order.length; i++) {
      if (text.charAt(order[i]) == text.charAt(order[i - 1])) {
        classes[order[i]] = classes[order[i - 1]];
      } else {
        classes[order[i]] = classes[order[i - 1]] + 1;
      }
    }
    return classes;
  }

  private static int[] sortDoubledShifts(String text, int length, int[] order, int[] classes) {
    int[] count = new int[order.length];
    int[] newOrder = new int[order.length];

    // Count the number of times each class occurs in the text
    for (int i = 0; i < text.length(); i++) {
      count[classes[i]] = count[classes[i]] + 1;
    }

    // For each class, calculate the position in the sorted array of the last such class
    for (int i = 1; i < text.length(); i++) {
      count[i] = count[i] + count[i - 1];
    }

    // Calculate the order of the doubled shifts
    for (int i = text.length() - 1; i >= 0; i--) {
      int doubledShiftStart = (order[i] - length + text.length()) % text.length();
      int clazz = classes[doubledShiftStart];
      count[clazz] = count[clazz] - 1;
      newOrder[count[clazz]] = doubledShiftStart;
    }

    return newOrder;
  }

  private static int[] updateClasses(int[] order, int[] classes, int length) {
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
}
