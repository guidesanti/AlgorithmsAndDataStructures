package br.com.eventhorizon.uri.datastructures;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.v2.PA;

import java.util.*;

/**
 * Problem: 2559
 * Name: Friday The 13th
 * Link: https://www.beecrowd.com.br/judge/en/problems/view/2559
 *
 * Solution:
 * To solve this problem it was used a segment tree with lazy propagation.
 * For details about this topic see:
 * - https://www.geeksforgeeks.org/segment-tree-set-1-sum-of-given-range
 * - https://www.geeksforgeeks.org/lazy-propagation-in-segment-tree
 */
public class P2559 implements PA {

  private static final int MAX_NUMBER_COUNT = 100000;

  private FastScanner scanner;

  private int numberCount;

  private final int[] numbers = new int[MAX_NUMBER_COUNT];

  private int operationCount;

  private List<List<Integer>> operations;

  @Override
  public void reset() {
    scanner = new FastScanner(System.in);
  }

  @Override
  public void trivialSolution() {
    reset();
    resetTestCase();
    while (readInput()) {
      for (List<Integer> operation : operations) {
        int opCode = operation.get(0);
        if (opCode == 1) {
          numbers[operation.get(1) - 1] = operation.get(2);
        } else if (opCode == 2) {
          int from = operation.get(1) - 1;
          int to = operation.get(2) - 1;
          int replacing = operation.get(3);
          int replacement = operation.get(4);
          for (int i = from; i <= to; i++) {
            if (numbers[i] == replacing) {
              numbers[i] = replacement;
            }
          }
        } else {
          int from = operation.get(1) - 1;
          int to = operation.get(2) - 1;
          int sum = 0;
          for (int i = from; i <= to; i++) {
            sum += numbers[i];
          }
          System.out.println(sum);
        }
      }
      resetTestCase();
    }
  }

  private boolean readInput() {
    String str = scanner.next();
    if (str == null) {
      return false;
    }
    numberCount = Integer.parseInt(str);
    for (int i = 0; i < numberCount; i++) {
      numbers[i] = scanner.nextInt();
    }
    operationCount = scanner.nextInt();
    for (int i = 0; i < operationCount; i++) {
      int opCode = scanner.nextInt();
      List<Integer> currOperations = new ArrayList<>();
      currOperations.add(opCode);
      currOperations.add(scanner.nextInt());
      currOperations.add(scanner.nextInt());
      if (opCode == 2) {
        currOperations.add(scanner.nextInt());
        currOperations.add(scanner.nextInt());
      }
      operations.add(currOperations);
    }
    return true;
  }

  private void resetTestCase() {
    numberCount = 0;
    operationCount = 0;
    operations = new ArrayList<>();
  }

  @Override
  public void finalSolution() {
    reset();
    String str = scanner.next();
    while (str != null) {
      numberCount = Integer.parseInt(str);

      // Read numbers and add them to segment tree
      SegmentTree segmentTree = new SegmentTree(numberCount);
      for (int i = 0; i < numberCount; i++) {
        segmentTree.add(scanner.nextInt());
      }

      // Read and process operations
      operationCount = scanner.nextInt();
      for (int i = 0; i < operationCount; i++) {
        int op = scanner.nextInt();
        if (op == 1) {
          segmentTree.replace(scanner.nextInt() - 1, scanner.nextInt());
        } else if (op == 2) {
          segmentTree.replace(scanner.nextInt() - 1, scanner.nextInt() - 1, scanner.nextInt(), scanner.nextInt());
        } else if (op == 3) {
          System.out.println(segmentTree.sum(scanner.nextInt() - 1, scanner.nextInt() - 1));
        } else {
          throw new RuntimeException("Invalid operation " + op);
        }
      }

      str = scanner.next();
    }
  }

  private static class SegmentTree {

    final int numberCount;

    final int maxTreeSize;

    final TreeNode[] tree;

    final int[] index;

    final Stack<Node> stack;

    int count;

    SegmentTree(int numberCount) {
      this.numberCount = numberCount;
      this.maxTreeSize = computeMaxTreeSize(numberCount);
      this.tree = new TreeNode[this.maxTreeSize];
      for (int i = 0; i < this.maxTreeSize; i++) {
        tree[i] = new TreeNode();
      }
      this.index = new int[this.numberCount];
      stack = new Stack<>();
      stack.push(new Node(0, 0, numberCount - 1));
      if (numberCount > 1) {
        while (true) {
          Node node = stack.peek();
          node.marked = true;
          Node left = left(node);
          Node right = right(node);
          if (right.to < numberCount) {
            stack.push(right);
          }
          if (right.to < numberCount) {
            stack.push(left);
          }
          if (right.isLeaf() || left.isLeaf()) {
            break;
          }
        }
      }
    }

    int computeMaxTreeSize(int n) {
      int leafCount = 1;
      while (n > leafCount) {
        leafCount <<= 1;
      }
      return (2 * leafCount) - 1;
    }

    void add(int value) {
      Node node = stack.pop();
      while (!node.isLeaf()) {
        if (node.marked) {
          tree[node.index].value = tree[left(node.index)].value + tree[right(node.index)].value;
          tree[node.index].sevenCount = tree[left(node.index)].sevenCount + tree[right(node.index)].sevenCount;
          tree[node.index].thirteenCount = tree[left(node.index)].thirteenCount + tree[right(node.index)].thirteenCount;
        } else {
          stack.push(node);
          while (true) {
            node = stack.peek();
            node.marked = true;
            Node left = left(node);
            Node right = right(node);
            if (right.to < numberCount) {
              stack.push(right);
            }
            if (right.to < numberCount) {
              stack.push(left);
            }
            if (right.isLeaf() || left.isLeaf()) {
              break;
            }
          }
        }
        node = stack.pop();
      }
      tree[node.index].value = value;
      tree[node.index].sevenCount = value == 7 ? 1 : 0;
      tree[node.index].thirteenCount = value == 13 ? 1 : 0;
      index[count] = node.index;
      count++;
      if (count == numberCount) {
        // Finish building the tree
        while (!stack.isEmpty()) {
          node = stack.pop();
          tree[node.index].value = tree[left(node.index)].value + tree[right(node.index)].value;
          tree[node.index].sevenCount = tree[left(node.index)].sevenCount + tree[right(node.index)].sevenCount;
          tree[node.index].thirteenCount = tree[left(node.index)].thirteenCount + tree[right(node.index)].thirteenCount;
        }
      }
    }

    void replace(int number, int value) {
      Node node = new Node(0, 0, numberCount - 1);
      while (!node.isLeaf()) {
        handlePendingOperation(node);
        if (number <= middle(node.from, node.to)) {
          node = left(node);
        } else {
          node = right(node);
        }
      }
      handlePendingOperation(node);

      int nodeIndex = index[number];
      int oldValue = tree[nodeIndex].value;
      if (oldValue == value) {
        return;
      }

      int diff = value - oldValue;
      int sevenDiff = oldValue == 7 ? -1 : value == 7 ? 1 : 0;
      int thirteenDiff = oldValue == 13 ? -1 : value == 13 ? 1 : 0;
      while (nodeIndex >= 0) {
        tree[nodeIndex].value += diff;
        tree[nodeIndex].sevenCount += sevenDiff;
        tree[nodeIndex].thirteenCount += thirteenDiff;
        nodeIndex = parent(nodeIndex);
      }
    }

    void replace(int from, int to, int oldValue, int newValue) {
      if (oldValue == newValue) {
        return;
      }
      Stack<Node> nodes = new Stack<>();
      nodes.push(new Node(0, 0, numberCount - 1));
      while (!nodes.isEmpty()) {
        Node node = nodes.pop();
        TreeNode treeNode = tree[node.index];

        // Handle current node pending update
        handlePendingOperation(node);

        // Ignore node if out of range
        int oldValueCount = oldValue == 7 ? treeNode.sevenCount : treeNode.thirteenCount;
        if (node.to < from || node.from > to || oldValueCount == 0) {
          continue;
        }

        if (node.from >= from && node.to <= to) {
          // Handle current node update if it lies completely in update range
          handleOperation(node, oldValue, newValue);
        } else if (node.marked) {
          // Handle current node update if it overlaps with update range
          int left = left(node.index);
          int right = right(node.index);
          tree[node.index].value = tree[left].value + tree[right].value;
          tree[node.index].sevenCount = tree[left].sevenCount + tree[right].sevenCount;
          tree[node.index].thirteenCount = tree[left].thirteenCount + tree[right].thirteenCount;
        } else {
          node.marked = true;
          nodes.push(node);
          nodes.push(right(node));
          nodes.push(left(node));
        }
      }
    }

    long sum(int from, int to) {
      long sum = 0;
      Stack<Node> nodes = new Stack<>();
      nodes.push(new Node(0, 0, numberCount - 1));
      while (!nodes.isEmpty()) {
        Node node = nodes.pop();

        // Handle current node pending update
        handlePendingOperation(node);

        // Ignore node if out of range
        if (node.to < from || node.from > to) {
          continue;
        }

        if (node.from >= from && node.to <= to) {
          sum += tree[node.index].value;
        } else {
          nodes.push(right(node));
          nodes.push(left(node));
        }
      }
      return sum;
    }

    void handlePendingOperation(Node node) {
      TreeNode treeNode = tree[node.index];
      if (treeNode.pendingOperation != null) {
        handleOperation(node, treeNode.pendingOperation.oldValue, treeNode.pendingOperation.newValue);
        treeNode.pendingOperation = null;
      }
    }

    void handleOperation(Node node, int oldValue, int newValue) {
      TreeNode treeNode = tree[node.index];
      if (oldValue == 7) {
        treeNode.value -= 7 * treeNode.sevenCount;
        treeNode.value += newValue * treeNode.sevenCount;
        treeNode.thirteenCount += newValue == 13 ? treeNode.sevenCount : 0;
        treeNode.sevenCount = 0;
      } else if (oldValue == 13) {
        treeNode.value -= 13 * treeNode.thirteenCount;
        treeNode.value += newValue * treeNode.thirteenCount;
        treeNode.sevenCount += newValue == 7 ? treeNode.thirteenCount : 0;
        treeNode.thirteenCount = 0;
      } else {
        throw new RuntimeException("Invalid pending operation: " + treeNode.pendingOperation);
      }
      if (!node.isLeaf()) {
        Node left = left(node);
        handlePendingOperation(left);
        tree[left.index].pendingOperation = new Operation(oldValue, newValue);
        Node right = right(node);
        handlePendingOperation(right);
        tree[right.index].pendingOperation = new Operation(oldValue, newValue);
      }
    }

    int parent(int i) {
      return (i - 1) >> 1;
    }

    int middle(int from, int to) {
      return from + (to - from) / 2;
    }

    int left(int i) {
      return (i << 1) + 1;
    }

    int right(int i) {
      return (i << 1) + 2;
    }

    Node left(Node node) {
      return new Node(left(node.index), node.from, middle(node.from, node.to));
    }

    Node right(Node node) {
      return new Node(right(node.index), middle(node.from, node.to) + 1, node.to);
    }
  }

  private static class Node {

    final int index;

    final int from;

    final int to;

    boolean marked;

    Node(int index, int from, int to) {
      this.index = index;
      this.from = from;
      this.to = to;
    }

    boolean isLeaf() {
      return from == to;
    }

    @Override
    public String toString() {
      return "Node{" + "index=" + index + ", from=" + from + ", to=" + to + '}';
    }
  }

  private static class TreeNode {

    int value;

    int sevenCount;

    int thirteenCount;

    Operation pendingOperation;

    @Override
    public String toString() {
      return "TreeNode{" +
              "value=" + value +
              ", sevenCount=" + sevenCount +
              ", thirteenCount=" + thirteenCount +
              ", pendingOperation=" + pendingOperation +
              '}';
    }
  }

  private static class Operation {

    final int oldValue;

    final int newValue;

    public Operation(int oldValue, int newValue) {
      this.oldValue = oldValue;
      this.newValue = newValue;
    }

    @Override
    public String toString() {
      return "PendingOperation{" +
              "oldValue=" + oldValue +
              ", newValue=" + newValue +
              '}';
    }
  }
}
