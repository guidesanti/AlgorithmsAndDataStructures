package br.com.eventhorizon.common.datastructures.tries;

import java.util.NoSuchElementException;

/**
 * TODO
 *
 * @param <T> TODO
 */
public class TrieMap<T> {

  private static final int ALPHABET_SIZE = 256;

  private final Node<T> root;

  private int size;

  public TrieMap() {
    this.root = new Node<>();
    this.size = 0;
  }

  public TrieMap(String[] keys, T[] values) {
    this();
    if (keys == null || values == null) {
      throw new IllegalArgumentException("keys and values cannot be null");
    }
    if (keys.length != values.length) {
      throw new IllegalArgumentException("keys and values must have the same length");
    }
    buildTrie(keys, values);
  }

  public void add(String key, T value) {
    if (key == null) {
      throw new IllegalArgumentException("key cannot be null");
    }
    if (value == null) {
      throw new IllegalArgumentException("value cannot be null");
    }
    if (key.isEmpty()) {
      return;
    }
    Node<T> current = root;
    for (int i = 0; i < key.length(); i++) {
      char ch = key.charAt(i);
      if (current.next[ch] == null) {
        current.next[ch] = new Node<>();
      }
      current = current.next[ch];
    }
    current.value = value;
    size++;
  }

  public T get(String key) {
    return null;
    // TODO
  }

  public T remove(String key) {
    if (size == 0) {
      throw new NoSuchElementException("Trie is empty");
    }
    Node<T> current = root;
    for (int i = 0; i < key.length(); i++) {
      char ch = key.charAt(i);
      if (current.next[ch] == null) {
        throw new NoSuchElementException("Key not found");
      }
      current = current.next[ch];
    }
    if (current.value == null) {
      throw new NoSuchElementException("Key not found");
    }
    T value = current.value;
    current.value = null;
    size--;
    // TODO: remove nodes
    return value;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public boolean containsKey(String key) {
    Node<T> current = root;
    for (int index = 0; index < key.length(); index++) {
      char symbol = key.charAt(index);
      if (current.next[symbol] == null) {
        return false;
      }
      current = current.next[symbol];
    }
    return current.value != null;
  }

  public boolean containsValue(T value) {
    return false;
    // TODO
  }

  private void buildTrie(String[] keys, T[] values) {
    for (int i = 0; i < keys.length; i++) {
      add(keys[i], values[i]);
    }
  }

  public static class Node<T> {

    private T value;

    private final Node<T>[] next;

    public Node() {
      value = null;
      next = new Node[ALPHABET_SIZE];
    }
  }
}
