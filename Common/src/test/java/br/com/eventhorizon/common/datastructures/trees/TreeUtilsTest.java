package br.com.eventhorizon.common.datastructures.trees;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TreeUtilsTest {

  @Test
  public void testMinimum() {
    Node<Integer> node = TreeUtils.minimum(TreeTestUtils.ROOT);
    assertNotNull(node);
    assertTrue(node.hasParent());
    assertFalse(node.hasChildren());
    assertEquals(10, node.key);
  }

  @Test
  public void testMaximum() {
    Node<Integer> node = TreeUtils.maximum(TreeTestUtils.ROOT);
    assertNotNull(node);
    assertTrue(node.hasParent());
    assertTrue(node.hasChildren());
    assertEquals(99, node.key);
  }

  @Test
  public void testPredecessor() {
    Node<Integer> node = TreeUtils.maximum(TreeTestUtils.ROOT);
    int i = TreeTestUtils.TREE_IN_ORDER.length - 1;
    while (node != null) {
      assertEquals(TreeTestUtils.TREE_IN_ORDER[i--], node.key);
      node = TreeUtils.predecessor(node);
    }
  }

  @Test
  public void testSuccessor() {
    Node<Integer> node = TreeUtils.minimum(TreeTestUtils.ROOT);
    int i = 0;
    while (node != null) {
      assertEquals(TreeTestUtils.TREE_IN_ORDER[i++], node.key);
      node = TreeUtils.successor(node);
    }
  }
}
