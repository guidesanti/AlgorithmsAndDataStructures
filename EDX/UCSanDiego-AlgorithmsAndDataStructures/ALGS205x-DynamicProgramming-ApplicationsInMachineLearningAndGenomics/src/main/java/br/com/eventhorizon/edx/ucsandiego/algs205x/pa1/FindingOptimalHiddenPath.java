package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindingOptimalHiddenPath implements PA {

  private static final String DELIMITER = "--------";

  private static String emittedSymbols;

  private static List<String> symbols;

  private static List<String> states;

  private static Map<String, Double> transitionProbabilities;

  private static Map<String, Double> emissionProbabilities;

  private static String path;

  private static void init() {
    emittedSymbols = "";
    symbols = new ArrayList<>();
    states = new ArrayList<>();
    transitionProbabilities = new HashMap<>();
    emissionProbabilities = new HashMap<>();
    path = "";
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
        emissionProbabilities.put(rowHeader + columnHeaders.get(j), Double.parseDouble(scanner.next()));
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
    System.out.println(path);
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    finalSolutionImpl();
    writeOutput();
  }

  private static void finalSolutionImpl() {
    int[][] edgeTo = new int[states.size()][emittedSymbols.length()];
    double[][] probabilities = new double[states.size()][emittedSymbols.length()];
    double initialProbability = 1.0 / states.size();
    for (int i = 0; i < probabilities.length; i++) {
      String key = states.get(i) + emittedSymbols.charAt(0);
      probabilities[i][0] = initialProbability * emissionProbabilities.get(key);
    }
    for (int j = 1; j < probabilities[0].length; j++) {
      for (int i = 0; i < probabilities.length; i++) {
        String currentState = states.get(i);
        char currentEmittedSymbol = emittedSymbols.charAt(j);
        double max = Double.NEGATIVE_INFINITY;
        for (int k = 0; k < probabilities.length; k++) {
          String transitionKey = states.get(k) + currentState;
          String emissionKey = currentState + currentEmittedSymbol;
          double curr = probabilities[k][j - 1] * transitionProbabilities.get(transitionKey) * emissionProbabilities.get(emissionKey);
          if (curr > max) {
            max = curr;
            edgeTo[i][j] = k;
          }
        }
        probabilities[i][j] = max;
      }
    }
    int k = 0;
    double max = Double.NEGATIVE_INFINITY;
    for (int i = 0; i < probabilities.length; i++) {
      if (probabilities[i][probabilities[0].length - 1] > max) {
        max = probabilities[i][probabilities[0].length - 1];
        k = i;
      }
    }
    StringBuilder s = new StringBuilder();
    for (int j = emittedSymbols.length() - 1; j >= 0; j--) {
      s.insert(0, states.get(k));
      k = edgeTo[k][j];
    }
    path = s.toString();
  }
}
