package br.com.eventhorizon.datastructures.trees;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TreeTraverserTest {

  @Test
  public void testTraverserDepthFirstPreOrderOnEmptyTree() {
    TreeTraverser<Integer> traverser = new TreeTraverser<>(null, TreeTraverser.Type.DEPTH_FIRST_PREORDER);
    assertFalse(traverser.hasNext());
    assertThrows(NoSuchElementException.class, traverser::next);
  }

  @Test
  public void testTraverserDepthFirstPreOrder() {
    TreeTraverser<Integer> traverser = new TreeTraverser<>(TreeTestUtils.ROOT, TreeTraverser.Type.DEPTH_FIRST_PREORDER);
    for (int i = 0; i < TreeTestUtils.TREE_SIZE; i++) {
      assertTrue(traverser.hasNext());
      assertEquals(TreeTestUtils.TREE_PRE_ORDER[i], traverser.next().key);
    }
  }

  @Test
  public void testTraverserDepthFirstInOrderOnEmptyTree() {
    TreeTraverser<Integer> traverser = new TreeTraverser<>(null, TreeTraverser.Type.DEPTH_FIRST_INORDER);
    assertFalse(traverser.hasNext());
    assertThrows(NoSuchElementException.class, traverser::next);
  }

  @Test
  public void testTraverserDepthFirstInOrder() {
    TreeTraverser<Integer> traverser = new TreeTraverser<>(TreeTestUtils.ROOT, TreeTraverser.Type.DEPTH_FIRST_INORDER);
    for (int i = 0; i < TreeTestUtils.TREE_SIZE; i++) {
      assertTrue(traverser.hasNext());
      assertEquals(TreeTestUtils.TREE_IN_ORDER[i], traverser.next().key);
    }
  }

  @Test
  public void testTraverserDepthFirstPostOrderOnEmptyTree() {
    TreeTraverser<Integer> traverser = new TreeTraverser<>(null, TreeTraverser.Type.DEPTH_FIRST_POSTORDER);
    assertFalse(traverser.hasNext());
    assertThrows(NoSuchElementException.class, traverser::next);
  }

  @Test
  public void testTraverserDepthFirstPostOrder() {
    TreeTraverser<Integer> traverser = new TreeTraverser<>(TreeTestUtils.ROOT, TreeTraverser.Type.DEPTH_FIRST_POSTORDER);
    for (int i = 0; i < TreeTestUtils.TREE_SIZE; i++) {
      assertTrue(traverser.hasNext());
      assertEquals(TreeTestUtils.TREE_POST_ORDER[i], traverser.next().key);
    }
  }

  @Test
  public void testTraverserBreadthFirstOnEmptyTree() {
    TreeTraverser<Integer> traverser = new TreeTraverser<>(null, TreeTraverser.Type.BREADTH_FIRST);
    assertFalse(traverser.hasNext());
    assertThrows(NoSuchElementException.class, traverser::next);
  }

  @Test
  public void testTraverserBreadthFirst() {
    TreeTraverser<Integer> traverser = new TreeTraverser<>(TreeTestUtils.ROOT, TreeTraverser.Type.BREADTH_FIRST);
    for (int i = 0; i < TreeTestUtils.TREE_SIZE; i++) {
      assertTrue(traverser.hasNext());
      assertEquals(TreeTestUtils.TREE_BREADTH_FIRST[i], traverser.next().key);
    }
    assertFalse(traverser.hasNext());
  }
}
