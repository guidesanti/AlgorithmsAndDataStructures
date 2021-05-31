package br.com.eventhorizon.edx.ucsandiego.algs204x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

public class GeneralizedMultiplePatternMatching implements PA {

  private static final char[] SYMBOLS = { 'A', 'C', 'G', 'T' };

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    String text = scanner.next();
    int numberOfPatterns = scanner.nextInt();
    Trie trie = new Trie(new Alphabet(SYMBOLS));
    for (int i = 0; i < numberOfPatterns; i++) {
      trie.add(scanner.next());
    }
    List<Integer> matches = new ArrayList<>();
    for (int i = 0; i < text.length(); i++) {
      if (trie.match(text, i)) {
        matches.add(i);
      }
    }
    matches.forEach(System.out::println);
  }

  private static class Trie {

    private final Alphabet alphabet;

    private final Trie.Node root;

    private int size;

    public Trie() {
      this.alphabet = Alphabet.DEFAULT;
      this.root = new Trie.Node();
      this.size = 0;
    }

    public Trie(Alphabet alphabet) {
      this.alphabet = alphabet;
      this.root = new Trie.Node();
      this.size = 0;
    }

    public Trie(String[] keys) {
      this();
      buildTrie(keys);
    }

    public void add(String key) {
      validateKey(key);
      Trie.Node current = root;
      for (int i = 0; i < key.length(); i++) {
        int symbolIndex = alphabet.symbolToIndex(key.charAt(i));
        if (current.next[symbolIndex] == null) {
          current.next[symbolIndex] = new Trie.Node();
        }
        current = current.next[symbolIndex];
      }
      if (!current.present) {
        current.present = true;
        size++;
      }
    }

    public void remove(String key) {
      validateKey(key);
      if (size == 0) {
        throw new NoSuchElementException("Trie is empty");
      }
      Stack<Trie.Node> stack = new Stack<>();
      Trie.Node current = root;
      for (int i = 0; i < key.length(); i++) {
        int symbolIndex = alphabet.symbolToIndex(key.charAt(i));
        if (current.next[symbolIndex] == null) {
          throw new NoSuchElementException("Key not found");
        }
        stack.push(current);
        current = current.next[symbolIndex];
      }
      if (!current.present) {
        throw new NoSuchElementException("Key not found");
      }
      current.present = false;
      if (!current.hasChildren()) {
        int index = key.length() - 1;
        while (!stack.isEmpty()) {
          Trie.Node node = stack.pop();
          node.next[alphabet.symbolToIndex(key.charAt(index--))] = null;
          if (node.hasChildren() || node.present) {
            break;
          }
        }
      }
      size--;
    }

    public boolean isEmpty() {
      return size == 0;
    }

    public int size() {
      return size;
    }

    public boolean contains(String key) {
      Trie.Node current = root;
      for (int index = 0; index < key.length(); index++) {
        int symbolIndex = alphabet.symbolToIndex(key.charAt(index));
        if (current.next[symbolIndex] == null) {
          return false;
        }
        current = current.next[symbolIndex];
      }
      return current.present;
    }

    public boolean match(String text, int offset) {
      Trie.Node node = root;
      while (node != null && offset < text.length()) {
        int symbolIndex = alphabet.symbolToIndex(text.charAt(offset++));
        if (node.present) {
          return true;
        }
        node = node.next[symbolIndex];
      }
      return node != null && node.present;
    }

    private void buildTrie(String[] keys) {
      for (String key : keys) {
        add(key);
      }
    }

    private void validateKey(String key) {
      if (key == null || key.isEmpty()) {
        throw new IllegalArgumentException("key cannot be null or empty");
      }
    }

    private class Node {

      private boolean present;

      private final Trie.Node[] next;

      public Node() {
        present = false;
        next = new Trie.Node[alphabet.size()];
      }

      public boolean hasChildren() {
        for (Trie.Node child : next) {
          if (child != null) {
            return true;
          }
        }
        return false;
      }
    }
  }

  private static class Alphabet {

    public static final Alphabet
        DEFAULT = new Alphabet();

    private static final int DEFAULT_ALPHABET_SIZE = 128;

    private final boolean defaultAlphabet;

    private final char[] symbols;

    private final Map<Character, Integer> symbolToIndexMap;

    public Alphabet() {
      this.defaultAlphabet = true;
      this.symbols = null;
      this.symbolToIndexMap = null;
    }

    public Alphabet(char[] symbols) {
      if (symbols == null || symbols.length == 0) {
        throw new IllegalArgumentException("symbols cannot be null or empty");
      }
      this.defaultAlphabet = false;
      this.symbols = symbols;
      this.symbolToIndexMap = new HashMap<>();
      for (int index = 0; index < symbols.length; index++) {
        this.symbolToIndexMap.put(symbols[index], index);
      }
    }

    public boolean isDefaultAlphabet() {
      return defaultAlphabet;
    }

    public int size() {
      return symbols == null ? DEFAULT_ALPHABET_SIZE : symbols.length;
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
      if (index < 0 || index >= (defaultAlphabet ? DEFAULT_ALPHABET_SIZE : symbols.length)) {
        throw new IndexOutOfBoundsException("Index cannot be less than zero or greater than or equals to the alphabet size");
      }
      return defaultAlphabet ? (char) index : symbols[index];
    }
  }

  private static class UnsupportedSymbolException extends RuntimeException {
    public UnsupportedSymbolException(String message) {
      super(message);
    }
  }
}
