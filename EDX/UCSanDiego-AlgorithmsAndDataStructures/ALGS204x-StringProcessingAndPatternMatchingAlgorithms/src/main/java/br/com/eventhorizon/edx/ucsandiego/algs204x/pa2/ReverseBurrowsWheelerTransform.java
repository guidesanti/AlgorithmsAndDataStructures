package br.com.eventhorizon.edx.ucsandiego.algs204x.pa2;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReverseBurrowsWheelerTransform implements PA {

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    String bwt = scanner.next();
    System.out.println(reverseBurrowsWheelerTransform(bwt));
  }

  private static String reverseBurrowsWheelerTransform(String bwt) {
    // Count the total number of each symbol within Burrows-Wheeler transform
    Map<Character, Integer> symbolCount = new HashMap<>();
    for (int index = 0; index < bwt.length(); index++) {
      char symbol = bwt.charAt(index);
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
    int[] lastToFirstColumnMap = new int[bwt.length()];
    for (int index = 0; index < bwt.length(); index++) {
      char symbol = bwt.charAt(index);
      int count = symbolCount.getOrDefault(symbol, 0);
      lastToFirstColumnMap[index] = symbolOffset.get(symbol) + count;
      count++;
      symbolCount.put(symbol, count);
    }

    // Reconstruct the text
    char[] text = new char[bwt.length()];
    int bwtIndex = 0;
    text[bwt.length() - 1] = '$';
    for (int textIndex = bwt.length() - 2; textIndex >= 0; textIndex--) {
      char symbol = bwt.charAt(bwtIndex);
      text[textIndex] = symbol;
      bwtIndex = lastToFirstColumnMap[bwtIndex];
    }

    return new String(text);
  }
}
