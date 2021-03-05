package br.com.eventhorizon.common.datastructures;

import static org.junit.jupiter.api.Assertions.assertTrue;

public final class TestUtils {

  public static boolean isBinarySearchTreeNodeOk(BinarySearchTree.Node<Integer> node, boolean checkHeight) {
    if (node == null) {
      return true;
    }
    if (node.parent != null && node.parent.left != node && node.parent.right != node) {
      return false;
    }
    if (checkHeight) {
      int leftHeight = 0;
      if (node.left != null) {
        if (node.left.parent != node) {
          return false;
        }
        leftHeight = node.left.height;
      }
      int rightHeight = 0;
      if (node.right != null) {
        if (node.right.parent != node) {
          return false;
        }
        rightHeight = node.right.height;
      }
      if (node.height != Math.max(leftHeight, rightHeight) + 1) {
        return false;
      }
    }
    return true;
  }

  public static boolean isBinarySearchTree(BinarySearchTree<Integer> tree) {
    BinarySearchTree.Traverser<Integer> traverser = tree.traverser(BinarySearchTree.Traverser.Type.DEPTH_FIRST_INORDER);
    int lastKey = Integer.MIN_VALUE;
    while (traverser.hasNext()) {
      BinarySearchTree.Node<Integer> node = traverser.next();
      if (!isBinarySearchTreeNodeOk(node, true)) {
        return false;
      }
      if (node.key < lastKey) {
        return false;
      }
      lastKey = node.key;
    }
    return true;
  }

  public static boolean isAvlTree(BinarySearchTree<Integer> tree) {
    BinarySearchTree.Traverser<Integer> traverser = tree.traverser(BinarySearchTree.Traverser.Type.DEPTH_FIRST_POSTORDER);
    while (traverser.hasNext()) {
      BinarySearchTree.Node<Integer> node = traverser.next();
      if (!isBinarySearchTreeNodeOk(node, true)) {
        return false;
      }
      int leftHeight = node.left == null ? 0 : node.left.height;
      int rightHeight = node.right == null ? 0 : node.right.height;
      int diff = rightHeight - leftHeight;
      if (diff < -1 || diff > 1) {
        return false;
      }
    }
    return true;
  }

  public static boolean isSplayTree(BinarySearchTree<Integer> tree) {
    BinarySearchTree.Traverser<Integer> traverser = tree.traverser(BinarySearchTree.Traverser.Type.DEPTH_FIRST_INORDER);
    int lastKey = Integer.MIN_VALUE;
    while (traverser.hasNext()) {
      BinarySearchTree.Node<Integer> node = traverser.next();
      if (!isBinarySearchTreeNodeOk(node, false)) {
        return false;
      }
      if (node.key < lastKey) {
        return false;
      }
      lastKey = node.key;
    }
    return true;
  }

  public static BinarySearchTree<Integer> generateBinarySearchTree(int n) {
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    ArrayList keys = new ArrayList(n);
    for (int i = 0; i < n; i++) {
      keys.add(i);
    }
    keys.shuffle();
    for (int i = 0; i < n; i++) {
      tree.add((int) keys.get(i));
    }
    assertTrue(TestUtils.isBinarySearchTree(tree));
    return tree;
  }

  public static SplayTree<Integer> generateSplayTree(int n) {
    SplayTree<Integer> tree = new SplayTree<>();
    ArrayList keys = new ArrayList(n);
    for (int i = 0; i < n; i++) {
      keys.add(i);
    }
    keys.shuffle();
    for (int i = 0; i < n; i++) {
      tree.add((int) keys.get(i));
    }
    assertTrue(TestUtils.isSplayTree(tree));
    return tree;
  }

}
