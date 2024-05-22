package br.com.eventhorizon.edx.ucsandiego.algs204x.pa2;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.v2.PA;

import java.util.*;
import java.util.stream.Collectors;

public class BWTMatching implements PA {

  @Override
  public void trivialSolution() {
    FastScanner scanner = new FastScanner(System.in);
    String bwt = scanner.next();
    int numberOfPatterns = scanner.nextInt();
    List<String> patterns = new ArrayList<>(numberOfPatterns);
    for (int i = 0; i < numberOfPatterns; i++) {
      patterns.add(scanner.next());
    }
    String text = reverse(bwt);
    for (int count : naiveMatch(text, patterns)) {
      System.out.print(count + " ");
    }
  }

  private static int[] naiveMatch(String text, Collection<String> patterns) {
    int[] counts = new int[patterns.size()];
    int index = 0;
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
          counts[index]++;
        }
      }
      index++;
    }
    return counts;
  }

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

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    String bwt = scanner.next();
    int numberOfPatterns = scanner.nextInt();
    List<String> patterns = new ArrayList<>(numberOfPatterns);
    for (int i = 0; i < numberOfPatterns; i++) {
      patterns.add(scanner.next());
    }
    for (int count : bwtMatch(bwt, patterns)) {
      System.out.print(count + " ");
    }
  }

  private static int[] bwtMatch(String bwt, Collection<String> patterns) {
    Map<Character, Integer> symbolCount = countSymbols(bwt);
    Map<Character, Integer> firstColumnSymbolOffsets = calculateFirstColumnSymbolOffsets(symbolCount);
    int[] lastToFirstColumnMap = calculateLastToFirstColumnMap(bwt, firstColumnSymbolOffsets);
    int[] counts = new int[patterns.size()];
    int patternIndex = 0;
    for (String pattern : patterns) {
      int index = pattern.length() - 1;
      char symbol = pattern.charAt(index--);
      int top = firstColumnSymbolOffsets.get(symbol);
      int bottom = top + symbolCount.get(symbol) - 1;
      while (top <= bottom) {
        if (index >= 0) {
          symbol = pattern.charAt(index--);
          boolean stop = false;
          while (bwt.charAt(top) != symbol) {
            top++;
            if (top > bottom) {
              stop = true;
              break;
            }
          }
          if (stop) {
            break;
          }
          while (bwt.charAt(bottom) != symbol) {
            bottom--;
            if (bottom < top) {
              stop = true;
              break;
            }
          }
          if (stop) {
            break;
          }
          top = lastToFirstColumnMap[top];
          bottom = lastToFirstColumnMap[bottom];
        } else {
          counts[patternIndex] += (bottom - top + 1);
          break;
        }
      }
      patternIndex++;
    }
    return counts;
  }

  private static Map<Character, Integer> countSymbols(String bwt) {
    Map<Character, Integer> symbolCount = new HashMap<>();
    symbolCount.put('$', 0);
    symbolCount.put('A', 0);
    symbolCount.put('C', 0);
    symbolCount.put('G', 0);
    symbolCount.put('T', 0);
    for (int index = 0; index < bwt.length(); index++) {
      char symbol = bwt.charAt(index);
      symbolCount.put(symbol, symbolCount.get(symbol) + 1);
    }
    return symbolCount;
  }

  private static Map<Character, Integer> calculateFirstColumnSymbolOffsets(Map<Character, Integer> symbolCount) {
    List<Character> symbols = symbolCount.keySet().stream().sorted().collect(Collectors.toList());
    Map<Character, Integer> symbolOffsets = new HashMap<>();
    int offset = 0;
    for (char symbol : symbols) {
      symbolOffsets.put(symbol, offset);
      offset += symbolCount.get(symbol);
    }
    return symbolOffsets;
  }

  private static int[] calculateLastToFirstColumnMap(String bwt, Map<Character, Integer> firstColumnSymbolOffsets) {
    Map<Character, Integer> symbolCount = new HashMap<>();
    int[] lastToFirstColumnMap = new int[bwt.length()];
    for (int index = 0; index < bwt.length(); index++) {
      char symbol = bwt.charAt(index);
      int count = symbolCount.getOrDefault(symbol, 0);
      lastToFirstColumnMap[index] = firstColumnSymbolOffsets.get(symbol) + count;
      count++;
      symbolCount.put(symbol, count);
    }
    return lastToFirstColumnMap;
  }
}
