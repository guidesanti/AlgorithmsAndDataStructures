package br.com.eventhorizon.string.matching;

import br.com.eventhorizon.string.bwt.ImprovedBurrowsWheelerTransform;

import java.util.*;
import java.util.stream.Collectors;

public class BurrowsWheelerTransformPatternMatcher implements PatternMatcher {

  @Override
  public Collection<Integer> match(String text, String pattern) {
    return match(text, Collections.singletonList(pattern));
  }

  @Override
  public Collection<Integer> match(String text, Collection<String> patterns) {
    List<Integer> shifts = new ArrayList<>();
    String bwt = new ImprovedBurrowsWheelerTransform().transform(text);
    Map<Character, Integer> symbolCount = countSymbols(bwt);
    Map<Character, Integer> firstColumnSymbolOffsets = calculateFirstColumnSymbolOffsets(symbolCount);
    int[] lastToFirstColumnMap = calculateLastToFirstColumnMap(bwt, firstColumnSymbolOffsets);
    int count = 0;
    for (String pattern : patterns) {
      int index = pattern.length() - 1;
      char symbol = pattern.charAt(index--);
      int top = firstColumnSymbolOffsets.get(symbol);
      int bottom = top + symbolCount.get(symbol);
      while (top <= bottom) {
        if (index >= 0) {
          symbol = pattern.charAt(index--);
          while (bwt.charAt(top) != symbol) {
            top++;
          }
          while (bwt.charAt(bottom) != symbol) {
            bottom--;
          }
          top = lastToFirstColumnMap[top];
          bottom = lastToFirstColumnMap[bottom];
        } else {
          count += (bottom - top + 1);
          break;
        }
      }
    }
    return shifts;
  }

  private Map<Character, Integer> countSymbols(String bwt) {
    Map<Character, Integer> symbolCount = new HashMap<>();
    for (int index = 0; index < bwt.length(); index++) {
      char symbol = bwt.charAt(index);
      symbolCount.put(symbol, symbolCount.getOrDefault(symbol, 0) + 1);
    }
    return symbolCount;
  }

  private Map<Character, Integer> calculateFirstColumnSymbolOffsets(Map<Character, Integer> symbolCount) {
    List<Character> symbols = symbolCount.keySet().stream().sorted().collect(Collectors.toList());
    Map<Character, Integer> symbolOffsets = new HashMap<>();
    int offset = 0;
    for (char symbol : symbols) {
      symbolOffsets.put(symbol, offset);
      offset += symbolCount.get(symbol);
    }
    return symbolOffsets;
  }

  private int[] calculateLastToFirstColumnMap(String bwt, Map<Character, Integer> firstColumnSymbolOffsets) {
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
