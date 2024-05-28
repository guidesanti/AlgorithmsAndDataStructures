package br.com.eventhorizon.edx.ucsandiego.algs201x.pa4;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.Stack;

public class IsBinarySearchTree2 implements PA {

  @Override
  public void trivialSolution() {
    System.out.println(recursivePreOrder(readInput()) ? "CORRECT" : "INCORRECT");
  }

  private static boolean recursivePreOrder(Node node) {
    if (node == null) {
      return true;
    }
    if (!verifyNode(node)) {
      return false;
    }
    if (!recursivePreOrder(node.left)) {
      return false;
    }
    return recursivePreOrder(node.right);
  }

  @Override
  public void finalSolution() {
    System.out.println(iterativePreOrder(readInput()) ? "CORRECT" : "INCORRECT");
  }

  private static boolean iterativePreOrder(Node root) {
    if (root == null) {
      return true;
    }
    Stack<Node> stack = new Stack<>();
    stack.push(root);
    while (!stack.isEmpty()) {
      Node node = stack.pop();
      if (!node.visited) {
        if (!verifyNode(node)) {
          return false;
        }
        node.visited = true;
        if (node.right != null) {
          stack.push(node.right);
        }
        if (node.left != null) {
          stack.push(node.left);
        }
      }
    }
    return true;
  }

  private static Node readInput() {
    FastScanner in = new FastScanner(System.in);
    int n = in.nextInt();
    if (n == 0) {
      return null;
    }
    Node[] nodes = new Node[n];
    for (int i = 0; i < n; i++) {
      nodes[i] = new Node();
    }
    for (int i = 0; i < n; i++) {
      Node node = nodes[i];
      node.key = in.nextInt();
      int leftIndex = in.nextInt();
      if (leftIndex != -1) {
        nodes[leftIndex].parent = node;
        node.left = nodes[leftIndex];
      }
      int rightIndex = in.nextInt();
      if (rightIndex != -1) {
        nodes[rightIndex].parent = node;
        node.right = nodes[rightIndex];
      }
    }
    return nodes[0];
  }

  private static boolean verifyNode(Node node) {
    Node parent = node.parent;
    if (parent == null) {
      if ((node.left != null && node.left.key >= node.key) ||
          (node.right != null && node.right.key < node.key)) {
        return false;
      }
    } else {
      if (parent.left == node) {
        if ((node.left != null && (node.left.key >= node.key || node.left.key >= parent.key)) ||
            (node.right != null && (node.right.key < node.key || node.right.key >= parent.key))) {
          return false;
        }
      } else {
        if ((node.left != null && (node.left.key >= node.key || node.left.key < parent.key)) ||
            (node.right != null && (node.right.key < node.key || node.right.key < parent.key))) {
          return false;
        }
      }
    }
    return true;
  }

  private static class Node {

    int key;

    Node parent;

    Node left;

    Node right;

    boolean visited;
  }
}
