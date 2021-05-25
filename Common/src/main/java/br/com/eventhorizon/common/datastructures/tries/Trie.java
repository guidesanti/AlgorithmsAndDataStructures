package br.com.eventhorizon.common.datastructures.tries;

public class Trie {

  private final Node root;

  public Trie() {
    this.root = new Node((char) 0, null);
  }

  public Trie(String[] patterns) {
    this();
    // TODO
  }

  public static class Node {

    private final char symbol;

    private Node[] next;

    public Node(char symbol, Node next) {
      this.symbol = symbol;
//      this.next = next;
    }

    public char getSymbol() {
      return symbol;
    }

    public Node[] getNext() {
      return next;
    }
  }
}
