package br.com.eventhorizon.edx.ucsandiego.algs206x.pa1;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class MaximalNonBranchingPathsInGraphTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/maximal-non-branching-paths-in-graph.csv";

  public MaximalNonBranchingPathsInGraphTest() {
    super(new MaximalNonBranchingPathsInGraph(), true, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    input = input.replace("%", "\n").replace(";", ",");
    expectedOutput = expectedOutput.replace("%", "\n").replace(";", ",");
    super.testNaiveSolution(input, expectedOutput);
  }

  @Override
  protected String generateInput(PATestType type) {
    Map<String, List<String>> adjacencies = new HashMap<>();
    Integer[] verticesAux = Utils.getRandomIntegerArray(10, 30, 0, 100);
    List<Integer> vertices = Arrays.stream(verticesAux).distinct().collect(Collectors.toList());
    for (int i = 0; i < vertices.size(); i++) {
      String from = "" + vertices.get(i);
      String to = "" + vertices.get((i + 1) % vertices.size());
      List<String> fromAdjacencies = new ArrayList<>();
      fromAdjacencies.add(to);
      adjacencies.put(from, fromAdjacencies);
    }
    for (int i = 0; i < 5; i++) {
      Integer start = vertices.get(Utils.getRandomInteger(0, vertices.size() - 1));
      Integer from = start;
      Integer to = vertices.get(Utils.getRandomInteger(0, vertices.size() - 1));
      while (!to.equals(start)) {
        String fromStr = "" + from;
        String toStr = "" + to;
        List<String> fromAdjacencies = adjacencies.get(fromStr);
        fromAdjacencies.add(toStr);
        adjacencies.put(fromStr, fromAdjacencies);
        from = to;
        to = vertices.get(Utils.getRandomInteger(0, vertices.size() - 1));
      }
      String fromStr = "" + from;
      String toStr = "" + to;
      List<String> fromAdjacencies = adjacencies.get(fromStr);
      fromAdjacencies.add(toStr);
      adjacencies.put(fromStr, fromAdjacencies);
    }
    StringBuilder str = new StringBuilder();
    adjacencies.forEach((from, toList) -> {
      str.append(from).append(" -> ").append(toList.get(0));
      for (int i = 1; i < toList.size(); i++) {
        str.append(",").append(toList.get(i));
      }
      str.append("\n");
    });
    return str.toString();
  }

  @Override
  protected void verify(String input, String expectedOutput, String actualOutput) {
    // Process expected output
    List<List<String>> expectedPaths = new ArrayList<>();
    for (String pathStr : expectedOutput.split("\n")) {
      expectedPaths.add(new ArrayList<>(Arrays.asList(pathStr.split("->"))));
    }

    // Process actual output
    List<List<String>> actualPaths = new ArrayList<>();
    for (String pathStr : actualOutput.split("\n")) {
      actualPaths.add(new ArrayList<>(Arrays.asList(pathStr.split("->"))));
    }

    assertEquals(expectedPaths.size(), actualPaths.size());
    for (List<String> actualPath : actualPaths) {
      if (actualPath.get(0).equals(actualPath.get(actualPath.size() - 1))) {
        boolean found = false;
        for (int i = 0; i < actualPath.size(); i++) {
          if (expectedPaths.contains(actualPath)) {
            found = true;
            break;
          }
          actualPath.remove(0);
          actualPath.add(actualPath.get(0));
        }
        assertTrue(found);
      } else {
        assert(expectedPaths.contains(actualPath));
      }
    }
  }
}
