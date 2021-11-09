package br.com.eventhorizon.uri.datastructures;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.v2.PA;

import java.util.*;
import java.util.logging.Logger;

public class P2559 implements PA {

  private static final Logger LOGGER = Logger.getLogger(P2559.class.getName());

  private FastScanner scanner;

  private int numberCount;

  private int[] numbers;

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
    numbers = new int[numberCount];
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

  @Override
  public void finalSolution() {
    reset();
    resetTestCase();
    SegmentTree2 tree = readNumbers();
    long[] time = new long[3];
    long[] count = new long[3];
    while (tree != null) {
      operationCount = scanner.nextInt();
      for (int i = 0; i < operationCount; i++) {
        int opCode = scanner.nextInt();
        long ini = System.currentTimeMillis();
        if (opCode == 1) {
          tree.replace(scanner.nextInt(), scanner.nextInt());
        } else if (opCode == 2) {
          tree.replace(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
        } else {
          System.out.println(tree.sum(scanner.nextInt(), scanner.nextInt()));
        }
        time[opCode - 1] += (System.currentTimeMillis() - ini);
        count[opCode - 1] += 1;
      }
      LOGGER.info("Op 1: " + ((double) time[0] / count[0]) + " ms");
      LOGGER.info("Op 2: " + ((double) time[1] / count[1]) + " ms");
      LOGGER.info("Op 3: " + ((double) time[2] / count[2]) + " ms");
      tree.clear();
      tree = readNumbers();
      resetTestCase();
    }
  }

  private SegmentTree2 readNumbers() {
    String str = scanner.next();
    if (str == null) {
      return null;
    }
    numberCount = Integer.parseInt(str);
    SegmentTree2 tree = new SegmentTree2(numberCount);
    for (int i = 0; i < numberCount; i++) {
      tree.add(scanner.nextInt());
    }
    tree.fillRemainingNodes();
    return tree;
  }

  public void resetTestCase() {
    numberCount = 0;
    numbers = null;
    operationCount = 0;
    operations = new ArrayList<>();
  }

  private interface Node {

    int from();

    int to();

    int value();

    void value(int value);

    boolean update();

    void update(boolean update);
  }

  private static class InternalNode implements Node {

    final int from;

    final int to;

    int sum;
    
    boolean update;

    InternalNode(int from, int to, int sum) {
      this.from = from;
      this.to = to;
      this.sum = sum;
    }

    @Override
    public int from() {
      return from;
    }

    @Override
    public int to() {
      return to;
    }

    @Override
    public int value() {
      return sum;
    }

    @Override
    public void value(int value) {
      this.sum = value;
    }

    @Override
    public boolean update() {
      return update;
    }

    @Override
    public void update(boolean update) {
      this.update = update;
    }

    @Override
    public String toString() {
      return "(" + from + "-" + to + ") -> " + sum;
    }
  }

  private static class LeafNode implements Node {

    final int number;

    int value;

    public LeafNode(int number, int value) {
      this.number = number;
      this.value = value;
    }

    @Override
    public int from() {
      return number;
    }

    @Override
    public int to() {
      return number;
    }

    @Override
    public int value() {
      return value;
    }

    @Override
    public void value(int value) {
      this.value = value;
    }

    @Override
    public boolean update() {
      return false;
    }

    @Override
    public void update(boolean update) {
    }

    @Override
    public String toString() {
      return number + " -> " + value;
    }
  }

  private static class SegmentTree {

    final Node[] nodes;

    final SplayTree<Integer> sevens;

    final SplayTree<Integer> thirteens;

    final Queue<Integer> nodesToUpdate;

    final int firstLeaf;

    int leafCount;

    SegmentTree(int numberCount) {
      int treeSize = computeTreeSize(numberCount);
      nodes = new Node[treeSize];
      sevens = new SplayTree<>();
      thirteens = new SplayTree<>();
      nodesToUpdate = new LinkedList<>();
      firstLeaf = treeSize / 2;
      leafCount = 0;
    }

    void add(int value) {
      int index = firstLeaf + leafCount;
      nodes[index] = new LeafNode(leafCount + 1, value);
      if (value == 7) {
        sevens.add(index);
      } else if (value == 13) {
        thirteens.add(index);
      }
      leafCount++;
    }

    void fillRemainingNodes() {
      // Fill remaining leaves
      int index = firstLeaf + leafCount;
      while (index < nodes.length) {
        nodes[index++] = new LeafNode(leafCount + 1, 0);
        leafCount++;
      }
      // Fill internal nodes
      for (int i = firstLeaf - 1; i >= 0; i--) {
        Node left = nodes[leftChild(i)];
        Node right = nodes[rightChild(i)];
        nodes[i] = new InternalNode(left.from(), right.to(), left.value() + right.value());
      }
    }

    int computeTreeSize(int n) {
      int leafCount = 1;
      while (n > leafCount) {
        leafCount <<= 1;
      }
      return (2 * leafCount) - 1;
    }

    void replace(int number, int value) {
      int nodeIndex = (nodes.length / 2) + number - 1;
      if (value == nodes[nodeIndex].value()) {
        return;
      }
      if (nodes[nodeIndex].value() == 7) {
        sevens.remove(nodeIndex);
      } else if (nodes[nodeIndex].value() == 13) {
        thirteens.remove(nodeIndex);
      }
      if (value == 7) {
        sevens.add(nodeIndex);
      } else if (value == 13) {
        thirteens.add(nodeIndex);
      }
      nodes[nodeIndex].value(value);
      int parent = parent(nodeIndex);
      if (parent >= 0 && !nodes[parent].update()) {
        nodes[parent].update(true);
        nodesToUpdate.add(parent);
      }
    }

    void replace(int from, int to, int oldValue, int newValue) {
      if (oldValue == newValue) {
        return;
      }
      if (oldValue == 7) {
        SplayTree<Integer> higherTree1 = sevens.split(firstLeaf + from - 2);
        SplayTree<Integer> higherTree2 = higherTree1.split(firstLeaf + to - 1);
        InOrderSplayTreeTraverser<Integer> traverser = new InOrderSplayTreeTraverser<>(higherTree1.root);
        while (traverser.hasNext()) {
          int index = traverser.next().key;
          nodes[index].value(newValue);
          int parent = parent(index);
          if (parent >= 0 && !nodes[parent].update()) {
            nodes[parent].update(true);
            nodesToUpdate.add(parent);
          }
        }
        sevens.join(higherTree2);
        if (newValue == 13) {
          SplayTreeNode<Integer> max13 = thirteens.maximum();
          SplayTreeNode<Integer> min7 = higherTree1.minimum();
          while (max13 != null && min7 != null && max13.key.compareTo(min7.key) > 0) {
            SplayTreeNode<Integer> next = SplayTree.minimum(min7.right);
            higherTree1.remove(min7);
            min7.parent = null;
            min7.right = null;
            thirteens.add(min7);
            min7 = next;
          }
          thirteens.join(higherTree1);
        }
      } else if (oldValue == 13) {
        SplayTree<Integer> higherTree1 = thirteens.split(firstLeaf + from - 2);
        SplayTree<Integer> higherTree2 = higherTree1.split(firstLeaf + to - 1);
        InOrderSplayTreeTraverser<Integer> traverser = new InOrderSplayTreeTraverser<>(higherTree1.root);
        while (traverser.hasNext()) {
          int index = traverser.next().key;
          nodes[index].value(newValue);
          int parent = parent(index);
          if (parent >= 0 && !nodes[parent].update()) {
            nodes[parent].update(true);
            nodesToUpdate.add(parent);
          }
        }
        thirteens.join(higherTree2);
        if (newValue == 7) {
          SplayTreeNode<Integer> max7 = sevens.maximum();
          SplayTreeNode<Integer> min13 = higherTree1.minimum();
          while (max7 != null && min13 != null && max7.key.compareTo(min13.key) > 0) {
            SplayTreeNode<Integer> next = SplayTree.minimum(min13.right);
            higherTree1.remove(min13);
            min13.parent = null;
            min13.right = null;
            sevens.add(min13);
            min13 = next;
          }
          sevens.join(higherTree1);
        }
      } else {
        throw new RuntimeException("Invalid replace operation, oldValue should be either 7 or 13, but received " + oldValue);
      }
    }

    void update() {
      while (!nodesToUpdate.isEmpty()) {
        int nodeIndex = nodesToUpdate.remove();
        nodes[nodeIndex].value(nodes[leftChild(nodeIndex)].value() + nodes[rightChild(nodeIndex)].value());
        nodes[nodeIndex].update(false);
        int parent = parent(nodeIndex);
        if (parent >= 0 && !nodes[parent].update()) {
          nodes[parent].update(true);
          nodesToUpdate.add(parent);
        }
      }
    }

    void clear() {
      Arrays.fill(nodes, null);
      sevens.clear();
      thirteens.clear();
    }

    int sum(int from, int to) {
      update();
      int sum = 0;
      int left = (nodes.length / 2) + from - 1;
      int right = (nodes.length / 2) + to - 1;
      Node leftNode = nodes[left];
      Node rightNode = nodes[right];
      int leftSum = leftNode.value();
      int rightSum = rightNode.value();
      while (true) {
        boolean stop = true;
        int leftParent = parent(left);
        int rightParent = parent(right);
        Node leftParentNode = leftParent >= 0 ? nodes[leftParent] : null;
        Node rightParentNode = rightParent >= 0 ? nodes[parent(right)] : null;
        if (leftParentNode != null && leftParentNode.to() < rightNode.from()) {
          if (left == leftChild(leftParent)) {
            leftSum += nodes[rightChild(leftParent)].value();
          }
          left = parent(left);
          stop = false;
        }
        if (rightParentNode != null && rightParentNode.from() > leftNode.to()) {
          if (right == rightChild(rightParent)) {
            rightSum += nodes[leftChild(rightParent)].value();
          }
          right = parent(right);
          stop = false;
        }
        if (stop) {
          sum = leftNode == rightNode ? leftSum : leftSum + rightSum;
          break;
        }
        leftNode = nodes[left];
        rightNode = nodes[right];
      }
      return sum;
    }

    int parent(int i) {
      return (i - 1) >> 1;
    }

    int leftChild(int i) {
      return (i << 1) + 1;
    }

    int rightChild(int i) {
      return (i << 1) + 2;
    }
  }

  private static class SegmentTree2 {

    final Node[] nodes;

    final RangeTree sevens;

    final RangeTree thirteens;

    final Queue<Integer> nodesToUpdate;

    final int firstLeaf;

    int leafCount;

    SegmentTree2(int numberCount) {
      int treeSize = computeTreeSize(numberCount);
      nodes = new Node[treeSize];
      sevens = new RangeTree();
      thirteens = new RangeTree();
      nodesToUpdate = new LinkedList<>();
      firstLeaf = treeSize / 2;
      leafCount = 0;
    }

    void add(int value) {
      int index = firstLeaf + leafCount;
      int number = leafCount + 1;
      nodes[index] = new LeafNode(number, value);
      if (value == 7) {
        sevens.add(number);
      } else if (value == 13) {
        thirteens.add(number);
      }
      leafCount++;
    }

    void fillRemainingNodes() {
      // Fill remaining leaves
      int index = firstLeaf + leafCount;
      while (index < nodes.length) {
        nodes[index++] = new LeafNode(leafCount + 1, 0);
        leafCount++;
      }
      // Fill internal nodes
      for (int i = firstLeaf - 1; i >= 0; i--) {
        Node left = nodes[leftChild(i)];
        Node right = nodes[rightChild(i)];
        nodes[i] = new InternalNode(left.from(), right.to(), left.value() + right.value());
      }
    }

    int computeTreeSize(int n) {
      int leafCount = 1;
      while (n > leafCount) {
        leafCount <<= 1;
      }
      return (2 * leafCount) - 1;
    }

    void replace(int number, int value) {
      int nodeIndex = (nodes.length / 2) + number - 1;
      if (value == nodes[nodeIndex].value()) {
        return;
      }
      if (nodes[nodeIndex].value() == 7) {
        sevens.remove(number, number);
      } else if (nodes[nodeIndex].value() == 13) {
        thirteens.remove(number, number);
      }
      if (value == 7) {
        sevens.add(number);
      } else if (value == 13) {
        thirteens.add(number);
      }
      nodes[nodeIndex].value(value);
      int parent = parent(nodeIndex);
      if (parent >= 0 && !nodes[parent].update()) {
        nodes[parent].update(true);
        nodesToUpdate.add(parent);
      }
    }

    void replace(int from, int to, int oldValue, int newValue) {
      if (oldValue == newValue) {
        return;
      }
      List<Range> ranges;
      if (oldValue == 7) {
        ranges = sevens.remove(from, to);
        if (newValue == 13) {
          for (Range range : ranges) {
            thirteens.add(range.from, range.to);
          }
        }
      } else if (oldValue == 13) {
        ranges = thirteens.remove(from, to);
        if (newValue == 7) {
          for (Range range : ranges) {
            sevens.add(range.from, range.to);
          }
        }
      } else {
        throw new RuntimeException("Invalid replace operation, oldValue should be either 7 or 13, but received " + oldValue);
      }
      for (Range range : ranges) {
        for (int i = range.from; i <= range.to; i++) {
          int nodeIndex = firstLeaf + i - 1;
          nodes[nodeIndex].value(newValue);
          int parent = parent(nodeIndex);
          if (parent >= 0 && !nodes[parent].update()) {
            nodes[parent].update(true);
            nodesToUpdate.add(parent);
          }
        }
      }
    }

    void update() {
      while (!nodesToUpdate.isEmpty()) {
        int nodeIndex = nodesToUpdate.remove();
        nodes[nodeIndex].value(nodes[leftChild(nodeIndex)].value() + nodes[rightChild(nodeIndex)].value());
        nodes[nodeIndex].update(false);
        int parent = parent(nodeIndex);
        if (parent >= 0 && !nodes[parent].update()) {
          nodes[parent].update(true);
          nodesToUpdate.add(parent);
        }
      }
    }

    void clear() {
      Arrays.fill(nodes, null);
    }

    int sum(int from, int to) {
      update();
      int sum = 0;
      int left = (nodes.length / 2) + from - 1;
      int right = (nodes.length / 2) + to - 1;
      Node leftNode = nodes[left];
      Node rightNode = nodes[right];
      int leftSum = leftNode.value();
      int rightSum = rightNode.value();
      while (true) {
        boolean stop = true;
        int leftParent = parent(left);
        int rightParent = parent(right);
        Node leftParentNode = leftParent >= 0 ? nodes[leftParent] : null;
        Node rightParentNode = rightParent >= 0 ? nodes[parent(right)] : null;
        if (leftParentNode != null && leftParentNode.to() < rightNode.from()) {
          if (left == leftChild(leftParent)) {
            leftSum += nodes[rightChild(leftParent)].value();
          }
          left = parent(left);
          stop = false;
        }
        if (rightParentNode != null && rightParentNode.from() > leftNode.to()) {
          if (right == rightChild(rightParent)) {
            rightSum += nodes[leftChild(rightParent)].value();
          }
          right = parent(right);
          stop = false;
        }
        if (stop) {
          sum = leftNode == rightNode ? leftSum : leftSum + rightSum;
          break;
        }
        leftNode = nodes[left];
        rightNode = nodes[right];
      }
      return sum;
    }

    int parent(int i) {
      return (i - 1) >> 1;
    }

    int leftChild(int i) {
      return (i << 1) + 1;
    }

    int rightChild(int i) {
      return (i << 1) + 2;
    }
  }

  private static class RangeTree {

    Range root;

    RangeTree() { }

    public static Range minimum(Range range) {
      if (range == null) {
        return null;
      }
      while (range.left != null) {
        range = range.left;
      }
      return range;
    }

    public static Range maximum(Range range) {
      if (range == null) {
        return null;
      }
      while (range.right != null) {
        range = range.right;
      }
      return range;
    }

    public static Range predecessor(Range range) {
      if (range.left != null) {
        return maximum(range.left);
      }
      Range parent = range.parent;
      while (parent != null && range == parent.left) {
        range = parent;
        parent = parent.parent;
      }
      return parent;
    }

    public static Range successor(Range range) {
      if (range.right != null) {
        return minimum(range.right);
      }
      Range parent = range.parent;
      while (parent != null && range == parent.right) {
        range = parent;
        parent = parent.parent;
      }
      return parent;
    }

    void add(int key) {
      if (root == null) {
        root = new Range(key, key);
      } else {
        Range range = root;
        while (true) {
          if (key >= range.from && key <= range.to) {
            break;
          }
          if (key == range.from - 1) {
            Range predecessor = predecessor(range);
            if (predecessor != null && key == predecessor.to + 1) {
              predecessor.to = range.to;
              remove(range);
            } else {
              range.from--;
            }
            break;
          }
          if (key == range.to + 1) {
            Range successor = successor(range);
            if (successor != null && key == successor.from - 1) {
              successor.from = range.from;
              remove(range);
            } else {
              range.to++;
            }
            break;
          }
          if (key < range.from) {
            if (range.left == null) {
              range.left = new Range(key, key, range);
              break;
            }
            range = range.left;
          } else {
            if (range.right == null) {
              range.right = new Range(key, key, range);
              break;
            }
            range = range.right;
          }
        }
      }
    }

    void add(int from, int to) {
      if (root == null) {
        root = new Range(from, to);
      } else {
        Range range = root;
        while (true) {
          if (to < range.from - 1) {
            if (range.left == null) {
              range.left = new Range(from, to, range);
              break;
            } else {
              range = range.left;
              continue;
            }
          }
          if (from > range.to + 1) {
            if (range.right == null) {
              range.right = new Range(from, to, range);
              break;
            } else {
              range = range.right;
              continue;
            }
          }
          if (from < range.from) {
            range.from = from;
            Range predecessor = predecessor(range);
            while (predecessor != null && predecessor.to >= range.from - 1) {
              Range next = predecessor(predecessor);
              if (range.from < from) {
                range.from = predecessor.from;
              }
              remove(predecessor);
              predecessor = next;
            }
          }
          if (to > range.to) {
            range.to = to;
            Range successor = successor(range);
            while (successor != null && successor.from <= range.to + 1) {
              Range next = successor(successor);
              if (range.to > to) {
                range.to = successor.to;
              }
              remove(successor);
              successor = next;
            }
          }
          break;
        }
      }
    }

    List<Range> remove(int from, int to) {
      if (root == null) {
        return Collections.emptyList();
      }
      List<Range> ranges = new ArrayList<>();
      List<Range> rangesToRemove = new ArrayList<>();
      List<Range> rangesToAdd = new ArrayList<>();
      Queue<Range> queue = new LinkedList<>();
      queue.add(root);
      while (!queue.isEmpty()) {
        Range range = queue.remove();
        if (range.left != null && range.left.to >= from) {
          queue.add(range.left);
        }
        if (range.right != null && range.right.from <= to) {
          queue.add(range.right);
        }
        if (from <= range.from && to >= range.to) {
          ranges.add(range);
          rangesToRemove.add(range);
        } else if (from <= range.from && to >= range.from) {
          ranges.add(new Range(range.from, to));
          range.to = to + 1;
        } else if (from <= range.to && to >= range.to) {
          ranges.add(new Range(from, range.to));
          range.from = from - 1;
        } else if (from > range.from && to < range.to) {
          ranges.add(new Range(from, to));
          rangesToAdd.add(new Range(to + 1, range.to));
          range.to = from - 1;
        }
      }
      for (Range removingRange : rangesToRemove) {
        remove(removingRange);
      }
      for (Range addingRange : rangesToAdd) {
        add(addingRange.from, addingRange.to);
      }
      return ranges;
    }

    void remove(Range range) {
      if (range.left == null) {
        transplant(range.right, range);
      } else if (range.right == null) {
        transplant(range.left, range);
      } else {
        Range successor = successor(range);
        if (successor != range.right) {
          transplant(successor.right, successor);
          successor.right = range.right;
          successor.right.parent = successor;
        }
        transplant(successor, range);
        successor.left = range.left;
        successor.left.parent = successor;
      }
    }

    void transplant(Range source, Range target) {
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
  }

  private static class Range {

    int from;

    int to;

    Range parent;

    Range left;

    Range right;

    public Range(int from, int to) {
      this.from = from;
      this.to = to;
    }

    public Range(int from, int to, Range parent) {
      this.from = from;
      this.to = to;
      this.parent = parent;
    }

    @Override
    public String toString() {
      return from + " -> " + to;
    }
  }

  private static class SplayTreeNode<T> {

    protected final T key;

    protected SplayTreeNode<T> parent;

    protected SplayTreeNode<T> left;

    protected SplayTreeNode<T> right;

    public SplayTreeNode(T key) {
      this.key = key;
    }

    @Override
    public String toString() {
      return "" + key;
    }
  }

  public static class SplayTree<T> {

    SplayTreeNode<T> root;

    SplayTree() { }

    SplayTree(SplayTreeNode<T> root) {
      this.root = root;
      if (this.root != null) {
        this.root.parent = null;
      }
    }

    SplayTreeNode<T> add(T key) {
      SplayTreeNode<T> newNode = new SplayTreeNode<>(key);
      add(newNode);
      return newNode;
    }

    void add(SplayTreeNode<T> node) {
      SplayTree<T> higherTree = split(node.key);
      node.right = higherTree.root;
      if (higherTree.root != null) {
        higherTree.root.parent = node;
      }
      higherTree.root = node;
      join(higherTree);
    }

    SplayTreeNode<T> remove(T key) {
      SplayTreeNode<T> removedNode = find(key);
      if (removedNode == null) {
        return null;
      }
      remove(removedNode);
      return removedNode;
    }

    void remove(SplayTreeNode<T> node) {
      if (node == null) {
        return;
      }
      root = node.left;
      if (node.left != null) {
        node.left.parent = null;
      }
      join(new SplayTree<>(node.right));
    }

    @SuppressWarnings("unchecked")
    SplayTreeNode<T> find(T key) {
      SplayTreeNode<T> last = root;
      SplayTreeNode<T> node = root;
      while (node != null) {
        last = node;
        if (((Comparable<? super T>)key).compareTo(node.key) == 0) {
          break;
        }
        if (((Comparable<? super T>)key).compareTo(node.key) < 0) {
          node = node.left;
        } else {
          node = node.right;
        }
      }
      splay(last);
      return node;
    }

    void clear() {
      root = null;
    }

    boolean contains(T key) {
      return find(key) != null;
    }

    SplayTreeNode<T> minimum() {
      SplayTreeNode<T> node = this.root;
      if (node == null) {
        return null;
      }
      while (node.left != null) {
        node = node.left;
      }
      return node;
    }

    SplayTreeNode<T> maximum() {
      SplayTreeNode<T> node = this.root;
      if (node == null) {
        return null;
      }
      while (node.right != null) {
        node = node.right;
      }
      return node;
    }

    static <T> SplayTreeNode<T> minimum(SplayTreeNode<T> node) {
      if (node == null) {
        return null;
      }
      while (node.left != null) {
        node = node.left;
      }
      return node;
    }

    static <T> SplayTreeNode<T> maximum(SplayTreeNode<T> node) {
      if (node == null) {
        return null;
      }
      while (node.right != null) {
        node = node.right;
      }
      return node;
    }

    boolean isEmpty() {
      return root == null;
    }

    void rotateLeft(SplayTreeNode<T> node) {
      SplayTreeNode<T> parent = node.parent;
      SplayTreeNode<T> right = node.right;
      if (right == null) {
        return;
      }
      SplayTreeNode<T> t2 = right.left;
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

    void rotateRight(SplayTreeNode<T> node) {
      SplayTreeNode<T> parent = node.parent;
      SplayTreeNode<T> left = node.left;
      if (left == null) {
        return;
      }
      SplayTreeNode<T> t2 = left.right;
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

    void splay(SplayTreeNode<T> node) {
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

    @SuppressWarnings("unchecked")
    void join(SplayTree<T> tree) {
      if (this.root == null) {
        this.root = tree.root;
      } else {
        if (tree.root != null && ((Comparable<? super T>)this.root.key).compareTo(tree.root.key) > 0) {
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

    @SuppressWarnings("unchecked")
    SplayTree<T> split(T key) {
      SplayTreeNode<T> splitNode = null;
      SplayTreeNode<T> last = null;
      SplayTreeNode<T> node = root;
      while (node != null) {
        last = node;
        if (((Comparable<? super T>)key).compareTo(node.key) >= 0) {
          splitNode = node;
        }
        if (key.equals(node.key)) {
          break;
        }
        if (((Comparable<? super T>)key).compareTo(node.key) < 0) {
          node = node.left;
        } else {
          node = node.right;
        }
      }
      splay(last);
      return split(splitNode);
    }

    SplayTree<T> split(SplayTreeNode<T> node) {
      SplayTree<T> higherTree;
      if (root == null) {
        higherTree = new SplayTree<>();
      } else {
        if (node == null) {
          higherTree = new SplayTree<>(root);
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

  private static class InOrderSplayTreeTraverser<T extends Comparable<T>> {

    private final Stack<SplayTreeNode<T>> stack;

    private SplayTreeNode<T> lastVisited;

    public InOrderSplayTreeTraverser(SplayTreeNode<T> root) {
      this.stack = new Stack<>();
      if (root != null) {
        this.stack.push(root);
      }
      this.lastVisited = null;
    }

    private SplayTreeNode<T> depthFirstInOrder() {
      if (stack.isEmpty()) {
        throw new NoSuchElementException();
      }
      SplayTreeNode<T> node = stack.pop();
      if (lastVisited == null
          || (node.left != null && lastVisited != node.left && lastVisited.key.compareTo(node.left.key) < 0)) {
        while (node.left != null) {
          pushNode(node);
          if (node.left != null) {
            node = node.left;
          }
        }
      }
      if (node.right != null) {
        pushNode(node.right);
      }
      lastVisited = node;
      return node;
    }

    private void pushNode(SplayTreeNode<T> node) {
      if (node != null) {
        stack.push(node);
      }
    }

    public boolean hasNext() {
      return !stack.isEmpty();
    }

    public SplayTreeNode<T> next() {
      return depthFirstInOrder();
    }
  }
}
