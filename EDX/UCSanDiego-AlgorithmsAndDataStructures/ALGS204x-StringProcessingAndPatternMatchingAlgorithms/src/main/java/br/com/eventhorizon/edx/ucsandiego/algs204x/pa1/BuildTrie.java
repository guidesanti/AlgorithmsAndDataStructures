package br.com.eventhorizon.edx.ucsandiego.algs204x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class BuildTrie implements PA {

  private static final char[] ALPHABET = { 'A', 'C', 'G', 'T' };

  private static final Map<Character, Integer> ALPHABET_SYMBOL_TO_INDEX_MAP = new HashMap<>();

  static {
    int index = 0;
    for (char symbol : ALPHABET) {
      ALPHABET_SYMBOL_TO_INDEX_MAP.put(symbol, index++);
    }
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);

    // Build the trie
    Trie trie = new Trie();
    int numberOfKeys = scanner.nextInt();
    for (int i = 0; i < numberOfKeys; i++) {
      trie.add(scanner.next());
    }

    // Print the trie
    int nodeIndex = 0;
    Queue<TrieNode> queue = new LinkedList<>();
    queue.add(trie.root);
    while (!queue.isEmpty()) {
      TrieNode node = queue.remove();
      for (int symbolIndex = 0; symbolIndex < ALPHABET.length; symbolIndex++) {
        TrieNode adjNode = node.next[symbolIndex];
        if (adjNode != null) {
          System.out.println(node.index + "->" + adjNode.index + ":" + symbolIndexToSymbol(symbolIndex));
          queue.add(adjNode);
        }
      }
    }
  }

  private static int symbolToSymbolIndex(char symbol) {
    return ALPHABET_SYMBOL_TO_INDEX_MAP.get(symbol);
  }

  private static char symbolIndexToSymbol(int symbolIndex) {
    return ALPHABET[symbolIndex];
  }

  private static class TrieNode {

    private final int index;

    private boolean present;

    private final TrieNode[] next;

    public TrieNode(int index) {
      this.index = index;
      this.present = false;
      this.next = new TrieNode[ALPHABET.length];
    }
  }

  private static class Trie {

    private final TrieNode root = new TrieNode(0);

    private int nodeIndex = 1;

    public void add(String key) {
      TrieNode current = root;
      for (int i = 0; i < key.length(); i++) {
        int symbolIndex = symbolToSymbolIndex(key.charAt(i));
        if (current.next[symbolIndex] == null) {
          current.next[symbolIndex] = new TrieNode(nodeIndex++);
        }
        current = current.next[symbolIndex];
      }
      if (!current.present) {
        current.present = true;
      }
    }
  }
}
