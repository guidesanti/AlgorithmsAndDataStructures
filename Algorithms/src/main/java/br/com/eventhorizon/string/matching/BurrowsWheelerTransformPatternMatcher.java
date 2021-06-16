package br.com.eventhorizon.string.matching;

import br.com.eventhorizon.string.bwt.ImprovedBurrowsWheelerTransform1;

import java.util.*;
import java.util.stream.Collectors;

public class BurrowsWheelerTransformPatternMatcher implements PatternMatcher {

  @Override
  public Collection<Integer> match(String text, String pattern) {
    return match(text, Collections.singletonList(pattern));
  }

  @Override
  public Collection<Integer> match(String text, Collection<String> patterns) {
    Set<Integer> shifts = new HashSet<>();
    String bwt = new ImprovedBurrowsWheelerTransform1().transform(text);
    Map<Character, Integer> symbolCount = countSymbols(bwt);
    Map<Character, Integer> firstColumnSymbolOffsets = calculateFirstColumnSymbolOffsets(symbolCount);
    int[] lastToFirstColumnMap = calculateLastToFirstColumnMap(bwt, firstColumnSymbolOffsets);
    int[] suffixArray = buildSuffixArray(lastToFirstColumnMap);
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
    symbols.remove((Character) '$');
    symbols.add(0, '$');
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

  private int[] buildSuffixArray(int[] lastToFirstColumnMap) {
    int index = 0;
    int[] suffixArray = new int[lastToFirstColumnMap.length];
    for (int suffixIndex = lastToFirstColumnMap.length - 1; suffixIndex >= 0; suffixIndex--) {
      suffixArray[index] = suffixIndex;
      index = lastToFirstColumnMap[index];
    }
    return suffixArray;
  }
}
