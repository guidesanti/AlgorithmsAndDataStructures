package br.com.eventhorizon.edx.ucsandiego.algs207x.pa3;

import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import br.com.eventhorizon.common.pa.TestProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindCirculationInNetworkTest extends PATest {

  private static final Logger LOGGER = Logger.getLogger(FindCirculationInNetworkTest.class.getName());

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa3/find-circulation-in-network.csv";

  public FindCirculationInNetworkTest() {
    super(new FindCirculationInNetwork(), true, false);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    TestProperties.setTimeLimit(4500);
    input = input.replace("%", "\n");
    expectedOutput = expectedOutput.replace("%", "\n");
    super.testFinalSolution(input, expectedOutput);
  }

  @Test
  public void stressTest() {
    if (skipStressTest) {
      LOGGER.warning("Stress limit test skipped");
      return;
    }
    LOGGER.info("Stress test duration: " + TestProperties.getStressTestDuration());
    long startTime = System.currentTimeMillis();
    for (long i = 0; true; i++) {
      String input = generateInput(PATestType.STRESS_TEST);
      LOGGER.info("Stress test " + i + " input: " + input);

      // Run and compare results
      resetOutput();
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      pa.finalSolution();
      String actualOutput = getActualOutput();
      verify(input, actualOutput);

      // Check elapsed time
      long elapsedTime = System.currentTimeMillis() - startTime;
      if (elapsedTime > TestProperties.getStressTestDuration()) {
        return;
      }
    }
  }

  @Override
  protected String generateInput(PATestType type) {
    StringBuilder input = new StringBuilder();
    int verticesCount = Utils.getRandomInteger(2, 40);
    int edgeCount = Utils.getRandomInteger(1, 1600);
    List<FindCirculationInNetwork.Edge> edges = new ArrayList<>();
    int remaining = edgeCount;
    while (remaining > 0) {
      int from = Utils.getRandomInteger(1, verticesCount);
      int to = Utils.getRandomInteger(1, verticesCount);
      if (to == from) {
        continue;
      }
      int lowerBound = Utils.getRandomInteger(0, 48);
      int capacity = Utils.getRandomInteger(lowerBound + 1, 50);
      edges.add(new FindCirculationInNetwork.Edge(from, to, lowerBound, capacity));
      remaining--;
    }
    input.append(verticesCount).append(" ").append(edgeCount).append("\n");
    edges.forEach(edge ->
        input.append(edge.from).append(" ")
            .append(edge.to).append(" ")
            .append(edge.lowerBound).append(" ")
            .append(edge.capacity).append("\n")
    );
    return input.toString();
  }

  protected void verify(String input, String actualOutput) {
    // ---------------------------- //
    // Process input
    // ---------------------------- //
    FastScanner scanner = new FastScanner(new ByteArrayInputStream(input.getBytes()));

    // Read number of vertices and number of edges
    int verticesCount = scanner.nextInt();
    int edgesCount = scanner.nextInt();

    // Read edges
    Map<Integer, FindCirculationInNetwork.Vertex> vertices = new HashMap<>();
    List<FindCirculationInNetwork.Edge> edges = new ArrayList<>();
    for (int i = 0; i < edgesCount; i++) {
      int from = scanner.nextInt();
      FindCirculationInNetwork.Vertex fromVertex = vertices.getOrDefault(from, new FindCirculationInNetwork.Vertex(from));
      FindCirculationInNetwork.Edge edge = new FindCirculationInNetwork.Edge(from, scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
      fromVertex.edges.add(edge);
      edges.add(edge);
      vertices.put(from, fromVertex);
      FindCirculationInNetwork.Vertex
          toVertex = vertices.getOrDefault(edge.to, new FindCirculationInNetwork.Vertex(edge.to));
      vertices.put(edge.to, toVertex);
    }

    // ---------------------------- //
    // Process actual output
    // ---------------------------- //
    scanner = new FastScanner(new ByteArrayInputStream(actualOutput.getBytes()));
    String existCirculation = scanner.next();
    if (existCirculation.equals("YES")) {
      // verify flow for each edge, it shall be between lower bound and capacity
      List<Integer> flow = new ArrayList<>();
      for (int i = 0; i < edgesCount; i++) {
        FindCirculationInNetwork.Edge edge = edges.get(i);
        int actualFlow = scanner.nextInt();
        assertTrue(actualFlow <= edge.capacity, "Actual flow if greater than capacity for edge " + edge);
        assertTrue(actualFlow >= edge.lowerBound, "Actual flow if small than lower bound for edge " + edge);
        FindCirculationInNetwork.Vertex from = vertices.get(edge.from);
        from.flow -= actualFlow;
        FindCirculationInNetwork.Vertex to = vertices.get(edge.to);
        to.flow += actualFlow;
      }

      // Verify conservation of flow for each vertex
      for (FindCirculationInNetwork.Vertex vertex : vertices.values()) {
        assertEquals(0, vertex.flow, "Flow is not conserved for vertex " + vertex);
      }
    }
  }
}
