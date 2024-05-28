package br.com.eventhorizon.edx.ucsandiego.algs204x.pa3;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;
import java.util.stream.Collectors;

public class FindPatternInString implements PA {

  private static final char EOF = '$';

  @Override
  public void trivialSolution() {
    FastScanner scanner = new FastScanner(System.in);
    String pattern = scanner.next();
    String text = scanner.next();
    Collection<Integer> matches = naiveMatch(text, pattern);
    matches.forEach(integer -> System.out.print(integer + " "));
  }

  private static Collection<Integer> naiveMatch(String text, String pattern) {
    if (pattern.length() == 0) {
      return Collections.emptyList();
    }
    List<Integer> shifts = new ArrayList<>();
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
        shifts.add(i);
      }
    }
    return shifts;
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    String pattern = scanner.next();
    String text = scanner.next();
    Collection<Integer> matches = Collections.emptyList();
    if (pattern.length() <= text.length()) {
      matches = match(text, Collections.singleton(pattern));
    }
    matches.stream().sorted().forEach(integer -> System.out.print(integer + " "));
  }

  private static Collection<Integer> match(String text, Collection<String> patterns) {
    Set<Integer> shifts = new HashSet<>();
    int[] suffixArray = buildSuffixArray(text);
    String bwt = burrowsWheelerTransform(text, suffixArray);
    Map<Character, Integer> symbolCount = countSymbols(bwt);
    Map<Character, Integer> firstColumnSymbolOffsets = calculateFirstColumnSymbolOffsets(symbolCount);
    int[] lastToFirstColumnMap = calculateLastToFirstColumnMap(bwt, firstColumnSymbolOffsets);
    for (String pattern : patterns) {
      int index = pattern.length() - 1;
      char symbol = pattern.charAt(index--);
      if (!firstColumnSymbolOffsets.containsKey(symbol)) {
        continue;
      }
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
          for (int i = top; i <= bottom; i++) {
            shifts.add(suffixArray[i]);
          }
          break;
        }
      }
    }
    return shifts;
  }

  private static Map<Character, Integer> countSymbols(String bwt) {
    Map<Character, Integer> symbolCount = new HashMap<>();
    for (int index = 0; index < bwt.length(); index++) {
      char symbol = bwt.charAt(index);
      symbolCount.put(symbol, symbolCount.getOrDefault(symbol, 0) + 1);
    }
    return symbolCount;
  }

  private static Map<Character, Integer> calculateFirstColumnSymbolOffsets(Map<Character, Integer> symbolCount) {
    List<Character> symbols = symbolCount.keySet().stream().sorted().collect(Collectors.toList());
    symbols.remove((Character) EOF);
    symbols.add(0, EOF);
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

  private static String burrowsWheelerTransform(String text, int[] suffixArray) {
    StringBuilder burrowsWheelerTransform = new StringBuilder();
    for (int firstColumnIndex : suffixArray) {
      if (firstColumnIndex > 0) {
        burrowsWheelerTransform.append(charAt(text, firstColumnIndex - 1));
      } else {
        burrowsWheelerTransform.append(EOF);
      }
    }
    return burrowsWheelerTransform.toString();
  }

  private static int[] buildSuffixArray(String text) {
    int[] order = sortCharacters(text);
    int[] classes = computeCharacterClasses(text, order);
    for (int length = 1; length <= text.length(); length *= 2) {
      order = sortDoubledShifts(text, length, order, classes);
      classes = updateClasses(order, classes, length);
    }
    return order;
  }

  private static int[] sortCharacters(String text) {
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

  private static int[] computeCharacterClasses(String text, int[] order) {
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

  private static int[] sortDoubledShifts(String text, int length, int[] order, int[] classes) {
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

  private static char charAt(String text, int index) {
    return index < text.length() ? text.charAt(index) : EOF;
  }
}
