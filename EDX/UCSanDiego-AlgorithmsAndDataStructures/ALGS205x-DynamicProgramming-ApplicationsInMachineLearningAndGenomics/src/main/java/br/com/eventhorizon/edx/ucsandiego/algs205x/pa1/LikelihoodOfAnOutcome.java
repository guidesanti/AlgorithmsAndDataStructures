package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LikelihoodOfAnOutcome implements PA {

  private static final String DELIMITER = "--------";

  private static String emittedSymbols;

  private static List<String> symbols;

  private static List<String> states;

  private static Map<String, Double> transitionProbabilities;

  private static Map<String, Double> emissionProbabilities;

  private static double probability;

  private static void init() {
    emittedSymbols = "";
    symbols = new ArrayList<>();
    states = new ArrayList<>();
    transitionProbabilities = new HashMap<>();
    emissionProbabilities = new HashMap<>();
    probability = 0;
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
        double sum = 0;
        for (int k = 0; k < probabilities.length; k++) {
          String transitionKey = states.get(k) + currentState;
          String emissionKey = currentState + currentEmittedSymbol;
          sum += probabilities[k][j - 1] * transitionProbabilities.get(transitionKey) * emissionProbabilities.get(emissionKey);
        }
        probabilities[i][j] = sum;
      }
    }
    for (int i = 0; i < probabilities.length; i++) {
      probability += probabilities[i][probabilities[0].length - 1];
    }
  }
}
