package br.com.eventhorizon.edx.ucsandiego.algs201x.pa4;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

public class SetWithRangeOfSums implements PA {

  private static final int MODULO = 1000000001;

  @Override
  public void naiveSolution() {
    finalSolution();
  }

  @Override
  public void finalSolution() {
    SplayTree tree = new SplayTree();
    FastScanner in = new FastScanner(System.in);
    int n = in.nextInt();
    int lastSumResult = 0;
    for (int i = 0; i < n; i++) {
      char type = in.nextChar();
      switch (type) {
        case '+' : {
          int x = in.nextInt();
          tree.add((x + lastSumResult) % MODULO);
        } break;
        case '-' : {
          int x = in.nextInt();
          tree.remove((x + lastSumResult) % MODULO);
        } break;
        case '?' : {
          int x = in.nextInt();
          System.out.println(tree.contains((x + lastSumResult) % MODULO) ? "Found" : "Not found");
        } break;
        case 's' : {
          int l = in.nextInt();
          int r = in.nextInt();
          long res = sum(tree, (l + lastSumResult) % MODULO, (r + lastSumResult) % MODULO);
          System.out.println(res);
          lastSumResult = (int)(res % MODULO);
        }
      }
    }
  }

  private static long sum(SplayTree tree, int from, int to) {
    SplayTree middleTree = tree.split(from - 1);
    SplayTree higherTree = middleTree.split(to);
    long sum = middleTree.root == null ? 0 : middleTree.root.sum;
    tree.join(middleTree);
    tree.join(higherTree);
    return sum;
  }

  private static class Node {

    final int key;

    long sum;

    Node parent;

    Node left;

    Node right;

    Node(int key) {
      this.key = key;
      this.sum = key;
    }

    Node(int key, Node parent) {
      this.key = key;
      this.sum = key;
      this.parent = parent;
    }

    @Override
    public String toString() {
      return "" + key;
    }
  }

  private static class SplayTree {

    Node root;

    SplayTree() { }

    SplayTree(Node root) {
      this.root = root;
      if (this.root != null) {
        this.root.parent = null;
      }
    }

    Node add(int key) {
      Node addedNode = find(key);
      if (addedNode != null) {
        return addedNode;
      }
      addedNode = new Node(key);
      SplayTree higherTree = split(key);
      addedNode.right = higherTree.root;
      if (higherTree.root != null) {
        higherTree.root.parent = addedNode;
      }
      higherTree.root = addedNode;
      join(higherTree);
      return  addedNode;
    }

    Node remove(int key) {
      Node removedNode = find(key);
      if (removedNode == null) {
        return null;
      }
      root = removedNode.left;
      if (removedNode.left != null) {
        removedNode.left.parent = null;
      }
      join(new SplayTree(removedNode.right));
      return removedNode;
    }

    boolean contains(int key) {
      return find(key) != null;
    }

    Node find(int key) {
      Node last = root;
      Node node = root;
      while (node != null) {
        last = node;
        if (key == node.key) {
          break;
        }
        if (key < node.key) {
          node = node.left;
        } else {
          node = node.right;
        }
      }
      splay(last);
      return node;
    }

    void rotateLeft(Node node) {
      Node parent = node.parent;
      Node right = node.right;
      if (right == null) {
        return;
      }
      Node t2 = right.left;
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

    void rotateRight(Node node) {
      Node parent = node.parent;
      Node left = node.left;
      if (left == null) {
        return;
      }
      Node t2 = left.right;
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

    void splay(Node node) {
      if (node == null) {
        return;
      }
      while (node.parent != null) {
        Node a;
        Node b;
        if (node.parent.parent == null) {
          a = node.parent;
          b = node;
          if (node == node.parent.left) {
            rotateRight(node.parent);
          } else {
            rotateLeft(node.parent);
          }
        } else if (node == node.parent.left && node.parent.parent.left == node.parent) {
          a = node.parent.parent;
          b = node.parent;
          rotateRight(node.parent.parent);
          rotateRight(node.parent);
        } else if (node == node.parent.right && node.parent.parent.right == node.parent) {
          a = node.parent.parent;
          b = node.parent;
          rotateLeft(node.parent.parent);
          rotateLeft(node.parent);
        } else if (node == node.parent.left && node.parent.parent.right == node.parent) {
          a = node.parent.parent;
          b = node.parent;
          rotateRight(node.parent);
          rotateLeft(node.parent);
        } else {
          a = node.parent.parent;
          b = node.parent;
          rotateLeft(node.parent);
          rotateRight(node.parent);
        }
        update(a);
        update(b);
      }
    }

    void join(SplayTree tree) {
      if (this.root == null) {
        this.root = tree.root;
      } else {
        if (tree.root != null && this.root.key > tree.root.key) {
          throw new RuntimeException("Trees cannot be merged");
        }
        splay(maximum());
        this.root.right = tree.root;
        if (this.root.right != null) {
          this.root.right.parent = this.root;
        }
      }
      update(this.root);
      tree.root = null;
    }

    SplayTree split(int key) {
      Node last = root;
      Node splitNode = null;
      Node node = root;
      while (node != null) {
        last = node;
        if (key >= node.key) {
          splitNode = node;
        }
        if (key == node.key) {
          break;
        }
        if (key < node.key) {
          node = node.left;
        } else {
          node = node.right;
        }
      }
      splay(last);
      return split(splitNode);
    }

    SplayTree split(Node node) {
      SplayTree higherTree;
      if (root == null) {
        higherTree = new SplayTree();
      } else {
        if (node == null) {
          higherTree = new SplayTree(root);
          this.root = null;
        } else {
          splay(node);
          higherTree = new SplayTree(root.right);
          root.right = null;
        }
      }
      update(this.root);
      update(higherTree.root);
      return higherTree;
    }

    Node maximum() {
      Node node = this.root;
      while (node.right != null) {
        node = node.right;
      }
      return node;
    }

    void update(Node node) {
      if (node == null) {
        return;
      }
      node.sum = node.key + (node.left != null ? node.left.sum : 0) + (node.right != null ? node.right.sum : 0);
    }
  }
}
