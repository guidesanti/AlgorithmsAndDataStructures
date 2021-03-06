package br.com.eventhorizon.common.datastructures.tree;

public final class TreeUtils {

  public static <T> Node<T> minimum(Node<T> node) {
    if (node == null) {
      return null;
    }
    while (node.left != null) {
      node = node.left;
    }
    return node;
  }

  public static <T> Node<T> maximum(Node<T> node) {
    if (node == null) {
      return null;
    }
    while (node.right != null) {
      node = node.right;
    }
    return node;
  }

  public static <T> Node<T> predecessor(Node<T> node) {
    if (node.left != null) {
      return TreeUtils.maximum(node.left);
    }
    Node<T> ancestor1 = node;
    Node<T> ancestor2 = node.parent;
    while (ancestor2 != null && ancestor1 == ancestor2.left) {
      ancestor1 = ancestor2;
      ancestor2 = ancestor2.parent;
    }
    return ancestor2;
  }

  public static <T> Node<T> successor(Node<T> node) {
    if (node.right != null) {
      return TreeUtils.minimum(node.right);
    }
    Node<T> ancestor1 = node;
    Node<T> ancestor2 = node.parent;
    while (ancestor2 != null && ancestor1 == ancestor2.right) {
      ancestor1 = ancestor2;
      ancestor2 = ancestor2.parent;
    }
    return ancestor2;
  }
}
