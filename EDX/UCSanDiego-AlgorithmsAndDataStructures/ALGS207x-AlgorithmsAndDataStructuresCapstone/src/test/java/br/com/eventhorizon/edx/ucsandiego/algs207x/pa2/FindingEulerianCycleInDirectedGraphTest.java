package br.com.eventhorizon.edx.ucsandiego.algs207x.pa2;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.TestProperties;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindingEulerianCycleInDirectedGraphTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa2/find-eulerian-cycle-in-directed-graph.csv";

  public FindingEulerianCycleInDirectedGraphTest() {
    super(new FindingEulerianCycleInDirectedGraph(), true, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    TestProperties.setTimeLimit(15000);
    input = input.replace("%", "\n");
    expectedOutput = expectedOutput.replace("%", "\n");
    super.testFinalSolution(input, expectedOutput);
  }

  @Override
  protected void verify(String input, String expectedOutput, String actualOutput) {
    // Process input
    Map<Integer, List<Integer>> adjacencies = new HashMap<>();
    FastScanner scanner = new FastScanner(new ByteArrayInputStream(input.getBytes()));
    int verticesCount = scanner.nextInt();
    int edgesCount = scanner.nextInt();
    for (int i = 0; i < edgesCount; i++) {
      int from = scanner.nextInt();
      int to = scanner.nextInt();
      List<Integer> adj = adjacencies.getOrDefault(from, new ArrayList<>());
      adj.add(to);
      adjacencies.put(from, adj);
    }

    // Process expected output
    scanner = new FastScanner(new ByteArrayInputStream(expectedOutput.getBytes()));
    List<Integer> expectedCycle = new ArrayList<>();
    int expectedHasEulerianCycle = scanner.nextInt();
    if (expectedHasEulerianCycle == 1) {
      for (int i = 0; i < edgesCount; i++) {
        expectedCycle.add(scanner.nextInt());
      }
    }

    // Process actual output
    scanner = new FastScanner(new ByteArrayInputStream(actualOutput.getBytes()));
    List<Integer> actualCycle = new ArrayList<>();
    int actualHasEulerianCycle = scanner.nextInt();
    if (actualHasEulerianCycle == 1) {
      for (int i = 0; i < edgesCount; i++) {
        actualCycle.add(scanner.nextInt());
      }
    }

    // Verify actual output against expected output
    assertEquals(expectedHasEulerianCycle, actualHasEulerianCycle);
    assertEquals(expectedCycle.size(), actualCycle.size());
    assertTrue(expectedCycle.containsAll(actualCycle));
  }
}
