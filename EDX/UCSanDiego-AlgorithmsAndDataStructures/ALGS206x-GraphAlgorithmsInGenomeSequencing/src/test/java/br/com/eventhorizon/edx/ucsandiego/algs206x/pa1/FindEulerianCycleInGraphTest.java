package br.com.eventhorizon.edx.ucsandiego.algs206x.pa1;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import br.com.eventhorizon.common.pa.TestProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class FindEulerianCycleInGraphTest extends PATest {

  private static final Logger LOGGER = Logger.getLogger(FindEulerianCycleInGraphTest.class.getName());

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/find-eulerian-cycle-in-graph.csv";

  public FindEulerianCycleInGraphTest() {
    super(new FindEulerianCycleInGraph());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(final String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.replace("%", "\n").replace(";", ",").getBytes()));
    pa.finalSolution();
    verify(input, getActualOutput());
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

      // Run and verify results
      resetOutput();
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      pa.finalSolution();
      verify(input, getActualOutput());

      // Check elapsed time
      long elapsedTime = System.currentTimeMillis() - startTime;
      if (elapsedTime > TestProperties.getStressTestDuration()) {
        return;
      }
    }
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

  private void verify(String input, String output) {
    // Process input
    input = input.replace(" ", "").replace("%", "\n").replace(";", ",");
    String[] line = input.split("\n");
    Map<String, List<String>> adjacencies = new HashMap<>();
    int adjacenciesCount = 0;
    for (String value : line) {
      String[] values = value.split("->");
      String from = values[0].trim();
      List<String> adj = adjacencies.getOrDefault(from, new ArrayList<>());
      String[] toList = values[1].split(",");
      adjacenciesCount += toList.length;;
      Collections.addAll(adj, toList);
      adjacencies.put(from, adj);
    }

    // Process output
    String[] vertices = output.split("->");

    // Verify output
    String from = vertices[0];
    assertEquals(adjacenciesCount, vertices.length - 1);
    for (int i = 1; i < vertices.length; i++) {
      String to = vertices[i];
      List<String> fromAdjacencies = adjacencies.get(from);
      assertNotNull(fromAdjacencies);
      assertTrue(fromAdjacencies.remove(to));
      if (fromAdjacencies.isEmpty()) {
        adjacencies.remove(from);
      } else {
        adjacencies.put(from, fromAdjacencies);
      }
      from = to;
    }
    assertTrue(adjacencies.isEmpty());
  }
}
