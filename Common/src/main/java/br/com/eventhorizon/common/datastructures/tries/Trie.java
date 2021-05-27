package br.com.eventhorizon.common.datastructures.tries;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Trie implementation with a default alphabet of 128 symbols from the ASCII table.
 * Any string with symbol not from ASCII table will result on UnsupportedSymbolException.
 * This implementation of trie does not accept null or empty keys.
 */
public class Trie {

  private static final int ALPHABET_SIZE = 128;

  private final Node root;

  private int size;

  /**
   * Creates an empty trie.
   */
  public Trie() {
    this.root = new Node();
    this.size = 0;
  }

  /**
   * Creates a trie built with keys provided on array <code>keys</code>.
   *
   * @param keys The keys to be added to the trie
   */
  public Trie(String[] keys) {
    this();
    buildTrie(keys);
  }

  /**
   * Adds a key to this trie.
   *
   * @param key The key to be added to this trie
   */
  public void add(String key) {
    validateKey(key);
    Node current = root;
    for (int i = 0; i < key.length(); i++) {
      char symbol = key.charAt(i);
      validateSymbol(symbol);
      if (current.next[symbol] == null) {
        current.next[symbol] = new Node();
      }
      current = current.next[symbol];
    }
    if (!current.present) {
      current.present = true;
      size++;
    }
  }

  /**
   * Removes a key from this trie.
   *
   * @param key The key to be removed from this trie
   */
  public void remove(String key) {
    validateKey(key);
    if (size == 0) {
      throw new NoSuchElementException("Trie is empty");
    }
    Stack<Node> stack = new Stack<>();
    Node current = root;
    for (int i = 0; i < key.length(); i++) {
      char ch = key.charAt(i);
      if (current.next[ch] == null) {
        throw new NoSuchElementException("Key not found");
      }
      stack.push(current);
      current = current.next[ch];
    }
    if (!current.present) {
      throw new NoSuchElementException("Key not found");
    }
    current.present = false;
    if (!current.hasChildren()) {
      int index = key.length() - 1;
      while (!stack.isEmpty()) {
        Node node = stack.pop();
        char symbol = key.charAt(index--);
        node.next[symbol] = null;
        if (node.hasChildren() || node.present) {
          break;
        }
      }
    }
    size--;
  }

  /**
   * Checks whether this trie is empty, that is contains no keys.
   *
   * @return True if this trie is empty, otherwise returns false
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Gets the size of this trie.
   *
   * @return The size of this trie
   */
  public int size() {
    return size;
  }

  /**
   * Checks whether a key is present within this trie.
   *
   * @param key The key to be checked if is present within this trie
   * @return True if the specified key is present within this trie, otherwise returns false
   */
  public boolean contains(String key) {
    Node current = root;
    for (int index = 0; index < key.length(); index++) {
      char symbol = key.charAt(index);
      validateSymbol(symbol);
      if (current.next[symbol] == null) {
        return false;
      }
      current = current.next[symbol];
    }
    return current.present;
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

  private void validateSymbol(char symbol) {
    if (symbol >= ALPHABET_SIZE) {
      throw new UnsupportedSymbolException("Unsupported symbol: " + symbol);
    }
  }

  private static class Node {

    private boolean present;

    private final Node[] next;

    public Node() {
      present = false;
      next = new Node[ALPHABET_SIZE];
    }

    public boolean hasChildren() {
      for (Node child : next) {
        if (child != null) {
          return true;
        }
      }
      return false;
    }
  }
}
