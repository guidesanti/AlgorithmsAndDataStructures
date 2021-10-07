package br.com.eventhorizon.edx.ucsandiego.algs201x.pa4;

import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.*;

public class IsBinarySearchTreeTest2 extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa4/is-binary-search-tree2.csv";

  public IsBinarySearchTreeTest2() {
    super(new IsBinarySearchTree2());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @Override
  protected String generateInput(PATestType type) {
    StringBuilder input = new StringBuilder();
    int n;
    switch (type) {
      case TIME_LIMIT_TEST:
        n = Utils.getRandomInteger(1, 100000);
        break;
      case STRESS_TEST:
      default:
        n = Utils.getRandomInteger(1, 10000);
        break;
    }
    List<TreeNode> availableNodes = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      availableNodes.add(new TreeNode(i));
    }
    Queue<TreeNode> leaves = new LinkedList<>();
    List<TreeNode> nodes = new ArrayList<>(n);
    TreeNode root = availableNodes.remove(0);
    root.key = Utils.getRandomInteger(0, 1000000000);
    leaves.add(root);
    Collections.shuffle(availableNodes);
    while (!leaves.isEmpty()) {
      TreeNode node = leaves.remove();
      if (!availableNodes.isEmpty()) {
        node.leftChild = availableNodes.remove(0);
        node.leftChild.parent = node;
        int minKey;
        int maxKey = node.key;
        if (node.parent != null &&
            node.parent.rightChild == node) {
          minKey = node.parent.key;
        } else {
          minKey = 0;
        }
        node.leftChild.key = minKey == maxKey ? minKey : Utils.getRandomInteger(minKey, maxKey);
        leaves.add(node.leftChild);
      }
      if (!availableNodes.isEmpty()) {
        node.rightChild = availableNodes.remove(0);
        node.rightChild.parent = node;
        int minKey = node.key;
        int maxKey;
        if (node.parent == null ||
            node.parent.rightChild == node) {
          maxKey = 1000000000;
        } else {
          maxKey = node.parent.key;
        }
        node.rightChild.key = minKey == maxKey ? minKey : Utils.getRandomInteger(minKey, maxKey);
        leaves.add(node.rightChild);
      }
      nodes.add(node);
    }
    nodes.sort(Comparator.comparingInt(o -> o.index));
    input.append(n);
    while (!nodes.isEmpty()) {
      TreeNode node = nodes.remove(0);
      input.append(" ").append(node.key)
          .append(" ").append(node.leftChild != null ? node.leftChild.index : -1)
          .append(" ").append(node.rightChild != null ? node.rightChild.index : -1);
    }
    return input.toString();
  }

  private static class TreeNode {

    int index;

    int key;

    TreeNode parent;

    TreeNode leftChild;

    TreeNode rightChild;

    public TreeNode(int index) {
      this.index = index;
    }
  }
}
