package br.com.eventhorizon.common.datastructures;

import br.com.eventhorizon.common.Utils;
import org.junit.jupiter.api.Test;

import javax.naming.OperationNotSupportedException;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AVLTreeTest {

  @Test
  public void testExceptions() {
    AVLTree<Integer> tree = new AVLTree<>();
    assertThrows(NoSuchElementException.class, () -> tree.remove(10));
    assertThrows(NoSuchElementException.class, tree::minimum);
    assertThrows(NoSuchElementException.class, tree::maximum);
    tree.add(10);
    assertThrows(DuplicateKeyException.class, () -> tree.add(10));
  }

  @Test
  public void testAddAndRemove() throws OperationNotSupportedException {
    AVLTree<Integer> tree = new AVLTree<>();
    int n = Utils.getRandomInteger(100, 10000);
    ArrayList keys = new ArrayList(n);
    for (int i = 0; i < n; i++) {
      keys.add(i);
    }
    keys.shuffle();
    ArrayList keysCopy = new ArrayList(keys.toArray());

    int count = 0;
    for (int i = 0; i < n; i++) {
      int key = (int) keys.get(i);
      tree.add(key);
      count++;
      assertTrue(TestUtils.isBinarySearchTree(tree));
      assertTrue(TestUtils.isAvlTree(tree));
      assertFalse(tree.isEmpty());
      assertEquals(count, tree.size());
      assertTrue(tree.contains(key));
    }

    while (!keys.isEmpty()) {
      int key = (int) keys.remove(0);
      tree.remove(key);
      count--;
      assertTrue(TestUtils.isBinarySearchTree(tree));
      assertTrue(TestUtils.isAvlTree(tree), Arrays.toString(keysCopy.toArray()));
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
    AVLTree<Integer> tree = new AVLTree<>();
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
        assertEquals(min, tree.minimum().key);
        assertEquals(max, tree.maximum().key);
      } catch (DuplicateKeyException e) {
        // Do nothing just avoid stopping the test because of duplicates
      }
    }
  }

  @Test
  public void testContains() {
    AVLTree<Integer> tree = new AVLTree<>();
    int n = Utils.getRandomInteger(10, 10000);
    for (int i = 0; i < n; i++) {
      int key = Utils.getRandomInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
      try {
        tree.add(key);
        assertTrue(TestUtils.isBinarySearchTree(tree));
        assertTrue(TestUtils.isAvlTree(tree));
        assertTrue(tree.contains(key));
      } catch (DuplicateKeyException e) {
        // Do nothing just avoid stopping the test because of duplicates
      }
    }
  }

  @Test
  public void testIsEmptyAndClear() throws OperationNotSupportedException {
    AVLTree<Integer> tree = new AVLTree<>();
    assertTrue(tree.isEmpty());
    assertEquals(0, tree.size());
    int n = Utils.getRandomInteger(100, 1000);
    for (int i = 0; i < n; i++) {
      int key = Utils.getRandomInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
      try {
        tree.add(key);
        assertTrue(TestUtils.isBinarySearchTree(tree));
        assertTrue(TestUtils.isAvlTree(tree));
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
}
