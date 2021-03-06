package br.com.eventhorizon.common.datastructures.tree;

import br.com.eventhorizon.common.datastructures.Queue;
import br.com.eventhorizon.common.datastructures.Stack;

import java.util.NoSuchElementException;
import java.util.function.Function;

public class TreeTraverser<T extends Comparable<T>> {

  private final Function<Void, Node<T>> traverser;

  private final Stack<Node<T>> stack;

  private final Queue<Node<T>> queue;

  private Node<T> lastVisited;

  public TreeTraverser(Node<T> root, TreeTraverser.Type type) {
    switch (type) {
      case DEPTH_FIRST_PREORDER:
        this.traverser = unused -> depthFirstPreOrder();
        break;
      case DEPTH_FIRST_INORDER:
        this.traverser = unused -> depthFirstInOrder();
        break;
      case DEPTH_FIRST_POSTORDER:
        this.traverser = unused -> depthFirstPostOrder();
        break;
      case BREADTH_FIRST:
        this.traverser = unused -> breadthFirst();
        break;
      default:
        throw new IllegalArgumentException("Unknown type");
    }
    this.stack = new Stack<>();
    this.queue = new Queue<>();
    if (root != null) {
      this.stack.push(root);
      this.queue.enqueue(root);
    }
    this.lastVisited = null;
  }

  private Node<T> depthFirstPreOrder() {
    if (stack.isEmpty()) {
      throw new NoSuchElementException();
    }
    Node<T> node = stack.pop();
    pushNode(node.right);
    pushNode(node.left);
    return node;
  }

  private Node<T> depthFirstInOrder() {
    if (stack.isEmpty()) {
      throw new NoSuchElementException();
    }
    Node<T> node = stack.pop();
    if (lastVisited == null ||
        (node.left != null &&
            lastVisited != node.left &&
            lastVisited.key.compareTo(node.left.key) < 0)) {
      while (node.left != null) {
        pushNode(node);
        if (node.left != null) {
          node = node.left;
        }
      }
    }
    if (node.right != null) {
      pushNode(node.right);
    }
    lastVisited = node;
    return node;
  }

  private Node<T> depthFirstPostOrder() {
    if (stack.isEmpty()) {
      throw new NoSuchElementException();
    }
    Node<T> node = stack.peek();
    if (lastVisited == null || (lastVisited != node.left && lastVisited != node.right)) {
      while (node.hasChildren()) {
        pushNode(node.right);
        pushNode(node.left);
        if (node.left != null) {
          node = node.left;
        } else {
          node = node.right;
        }
      }
    }
    lastVisited = stack.pop();
    return lastVisited;
  }

  private Node<T> breadthFirst() {
    if (queue.isEmpty()) {
      throw new NoSuchElementException();
    }
    Node<T> node = queue.dequeue();
    enqueueNode(node.left);
    enqueueNode(node.right);
    return node;
  }

  private void pushNode(Node<T> node) {
    if (node != null) {
      stack.push(node);
    }
  }

  private void enqueueNode(Node<T> node) {
    if (node != null) {
      queue.enqueue(node);
    }
  }

  public boolean hasNext() {
    return !stack.isEmpty();
  }

  public Node<T> next() {
    return traverser.apply(null);
  }

  public enum Type {
    DEPTH_FIRST_PREORDER,
    DEPTH_FIRST_INORDER,
    DEPTH_FIRST_POSTORDER,
    BREADTH_FIRST
  }
}
