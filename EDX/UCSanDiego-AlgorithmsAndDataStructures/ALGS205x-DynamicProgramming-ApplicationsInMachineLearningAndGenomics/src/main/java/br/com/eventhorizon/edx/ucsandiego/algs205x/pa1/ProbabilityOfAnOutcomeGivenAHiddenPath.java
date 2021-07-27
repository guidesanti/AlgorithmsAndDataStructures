package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProbabilityOfAnOutcomeGivenAHiddenPath implements PA {

  private static final String DELIMITER = "--------";

  private static String emittedSymbols;

  private static List<String> symbols;

  private static String path;

  private static List<String> states;

  private static Map<String, Double> emissionProbabilities;

  private static double probability;

  private static void init() {
    emittedSymbols = "";
    symbols = new ArrayList<>();
    path = "";
    states = new ArrayList<>();
    emissionProbabilities = new HashMap<>();
    probability = 0.0;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read emitted symbols
    emittedSymbols = scanner.next();
    readDelimiter(scanner);

    // Read symbols
    String symbol = scanner.next();
    while (!symbol.equals(DELIMITER)) {
      symbols.add(symbol);
      symbol = scanner.next();
    }

    // Read path
    path = scanner.next();
    readDelimiter(scanner);

    // Read states
    String state = scanner.next();
    while (!state.equals(DELIMITER)) {
      states.add(state);
      state = scanner.next();
    }

    // Read emission probabilities
    List<String> columnHeaders = new ArrayList<>();
    for (int i = 0; i < symbols.size(); i++) {
      columnHeaders.add(scanner.next());
    }
    for (int i = 0; i < states.size(); i++) {
      String rowHeader = scanner.next();
      for (int j = 0; j < symbols.size(); j++) {
        emissionProbabilities.put(rowHeader + columnHeaders.get(j), Math.log(Double.parseDouble(scanner.next())));
      }
    }
  }

  private static void readDelimiter(FastScanner scanner) {
    String line = scanner.next();
    if (!line.equals(DELIMITER)) {
      throw new RuntimeException("Expected delimiter '" + DELIMITER + "', but received " + line);
    }
  }

  private static void writeOutput() {
    System.out.printf("%6.3e%n", probability);
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    finalSolutionImpl();
    writeOutput();
  }

  private static void finalSolutionImpl() {
    for (int i = 0; i < path.length(); i++) {
      String key = "" + path.charAt(i) + emittedSymbols.charAt(i);
      probability += emissionProbabilities.get(key);
    }
    probability = Math.pow(Math.E, probability);
  }
}
