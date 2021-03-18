package br.com.eventhorizon.common.datastructures.tree;

import java.util.NoSuchElementException;

public class AVLTree<T> extends BinarySearchTree<T> {

  public AVLTree() {
    super();
  }

  @Override
  public Node<T> add(T key) {
    Node<T> addedNode = super.add(key);
    retrace(addedNode.parent, false);
    return addedNode;
  }

  @Override
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
    retrace(aux, true);
    size--;
    return removedNode;
  }

  private void retrace(Node<T> node, boolean continuous) {
    while (node != null) {
      if (balance(node)) {
        if (!continuous) {
          break;
        }
      }
      node = node.parent;
    }
  }

  private boolean balance(Node<T> node) {
    if (node == null) {
      return false;
    }
    if (node.balanceFactor() == 2) {
      Node<T> right = node.right;
      if (right.balanceFactor() == 0 || right.balanceFactor() == 1) {
        rotateLeft(node);
      } else {
        rotateRightLeft(node);
      }
      return true;
    }
    if (node.balanceFactor() == -2) {
      Node<T> left = node.left;
      if (left.balanceFactor() == 0 || left.balanceFactor() == -1) {
        rotateRight(node);
      } else {
        rotateLeftRight(node);
      }
      return true;
    }
    return false;
  }

  private void rotateLeftRight(Node<T> node) {
    super.rotateLeft(node.left);
    super.rotateRight(node);
  }

  private void rotateRightLeft(Node<T> node) {
    super.rotateRight(node.right);
    super.rotateLeft(node);
  }
}
