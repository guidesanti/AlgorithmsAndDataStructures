package br.com.eventhorizon.common.datastructures.trees;

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
    Node<T> parent = node.parent;
    while (parent != null && node == parent.left) {
      node = parent;
      parent = parent.parent;
    }
    return parent;
  }

  public static <T> Node<T> successor(Node<T> node) {
    if (node.right != null) {
      return TreeUtils.minimum(node.right);
    }
    Node<T> parent = node.parent;
    while (parent != null && node == parent.right) {
      node = parent;
      parent = parent.parent;
    }
    return parent;
  }
}
