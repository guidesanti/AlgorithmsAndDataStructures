package br.com.eventhorizon.uri.datastructures;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.v2.PA;

import java.util.Stack;

public class P1500 implements PA {

  private FastScanner scanner;

  @Override
  public void reset() {
    scanner = new FastScanner(System.in);
  }

  @Override
  public void trivialSolution() {
    reset();
    int testCaseCount = scanner.nextInt();
    while (testCaseCount-- > 0) {
      trivialProcessTestCase(scanner.nextInt(), scanner.nextInt());
    }
  }
  
  private void trivialProcessTestCase(int numberCount, int commandCount) {
    long[] numbers = new long[numberCount + 1];
    int p;
    int q;
    int v;
    while (commandCount-- > 0) {
      if (scanner.nextInt() == 0) {
        p = scanner.nextInt();
        q = scanner.nextInt();
        v = scanner.nextInt();
        for (int i = p; i <= q; i++) {
          numbers[i] += v;
        }
      } else {
        p = scanner.nextInt();
        q = scanner.nextInt();
        long sum = 0;
        for (int i = p; i <= q; i++) {
          sum += numbers[i];
        }
        System.out.println(sum);
      }
    }
  }

  @Override
  public void finalSolution() {
    reset();
    int testCaseCount = scanner.nextInt();
    while (testCaseCount-- > 0) {
      finalProcessTestCase(scanner.nextInt(), scanner.nextInt());
    }
  }

  private void finalProcessTestCase(int numberCount, int commandCount) {
    SegmentTree segmentTree = new SegmentTree(numberCount);
    while (commandCount-- > 0) {
      if (scanner.nextInt() == 0) {
        segmentTree.update(scanner.nextInt() - 1, scanner.nextInt() - 1, scanner.nextInt());
      } else {
        System.out.println(segmentTree.sum(scanner.nextInt() - 1, scanner.nextInt() - 1));
      }
    }
  }

  private static class SegmentTree {

    final int numberCount;

    final int treeSize;

    final long[] tree;

    long[] lazy;

    SegmentTree(int numberCount) {
      this.numberCount = numberCount;
      this.treeSize = computeMaxTreeSize(numberCount);
      this.tree = new long[this.treeSize];
      this.lazy = new long[this.treeSize];
    }

    int computeMaxTreeSize(int n) {
      int leafCount = 1;
      while (n > leafCount) {
        leafCount <<= 1;
      }
      return (2 * leafCount) - 1;
    }

    void update(int from, int to, int value) {
      Stack<Node> nodes = new Stack<>();
      nodes.push(new Node(0, 0, numberCount - 1));
      while (!nodes.isEmpty()) {
        Node node = nodes.pop();
        if (node.to < from || node.from > to) {
          continue;
        }

        // Handle current node pending update
        long pendingUpdate = lazy[node.index];
        if (pendingUpdate > 0) {
          tree[node.index] += (node.to - node.from + 1) * pendingUpdate;
          lazy[node.index] = 0;
          int left = left(node.index);
          if (left < treeSize) {
            lazy[left] += pendingUpdate;
          }
          int right = right(node.index);
          if (right < treeSize) {
            lazy[right] += pendingUpdate;
          }
        }

        if (node.from >= from && node.to <= to) {
          // Handle current node update if it lies completely in update range
          int n = (node.to - node.from + 1);
          tree[node.index] += ((long) n * value);
          int left = left(node.index);
          if (left < treeSize) {
            lazy[left] += value;
          }
          int right = right(node.index);
          if (right < treeSize) {
            lazy[right] += value;
          }
        } else {
          // Handle current node update if it overlaps with update range
          int n = Math.min(node.to, to) - Math.max(node.from, from) + 1;
          tree[node.index] += (long) n * value;
          Node right = right(node);
          if (right != null) {
            nodes.push(right);
          }
          Node left = left(node);
          if (left != null) {
            nodes.push(left);
          }
        }
      }
    }

    long sum(int from, int to) {
      long sum = 0;
      Stack<Node> nodes = new Stack<>();
      nodes.push(new Node(0, 0, numberCount - 1));
      while (!nodes.isEmpty()) {
        Node node = nodes.pop();

        if (node.to < from || node.from > to) {
          continue;
        }

        // Handle current node pending update
        long pendingUpdate = lazy[node.index];
        if (pendingUpdate > 0) {
          tree[node.index] += (node.to - node.from + 1) * pendingUpdate;
          lazy[node.index] = 0;

          // Postpone children updates
          int left = left(node.index);
          if (left < treeSize) {
            lazy[left] += pendingUpdate;
          }
          int right = right(node.index);
          if (right < treeSize) {
            lazy[right] += pendingUpdate;
          }
        }

        if (node.from >= from && node.to <= to) {
          sum += tree[node.index];
        } else if (node.to >= from || node.from <= to) {
          Node right = right(node);
          if (right != null) {
            nodes.push(right);
          }
          Node left = left(node);
          if (left != null) {
            nodes.push(left);
          }
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
      int left = left(node.index);
      if (left >= treeSize) {
        return null;
      }
      return new Node(left(node.index), node.from, middle(node.from, node.to));
    }

    Node right(Node node) {
      int right = right(node.index);
      if (right >= treeSize) {
        return null;
      }
      return new Node(right(node.index), middle(node.from, node.to) + 1, node.to);
    }
  }

  private static class Node {

    final int index;

    final int from;

    final int to;

    Node(int index, int from, int to) {
      this.index = index;
      this.from = from;
      this.to = to;
    }

    @Override
    public String toString() {
      return "Node{" + "index=" + index + ", from=" + from + ", to=" + to + '}';
    }
  }
}
