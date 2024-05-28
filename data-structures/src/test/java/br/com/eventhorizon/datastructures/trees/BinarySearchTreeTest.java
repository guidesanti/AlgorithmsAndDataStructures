package br.com.eventhorizon.datastructures.trees;

import br.com.eventhorizon.datastructures.ArrayList;
import br.com.eventhorizon.datastructures.DuplicateKeyException;
import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class BinarySearchTreeTest {

  @Test
  public void testExceptions() {
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    assertThrows(NoSuchElementException.class, () -> tree.remove(10));
    tree.add(10);
    assertThrows(DuplicateKeyException.class, () -> tree.add(10));
  }

  @Test
  public void testAddAndRemove() throws OperationNotSupportedException {
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
      Assertions.assertTrue(TreeTestUtils.isBinarySearchTree(tree));
      assertFalse(tree.isEmpty());
      assertEquals(count, tree.size());
      assertTrue(tree.contains(key));
    }

    while (!keys.isEmpty()) {
      int key = (int) keys.remove(0);
      tree.remove(key);
      count--;
      assertTrue(TreeTestUtils.isBinarySearchTree(tree));
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
    assertNull(tree.minimum());
    assertNull(tree.maximum());
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
        assertTrue(TreeTestUtils.isBinarySearchTree(tree));
        assertEquals(min, tree.minimum().key);
        assertEquals(max, tree.maximum().key);
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
        assertTrue(TreeTestUtils.isBinarySearchTree(tree));
        assertTrue(tree.contains(key));
      } catch (DuplicateKeyException e) {
        // Do nothing just avoid stopping the test because of duplicates
      }
    }
  }

  @Test
  public void testIsEmptyAndClear() throws OperationNotSupportedException {
    BinarySearchTree<Integer> tree = new BinarySearchTree<>();
    assertTrue(tree.isEmpty());
    assertEquals(0, tree.size());
    int n = Utils.getRandomInteger(100, 1000);
    for (int i = 0; i < n; i++) {
      int key = Utils.getRandomInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
      try {
        tree.add(key);
        assertTrue(TreeTestUtils.isBinarySearchTree(tree));
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
    BinarySearchTree<Integer> tree = TreeTestUtils.generateBinarySearchTree(n);
    tree.rotateLeft(tree.root);
    assertTrue(TreeTestUtils.isBinarySearchTree(tree));
    for (int i = 0; i < 100; i++) {
      int key = Utils.getRandomInteger(0, n - 1);
      Node<Integer> node = tree.find(key);
      assertNotNull(node);
      tree.rotateLeft(node);
      assertTrue(TreeTestUtils.isBinarySearchTree(tree));
    }
  }

  @Test
  public  void testRotateRight() {
    int n = 10000;
    BinarySearchTree<Integer> tree = TreeTestUtils.generateBinarySearchTree(n);
    tree.rotateRight(tree.root);
    assertTrue(TreeTestUtils.isBinarySearchTree(tree));
    for (int i = 0; i < 100; i++) {
      int key = Utils.getRandomInteger(0, n - 1);
      Node<Integer> node = tree.find(key);
      assertNotNull(node);
      tree.rotateRight(node);
      assertTrue(TreeTestUtils.isBinarySearchTree(tree));
    }
  }
}
