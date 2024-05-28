package br.com.eventhorizon.edx.ucsandiego.algs201x.pa4;

import br.com.eventhorizon.datastructures.Stack;
import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.NoSuchElementException;

public class Rope implements PA {

  @Override
  public void trivialSolution() {
    FastScanner in = new FastScanner(System.in);
    String text = in.next();
    int numberOfQueries = in.nextInt();
    while (numberOfQueries-- > 0) {
      int i = in.nextInt();
      int j = in.nextInt();
      int k = in.nextInt();
      text = naiveCutAndCat(text, i, j, k);
    }
    System.out.println(text);
  }

  private static String naiveCutAndCat(String text, int i, int j, int k) {
    String t = text.substring(0, i) + text.substring(j + 1);
    return t.substring(0, k) + text.substring(i, j + 1) + t.substring(k);
  }

  @Override
  public void finalSolution() {
    FastScanner in = new FastScanner(System.in);
    String text = in.next();
    SplayTree tree = new SplayTree();
    tree.build(text);
    int numberOfQueries = in.nextInt();
    while (numberOfQueries-- > 0) {
      int i = in.nextInt();
      int j = in.nextInt();
      int k = in.nextInt();
      finalCutAndCat(tree, i, j, k);
    }
    print(tree);
  }

  private static void finalCutAndCat(SplayTree tree1, int i, int j, int k) {
    if (k == 0) {
      SplayTree tree2 = tree1.split(i);
      SplayTree tree3 = tree2.split(j - i + 1);
      tree1.join(tree3);
      tree2.join(tree1);
      tree1.root = tree2.root;
    } else {
      SplayTree tree2 = tree1.split(i);
      SplayTree tree3 = tree2.split(j - i + 1);
      tree1.join(tree3);
      tree3 = tree1.split(k);
      tree1.join(tree2);
      tree1.join(tree3);
    }
  }

  private static void print(SplayTree tree) {
    Traverser traverser = new Traverser(tree.root);
    while (traverser.hasNext()) {
      Node node = traverser.next();
      if (node.isLeaf() && node.ch != null) {
        System.out.print(node.ch);
      }
    }
    System.out.println();
  }

  private static class Node {

    int key;

    Character ch;

    Node parent;

    Node left;

    Node right;

    Node(int key) {
      this.key = key;
      this.ch = null;
    }

    Node(char ch) {
      this.key = 1;
      this.ch = ch;
    }

    boolean hasChildren() {
      return left != null || right != null;
    }

    boolean isLeaf() {
      return left == null && right == null;
    }

    @Override
    public String toString() {
      return ch != null ? "" + ch : "" + key;
    }
  }

  private static class SplayTree {

    Node root;

    SplayTree() {
      root = new Node(0);
    }

    SplayTree(Node root) {
      if (root == null || root.key == 0) {
        this.root = new Node(0);
      } else {
        this.root = root;
        this.root.parent = null;
      }
    }

    void build(String text) {
      root = new Node(1);
      root.left = new Node(text.charAt(0));
      root.left.parent = root;
      for (int i = 1; i < text.length(); i++) {
        Node insertionNode = root;
        Node last = root;
        Node node = root;
        int index = i;
        while (node != null && !node.isLeaf()) {
          last = node;
          if (index < node.key) {
            node = node.left;
          } else {
            index -= node.key;
            if (node.parent != null && node.key < node.parent.key / 2) {
              insertionNode = node;
            }
            node = node.right;
          }
        }
        if (last.right == null) {
          last.right = new Node(text.charAt(i));
          last.right.parent = last;
        } else {
          if (insertionNode == root) {
            Node parent = new Node(2 * insertionNode.key);
            parent.left = root;
            parent.left.parent = parent;
            parent.right = new Node(1);
            parent.right.parent = parent;
            parent.right.left = new Node(text.charAt(i));
            parent.right.left.parent = parent.right;
            root = parent;
          } else {
            Node parent = new Node(2 * insertionNode.key);
            Node insertionNodeParent = insertionNode.parent;
            parent.left = insertionNode;
            parent.left.parent = parent;
            parent.right = new Node(1);
            parent.right.parent = parent;
            parent.right.left = new Node(text.charAt(i));
            parent.right.left.parent = parent.right;
            insertionNodeParent.right = parent;
            parent.parent = insertionNodeParent;
          }
        }
      }
      Node maximum = maximum();
      if (maximum.right != null) {
        Node leaf = maximum.right;
        maximum.right = new Node(1);
        maximum.right.parent = maximum;
        maximum.right.left = leaf;
        maximum.right.left.parent = maximum.right;
      }
    }

    void rotateLeft(Rope.Node node) {
      Rope.Node parent = node.parent;
      Rope.Node right = node.right;
      if (right == null) {
        return;
      }
      Rope.Node t2 = right.left;
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

    void rotateRight(Rope.Node node) {
      Rope.Node parent = node.parent;
      Rope.Node left = node.left;
      if (left == null) {
        return;
      }
      Rope.Node t2 = left.right;
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

    void splay(Rope.Node node) {
      if (node == null) {
        return;
      }
      while (node.parent != null) {
        if (node.parent.parent == null) {
          if (node == node.parent.left) {
            Rope.Node aux = node.parent;
            rotateRight(node.parent);
            update(aux);
          } else {
            rotateLeft(node.parent);
            update(node);
          }
        } else if (node == node.parent.left && node.parent.parent.left == node.parent) {
          Rope.Node aux1 = node.parent.parent;
          Rope.Node aux2 = node.parent;
          rotateRight(node.parent.parent);
          rotateRight(node.parent);
          update(aux1);
          update(aux2);
        } else if (node == node.parent.right && node.parent.parent.right == node.parent) {
          Rope.Node aux1 = node.parent.parent;
          Rope.Node aux2 = node.parent;
          rotateLeft(node.parent.parent);
          rotateLeft(node.parent);
          update(aux1);
          update(aux2);
        } else if (node == node.parent.left && node.parent.parent.right == node.parent) {
          Rope.Node aux1 = node.parent.parent;
          Rope.Node aux2 = node.parent;
          Rope.Node aux3 = node;
          rotateRight(node.parent);
          rotateLeft(node.parent);
          update(aux1);
          update(aux2);
          update(aux3);
        } else {
          Rope.Node aux1 = node.parent.parent;
          Rope.Node aux2 = node.parent;
          Rope.Node aux3 = node;
          rotateLeft(node.parent);
          rotateRight(node.parent);
          update(aux1);
          update(aux2);
          update(aux3);
        }
      }
    }

    void join(SplayTree tree) {
      if (tree.isEmpty()) {
        return;
      }
      if (this.isEmpty()) {
        this.root = tree.root;
      } else {
        splay(maximum());
        if (root.right == null) {
          this.root.right = tree.root;
          if (this.root.right != null) {
            this.root.right.parent = this.root;
          }
        } else {
          Rope.Node node = new Rope.Node(countLeaves(root));
          node.left = root;
          node.left.parent = node;
          node.right = tree.root;
          node.right.parent = node;
          root = node;
        }
      }
      tree.clear();
    }

    boolean isEmpty() {
      return root == null || !root.hasChildren();
    }

    void clear() {
      root = new Node(0);
    }

    SplayTree split(int key) {
      Rope.Node last = root;
      Rope.Node node = root;
      Rope.Node splitNode = null;
      int index = key;
      while (node != null && !node.isLeaf()) {
        last = node;
        if (index < node.key) {
          node = node.left;
        } else {
          splitNode = last;
          index -= node.key;
          node = node.right;
        }
      }
      return split(splitNode);
    }

    SplayTree split(Rope.Node node) {
      SplayTree higherTree;
      if (node == null) {
        higherTree = new SplayTree(root);
        this.clear();
      } else {
        splay(node);
        higherTree = new SplayTree(root.right);
        root.right = null;
      }
      update(this.root);
      return higherTree;
    }

    Rope.Node maximum() {
      Rope.Node node = this.root;
      while (node.right != null && !node.right.isLeaf()) {
        node = node.right;
      }
      return node;
    }

    void update(Rope.Node node) {
      if (node == null) {
        return;
      }
      if (node.left == null) {
        node.key = 0;
      } else {
        node.key = countLeaves(node.left);
      }
    }

    int countLeaves(Rope.Node node) {
      int count = 0;
      while (node != null) {
        count += node.key;
        node = node.right;
      }
      return count;
    }

    @Override
    public String toString() {
      StringBuilder str = new StringBuilder();
      Traverser traverser = new Traverser(this.root);
      while(traverser.hasNext()) {
        str.append(traverser.nextLeaf().ch);
      }
      return str.toString();
    }
  }

  private static class Traverser {

    private final Stack<Rope.Node> stack;

    public Traverser(Rope.Node root) {
      this.stack = new Stack<>();
      if (root != null) {
        this.stack.push(root);
      }
    }

    public boolean hasNext() {
      return !stack.isEmpty();
    }

    public Rope.Node nextLeaf() {
      Rope.Node node = next();
      while (!node.isLeaf()) {
        node = next();
      }
      return node;
    }

    private Rope.Node next() {
      if (stack.isEmpty()) {
        throw new NoSuchElementException();
      }
      Rope.Node node = stack.pop();
      pushNode(node.right);
      pushNode(node.left);
      return node;
    }

    private void pushNode(Rope.Node node) {
      if (node != null) {
        stack.push(node);
      }
    }
  }
}
