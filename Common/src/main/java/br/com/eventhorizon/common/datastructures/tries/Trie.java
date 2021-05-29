package br.com.eventhorizon.common.datastructures.tries;

import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Trie implementation with a default alphabet of 128 symbols from the ASCII table.
 * Any string with symbol not from ASCII table will result on UnsupportedSymbolException.
 * This implementation of trie does not accept null or empty keys.
 */
public class Trie {

  private final Alphabet alphabet;

  private final Node root;

  private int size;

  /**
   * Creates an empty trie with default alphabet Alphabet.DEFAULT.
   */
  public Trie() {
    this.alphabet = Alphabet.DEFAULT;
    this.root = new Node();
    this.size = 0;
  }

  /**
   * Creates an empty trie with custom alphabet.
   *
   * @param alphabet The custom alphabet to be used
   */
  public Trie(Alphabet alphabet) {
    this.alphabet = alphabet;
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
      int symbolIndex = alphabet.symbolToIndex(key.charAt(i));
      if (current.next[symbolIndex] == null) {
        current.next[symbolIndex] = new Node();
      }
      current = current.next[symbolIndex];
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
        Node node = stack.pop();
        node.next[alphabet.symbolToIndex(key.charAt(index--))] = null;
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
      int symbolIndex = alphabet.symbolToIndex(key.charAt(index));
      if (current.next[symbolIndex] == null) {
        return false;
      }
      current = current.next[symbolIndex];
    }
    return current.present;
  }

  /**
   * Checks whether any of the keys present in this trie matches the text starting from offset.
   *
   * @param text The text to verify a match
   * @param offset The offset from the beginning of the text
   * @return True if any of the keys present in this trie matches the text starting from offset, otherwise returns false
   */
  public boolean match(String text, int offset) {
    Node node = root;
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

    private final Node[] next;

    public Node() {
      present = false;
      next = new Node[alphabet.size()];
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
