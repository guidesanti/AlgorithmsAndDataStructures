package br.com.eventhorizon.common.datastructures;

import java.util.NoSuchElementException;
import java.util.function.Function;

public class BinarySearchTree<T extends Comparable<T>> {

  private Node<T> root;

  private int size;

  public BinarySearchTree() {
    root = null;
    size = 0;
  }

  public void add(T key) {
    Node<T> parent = null;
    Node<T> current = root;
    while (current != null) {
      parent = current;
      if (key.equals(current.key)) {
        throw new DuplicateKeyException();
      }
      if (key.compareTo(current.key) < 0) {
        current = current.left;
      } else {
        current = current.right;
      }
    }
    if (parent == null) {
      root = new Node<>(key);
    } else if(key.compareTo(parent.key) < 0) {
      parent.left = new Node<>(key, parent, null, null);
    } else {
      parent.right = new Node<>(key, parent, null, null);
    }
    size++;
  }

  public void remove(T key) {
    Node<T> node = find(key);
    if (node == null) {
      throw new NoSuchElementException();
    }
    if (node.left == null) {
      transplant(node.right, node);
    } else if (node.right == null) {
      transplant(node.left, node);
    } else {
      // TODO
    }
    size--;
  }

  public T minimum() {
    if (root == null) {
      throw new NoSuchElementException();
    }
    return root.minimum().key;
  }

  public T maximum() {
    if (root == null) {
      throw new NoSuchElementException();
    }
    return root.maximum().key;
  }

  public void clear() {
    root = null;
    size = 0;
  }

  public boolean contains(T key) {
    return find(key) != null;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  private Node<T> find(T key) {
    Node<T> node = root;
    while (node != null) {
      if (key.equals(node.key)) {
        return node;
      }
      if (key.compareTo(node.key) < 0) {
        node = node.left;
      } else {
        node = node.right;
      }
    }
    return null;
  }

  private void transplant(Node<T> source, Node<T> target) {
    if (target.parent == null) {
      root = source;
    } else if (target.parent.left == target) {
      target.parent.left = source;
    } else {
      target.parent.right = source;
    }
    if (source != null) {
      source.parent = target.parent;
    }
  }

  public Traverser<T> traverser(Traverser.Type type) {
    return new Traverser<T>(root, type);
  }

  public static class Node<T> {

    private final T key;

    private Node<T> parent;

    private Node<T> left;

    private Node<T> right;

    public Node(T key) {
      this.key = key;
    }

    public Node(T key, Node<T> left, Node<T> right) {
      this.key = key;
      this.left = left;
      this.right = right;
    }

    public Node(T key, Node<T> parent, Node<T> left, Node<T> right) {
      this.key = key;
      this.parent = parent;
      this.left = left;
      this.right = right;
    }

    public T getKey() {
      return key;
    }

    public Node<T> getParent() {
      return parent;
    }

    public void setParent(Node<T> parent) {
      this.parent = parent;
    }

    public Node<T> getLeft() {
      return left;
    }

    public void setLeft(Node<T> left) {
      this.left = left;
    }

    public Node<T> getRight() {
      return right;
    }

    public void setRight(Node<T> right) {
      this.right = right;
    }

    public boolean hasParent() {
      return parent != null;
    }

    public  boolean hasChildren() {
      return left != null || right != null;
    }

    public Node<T> predecessor() {
      if (this.left != null) {
        return this.left.maximum();
      }
      Node<T> ancestor1 = this;
      Node<T> ancestor2 = this.parent;
      while (ancestor2 != null && ancestor1 == ancestor2.left) {
        ancestor1 = ancestor2;
        ancestor2 = ancestor2.parent;
      }
      return ancestor2;
    }

    public Node<T> successor() {
      if (this.right != null) {
        return this.right.minimum();
      }
      Node<T> ancestor1 = this;
      Node<T> ancestor2 = this.parent;
      while (ancestor2 != null && ancestor1 == ancestor2.right) {
        ancestor1 = ancestor2;
        ancestor2 = ancestor2.parent;
      }
      return ancestor2;
    }

    public Node<T> minimum() {
      Node<T> node = this;
      while (node.left != null) {
        node = node.left;
      }
      return node;
    }

    public Node<T> maximum() {
      Node<T> node = this;
      while (node.right != null) {
        node = node.right;
      }
      return node;
    }

    @Override
    public String toString() {
      return "" + key;
    }
  }

  public static class Traverser<T extends Comparable<T>> {

    private final Function<Void, Node<T>> traverser;

    private final Stack<Node<T>> stack;

    private final Queue<Node<T>> queue;

    private Node<T> lastVisited;

    Traverser(Node<T> root, Type type) {
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

    public T next() {
      return traverser.apply(null).key;
    }

    public enum Type {
      DEPTH_FIRST_PREORDER,
      DEPTH_FIRST_INORDER,
      DEPTH_FIRST_POSTORDER,
      BREADTH_FIRST;
    }
  }
}
