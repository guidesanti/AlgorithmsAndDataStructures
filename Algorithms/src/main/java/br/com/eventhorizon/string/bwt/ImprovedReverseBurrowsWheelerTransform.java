package br.com.eventhorizon.string.bwt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ImprovedReverseBurrowsWheelerTransform implements ReverseBurrowsWheelerTransform {

  @Override
  public String reverse(String burrowsWheelerTransform) {
    // Count the total number of each symbol within Burrows-Wheeler transform
    Map<Character, Integer> symbolCount = new HashMap<>();
    for (int index = 0; index < burrowsWheelerTransform.length(); index++) {
      char symbol = burrowsWheelerTransform.charAt(index);
      symbolCount.put(symbol, symbolCount.getOrDefault(symbol, 0) + 1);
    }

    // Calculate the offset of first occurrence of each symbol in the first column
    List<Character> symbols = symbolCount.keySet().stream().sorted().collect(Collectors.toList());
    Map<Character, Integer> symbolOffset = new HashMap<>();
    int offset = 0;
    for (char symbol : symbols) {
      symbolOffset.put(symbol, offset);
      offset += symbolCount.get(symbol);
    }

    // Calculate the last column to first column map
    symbolCount = new HashMap<>();
    int[] lastToFirstColumnMap = new int[burrowsWheelerTransform.length()];
    for (int index = 0; index < burrowsWheelerTransform.length(); index++) {
      char symbol = burrowsWheelerTransform.charAt(index);
      int count = symbolCount.getOrDefault(symbol, 0);
      lastToFirstColumnMap[index] = symbolOffset.get(symbol) + count;
      count++;
      symbolCount.put(symbol, count);
    }

    // Reconstruct the text
    char[] text = new char[burrowsWheelerTransform.length() - 1];
    int bwtIndex = 0;
    for (int textIndex = burrowsWheelerTransform.length() - 2; textIndex >= 0; textIndex--) {
      char symbol = burrowsWheelerTransform.charAt(bwtIndex);
      text[textIndex] = symbol;
      bwtIndex = lastToFirstColumnMap[bwtIndex];
    }

    return new String(text);
  }
}
