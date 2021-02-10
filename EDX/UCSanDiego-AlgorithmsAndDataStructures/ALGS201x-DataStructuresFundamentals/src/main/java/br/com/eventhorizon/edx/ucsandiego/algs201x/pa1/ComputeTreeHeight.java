package br.com.eventhorizon.edx.ucsandiego.algs201x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ComputeTreeHeight implements PA {

  @Override
  public void naiveSolution() {
    int[] tree = readInput();
    System.out.println(naiveComputeHeight(tree));
  }

  private static int[] readInput() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    int[] tree = new int[n];
    for (int i = 0; i < n; i++) {
      tree[i] = scanner.nextInt();
    }
    return tree;
  }

  private int naiveComputeHeight(int[] tree) {
    int maxHeight = 0;
    for (int vertex = 0; vertex < tree.length; vertex++) {
      int height = 0;
      for (int i = vertex; i != -1; i = tree[i])
        height++;
      maxHeight = Math.max(maxHeight, height);
    }
    return maxHeight;
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    Node[] nodes = new Node[n];
    for (int i = 0; i < n; i++) {
      nodes[i] = new Node();
    }
    Node root = null;
    for (int i = 0; i < n; i++) {
      int parentIndex = scanner.nextInt();
      if (parentIndex == -1) {
        root = nodes[i];
      } else {
        nodes[parentIndex].addChild(nodes[i]);
      }
    }
    System.out.println(finalSolutionComputeHeight(root));
  }

  private static int finalSolutionComputeHeight(Node root) {
    if (root == null) {
      return 0;
    }
    root.height = 1;
    Stack<Node> stack = new Stack<>();
    stack.push(root);
    int maxHeight = 0;
    while (!stack.isEmpty()) {
      Node node = stack.pop();
      if (node.children.isEmpty()) {
        if (node.height > maxHeight) {
          maxHeight = node.height;
        }
      } else {
        int childHeight = node.height + 1;
        node.children.forEach(child -> {
            child.height = childHeight;
            stack.push(child);
        });
      }
    }
    return maxHeight;
  }

  private static class Node {

    int height;

    List<Node> children = new ArrayList<>();

    void addChild(Node node) {
      children.add(node);
    }
  }
}
