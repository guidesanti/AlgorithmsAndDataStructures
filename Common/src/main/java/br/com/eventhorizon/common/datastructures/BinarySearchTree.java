package br.com.eventhorizon.common.datastructures;

import javax.naming.OperationNotSupportedException;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class BinarySearchTree<T extends Comparable<T>> {

  protected Node<T> root;

  protected int size;

  public BinarySearchTree() {
    this.root = null;
    this.size = 0;
  }

  public Node<T> add(T key) {
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
    Node<T> addedNode;
    if (parent == null) {
      addedNode = new Node<>(key);
      root = addedNode;
    } else if(key.compareTo(parent.key) < 0) {
      addedNode = new Node<>(key, parent);
      parent.left = addedNode;
    } else {
      addedNode = new Node<>(key, parent);
      parent.right = addedNode;
    }
    updateHeight(parent);
    size++;
    return addedNode;
  }

  public Node<T> remove(T key) {
    Node<T> removedNode = find(key);
    if (removedNode == null) {
      throw new NoSuchElementException();
    }
    Node<T> aux = removedNode.parent;
    if (removedNode.left == null) {
      transplant(removedNode.right, removedNode);
    } else if (removedNode.right == null) {
      transplant(removedNode.left, removedNode);
    } else {
      Node<T> successor = removedNode.successor();
      aux = successor;
      if (successor != removedNode.right) {
        aux = successor.parent;
        transplant(successor.right, successor);
        successor.right = removedNode.right;
        successor.right.parent = successor;
      }
      transplant(successor, removedNode);
      successor.left = removedNode.left;
      successor.left.parent = successor;
    }
    updateHeight(aux);
    size--;
    return removedNode;
  }

  public Node<T> minimum() {
    if (root == null) {
      throw new NoSuchElementException();
    }
    return root.minimum();
  }

  public Node<T> maximum() {
    if (root == null) {
      throw new NoSuchElementException();
    }
    return root.maximum();
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

  public int size() throws OperationNotSupportedException {
    return size;
  }

  protected Node<T> find(T key) {
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

  protected void updateHeight(Node<T> node) {
    while (node != null) {
      node.height = Math.max(node.left == null ? 0 : node.left.height, node.right == null ? 0 : node.right.height) + 1;
      node = node.parent;
    }
  }

  protected void transplant(Node<T> source, Node<T> target) {
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

  protected void rotateLeft(Node<T> node) {
    Node<T> parent = node.parent;
    Node<T> right = node.right;
    if (right == null) {
      return;
    }
    Node<T> t2 = right.left;
    node.right = t2;
    if (t2 != null) {
      t2.parent = node;
    }
    node.parent = right;
    right.left = node;
    right.parent = parent;
    if (parent == null) {
      this.root = right;
    } else if (parent.left == node) {
      parent.left = right;
    } else {
      parent.right = right;
    }
    updateHeight(node);
  }

  protected void rotateRight(Node<T> node) {
    Node<T> parent = node.parent;
    Node<T> left = node.left;
    if (left == null) {
      return;
    }
    Node<T> t2 = left.right;
    node.left = t2;
    if (t2 != null) {
      t2.parent = node;
    }
    node.parent = left;
    left.right = node;
    left.parent = parent;
    if (parent == null) {
      this.root = left;
    } else if (parent.left == node) {
      parent.left = left;
    } else {
      parent.right = left;
    }
    updateHeight(node);
  }

  public Traverser<T> traverser(Traverser.Type type) {
    return new Traverser<>(root, type);
  }

  public static class Node<T> {

    protected final T key;

    protected int height;

    protected Node<T> parent;

    protected Node<T> left;

    protected Node<T> right;

    public Node(T key) {
      this.key = key;
      this.height = 1;
    }

    public Node(T key, Node<T> parent) {
      this.key = key;
      this.height = 1;
      this.parent = parent;
    }

    public T getKey() {
      return key;
    }

    public int getHeight() {
      return height;
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

    public int balanceFactor() {
      int leftHeight = left == null ? 0 : left.height;
      int rightHeight = right == null ? 0 : right.height;
      return rightHeight - leftHeight;
    }

    public boolean hasParent() {
      return parent != null;
    }

    public  boolean hasChildren() {
      return left != null || right != null;
    }

    public boolean isLeaf() {
      return left == null && right == null;
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
}
