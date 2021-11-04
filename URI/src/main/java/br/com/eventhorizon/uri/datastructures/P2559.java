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
    SegmentTree tree = readNumbers();
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
        LOGGER.info("Op 1: " + ((double) time[0] / count[0]) + " ms");
        LOGGER.info("Op 2: " + ((double) time[1] / count[1]) + " ms");
        LOGGER.info("Op 3: " + ((double) time[2] / count[2]) + " ms");
      }
      tree.clear();
      tree = readNumbers();
      resetTestCase();
    }
  }

  private SegmentTree readNumbers() {
    String str = scanner.next();
    if (str == null) {
      return null;
    }
    numberCount = Integer.parseInt(str);
    SegmentTree tree = new SegmentTree(numberCount);
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

    final AVLTree<Integer> sevens;

    final AVLTree<Integer> thirteens;

    final Queue<Integer> nodesToUpdate;

    final int firstLeaf;

    int leafCount;

    SegmentTree(int numberCount) {
      int treeSize = computeTreeSize(numberCount);
      nodes = new Node[treeSize];
//      sevens = new TreeSet<>();
      sevens = new AVLTree<>();
//      thirteens = new TreeSet<>();
      thirteens = new AVLTree<>();
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
      TreeTraverser<Integer> traverser;
      if (oldValue == 7) {
        traverser = new TreeTraverser<>(sevens.findGreaterThanOrEqual(firstLeaf + from - 1));
      } else if (oldValue == 13) {
        traverser = new TreeTraverser<>(thirteens.findGreaterThanOrEqual(firstLeaf + from - 1));
      } else {
        return;
      }
      List<Integer> indexesToAdd = new ArrayList<>();
      List<AvlNode<Integer>> nodesToRemove = new ArrayList<>();
      while (traverser.hasNext()) {
        AvlNode<Integer> node = traverser.next();
        int index = node.key;
        if ((index - firstLeaf) > (to - 1)) {
          break;
        }
        nodesToRemove.add(node);
        nodes[index].value(newValue);
        if (newValue == 7 || newValue == 13) {
          indexesToAdd.add(index);
        }
        int parent = parent(index);
        if (parent >= 0 && !nodes[parent].update()) {
          nodes[parent].update(true);
          nodesToUpdate.add(parent);
        }
      }
      for (int index : indexesToAdd) {
        if (newValue == 7) {
          sevens.add(index);
        } else {
          thirteens.add(index);
        }
      }
      for (AvlNode<Integer> node : nodesToRemove) {
        if (oldValue == 7) {
          sevens.remove(node);
        } else {
          thirteens.remove(node);
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

  private static class AvlNode<T> {

    protected final T key;

    protected int height;

    protected AvlNode<T> parent;

    protected AvlNode<T> left;

    protected AvlNode<T> right;

    public AvlNode(T key) {
      this.key = key;
      this.height = 1;
    }

    public AvlNode(T key, AvlNode<T> parent) {
      this.key = key;
      this.height = 1;
      this.parent = parent;
    }

    public int balanceFactor() {
      int leftHeight = left == null ? 0 : left.height;
      int rightHeight = right == null ? 0 : right.height;
      return rightHeight - leftHeight;
    }

    public boolean hasParent() {
      return parent != null;
    }

    public boolean hasChildren() {
      return left != null || right != null;
    }

    public boolean isLeaf() {
      return left == null && right == null;
    }

    @Override
    public String toString() {
      return "" + key;
    }
  }

  private static class BinarySearchTree<T> {

    protected AvlNode<T> root;

    protected int size;

    public BinarySearchTree() {
      this.root = null;
      this.size = 0;
    }

    public AvlNode<T> minimum(AvlNode<T> node) {
      if (node == null) {
        return null;
      }
      while (node.left != null) {
        node = node.left;
      }
      return node;
    }

    public AvlNode<T> maximum(AvlNode<T> node) {
      if (node == null) {
        return null;
      }
      while (node.right != null) {
        node = node.right;
      }
      return node;
    }

    public AvlNode<T> predecessor(AvlNode<T> node) {
      if (node.left != null) {
        return maximum(node.left);
      }
      AvlNode<T> parent = node.parent;
      while (parent != null && node == parent.left) {
        node = parent;
        parent = parent.parent;
      }
      return parent;
    }

    public AvlNode<T> successor(AvlNode<T> node) {
      if (node.right != null) {
        return minimum(node.right);
      }
      AvlNode<T> parent = node.parent;
      while (parent != null && node == parent.right) {
        node = parent;
        parent = parent.parent;
      }
      return parent;
    }

    @SuppressWarnings("unchecked")
    public AvlNode<T> add(T key) {
      AvlNode<T> parent = null;
      AvlNode<T> current = root;
      while (current != null) {
        parent = current;
        if (key.equals(current.key)) {
          throw new RuntimeException("Duplicate key");
        }
        if (((Comparable<? super T>)key).compareTo(current.key) < 0) {
          current = current.left;
        } else {
          current = current.right;
        }
      }
      AvlNode<T> addedNode;
      if (parent == null) {
        addedNode = new AvlNode<>(key);
        root = addedNode;
      } else if(((Comparable<? super T>)key).compareTo(parent.key) < 0) {
        addedNode = new AvlNode<>(key, parent);
        parent.left = addedNode;
      } else {
        addedNode = new AvlNode<>(key, parent);
        parent.right = addedNode;
      }
      updateHeight(parent);
      size++;
      return addedNode;
    }

    public AvlNode<T> remove(T key) {
      AvlNode<T> removedNode = find(key);
      if (removedNode == null) {
        throw new NoSuchElementException();
      }
      AvlNode<T> aux = removedNode.parent;
      if (removedNode.left == null) {
        transplant(removedNode.right, removedNode);
      } else if (removedNode.right == null) {
        transplant(removedNode.left, removedNode);
      } else {
        AvlNode<T> successor = successor(removedNode);
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
      size--;
      return removedNode;
    }

    public void remove(AvlNode<T> removedNode) {
      if (removedNode == null) {
        throw new NoSuchElementException();
      }
      AvlNode<T> aux = removedNode.parent;
      if (removedNode.left == null) {
        transplant(removedNode.right, removedNode);
      } else if (removedNode.right == null) {
        transplant(removedNode.left, removedNode);
      } else {
        AvlNode<T> successor = successor(removedNode);
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
      size--;
    }

    public void clear() {
      root = null;
      size = 0;
    }

    public boolean isEmpty() {
      return size == 0;
    }

    public int size() {
      return size;
    }

    @SuppressWarnings("unchecked")
    protected AvlNode<T> find(T key) {
      AvlNode<T> node = root;
      while (node != null) {
        if (key.equals(node.key)) {
          return node;
        }
        if (((Comparable<? super T>)key).compareTo(node.key) < 0) {
          node = node.left;
        } else {
          node = node.right;
        }
      }
      return null;
    }

    @SuppressWarnings("unchecked")
    protected AvlNode<T> findGreaterThanOrEqual(T key) {
      AvlNode<T> node = root;
      while (node != null) {
        if (key.equals(node.key)) {
          return node;
        }
        if (((Comparable<? super T>)key).compareTo(node.key) < 0) {
          if (node.left != null && ((Comparable<? super T>)key).compareTo(node.left.key) <= 0) {
            node = node.left;
          } else {
            return node;
          }
        } else {
          if (node.right != null) {
            node = node.right;
          } else {
            return null;
          }
        }
      }
      return null;
    }

    protected void updateHeight(AvlNode<T> node) {
      while (node != null) {
        node.height = Math.max(node.left == null ? 0 : node.left.height, node.right == null ? 0 : node.right.height) + 1;
        node = node.parent;
      }
    }

    protected void transplant(AvlNode<T> source, AvlNode<T> target) {
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

    protected void rotateLeft(AvlNode<T> node) {
      AvlNode<T> parent = node.parent;
      AvlNode<T> right = node.right;
      if (right == null) {
        return;
      }
      AvlNode<T> t2 = right.left;
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
      updateHeight(node);
    }

    protected void rotateRight(AvlNode<T> node) {
      AvlNode<T> parent = node.parent;
      AvlNode<T> left = node.left;
      if (left == null) {
        return;
      }
      AvlNode<T> t2 = left.right;
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
      updateHeight(node);
    }
  }

  private static class AVLTree<T> extends BinarySearchTree<T> {

    public AVLTree() {
      super();
    }

    @Override
    public AvlNode<T> add(T key) {
      AvlNode<T> addedNode = super.add(key);
      retrace(addedNode.parent, false);
      return addedNode;
    }

    @Override
    public AvlNode<T> remove(T key) {
      AvlNode<T> removedNode = find(key);
      if (removedNode == null) {
        throw new NoSuchElementException();
      }
      AvlNode<T> aux = removedNode.parent;
      if (removedNode.left == null) {
        transplant(removedNode.right, removedNode);
      } else if (removedNode.right == null) {
        transplant(removedNode.left, removedNode);
      } else {
        AvlNode<T> successor = successor(removedNode);
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

    private void retrace(AvlNode<T> node, boolean continuous) {
      while (node != null) {
        if (balance(node)) {
          if (!continuous) {
            break;
          }
        }
        node = node.parent;
      }
    }

    private boolean balance(AvlNode<T> node) {
      if (node == null) {
        return false;
      }
      if (node.balanceFactor() == 2) {
        AvlNode<T> right = node.right;
        if (right.balanceFactor() == 0 || right.balanceFactor() == 1) {
          rotateLeft(node);
        } else {
          rotateRightLeft(node);
        }
        return true;
      }
      if (node.balanceFactor() == -2) {
        AvlNode<T> left = node.left;
        if (left.balanceFactor() == 0 || left.balanceFactor() == -1) {
          rotateRight(node);
        } else {
          rotateLeftRight(node);
        }
        return true;
      }
      return false;
    }

    private void rotateLeftRight(AvlNode<T> node) {
      super.rotateLeft(node.left);
      super.rotateRight(node);
    }

    private void rotateRightLeft(AvlNode<T> node) {
      super.rotateRight(node.right);
      super.rotateLeft(node);
    }
  }

  private static class TreeTraverser<T extends Comparable<T>> {

    private final Stack<AvlNode<T>> stack;

    private AvlNode<T> lastVisited;

    public TreeTraverser(AvlNode<T> startNode) {
      this.stack = new Stack<>();
      if (startNode == null) {
        return;
      }
      this.lastVisited = startNode;
      Stack<AvlNode<T>> tempStack = new Stack<>();
      tempStack.push(startNode);
      AvlNode<T> child = startNode;
      AvlNode<T> parent = startNode.parent;
      while (parent != null) {
        if (child == parent.left) {
          tempStack.push(parent);
        }
        child = parent;
        parent = child.parent;
      }
      while (!tempStack.empty()) {
        this.stack.push(tempStack.pop());
      }
    }

    private AvlNode<T> depthFirstInOrder() {
      if (stack.isEmpty()) {
        throw new NoSuchElementException();
      }
      AvlNode<T> node = stack.pop();
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

    private void pushNode(AvlNode<T> node) {
      if (node != null) {
        stack.push(node);
      }
    }

    public boolean hasNext() {
      return !stack.isEmpty();
    }

    public AvlNode<T> next() {
      return depthFirstInOrder();
    }
  }
}
