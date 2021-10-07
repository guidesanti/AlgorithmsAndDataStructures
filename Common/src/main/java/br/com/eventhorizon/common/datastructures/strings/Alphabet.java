package br.com.eventhorizon.common.datastructures.strings;

import br.com.eventhorizon.common.datastructures.UnsupportedSymbolException;

import java.util.*;

public class Alphabet {

  public static final Alphabet DEFAULT = new Alphabet();

  private static final int DEFAULT_ALPHABET_SIZE = 128;

  private int index;

  private final List<Character> symbols;

  private final Map<Character, Integer> symbolToIndexMap;

  public Alphabet() {
    this.symbols = new ArrayList<>();
    this.symbolToIndexMap = new HashMap<>();
    for (int i = 0; i < DEFAULT_ALPHABET_SIZE; i++) {
      this.symbols.add(i, (char) i);
      this.symbolToIndexMap.put((char) i, index++);
    }
  }

  public Alphabet(char[] symbols) {
    if (symbols == null || symbols.length == 0) {
      throw new IllegalArgumentException("symbols cannot be null or empty");
    }
    this.symbols = new ArrayList<>();
    this.symbolToIndexMap = new HashMap<>();
    for (char symbol : symbols) {
      this.symbols.add(symbol);
      this.symbolToIndexMap.put(symbols[index], index++);
    }
  }

  public Alphabet(Collection<Character> symbols) {
    if (symbols == null || symbols.size() == 0) {
      throw new IllegalArgumentException("symbols cannot be null or empty");
    }
    this.symbols = new ArrayList<>();
    this.symbolToIndexMap = new HashMap<>();
    for (char symbol : symbols) {
      this.symbols.add(symbol);
      this.symbolToIndexMap.put(symbol, index++);
    }
  }

  public void add(char symbol) {
    if (!symbolToIndexMap.containsKey(symbol)) {
      symbols.add(symbol);
      symbolToIndexMap.put(symbol, index++);
    }
  }

  public int size() {
    return symbols.size();
  }

  public boolean contains(char symbol) {
    return symbolToIndexMap.containsKey(symbol);
  }

  public int symbolToIndex(char symbol) {
    if (!symbolToIndexMap.containsKey(symbol)) {
      throw new UnsupportedSymbolException("Unsupported symbol: " + symbol);
    }
    return symbols == null ? symbol : symbolToIndexMap.get(symbol);
  }

  public char indexToSymbol(int index) {
    if (index < 0 || index >= symbols.size()) {
      throw new IndexOutOfBoundsException("Index cannot be less than zero or greater than or equals to the alphabet size");
    }
    return symbols.get(index);
  }

  public List<Character> symbols() {
    return symbols;
  }
}
