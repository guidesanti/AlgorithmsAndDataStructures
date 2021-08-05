package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HMMParameterEstimationWithViterbiLearning implements PA {

  private static final String DELIMITER = "--------";

  private static int iterations;

  private static String emittedSymbols;

  private static List<Character> symbols;

  private static Map<Character, Integer> symbolIndex;

  private static String stateTransitions;

  private static List<Character> states;

  private static Map<Character, Integer> stateIndex;

  private static int[][] transitionCount;

  private static int[] totalTransitionCount;

  private static int[][] emissionCount;

  private static int[] totalEmissionCount;

  private static double[][] transitionProbabilities;

  private static double[][] emissionProbabilities;

  private static void init() {
    iterations = 0;
    emittedSymbols = "";
    symbols = new ArrayList<>();
    symbolIndex = new HashMap<>();
    stateTransitions = "";
    states = new ArrayList<>();
    stateIndex = new HashMap<>();
    transitionCount = null;
    totalTransitionCount = null;
    emissionCount = null;
    totalEmissionCount = null;
    transitionProbabilities = null;
    emissionProbabilities = null;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read the number of iterations
    iterations = scanner.nextInt();
    readDelimiter(scanner);

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

    // Read starting transition matrix
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

    // Read starting emission matrix
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
    for (int i = 0; i < transitionProbabilities.length; i++) {
      System.out.print(states.get(i) + " ");
      for (int j = 0; j < transitionProbabilities[0].length; j++) {
        if (transitionProbabilities[i][j] > 0) {
          System.out.printf("%6.3e ", transitionProbabilities[i][j]);
        } else {
          System.out.print("0 ");
        }
      }
      System.out.println();
    }
    System.out.println(DELIMITER);
    for (Character symbol : symbols) {
      System.out.print(symbol + " ");
    }
    System.out.println();
    for (int i = 0; i < emissionProbabilities.length; i++) {
      System.out.print(states.get(i) + " ");
      for (int j = 0; j < symbols.size(); j++) {
        if (emissionProbabilities[i][j] > 0) {
          System.out.printf("%6.3e ", emissionProbabilities[i][j]);
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
    for (int i = 0; i < iterations; i++) {
      findOptimalPath();
      estimateParameters();
    }
  }

  private static void findOptimalPath() {
    int[][] edgeTo = new int[states.size()][emittedSymbols.length()];
    double[][] score = new double[states.size()][emittedSymbols.length()];
    double initialProbability = 1.0 / states.size();

    // Compute score
    for (int i = 0; i < score.length; i++) {
      score[i][0] = initialProbability * emissionProbabilities[i][symbolIndex.get(emittedSymbols.charAt(0))];
    }
    for (int j = 1; j < score[0].length; j++) {
      for (int i = 0; i < score.length; i++) {
        double max = Double.NEGATIVE_INFINITY;
        for (int k = 0; k < score.length; k++) {
          double curr = score[k][j - 1] * transitionProbabilities[k][i] * emissionProbabilities[i][symbolIndex.get(emittedSymbols.charAt(j))];
          if (curr > max) {
            max = curr;
            edgeTo[i][j] = k;
          }
        }
        score[i][j] = max;
      }
    }
    int k = 0;
    double max = Double.NEGATIVE_INFINITY;
    for (int i = 0; i < score.length; i++) {
      if (score[i][score[0].length - 1] > max) {
        max = score[i][score[0].length - 1];
        k = i;
      }
    }

    // Reconstruct path
    StringBuilder path = new StringBuilder();
    for (int j = emittedSymbols.length() - 1; j >= 0; j--) {
      path.insert(0, states.get(k));
      k = edgeTo[k][j];
    }

    stateTransitions = path.toString();
  }

  private static void estimateParameters() {
    countTransitionsAndEmissions();
    buildTransitionMatrix();
    buildEmissionMatrix();
  }

  private static void countTransitionsAndEmissions() {
    transitionCount = new int[states.size()][states.size()];
    totalTransitionCount = new int[states.size()];
    emissionCount = new int[states.size()][symbols.size()];
    totalEmissionCount = new int[states.size()];
    for (int i = 0; i < stateTransitions.length() - 1; i++) {
      int fromState = stateIndex.get(stateTransitions.charAt(i));
      int toState = stateIndex.get(stateTransitions.charAt(i + 1));
      transitionCount[fromState][toState]++;
      totalTransitionCount[fromState]++;
      int symbol = symbolIndex.get(emittedSymbols.charAt(i));
      emissionCount[fromState][symbol]++;
      totalEmissionCount[fromState]++;
    }
    int fromState = stateIndex.get(stateTransitions.charAt(stateTransitions.length() - 1));
    int symbol = symbolIndex.get(emittedSymbols.charAt(emittedSymbols.length() - 1));
    emissionCount[fromState][symbol]++;
    totalEmissionCount[fromState]++;
  }

  private static void buildTransitionMatrix() {
    for (int i = 0; i < transitionProbabilities.length; i++) {
      for (int j = 0; j < transitionProbabilities[0].length; j++) {
        double a = transitionCount[i][j];
        double b = totalTransitionCount[i];
        if (b > 0.0) {
          transitionProbabilities[i][j] = a / b;
        } else {
          transitionProbabilities[i][j] = 1.0 / states.size();
        }
      }
    }
  }

  private static void buildEmissionMatrix() {
    for (int i = 0; i < emissionProbabilities.length; i++) {
      for (int j = 0; j < emissionProbabilities[0].length; j++) {
        double a = emissionCount[i][j];
        double b = totalEmissionCount[i];
        if (b > 0.0) {
          emissionProbabilities[i][j] = a / b;
        } else {
          emissionProbabilities[i][j] = 1.0 / symbols.size();
        }
      }
    }
  }
}
