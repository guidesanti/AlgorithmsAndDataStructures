package br.com.eventhorizon.common.datastructures.trees;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.datastructures.trees.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {

  @Test
  public void testNode() {
    Node<Integer> node = new Node<>(10);
    assertNotNull(node.key);
    assertEquals(10, node.key);
    assertEquals(1, node.height);
    assertNull(node.parent);
    assertNull(node.left);
    assertNull(node.right);

    Node<Integer> parent = new Node<>(20);
    node = new Node<>(10, parent);
    assertNotNull(node.key);
    assertEquals(10, node.key);
    assertEquals(1, node.height);
    assertSame(parent, node.parent);
    assertNull(node.left);
    assertNull(node.right);
  }

  @Test
  public void testBalanceFactor() {
    Node<Integer> left = new Node<>(2);
    left.height = Utils.getRandomInteger(1, Integer.MAX_VALUE);
    Node<Integer> right = new Node<>(3);
    right.height = Utils.getRandomInteger(1, Integer.MAX_VALUE);
    Node<Integer> node = new Node<>(1);
    node.left = left;
    node.right = right;
    assertEquals(right.height - left.height, node.balanceFactor());
  }

  @Test
  public void testHasParent() {
    Node<Integer> left = new Node<>(2);
    Node<Integer> right = new Node<>(3);
    Node<Integer> node = new Node<>(1);
    node.left = left;
    left.parent = node;
    node.right = right;
    right.parent = node;
    assertFalse(node.hasParent());
    assertTrue(left.hasParent());
    assertTrue(right.hasParent());
  }

  @Test
  public void testHasChildren() {
    Node<Integer> left = new Node<>(2);
    Node<Integer> right = new Node<>(3);
    Node<Integer> node = new Node<>(1);
    node.left = left;
    left.parent = node;
    node.right = right;
    right.parent = node;
    assertTrue(node.hasChildren());
    assertFalse(left.hasChildren());
    assertFalse(right.hasChildren());
  }

  @Test
  public void testIsLeaf() {
    Node<Integer> left = new Node<>(2);
    Node<Integer> right = new Node<>(3);
    Node<Integer> node = new Node<>(1);
    node.left = left;
    left.parent = node;
    node.right = right;
    right.parent = node;
    assertFalse(node.isLeaf());
    assertTrue(left.isLeaf());
    assertTrue(right.isLeaf());
  }
}
