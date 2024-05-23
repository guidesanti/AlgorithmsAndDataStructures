package br.com.eventhorizon.edx.ucsandiego.algs204x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.v2.PA;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BuildSuffixTree implements PA {

  private static final char[] alphabetSymbols = { 'A', 'C', 'G', 'T', '$' };

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    String text = scanner.next();
    SuffixTree suffixTree = new SuffixTree(new Alphabet(alphabetSymbols), text);
    printSuffixTree(suffixTree);
  }

  private static void printSuffixTree(SuffixTree suffixTree) {
    Queue<SuffixTree.Node> queue = new LinkedList<>();
    queue.add(suffixTree.root);
    while (!queue.isEmpty()) {
      SuffixTree.Node node = queue.remove();
      for (SuffixTree.Node childNode : node.next) {
        if (childNode != null) {
          queue.add(childNode);
          System.out.println(childNode);
        }
      }
    }
  }

  private static class Alphabet {

    public static final Alphabet DEFAULT = new Alphabet();

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

    public boolean contains(char symbol) {
      return symbolToIndexMap.containsKey(symbol);
    }

    public int symbolToIndex(char symbol) {
      if (defaultAlphabet) {
        if (symbol >= DEFAULT_ALPHABET_SIZE) {
          throw new IllegalArgumentException("Unsupported symbol: " + symbol);
        }
      } else {
        if (!symbolToIndexMap.containsKey(symbol)) {
          throw new IllegalArgumentException("Unsupported symbol: " + symbol);
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

  private static class SuffixTree {

    private final Alphabet alphabet;

    private final String text;

    private final Node root;

    public SuffixTree(Alphabet alphabet, String text) {
      this.alphabet = alphabet;
      this.text = text;
      this.root = new Node();
      buildSuffixTree(this.text);
    }

    private void add(int offset, int length) {
      Node node = root;
      int symbolIndex = alphabet.symbolToIndex(text.charAt(offset));
      while (true) {
        if (node.next[symbolIndex] == null) {
          node.next[symbolIndex] = new Node(offset, length);
          break;
        }
        int commonLength = compare(offset, node.next[symbolIndex].offset, node.next[symbolIndex].length);
        if (node.next[symbolIndex].isLeaf() || commonLength < node.next[symbolIndex].length) {
          Node newSuffixNode = new Node(offset + commonLength, length - commonLength);
          Node partialSuffixNode = new Node(offset, commonLength);
          partialSuffixNode.next[alphabet.symbolToIndex(text.charAt(offset + commonLength))] = newSuffixNode;
          partialSuffixNode.next[alphabet.symbolToIndex(text.charAt(node.next[symbolIndex].offset + commonLength))] = node.next[symbolIndex];
          node.next[symbolIndex].offset += commonLength;
          node.next[symbolIndex].length -= commonLength;
          node.next[symbolIndex] = partialSuffixNode;
          break;
        } else {
          node.next[symbolIndex].offset = offset;
          offset += commonLength;
          length -= commonLength;
          node = node.next[symbolIndex];
          symbolIndex = alphabet.symbolToIndex(text.charAt(offset));
        }
      }
    }

    private int compare(int offset1, int offset2, int maxLength) {
      int length = 0;
      while (offset1 < text.length() && offset2 < text.length() &&
          text.charAt(offset1) == text.charAt(offset2) && length < maxLength) {
        offset1++;
        offset2++;
        length++;
      }
      return length;
    }

    private void buildSuffixTree(String text) {
      for (int offset = text.length() - 1; offset >= 0; offset--) {
        add(offset, text.length() - offset);
      }
    }

    private class Node {

      private final Node[] next;

      private int offset;

      private int length;

      public Node() {
        this.next = new Node[alphabet.size()];
      }

      public Node(int offset, int length) {
        this.next = new Node[alphabet.size()];
        this.offset = offset;
        this.length = length;
      }

      public boolean isLeaf() {
        for (Node child : next) {
          if (child != null) {
            return false;
          }
        }
        return true;
      }

      @Override
      public String toString() {
        return text.substring(offset, offset + length);
      }
    }
  }
}
