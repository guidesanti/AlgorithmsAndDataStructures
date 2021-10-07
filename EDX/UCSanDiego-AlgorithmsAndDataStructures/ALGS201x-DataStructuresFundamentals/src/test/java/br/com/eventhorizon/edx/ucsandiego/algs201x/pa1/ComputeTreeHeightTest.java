package br.com.eventhorizon.edx.ucsandiego.algs201x.pa1;

import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import br.com.eventhorizon.common.pa.TestProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;
import java.util.*;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class ComputeTreeHeightTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/compute-tree-height.csv";

  public ComputeTreeHeightTest() {
    super(new ComputeTreeHeight());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput.replace("%", "\n"));
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput.replace("%", "\n"));
  }

  @Test
  public void testFinalSolutionWithWorstCase() {
    StringBuilder input = new StringBuilder();
    input.append("100000 ");
    for (int i = 0; i < 100000 - 1; i++) {
      input.append(i + 1).append(" ");
    }
    input.append(-1);
    super.testFinalSolution(input.toString(), "100000");
    System.setIn(new ByteArrayInputStream(input.toString().getBytes()));
    assertTimeoutPreemptively(ofMillis(TestProperties.getTimeLimit()), pa::finalSolution);
  }

  @Override
  protected String generateInput(PATestType type) {
    StringBuilder input = new StringBuilder();
    int n;

    if (type == PATestType.TIME_LIMIT_TEST) {
      n = Utils.getRandomInteger(1, 100000);
    } else {
      n = Utils.getRandomInteger(1, 100);
    }

    List<Node> availableNodes = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      availableNodes.add(new Node(i));
    }
    List<Node> nodes = new ArrayList<>();
    // Set root node
    int index;
    if (availableNodes.size() == 1) {
      index = 0;
    } else {
      index = Utils.getRandomInteger(0, availableNodes.size() - 1);
    }
    Node root = availableNodes.remove(index);
    root.parent = null;
    // Set all other nodes
    Queue<Node> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
      Node node = queue.remove();
      nodes.add(node);
      int numberOfChild = Utils.getRandomInteger(1, 5);
      for (int i = 0; i < numberOfChild; i++) {
        if (availableNodes.isEmpty()) {
          break;
        }
        if (availableNodes.size() == 1) {
          index = 0;
        } else {
          index = Utils.getRandomInteger(0, availableNodes.size() - 1);
        }
        Node child = availableNodes.remove(index);
        child.parent = node;
        node.addChild(child);
        queue.add(child);
      }
      if (availableNodes.isEmpty()) {
        if (!queue.isEmpty()) {
          nodes.addAll(queue);
        }
        break;
      }
    }
    nodes.sort(Comparator.comparing(Node::getIndex));
    input.append(nodes.size());
    for (int i = 0; i < nodes.size(); i++) {
      Node node = nodes.get(i);
      if (node.parent == null) {
        input.append(" -1");
      } else {
        input.append(" ").append(node.parent.index);
      }
    }
    return input.toString();
  }

  private static class Node {

    int index;

    Node parent;

    List<Node> children = new ArrayList<>();

    public Node(int index) {
      this.index = index;
    }

    public int getIndex() {
      return index;
    }

    void addChild(Node node) {
      children.add(node);
    }
  }
}
