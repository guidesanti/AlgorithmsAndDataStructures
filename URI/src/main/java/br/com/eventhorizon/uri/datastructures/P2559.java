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
    SegmentTree segmentTree = new SegmentTree(MAX_NUMBER_COUNT);
    String str = scanner.next();
    while (str != null) {
      numberCount = Integer.parseInt(str);

      // Reset segment tree
      segmentTree.reset(numberCount);

      // Read numbers and add them to segment tree
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
          segmentTree.sum(scanner.nextInt() - 1, scanner.nextInt() - 1);
        } else {
          throw new RuntimeException("Invalid operation " + op);
        }
      }

      str = scanner.next();
    }
  }

  private static class SegmentTree {

    final int maxNumberCount;

    final int maxTreeSize;

    final int[] tree;

    final int[] lazy;

    final Stack<Node> stack = new Stack<>();

    int numberCount;

    int count;

    int firstLeaf;

    SegmentTree(int maxNumberCount) {
      this.maxNumberCount = maxNumberCount;
      this.maxTreeSize = computeMaxTreeSize(maxNumberCount);
      this.firstLeaf = maxTreeSize / 2;
      this.tree = new int[this.maxTreeSize];
      this.lazy = new int[this.maxTreeSize];
    }

    void reset(int numberCount) {
      this.numberCount = numberCount;
      this.count = 0;
      // TODO: set first leaf
      stack.clear();
      stack.push(new Node(0, 0, numberCount - 1));
      while (true) {
        Node node = stack.peek();
        node.marked = true;
        Node left = left(node);
        Node right = right(node);
        stack.push(right);
        stack.push(left);
        if (left.isLeaf()) {
          break;
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
          tree[node.index] = tree[left(node.index)] + tree[right(node.index)];
        } else {
          stack.push(node);
          while (true) {
            node = stack.peek();
            node.marked = true;
            Node left = left(node);
            Node right = right(node);
            stack.push(right);
            stack.push(left);
            if (left.isLeaf()) {
              break;
            }
          }
        }
        node = stack.pop();
      }
      tree[node.index] = value;
      count++;
      if (count == numberCount) {
        // Finish building the tree
        while (!stack.isEmpty()) {
          node = stack.pop();
          tree[node.index] = tree[left(node.index)] + tree[right(node.index)];
        }
      }
    }

    void replace(int number, int value) {
      // TODO
    }

    void replace(int from, int to, int oldValue, int newValue) {
      // TODO
    }

    void update(int from, int to, int value) {
      Stack<Node> nodes = new Stack<>();
      nodes.push(new Node(0, 0, maxNumberCount - 1));
      while (!nodes.isEmpty()) {
        Node node = nodes.pop();

        // Handle current node pending update
        long pendingUpdate = lazy[node.index];
        if (pendingUpdate > 0) {
          tree[node.index] += (node.to - node.from + 1) * pendingUpdate;
          lazy[node.index] = 0;
          if (node.index < firstLeaf) {
            lazy[left(node.index)] += pendingUpdate;
            lazy[right(node.index)] += pendingUpdate;
          }
        }

        // Ignore node if out of range
        if (node.to < from || node.from > to) {
          continue;
        }

        if (node.from >= from && node.to <= to) {
          // Handle current node update if it lies completely in update range
          tree[node.index] += ((long) (node.to - node.from + 1) * value);
          if (node.index < firstLeaf) {
            lazy[left(node.index)] += value;
            lazy[right(node.index)] += value;
          }
        } else {
          // Handle current node update if it overlaps with update range
          tree[node.index] += (long) (Math.min(node.to, to) - Math.max(node.from, from) + 1) * value;
          nodes.push(right(node));
          nodes.push(left(node));
        }
      }
    }

    long sum(int from, int to) {
      long sum = 0;
      Stack<Node> nodes = new Stack<>();
      nodes.push(new Node(0, 0, maxNumberCount - 1));
      while (!nodes.isEmpty()) {
        Node node = nodes.pop();

        // Handle current node pending update
        long pendingUpdate = lazy[node.index];
        if (pendingUpdate > 0) {
          tree[node.index] += (node.to - node.from + 1) * pendingUpdate;
          lazy[node.index] = 0;

          // Postpone children updates
          if (node.index < firstLeaf) {
            lazy[left(node.index)] += pendingUpdate;
            lazy[right(node.index)] += pendingUpdate;
          }
        }

        // Ignore node if out of range
        if (node.to < from || node.from > to) {
          continue;
        }

        if (node.from >= from && node.to <= to) {
          sum += tree[node.index];
        } else if (node.to >= from || node.from <= to) {
          nodes.push(right(node));
          nodes.push(left(node));
        }
      }
      return sum;
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
}
