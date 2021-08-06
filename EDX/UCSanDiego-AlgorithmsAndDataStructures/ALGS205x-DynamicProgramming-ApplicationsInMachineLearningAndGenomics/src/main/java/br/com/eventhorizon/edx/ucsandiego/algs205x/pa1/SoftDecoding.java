package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoftDecoding implements PA {

  private static final String DELIMITER = "--------";

  private static String emittedSymbols;

  private static List<Character> symbols;

  private static Map<Character, Integer> symbolIndex;

  private static List<Character> states;

  private static Map<Character, Integer> stateIndex;

  private static double[][] transitionProbabilities;

  private static double[][] emissionProbabilities;

  private static double[][] result;

  private static void init() {
    emittedSymbols = "";
    symbols = new ArrayList<>();
    symbolIndex = new HashMap<>();
    states = new ArrayList<>();
    stateIndex = new HashMap<>();
    transitionProbabilities = null;
    emissionProbabilities = null;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read emitted symbols
    emittedSymbols = scanner.next();
    readDelimiter(scanner);

    // Read symbols
    String symbolStr = scanner.next();
    while (!symbolStr.equals(DELIMITER)) {
      if (symbolStr.length() > 1) {
        throw new RuntimeException("Not expecting symbol with more than one character");
      }
      symbols.add(symbolStr.charAt(0));
      symbolStr = scanner.next();
    }

    // Read states
    String stateStr = scanner.next();
    while (!stateStr.equals(DELIMITER)) {
      if (stateStr.length() > 1) {
        throw new RuntimeException("Not expecting state with more than one character");
      }
      states.add(stateStr.charAt(0));
      stateStr = scanner.next();
    }

    // Read transition matrix
    transitionProbabilities = new double[states.size()][states.size()];
    for (int i = 0; i < transitionProbabilities[0].length; i++) {
      scanner.next();
    }
    for (int i = 0; i < transitionProbabilities.length; i++) {
      scanner.nextChar();
      for (int j = 0; j < transitionProbabilities[0].length; j++) {
        transitionProbabilities[i][j] = scanner.nextDouble();
      }
    }
    readDelimiter(scanner);

    // Read emission matrix
    emissionProbabilities = new double[states.size()][symbols.size()];
    for (int i = 0; i < emissionProbabilities[0].length; i++) {
      scanner.next();
    }
    for (int i = 0; i < emissionProbabilities.length; i++) {
      scanner.nextChar();
      for (int j = 0; j < emissionProbabilities[0].length; j++) {
        emissionProbabilities[i][j] = scanner.nextDouble();
      }
    }
  }

  private static void readDelimiter(FastScanner scanner) {
    String line = scanner.next();
    if (!line.equals(DELIMITER)) {
      throw new RuntimeException("Expected delimiter '" + DELIMITER + "', but received " + line);
    }
  }

  private static void postReadInputInit() {
    // Initialize symbol index
    int index = 0;
    for (Character symbol : symbols) {
      symbolIndex.put(symbol, index++);
    }
    // Initialize state index
    index = 0;
    for (Character state : states) {
      stateIndex.put(state, index++);
    }
  }

  private static void writeOutput() {
    for (Character state : states) {
      System.out.print(state + " ");
    }
    System.out.println();
    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result[0].length; j++) {
        if (result[i][j] > 0) {
          System.out.printf("%6.3e ", result[i][j]);
        } else {
          System.out.print("0 ");
        }
      }
      System.out.println();
    }
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    postReadInputInit();
    finalSolutionImpl();
    writeOutput();
  }

  private static void finalSolutionImpl() {
    double initialProbability = 1.0 / states.size();

    // Compute forward score
    double[][] forwardProbabilities = new double[states.size()][emittedSymbols.length()];
    for (int i = 0; i < forwardProbabilities.length; i++) {
      forwardProbabilities[i][0] = initialProbability * emissionProbabilities[i][symbolIndex.get(emittedSymbols.charAt(0))];
    }
    for (int j = 1; j < forwardProbabilities[0].length; j++) {
      for (int i = 0; i < forwardProbabilities.length; i++) {
        double sum = 0.0;
        for (int k = 0; k < forwardProbabilities.length; k++) {
          sum += forwardProbabilities[k][j - 1] * transitionProbabilities[k][i] * emissionProbabilities[i][symbolIndex.get(emittedSymbols.charAt(j))];
        }
        forwardProbabilities[i][j] = sum;
      }
    }
    double probability = 0.0;
    for (double[] forwardProbability : forwardProbabilities) {
      probability += forwardProbability[forwardProbabilities[0].length - 1];
    }

    // Compute backward score
    double[][] backwardScore = new double[states.size()][emittedSymbols.length()];
    for (int i = 0; i < backwardScore.length; i++) {
      backwardScore[i][backwardScore[0].length - 1] = emissionProbabilities[i][symbolIndex.get(emittedSymbols.charAt(emittedSymbols.length() - 1))];
    }
    for (int j = backwardScore[0].length - 2; j >= 0; j--) {
      for (int i = 0; i < backwardScore.length; i++) {
        double sum = 0.0;
        for (int k = 0; k < backwardScore.length; k++) {
          sum += backwardScore[k][j + 1] * transitionProbabilities[i][k] * emissionProbabilities[i][symbolIndex.get(emittedSymbols.charAt(j))];
        }
        backwardScore[i][j] = sum;
      }
    }

    result = new double[emittedSymbols.length()][states.size()];
    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result[0].length; j++) {
        result[i][j] = (forwardProbabilities[j][i] * backwardScore[j][i]) / (probability * emissionProbabilities[j][symbolIndex.get(emittedSymbols.charAt(i))]);
      }
    }
  }
}
