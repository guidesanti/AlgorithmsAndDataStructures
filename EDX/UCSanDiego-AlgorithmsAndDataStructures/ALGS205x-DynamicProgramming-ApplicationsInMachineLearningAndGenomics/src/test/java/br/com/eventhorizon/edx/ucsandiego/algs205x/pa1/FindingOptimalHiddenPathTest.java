package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.test.PATestBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FindingOptimalHiddenPathTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/finding-optimal-hidden-path.csv";

  private static final String DELIMITER = "--------";

  private static String emittedSymbols;

  private static List<String> symbols;

  private static List<String> states;

  private static Map<String, Double> transitionProbabilities;

  private static Map<String, Double> emissionProbabilities;

  public FindingOptimalHiddenPathTest() {
    super(new FindingOptimalHiddenPath());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    init();
    readInput(input);
    reset(input);
    pa.finalSolution();
    String actualOutput = getActualOutput();
    assertNotNull(expectedOutput);
    assertEquals(probability(expectedOutput), probability(actualOutput));
  }

  private static void init() {
    emittedSymbols = "";
    symbols = new ArrayList<>();
    states = new ArrayList<>();
    transitionProbabilities = new HashMap<>();
    emissionProbabilities = new HashMap<>();
  }

  private void readInput(String input) {
    FastScanner scanner = new FastScanner(new ByteArrayInputStream(input.getBytes()));

    // Read emitted symbols
    emittedSymbols = scanner.next();
    readDelimiter(scanner);

    // Read symbols
    String symbol = scanner.next();
    while (!symbol.equals(DELIMITER)) {
      symbols.add(symbol);
      symbol = scanner.next();
    }

    // Read states
    String state = scanner.next();
    while (!state.equals(DELIMITER)) {
      states.add(state);
      state = scanner.next();
    }

    // Read transition probabilities
    List<String> columnHeaders = new ArrayList<>();
    for (int i = 0; i < states.size(); i++) {
      columnHeaders.add(scanner.next());
    }
    for (int i = 0; i < states.size(); i++) {
      String rowHeader = scanner.next();
      for (int j = 0; j < states.size(); j++) {
        //        transitionProbabilities.put(rowHeader + columnHeaders.get(j), Math.log(Double.parseDouble(scanner.next())));
        transitionProbabilities.put(rowHeader + columnHeaders.get(j), Double.parseDouble(scanner.next()));
      }
    }
    readDelimiter(scanner);

    // Read emission probabilities
    columnHeaders = new ArrayList<>();
    for (int i = 0; i < symbols.size(); i++) {
      columnHeaders.add(scanner.next());
    }
    for (int i = 0; i < states.size(); i++) {
      String rowHeader = scanner.next();
      for (int j = 0; j < symbols.size(); j++) {
        //        emissionProbabilities.put(rowHeader + columnHeaders.get(j), Math.log(Double.parseDouble(scanner.next())));
        emissionProbabilities.put(rowHeader + columnHeaders.get(j), Double.parseDouble(scanner.next()));
      }
    }
  }

  private void readDelimiter(FastScanner scanner) {
    String line = scanner.next();
    if (!line.equals(DELIMITER)) {
      throw new RuntimeException("Expected delimiter '" + DELIMITER + "', but received " + line);
    }
  }

  private double probability(String path) {
    double probability = 1.0 / states.size();
    for (int i = 1; i < path.length(); i++) {
      String stateTransitionKey = "" + path.charAt(i - 1) + path.charAt(i);
      probability *= transitionProbabilities.get(stateTransitionKey);
    }
    return probability;
  }
}
