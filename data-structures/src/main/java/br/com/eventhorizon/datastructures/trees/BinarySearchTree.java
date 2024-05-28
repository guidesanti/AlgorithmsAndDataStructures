package br.com.eventhorizon.datastructures.trees;

import br.com.eventhorizon.datastructures.DuplicateKeyException;

import java.util.NoSuchElementException;

public class BinarySearchTree<T> {

  protected Node<T> root;

  protected int size;

  public BinarySearchTree() {
    this.root = null;
    this.size = 0;
  }

  @SuppressWarnings("unchecked")
  public Node<T> add(T key) {
    Node<T> parent = null;
    Node<T> current = root;
    while (current != null) {
      parent = current;
      if (key.equals(current.key)) {
        throw new DuplicateKeyException();
      }
      if (((Comparable<? super T>)key).compareTo(current.key) < 0) {
        current = current.left;
      } else {
        current = current.right;
      }
    }
    Node<T> addedNode;
    if (parent == null) {
      addedNode = new Node<>(key);
      root = addedNode;
    } else if(((Comparable<? super T>)key).compareTo(parent.key) < 0) {
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
      Node<T> successor = TreeUtils.successor(removedNode);
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
    return TreeUtils.minimum(this.root);
  }

  public Node<T> maximum() {
    return TreeUtils.maximum(this.root);
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

  @SuppressWarnings("unchecked")
  protected Node<T> find(T key) {
    Node<T> node = root;
    while (node != null) {
      if (key.equals(node.key)) {
        return node;
      }
      if (((Comparable<? super T>)key).compareTo(node.key) < 0) {
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
}
