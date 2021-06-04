package br.com.eventhorizon.common.datastructures.trees;

import br.com.eventhorizon.common.datastructures.Alphabet;

public class SuffixTree {

  private static final char END_OF_STRING_MARK = '$';

  private final Alphabet alphabet;

  private final String text;

  private final Node root;

  private int size;

  public SuffixTree(Alphabet alphabet, String text) {
    if (!alphabet.contains(END_OF_STRING_MARK)) {
      throw new IllegalArgumentException("alphabet should contains the end of string mark '" + END_OF_STRING_MARK + "' symbol");
    }
    this.alphabet = alphabet;
    this.text = text + END_OF_STRING_MARK;
    this.root = new Node();
    this.size = 0;
    buildSuffixTree(this.text);
  }

  public Node root() {
    return root;
  }

  public int size() {
    return size;
  }

  private void add(int offset, int length) {
    Node node = root;
    int symbolIndex = alphabet.symbolToIndex(text.charAt(offset));
    while (true) {
      if (node.next[symbolIndex] == null) {
        node.next[symbolIndex] = new Node(offset, length);
        size++;
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

    public Node[] next() {
      return next;
    }

    public int offset() {
      return offset;
    }

    public int length() {
      return length;
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
      return "Node{" + text.substring(offset, offset + length) + "}";
    }
  }
}
