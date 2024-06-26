package br.com.eventhorizon.datastructures.trees;

import br.com.eventhorizon.datastructures.ArrayList;
import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SplayTreeTest {

  private static final Integer[] KEYS = { 10, 25, 30, 45, 46, 47, 60, 65, 70, 75, 80, 90, 95, 99 };

  @Test
  public void testAddAndRemove() {
    SplayTree<Integer> tree = new SplayTree<>();
    int n = 10000;
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
      Assertions.assertTrue(TreeTestUtils.isSplayTree(tree));
      assertFalse(tree.isEmpty());
      assertTrue(tree.contains(key));
    }

    while (!keys.isEmpty()) {
      int key = (int) keys.remove(0);
      tree.remove(key);
      count--;
      assertTrue(TreeTestUtils.isSplayTree(tree));
      if (count > 0) {
        assertFalse(tree.isEmpty());
      } else {
        assertTrue(tree.isEmpty());
      }
      assertFalse(tree.contains(key));
    }
  }

  @Test
  public void testFind() {
    SplayTree<Integer> tree = new SplayTree<>();
    int n = 10000;
    ArrayList keys = new ArrayList(n);
    for (int i = 0; i < n; i++) {
      keys.add(i);
    }
    keys.shuffle();

    for (int i = 0; i < n; i++) {
      int key = (int) keys.get(i);
      tree.add(key);
      assertTrue(TreeTestUtils.isSplayTree(tree));
    }

    for (int i = 0; i < n; i++) {
      int key = (int) keys.get(i);
      assertTrue(tree.contains(key));
      Node<Integer> node = tree.find(key);
      assertEquals(key, node.key);
      assertSame(tree.root, node);
    }
  }

  @Test
  public void testSplay() {
    SplayTree<Integer> tree = TreeTestUtils.generateSplayTree(1000);
    assertTrue(TreeTestUtils.isSplayTree(tree));
    for (int i = 0; i < 1000; i++) {
      int key = Utils.getRandomInteger(0, 99);
      Node<Integer> node = tree.find(key);
      assertNotNull(node);
      tree.splay(node);
      assertTrue(TreeTestUtils.isSplayTree(tree));
      assertEquals(node, tree.root);
    }
  }

  @Test
  public void testSplitOnEmptyTree() {
    SplayTree<Integer> tree = new SplayTree<>();
    SplayTree<Integer> higherTree = tree.split((Node<Integer>) null);
    assertNotNull(higherTree);
    assertNotSame(tree, higherTree);
    assertTrue(tree.isEmpty());
    assertTrue(higherTree.isEmpty());

    for (int i = 0; i < 100; i++) {
      higherTree = tree.split(Utils.getRandomInteger(Integer.MIN_VALUE, Integer.MAX_VALUE));
      assertNotNull(higherTree);
      assertNotSame(tree, higherTree);
      assertTrue(tree.isEmpty());
      assertTrue(higherTree.isEmpty());
    }
  }

  @Test
  public void testSplitOnTreeWithOnlyOneNode() {
    SplayTree<Integer> tree = new SplayTree<>();

    tree.clear();
    tree.root = new Node<>(10);
    SplayTree<Integer> higherTree = tree.split((Node<Integer>) null);
    assertNotNull(higherTree);
    assertNotSame(tree, higherTree);
    assertTrue(TreeTestUtils.isSplayTree(tree));
    assertTrue(TreeTestUtils.isSplayTree(higherTree));
    assertTrue(tree.isEmpty());
    assertFalse(tree.contains(10));
    assertFalse(higherTree.isEmpty());
    assertTrue(higherTree.contains(10));

    for (int i = 0; i < 100; i++) {
      tree.clear();
      tree.root = new Node<>(10);
      higherTree = tree.split(Utils.getRandomInteger(Integer.MIN_VALUE, 9));
      assertNotNull(higherTree);
      assertNotSame(tree, higherTree);
      assertTrue(TreeTestUtils.isSplayTree(tree));
      assertTrue(TreeTestUtils.isSplayTree(higherTree));
      assertTrue(tree.isEmpty());
      assertFalse(tree.contains(10));
      assertFalse(higherTree.isEmpty());
      assertTrue(higherTree.contains(10));
    }

    tree.clear();
    tree.root = new Node<>(10);
    higherTree = tree.split(10);
    assertNotNull(higherTree);
    assertNotSame(tree, higherTree);
    assertTrue(TreeTestUtils.isSplayTree(tree));
    assertTrue(TreeTestUtils.isSplayTree(higherTree));
    assertFalse(tree.isEmpty());
    assertTrue(tree.contains(10));
    assertTrue(higherTree.isEmpty());
    assertFalse(higherTree.contains(10));

    for (int i = 0; i < 100; i++) {
      tree.clear();
      tree.root = new Node<>(10);
      higherTree = tree.split(Utils.getRandomInteger(11, Integer.MAX_VALUE));
      assertNotNull(higherTree);
      assertNotSame(tree, higherTree);
      assertTrue(TreeTestUtils.isSplayTree(tree));
      assertTrue(TreeTestUtils.isSplayTree(higherTree));
      assertFalse(tree.isEmpty());
      assertTrue(tree.contains(10));
      assertTrue(higherTree.isEmpty());
      assertFalse(higherTree.contains(10));
    }
  }

  @Test
  public void testSplitOnTreeWithMoreThanOneNode1() {
    SplayTree<Integer> tree = createTestTree();
    SplayTree<Integer> higherTree = tree.split((Node<Integer>) null);
    assertNotNull(higherTree);
    assertNotSame(tree, higherTree);
    assertTrue(TreeTestUtils.isSplayTree(tree));
    assertTrue(TreeTestUtils.isSplayTree(higherTree));
    assertTrue(tree.isEmpty());
    assertFalse(higherTree.isEmpty());
    assertTrue(higherTree.contains(25));
    assertTrue(higherTree.contains(60));
    assertTrue(higherTree.contains(10));
    assertTrue(higherTree.contains(45));
    assertTrue(higherTree.contains(30));
    assertTrue(higherTree.contains(47));
    assertTrue(higherTree.contains(46));
    assertTrue(higherTree.contains(70));
    assertTrue(higherTree.contains(65));
    assertTrue(higherTree.contains(99));
    assertTrue(higherTree.contains(80));
    assertTrue(higherTree.contains(75));
    assertTrue(higherTree.contains(90));
    assertTrue(higherTree.contains(95));

    ArrayList keys = new ArrayList(KEYS);
    int n = Utils.getRandomInteger(1, keys.size() - 2);
    ArrayList lowerKeys = keys.subList(0, n);
    ArrayList higherKeys = keys.subList(n + 1, keys.size() - 1);
    int splitKey = (int)keys.get(n);
    tree = createTestTree();
    higherTree = tree.split(splitKey);
    assertNotNull(higherTree);
    assertNotSame(tree, higherTree);
    assertTrue(TreeTestUtils.isSplayTree(tree));
    assertTrue(TreeTestUtils.isSplayTree(higherTree));
    assertTrue(tree.maximum().key.compareTo(splitKey) <= 0);
    assertTrue(higherTree.minimum().key.compareTo(splitKey) > 0);
    for (int i = 0; i < lowerKeys.size(); i++) {
      assertTrue(tree.contains((int)lowerKeys.get(i)));
      assertFalse(higherTree.contains((int)lowerKeys.get(i)));
    }
    for (int i = 0; i < higherKeys.size(); i++) {
      assertFalse(tree.contains((int)higherKeys.get(i)));
      assertTrue(higherTree.contains((int)higherKeys.get(i)));
    }
  }

  @Test
  public void testSplitOnTreeWithMoreThanOneNode2() {
    Node<Integer> node1 = new Node<>(1);
    Node<Integer> node3 = new Node<>(3);
    Node<Integer> node7 = new Node<>(7);
    Node<Integer> node10 = new Node<>(10);
    Node<Integer> node11 = new Node<>(11);
    Node<Integer> node12 = new Node<>(12);
    Node<Integer> node15 = new Node<>(15);
    Node<Integer> node20 = new Node<>(20);
    Node<Integer> node40 = new Node<>(40);

    node1.parent = node3;
    node7.parent = node3;
    node11.parent = node12;
    node15.parent = node12;
    node3.parent = node10;
    node12.parent = node10;
    node10.parent = node20;
    node40.parent = node20;

    node20.left = node10;
    node20.right = node40;
    node10.left = node3;
    node10.right = node12;
    node3.left = node1;
    node3.right = node7;
    node12.left = node11;
    node12.right = node15;

    SplayTree<Integer> tree = new SplayTree<>();
    tree.root = node20;
    SplayTree<Integer> higherTree = tree.split(11);

    assertTrue(tree.contains(1));
    assertTrue(tree.contains(3));
    assertTrue(tree.contains(7));
    assertTrue(tree.contains(10));
    assertTrue(tree.contains(11));
    assertFalse(tree.contains(12));
    assertFalse(tree.contains(15));
    assertFalse(tree.contains(20));
    assertFalse(tree.contains(40));

    assertFalse(higherTree.contains(1));
    assertFalse(higherTree.contains(3));
    assertFalse(higherTree.contains(7));
    assertFalse(higherTree.contains(10));
    assertFalse(higherTree.contains(11));
    assertTrue(higherTree.contains(12));
    assertTrue(higherTree.contains(15));
    assertTrue(higherTree.contains(20));
    assertTrue(higherTree.contains(40));
  }

  @Test
  public void testSplitOnTreeWithMoreThanOneNode3() {
    Node<Integer> node1 = new Node<>(1);
    Node<Integer> node3 = new Node<>(3);
    Node<Integer> node7 = new Node<>(7);
    Node<Integer> node10 = new Node<>(10);
    Node<Integer> node12 = new Node<>(12);
    Node<Integer> node15 = new Node<>(15);
    Node<Integer> node20 = new Node<>(20);
    Node<Integer> node40 = new Node<>(40);

    node1.parent = node3;
    node7.parent = node3;
    node15.parent = node12;
    node3.parent = node10;
    node12.parent = node10;
    node10.parent = node20;
    node40.parent = node20;

    node20.left = node10;
    node20.right = node40;
    node10.left = node3;
    node10.right = node12;
    node3.left = node1;
    node3.right = node7;
    node12.right = node15;

    SplayTree<Integer> tree = new SplayTree<>();
    tree.root = node20;
    SplayTree<Integer> higherTree = tree.split(11);

    assertTrue(tree.contains(1));
    assertTrue(tree.contains(3));
    assertTrue(tree.contains(7));
    assertTrue(tree.contains(10));
    assertFalse(tree.contains(11));
    assertFalse(tree.contains(12));
    assertFalse(tree.contains(15));
    assertFalse(tree.contains(20));
    assertFalse(tree.contains(40));

    assertFalse(higherTree.contains(1));
    assertFalse(higherTree.contains(3));
    assertFalse(higherTree.contains(7));
    assertFalse(higherTree.contains(10));
    assertFalse(higherTree.contains(11));
    assertTrue(higherTree.contains(12));
    assertTrue(higherTree.contains(15));
    assertTrue(higherTree.contains(20));
    assertTrue(higherTree.contains(40));
  }

  @Test
  public void testSplitOnNonExistingKey() {
    SplayTree<Integer> tree = createTestTree();
    SplayTree<Integer> higherTree = tree.split((Node<Integer>) null);
    assertNotNull(higherTree);
    assertNotSame(tree, higherTree);
    assertTrue(TreeTestUtils.isSplayTree(tree));
    assertTrue(TreeTestUtils.isSplayTree(higherTree));
    assertTrue(tree.isEmpty());
    assertFalse(higherTree.isEmpty());
    assertTrue(higherTree.contains(25));
    assertTrue(higherTree.contains(60));
    assertTrue(higherTree.contains(10));
    assertTrue(higherTree.contains(45));
    assertTrue(higherTree.contains(30));
    assertTrue(higherTree.contains(47));
    assertTrue(higherTree.contains(46));
    assertTrue(higherTree.contains(70));
    assertTrue(higherTree.contains(65));
    assertTrue(higherTree.contains(99));
    assertTrue(higherTree.contains(80));
    assertTrue(higherTree.contains(75));
    assertTrue(higherTree.contains(90));
    assertTrue(higherTree.contains(95));

    tree = createTestTree();
    higherTree = tree.split(45);
    assertNotNull(higherTree);
    assertNotSame(tree, higherTree);
    assertTrue(TreeTestUtils.isSplayTree(tree));
    assertTrue(TreeTestUtils.isSplayTree(higherTree));
    assertTrue(tree.maximum().key.compareTo(45) <= 0);
    assertTrue(higherTree.minimum().key.compareTo(45) > 0);
  }

  private SplayTree<Integer> createTestTree() {
    SplayTree<Integer> tree = new SplayTree<>();
    tree.root = new Node<>(50);
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

    tree.root.parent = null;
    node25.parent = tree.root;
    node60.parent = tree.root;
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

    tree.root.left = node25;
    tree.root.right = node60;
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

    return tree;
  }
}
