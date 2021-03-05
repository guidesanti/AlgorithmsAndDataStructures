package br.com.eventhorizon.common.datastructures;

import javax.naming.OperationNotSupportedException;
import java.util.NoSuchElementException;

public class SplayTree<T extends Comparable<T>> extends BinarySearchTree<T> {

  public SplayTree() { }

  private SplayTree(Node<T> root) {
    this.root = root;
    if (this.root != null) {
      this.root.parent = null;
    }
  }

  @Override
  public Node<T> add(T key) {
    Node<T> addedNode = new Node<>(key);
    SplayTree<T> higherTree = split(key);
    addedNode.right = higherTree.root;
    if (higherTree.root != null) {
      higherTree.root.parent = addedNode;
    }
    higherTree.root = addedNode;
    join(higherTree);
    return  addedNode;
  }

  @Override
  public Node<T> remove(T key) {
    Node<T> removedNode = find(key);
    if (removedNode == null) {
      throw new NoSuchElementException();
    }
    root = removedNode.left;
    if (removedNode.left != null) {
      removedNode.left.parent = null;
    }
    join(new SplayTree<>(removedNode.right));
    return removedNode;
  }

  @Override
  protected Node<T> find(T key) {
    Node<T> foundNode = super.find(key);
    splay(foundNode);
    return foundNode;
  }

  @Override
  public boolean isEmpty() {
    return root == null;
  }

  @Override
  public int size() throws OperationNotSupportedException {
    throw new OperationNotSupportedException();
  }

  @Override
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
  }

  @Override
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
  }

  protected void splay(Node<T> node) {
    if (node == null) {
      return;
    }
    while (node.parent != null) {
      if (node.parent.parent == null) {
        if (node == node.parent.left) {
          rotateRight(node.parent);
        } else {
          rotateLeft(node.parent);
        }
      } else if (node == node.parent.left && node.parent.parent.left == node.parent) {
        rotateRight(node.parent.parent);
        rotateRight(node.parent);
      } else if (node == node.parent.right && node.parent.parent.right == node.parent) {
        rotateLeft(node.parent.parent);
        rotateLeft(node.parent);
      } else if (node == node.parent.left && node.parent.parent.right == node.parent) {
        rotateRight(node.parent);
        rotateLeft(node.parent);
      } else {
        rotateLeft(node.parent);
        rotateRight(node.parent);
      }
    }
  }

  protected void join(SplayTree<T> tree) {
    if (this.root == null) {
      this.root = tree.root;
      this.size = tree.size;
    } else {
      if (tree.root != null && this.root.key.compareTo(tree.root.key) > 0) {
        throw new RuntimeException("Trees cannot be merged");
      }
      splay(maximum());
      this.root.right = tree.root;
      if (this.root.right != null) {
        this.root.right.parent = this.root;
      }
    }
    tree.clear();
  }

  protected SplayTree<T> split(T key) {
    Node<T> splitNode = null;
    Node<T> node = root;
    while (node != null) {
      if (key.compareTo(node.key) >= 0) {
        splitNode = node;
      }
      if (key.equals(node.key)) {
        break;
      }
      if (key.compareTo(node.key) < 0) {
        node = node.left;
      } else {
        node = node.right;
      }
    }
    return split(splitNode);
  }

  protected SplayTree<T> split(Node<T> node) {
    SplayTree<T> higherTree;
    if (root == null) {
      higherTree = new SplayTree<>();
    } else {
      if (node == null) {
        higherTree = new SplayTree<>(root);
        higherTree.size = size;
        this.clear();
      } else {
        splay(node);
        higherTree = new SplayTree<>(root.right);
        root.right = null;
      }
    }
    return higherTree;
  }
}
