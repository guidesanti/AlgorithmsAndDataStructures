package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

public class HMMParameterEstimation implements PA {

  private static final String DELIMITER = "--------";

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

    // Read state transitions
    stateTransitions = scanner.next();
    readDelimiter(scanner);

    // Read states
    String stateStr = scanner.next();
    while (stateStr != null) {
      if (stateStr.length() > 1) {
        throw new RuntimeException("Not expecting state with more than one character");
      }
      states.add(stateStr.charAt(0));
      stateStr = scanner.next();
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
    transitionCount = new int[states.size()][states.size()];
    totalTransitionCount = new int[states.size()];
    emissionCount = new int[states.size()][symbols.size()];
    totalEmissionCount = new int[states.size()];
    transitionProbabilities = new double[states.size()][states.size()];
    emissionProbabilities = new double[states.size()][symbols.size()];
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
    countTransitionsAndEmissions();
    buildTransitionMatrix();
    buildEmissionMatrix();
  }

  private static void countTransitionsAndEmissions() {
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
