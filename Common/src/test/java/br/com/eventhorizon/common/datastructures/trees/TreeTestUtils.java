package br.com.eventhorizon.common.datastructures.trees;

import br.com.eventhorizon.common.datastructures.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public final class TreeTestUtils {

  public static final Node<Integer> ROOT;

  public static final int TREE_SIZE = 15;

  public static final Integer[] TREE_PRE_ORDER = { 50, 25, 10, 45, 30, 47, 46, 60, 70, 65, 99, 80, 75, 90, 95 };

  public static final Integer[] TREE_IN_ORDER = { 10, 25, 30, 45, 46, 47, 50, 60, 65, 70, 75, 80, 90, 95, 99 };

  public static final Integer[] TREE_POST_ORDER = { 10, 30, 46, 47, 45, 25, 65, 75, 95, 90, 80, 99, 70, 60, 50 };

  public static final Integer[] TREE_BREADTH_FIRST = { 50, 25, 60, 10, 45, 70, 30, 47, 65, 99, 46, 80, 75, 90, 95 };

  static {
    ROOT = new Node<>(50);
    Node<Integer> node25 = new Node<>(25);
    Node<Integer> node60 = new Node<>(60);
    Node<Integer> node10 = new Node<>(10);
    Node<Integer> node45 = new Node<>(45);
    Node<Integer> node30 = new Node<>(30);
    Node<Integer> node47 = new Node<>(47);
    Node<Integer> node46 = new Node<>(46);
    Node<Integer> node70 = new Node<>(70);
    Node<Integer> node65 = new Node<>(65);
    Node<Integer> node99 = new Node<>(99);
    Node<Integer> node80 = new Node<>(80);
    Node<Integer> node75 = new Node<>(75);
    Node<Integer> node90 = new Node<>(90);
    Node<Integer> node95 = new Node<>(95);

    ROOT.parent = null;
    node25.parent = ROOT;
    node60.parent = ROOT;
    node10.parent = node25;
    node45.parent = node25;
    node70.parent = node60;
    node65.parent = node70;
    node99.parent = node70;
    node30.parent = node45;
    node47.parent = node45;
    node46.parent = node47;
    node80.parent = node99;
    node75.parent = node80;
    node90.parent = node80;
    node95.parent = node90;

    ROOT.left = node25;
    ROOT.right = node60;
    node25.left = node10;
    node25.right = node45;
    node60.right = node70;
    node70.left = node65;
    node70.right = node99;
    node45.left = node30;
    node45.right = node47;
    node47.left = node46;
    node80.left = node75;
    node80.right = node90;
    node90.right = node95;
    node99.left = node80;
  }

  public static boolean isBinarySearchTreeNodeOk(Node<Integer> node, boolean checkHeight) {
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
    TreeTraverser<Integer> traverser = new TreeTraverser<>(tree.root, TreeTraverser.Type.DEPTH_FIRST_INORDER);
    int lastKey = Integer.MIN_VALUE;
    while (traverser.hasNext()) {
      Node<Integer> node = traverser.next();
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
    TreeTraverser<Integer> traverser = new TreeTraverser<>(tree.root, TreeTraverser.Type.DEPTH_FIRST_POSTORDER);
    while (traverser.hasNext()) {
      Node<Integer> node = traverser.next();
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

  public static boolean isSplayTree(SplayTree<Integer> tree) {
    TreeTraverser<Integer> traverser = new TreeTraverser<>(tree.root, TreeTraverser.Type.DEPTH_FIRST_INORDER);
    int lastKey = Integer.MIN_VALUE;
    while (traverser.hasNext()) {
      Node<Integer> node = traverser.next();
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
    assertTrue(TreeTestUtils.isBinarySearchTree(tree));
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
    assertTrue(TreeTestUtils.isSplayTree(tree));
    return tree;
  }
}
