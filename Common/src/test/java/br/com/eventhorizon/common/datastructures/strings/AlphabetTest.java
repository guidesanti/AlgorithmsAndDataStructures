package br.com.eventhorizon.common.datastructures.strings;

import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.datastructures.UnsupportedSymbolException;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AlphabetTest {

  private static final int DEFAULT_ALPHABET_SIZE = 128;

  @Test
  public void testDefaultAlphabet() {
    Alphabet alphabet = new Alphabet();
    assertEquals(DEFAULT_ALPHABET_SIZE, alphabet.size());
    for (int index = 0; index < DEFAULT_ALPHABET_SIZE; index++) {
      assertEquals((char) index, alphabet.indexToSymbol(index));
      assertEquals(index, alphabet.symbolToIndex((char) index));
    }
    for (int index = DEFAULT_ALPHABET_SIZE; index < Character.MAX_VALUE; index++) {
      int finalIndex = index;
      assertThrows(IndexOutOfBoundsException.class, () -> alphabet.indexToSymbol(finalIndex));
      assertThrows(UnsupportedSymbolException.class, () -> alphabet.symbolToIndex((char) finalIndex));
    }
  }

  @Test
  public void testCustomAlphabet() {
    assertThrows(IllegalArgumentException.class, () -> new Alphabet(new char[0]));
    Set<Character> symbolSet = new HashSet<>(Arrays.asList(Utils.getRandomCharArray(Utils.CharType.ALL, 1, 100)));
    Map<Character, Integer> symbolMap = new HashMap<>();
    char[] symbols = new char[symbolSet.size()];
    int index = 0;
    for (char symbol : symbolSet) {
      symbols[index] = symbol;
      symbolMap.put(symbol, index);
      index++;
    }
    Alphabet alphabet = new Alphabet(symbols);
    assertEquals(symbols.length, alphabet.size());
    for (char symbol = 0; symbol < Character.MAX_VALUE; symbol++) {
      if (symbolSet.contains(symbol)) {
        assertEquals(symbolMap.get(symbol), alphabet.symbolToIndex(symbol));
      } else {
        char finalSymbol = symbol;
        assertThrows(UnsupportedSymbolException.class, () -> alphabet.symbolToIndex(finalSymbol));
      }
    }
    for (index = 0; index < Character.MAX_VALUE; index++) {
      if (index < alphabet.size()) {
        assertEquals(symbols[index], alphabet.indexToSymbol(index));
      } else {
        int finalIndex = index;
        assertThrows(IndexOutOfBoundsException.class, () -> alphabet.indexToSymbol(finalIndex));
      }
    }
  }
}
