package br.com.eventhorizon.common.datastructures;

import br.com.eventhorizon.common.Utils;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class BinarySearchTreeTest {

  private static final BinarySearchTree.Node<Integer> ROOT;

  private static final int TREE_SIZE = 15;

  private static final Integer[] TREE_PRE_ORDER = { 50, 25, 10, 45, 30, 47, 46, 60, 70, 65, 99, 80, 75, 90, 95 };

  private static final Integer[] TREE_IN_ORDER = { 10, 25, 30, 45, 46, 47, 50, 60, 65, 70, 75, 80, 90, 95, 99 };

  private static final Integer[] TREE_POST_ORDER = { 10, 30, 46, 47, 45, 25, 65, 75, 95, 90, 80, 99, 70, 60, 50 };

  private static final Integer[] TREE_BREADTH_FIRST = { 50, 25, 60, 10, 45, 70, 30, 47, 65, 99, 46, 80, 75, 90, 95 };

  static {
    ROOT = new BinarySearchTree.Node<>(50);
    BinarySearchTree.Node<Integer> node25 = new BinarySearchTree.Node<>(25);
    BinarySearchTree.Node<Integer> node60 = new BinarySearchTree.Node<>(60);
    BinarySearchTree.Node<Integer> node10 = new BinarySearchTree.Node<>(10);
    BinarySearchTree.Node<Integer> node45 = new BinarySearchTree.Node<>(45);
    BinarySearchTree.Node<Integer> node30 = new BinarySearchTree.Node<>(30);
    BinarySearchTree.Node<Integer> node47 = new BinarySearchTree.Node<>(47);
    BinarySearchTree.Node<Integer> node46 = new BinarySearchTree.Node<>(46);
    BinarySearchTree.Node<Integer> node70 = new BinarySearchTree.Node<>(70);
    BinarySearchTree.Node<Integer> node65 = new BinarySearchTree.Node<>(65);
    BinarySearchTree.Node<Integer> node99 = new BinarySearchTree.Node<>(99);
    BinarySearchTree.Node<Integer> node80 = new BinarySearchTree.Node<>(80);
    BinarySearchTree.Node<Integer> node75 = new BinarySearchTree.Node<>(75);
    BinarySearchTree.Node<Integer> node90 = new BinarySearchTree.Node<>(90);
    BinarySearchTree.Node<Integer> node95 = new BinarySearchTree.Node<>(95);

    ROOT.setParent(null);
    node25.setParent(ROOT);
    node60.setParent(ROOT);
    node10.setParent(node25);
    node45.setParent(node25);
    node70.setParent(node60);
    node65.setParent(node70);
    node99.setParent(node70);
    node30.setParent(node45);
    node47.setParent(node45);
    node46.setParent(node47);
    node80.setParent(node99);
    node75.setParent(node80);
    node90.setParent(node80);
    node95.setParent(node90);

    ROOT.setLeft(node25);
    ROOT.setRight(node60);
    node25.setLeft(node10);
    node25.setRight(node45);
    node60.setRight(node70);
    node70.setLeft(node65);
    node70.setRight(node99);
    node45.setLeft(node30);
    node45.setRight(node47);
    node47.setLeft(node46);
    node80.setLeft(node75);
    node80.setRight(node90);
    node90.setRight(node95);
    node99.setLeft(node80);
  }

  // -----------------------------------------------
  // Node
  // -----------------------------------------------

  @Test
  public void testNodePredecessor() {
    BinarySearchTree.Node<Integer> node = ROOT.maximum();
    int i = TREE_IN_ORDER.length - 1;
    while (node != null) {
      assertEquals(TREE_IN_ORDER[i--], node.getKey());
      node = node.predecessor();
    }
  }

  @Test
  public void testNodeSuccessor() {
    BinarySearchTree.Node<Integer> node = ROOT.minimum();
    int i = 0;
    while (node != null) {
      assertEquals(TREE_IN_ORDER[i++], node.getKey());
      node = node.successor();
    }
  }

  @Test
  public void testNodeMinimum() {
    BinarySearchTree.Node<Integer> node = ROOT.minimum();
    assertNotNull(node);
    assertTrue(node.hasParent());
    assertFalse(node.hasChildren());
    assertEquals(10, node.getKey());
  }

  @Test
  public void testNodeMaximum() {
    BinarySearchTree.Node<Integer> node = ROOT.maximum();
    assertNotNull(node);
    assertTrue(node.hasParent());
    assertTrue(node.hasChildren());
    assertEquals(99, node.getKey());
  }

  // -----------------------------------------------
  // Traverser
  // -----------------------------------------------

  @Test
  public void testTraverserDepthFirstPreOrderOnEmptyTree() {
    BinarySearchTree.Traverser<Integer> traverser = new BinarySearchTree.Traverser<>(null, BinarySearchTree.Traverser.Type.DEPTH_FIRST_PREORDER);
    assertFalse(traverser.hasNext());
    assertThrows(NoSuchElementException.class, traverser::next);
  }

  @Test
  public void testTraverserDepthFirstPreOrder() {
    BinarySearchTree.Traverser<Integer> traverser = new BinarySearchTree.Traverser<>(ROOT, BinarySearchTree.Traverser.Type.DEPTH_FIRST_PREORDER);
    for (int i = 0; i < TREE_SIZE; i++) {
      assertTrue(traverser.hasNext());
      assertEquals(TREE_PRE_ORDER[i], traverser.next().key);
    }
  }

  @Test
  public void testTraverserDepthFirstInOrderOnEmptyTree() {
    BinarySearchTree.Traverser<Integer> traverser = new BinarySearchTree.Traverser<>(null, BinarySearchTree.Traverser.Type.DEPTH_FIRST_INORDER);
    assertFalse(traverser.hasNext());
    assertThrows(NoSuchElementException.class, traverser::next);
  }

  @Test
  public void testTraverserDepthFirstInOrder() {
    BinarySearchTree.Traverser<Integer> traverser = new BinarySearchTree.Traverser<>(ROOT, BinarySearchTree.Traverser.Type.DEPTH_FIRST_INORDER);
    for (int i = 0; i < TREE_SIZE; i++) {
      assertTrue(traverser.hasNext());
      assertEquals(TREE_IN_ORDER[i], traverser.next().key);
    }
  }

  @Test
  public void testTraverserDepthFirstPostOrderOnEmptyTree() {
    BinarySearchTree.Traverser<Integer> traverser = new BinarySearchTree.Traverser<>(null, BinarySearchTree.Traverser.Type.DEPTH_FIRST_POSTORDER);
    assertFalse(traverser.hasNext());
    assertThrows(NoSuchElementException.class, traverser::next);
  }

  @Test
  public void testTraverserDepthFirstPostOrder() {
    BinarySearchTree.Traverser<Integer> traverser = new BinarySearchTree.Traverser<>(ROOT, BinarySearchTree.Traverser.Type.DEPTH_FIRST_POSTORDER);
    for (int i = 0; i < TREE_SIZE; i++) {
      assertTrue(traverser.hasNext());
      assertEquals(TREE_POST_ORDER[i], traverser.next().key);
    }
  }

  @Test
  public void testTraverserBreadthFirstOnEmptyTree() {
    BinarySearchTree.Traverser<Integer> traverser = new BinarySearchTree.Traverser<>(null, BinarySearchTree.Traverser.Type.BREADTH_FIRST);
    assertFalse(traverser.hasNext());
    assertThrows(NoSuchElementException.class, traverser::next);
  }

  @Test
  public void testTraverserBreadthFirst() {
    BinarySearchTree.Traverser<Integer> traverser = new BinarySearchTree.Traverser<>(ROOT, BinarySearchTree.Traverser.Type.BREADTH_FIRST);
    for (int i = 0; i < TREE_SIZE; i++) {
      assertTrue(traverser.hasNext());
      assertEquals(TREE_BREADTH_FIRST[i], traverser.next().key);
    }
  }

  // -----------------------------------------------
  // BinarySearchTree
  // -----------------------------------------------

  @Test
  public void testExceptions() {
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    assertThrows(NoSuchElementException.class, () -> tree.remove(10));
    assertThrows(NoSuchElementException.class, tree::minimum);
    assertThrows(NoSuchElementException.class, tree::maximum);
    tree.add(10);
    assertThrows(DuplicateKeyException.class, () -> tree.add(10));
  }

//  @Test
//  public void testAddAndRemove1() {
//    BinarySearchTree<Integer> tree = new BinarySearchTree<>();
//
//    tree.add(8);
//    assertTrue(TestUtils.isBinarySearchTree(tree));
//    tree.add(4);
//    assertTrue(TestUtils.isBinarySearchTree(tree));
//    tree.add(12);
//    assertTrue(TestUtils.isBinarySearchTree(tree));
//    tree.add(2);
//    assertTrue(TestUtils.isBinarySearchTree(tree));
//    tree.add(6);
//    assertTrue(TestUtils.isBinarySearchTree(tree));
//    tree.add(10);
//    assertTrue(TestUtils.isBinarySearchTree(tree));
//    tree.add(14);
//    assertTrue(TestUtils.isBinarySearchTree(tree));
//    tree.add(1);
//    assertTrue(TestUtils.isBinarySearchTree(tree));
//    tree.add(3);
//    assertTrue(TestUtils.isBinarySearchTree(tree));
//    tree.add(5);
//    assertTrue(TestUtils.isBinarySearchTree(tree));
//    tree.add(7);
//    assertTrue(TestUtils.isBinarySearchTree(tree));
//    tree.add(9);
//    assertTrue(TestUtils.isBinarySearchTree(tree));
//    tree.add(11);
//    assertTrue(TestUtils.isBinarySearchTree(tree));
//    tree.add(13);
//    assertTrue(TestUtils.isBinarySearchTree(tree));
//    tree.add(15);
//    assertTrue(TestUtils.isBinarySearchTree(tree));
//
//    tree.remove(1);
//    assertTrue(TestUtils.isBinarySearchTree(tree));
//    tree.remove(3);
//    assertTrue(TestUtils.isBinarySearchTree(tree));
//    tree.remove(6);
//    assertTrue(TestUtils.isBinarySearchTree(tree));
//  }

  @Test
  public void testAddAndRemove() {
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    int n = Utils.getRandomInteger(100, 10000);
    ArrayList keys = new ArrayList(n);
    for (int i = 0; i < n; i++) {
      keys.add(i);
    }
    keys.shuffle();

    int count = 0;
    for (int i = 0; i < n; i++) {
      int key = (int) keys.get(i);
      tree.add(key);
      count++;
      assertTrue(TestUtils.isBinarySearchTree(tree));
      assertFalse(tree.isEmpty());
      assertEquals(count, tree.size());
      assertTrue(tree.contains(key));
    }

    while (!keys.isEmpty()) {
      int key = (int) keys.remove(0);
      tree.remove(key);
      count--;
      assertTrue(TestUtils.isBinarySearchTree(tree));
      if (count > 0) {
        assertFalse(tree.isEmpty());
      } else {
        assertTrue(tree.isEmpty());
      }
      assertEquals(count, tree.size());
      assertFalse(tree.contains(key));
    }
  }

  @Test
  public void testMinimumAndMaximum() {
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    int n = Utils.getRandomInteger(100, 10000);
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < n; i++) {
      int key = Utils.getRandomInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
      if (key < min) {
        min = key;
      }
      if (key > max) {
        max = key;
      }
      try {
        tree.add(key);
        assertTrue(TestUtils.isBinarySearchTree(tree));
        assertEquals(min, tree.minimum());
        assertEquals(max, tree.maximum());
      } catch (DuplicateKeyException e) {
        // Do nothing just avoid stopping the test because of duplicates
      }
    }
  }

  @Test
  public void testContains() {
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    int n = Utils.getRandomInteger(10, 10000);
    for (int i = 0; i < n; i++) {
      int key = Utils.getRandomInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
      try {
        tree.add(key);
        assertTrue(TestUtils.isBinarySearchTree(tree));
        assertTrue(tree.contains(key));
      } catch (DuplicateKeyException e) {
        // Do nothing just avoid stopping the test because of duplicates
      }
    }
  }

  @Test
  public void testIsEmptyAndClear() {
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    assertTrue(tree.isEmpty());
    assertEquals(0, tree.size());
    int n = Utils.getRandomInteger(100, 1000);
    for (int i = 0; i < n; i++) {
      int key = Utils.getRandomInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
      try {
        tree.add(key);
        assertTrue(TestUtils.isBinarySearchTree(tree));
        assertFalse(tree.isEmpty());
        assertEquals(i + 1, tree.size());
      } catch (DuplicateKeyException e) {
        // Do nothing just avoid stopping the test because of duplicates
      }
    }
    tree.clear();
    assertTrue(tree.isEmpty());
    assertEquals(0, tree.size());
  }

  @Test
  public  void testRotateLeft() {
    int n = 10000;
    BinarySearchTree<Integer> tree = TestUtils.generateBinarySearchTree(n);
    tree.rotateLeft(tree.root);
    assertTrue(TestUtils.isBinarySearchTree(tree));
    for (int i = 0; i < 100; i++) {
      int key = Utils.getRandomInteger(0, n - 1);
      BinarySearchTree.Node<Integer> node = tree.find(key);
      assertNotNull(node);
      tree.rotateLeft(node);
      assertTrue(TestUtils.isBinarySearchTree(tree));
    }
  }

  @Test
  public  void testRotateRight() {
    int n = 10000;
    BinarySearchTree<Integer> tree = TestUtils.generateBinarySearchTree(n);
    tree.rotateRight(tree.root);
    assertTrue(TestUtils.isBinarySearchTree(tree));
    for (int i = 0; i < 100; i++) {
      int key = Utils.getRandomInteger(0, n - 1);
      BinarySearchTree.Node<Integer> node = tree.find(key);
      assertNotNull(node);
      tree.rotateRight(node);
      assertTrue(TestUtils.isBinarySearchTree(tree));
    }
  }
}
