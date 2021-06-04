package br.com.eventhorizon.edx.ucsandiego.algs204x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class ShortestNonSharedSubstring implements PA {

  private static int limit;

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    String result = shortestNonSharedSubstring(scanner.next(), scanner.next());
    if (result != null) {
      System.out.println(result);
    } else {
      System.out.println("");
    }
  }

  private static String shortestNonSharedSubstring(String text1, String text2) {
    limit = text1.length();
    String result = null;
    int min = Integer.MAX_VALUE;
    String text = text1 + "#" + text2;
    SuffixTree suffixTree = new SuffixTree(new Alphabet(), text);
    Map<SuffixTree.Node, SuffixTree.Node> parent = new HashMap<>();
    Queue<SuffixTree.Node> queue = new LinkedList<>();
    for (SuffixTree.Node node : suffixTree.root().next()) {
      if (node == null) {
        continue;
      }
      parent.put(node, suffixTree.root());
      queue.add(node);
    }
    while (!queue.isEmpty()) {
      SuffixTree.Node node = queue.remove();
      for (SuffixTree.Node child : node.next()) {
        if (child != null) {
          parent.put(child, node);
          queue.add(child);
        }
      }
      if (node.offset() >= limit) {
        continue;
      }
      if (!isShared(node)) {
        StringBuilder str = new StringBuilder();
        str.append(text.charAt(node.offset()));
        node = parent.get(node);
        while (node != suffixTree.root()) {
          str.insert(0, text.substring(node.offset(), node.offset() + node.length()));
          node = parent.get(node);
        }
        if (str.length() < min) {
          min = str.length();
          result = str.toString();
        }
      }
    }
    if (result != null && result.length() > text2.length()) {
      return null;
    }
    return result;
  }

  private static boolean isShared(SuffixTree.Node node) {
    Shared shared = new Shared();
    isShared(node, shared);
    return shared.onText1 && shared.onText2;
  }

  private static void isShared(SuffixTree.Node node, Shared shared) {
    for (SuffixTree.Node child : node.next()) {
      if (child == null) {
        continue;
      }
      if (child.isLeaf()) {
        if (child.offset() <= limit) {
          shared.onText1 = true;
        } else {
          shared.onText2 = true;
        }
      } else {
        isShared(child, shared);
      }
    }
  }

  private static class Shared {

    boolean onText1;

    boolean onText2;
  }

  private static class SuffixTree {

    private static final char END_OF_STRING_MARK = '$';

    private final Alphabet alphabet;

    private final String text;

    private final SuffixTree.Node root;

    private int size;

    public SuffixTree(Alphabet alphabet, String text) {
      if (!alphabet.contains(END_OF_STRING_MARK)) {
        throw new IllegalArgumentException("alphabet should contains the end of string mark '" + END_OF_STRING_MARK + "' symbol");
      }
      this.alphabet = alphabet;
      this.text = text + END_OF_STRING_MARK;
      this.root = new SuffixTree.Node();
      this.size = 0;
      buildSuffixTree(this.text);
    }

    public SuffixTree.Node root() {
      return root;
    }

    public int size() {
      return size;
    }

    private void add(int offset, int length) {
      SuffixTree.Node node = root;
      int symbolIndex = alphabet.symbolToIndex(text.charAt(offset));
      while (true) {
        if (node.next[symbolIndex] == null) {
          node.next[symbolIndex] = new SuffixTree.Node(offset, length);
          size++;
          break;
        }
        int commonLength = compare(offset, node.next[symbolIndex].offset, node.next[symbolIndex].length);
        if (node.next[symbolIndex].isLeaf() || commonLength < node.next[symbolIndex].length) {
          SuffixTree.Node
              newSuffixNode = new SuffixTree.Node(offset + commonLength, length - commonLength);
          SuffixTree.Node
              partialSuffixNode = new SuffixTree.Node(offset, commonLength);
          partialSuffixNode.next[alphabet.symbolToIndex(text.charAt(offset + commonLength))] = newSuffixNode;
          partialSuffixNode.next[alphabet.symbolToIndex(text.charAt(node.next[symbolIndex].offset + commonLength))] = node.next[symbolIndex];
          node.next[symbolIndex].offset += commonLength;
          node.next[symbolIndex].length -= commonLength;
          node.next[symbolIndex] = partialSuffixNode;
          size += 2;
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

    public class Node {

      private final SuffixTree.Node[] next;

      private int offset;

      private int length;

      public Node() {
        this.next = new SuffixTree.Node[alphabet.size()];
      }

      public Node(int offset, int length) {
        this.next = new SuffixTree.Node[alphabet.size()];
        this.offset = offset;
        this.length = length;
      }

      public SuffixTree.Node[] next() {
        return next;
      }

      public int offset() {
        return offset;
      }

      public int length() {
        return length;
      }

      public boolean isLeaf() {
        for (SuffixTree.Node child : next) {
          if (child != null) {
            return false;
          }
        }
        return true;
      }

      @Override
      public String toString() {
        return "Node{" + text.substring(offset, offset + length) + "}";
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
      if (defaultAlphabet) {
        return (symbol > 0) && (symbol < DEFAULT_ALPHABET_SIZE);
      }
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
}
