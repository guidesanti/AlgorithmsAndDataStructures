package br.com.eventhorizon.common.datastructures.strings;

import br.com.eventhorizon.common.datastructures.UnsupportedSymbolException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Alphabet {

  public static final Alphabet DEFAULT = new Alphabet();

  private static final int DEFAULT_ALPHABET_SIZE = 128;

  private final boolean defaultAlphabet;

  private int index;

  private final List<Character> symbols;

  private final Map<Character, Integer> symbolToIndexMap;

  public Alphabet() {
    this.index = DEFAULT_ALPHABET_SIZE;
    this.defaultAlphabet = true;
    this.symbols = null;
    this.symbolToIndexMap = null;
  }

  public Alphabet(char[] symbols) {
    if (symbols == null || symbols.length == 0) {
      throw new IllegalArgumentException("symbols cannot be null or empty");
    }
    this.defaultAlphabet = false;
    this.symbols = new ArrayList<>();
    this.symbolToIndexMap = new HashMap<>();
    for (char symbol : symbols) {
      this.symbols.add(symbol);
      this.symbolToIndexMap.put(symbols[index], index++);
    }
  }

  public void add(char symbol) {
    if (!symbolToIndexMap.containsKey(symbol)) {
      symbols.add(symbol);
      symbolToIndexMap.put(symbol, index++);
    }
  }

  public boolean isDefaultAlphabet() {
    return defaultAlphabet;
  }

  public int size() {
    return symbols == null ? DEFAULT_ALPHABET_SIZE : symbols.size();
  }

  public boolean contains(char symbol) {
    if (defaultAlphabet) {
      return (symbol > 0) && (symbol < DEFAULT_ALPHABET_SIZE);
    }
    return symbolToIndexMap.containsKey(symbol);
  }

  public int symbolToIndex(char symbol) {
    if (defaultAlphabet) {
      if (symbol >= DEFAULT_ALPHABET_SIZE) {
        throw new UnsupportedSymbolException("Unsupported symbol: " + symbol);
      }
    } else {
      if (!symbolToIndexMap.containsKey(symbol)) {
        throw new UnsupportedSymbolException("Unsupported symbol: " + symbol);
      }
    }
    return symbols == null ? symbol : symbolToIndexMap.get(symbol);
  }

  public char indexToSymbol(int index) {
    if (index < 0 || index >= (defaultAlphabet ? DEFAULT_ALPHABET_SIZE : symbols.size())) {
      throw new IndexOutOfBoundsException("Index cannot be less than zero or greater than or equals to the alphabet size");
    }
    return defaultAlphabet ? (char) index : symbols.get(index);
  }

  public List<Character> symbols() {
    return symbols;
  }
}
