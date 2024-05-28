package br.com.eventhorizon.datastructures.trees;

public class Node<T> {

  protected final T key;

  protected int height;

  protected Node<T> parent;

  protected Node<T> left;

  protected Node<T> right;

  public Node(T key) {
    this.key = key;
    this.height = 1;
  }

  public Node(T key, Node<T> parent) {
    this.key = key;
    this.height = 1;
    this.parent = parent;
  }

  public int balanceFactor() {
    int leftHeight = left == null ? 0 : left.height;
    int rightHeight = right == null ? 0 : right.height;
    return rightHeight - leftHeight;
  }

  public boolean hasParent() {
    return parent != null;
  }

  public boolean hasChildren() {
    return left != null || right != null;
  }

  public boolean isLeaf() {
    return left == null && right == null;
  }

  @Override
  public String toString() {
    return "" + key;
  }
}
