package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaumWelchLearning implements PA {

  private static final String DELIMITER = "--------";

  private static int iterations;

  private static String emittedSymbols;

  private static List<Character> symbols;

  private static Map<Character, Integer> symbolIndex;

  private static List<Character> states;

  private static Map<Character, Integer> stateIndex;

  private static double[][] transitionProbabilities;

  private static double[][] emissionProbabilities;

  private static double[][][] t;

  private static double[][] e;

  private static void init() {
    iterations = 0;
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
      part1();
      estimateParameters();
    }
  }

  private static void part1() {
    double initialProbability = 1.0 / states.size();

    // Compute forward
    double[][] forward = new double[states.size()][emittedSymbols.length()];
    for (int i = 0; i < forward.length; i++) {
      forward[i][0] = initialProbability * emissionProbabilities[i][symbolIndex.get(emittedSymbols.charAt(0))];
    }
    for (int j = 1; j < forward[0].length; j++) {
      for (int i = 0; i < forward.length; i++) {
        double sum = 0.0;
        for (int k = 0; k < forward.length; k++) {
          sum += forward[k][j - 1] * transitionProbabilities[k][i] * emissionProbabilities[i][symbolIndex.get(emittedSymbols.charAt(j))];
        }
        forward[i][j] = sum;
      }
    }
    double forwardToSink = 0.0;
    for (double[] forwardProbability : forward) {
      forwardToSink += forwardProbability[forward[0].length - 1];
    }

    // Compute backward
    double[][] backward = new double[states.size()][emittedSymbols.length()];
    for (int i = 0; i < backward.length; i++) {
      backward[i][backward[0].length - 1] = emissionProbabilities[i][symbolIndex.get(emittedSymbols.charAt(emittedSymbols.length() - 1))];
    }
    for (int j = backward[0].length - 2; j >= 0; j--) {
      for (int i = 0; i < backward.length; i++) {
        double sum = 0.0;
        for (int k = 0; k < backward.length; k++) {
          sum += backward[k][j + 1] * transitionProbabilities[i][k] * emissionProbabilities[i][symbolIndex.get(emittedSymbols.charAt(j))];
        }
        backward[i][j] = sum;
      }
    }

    t = new double[states.size()][states.size()][emittedSymbols.length() - 1];
    for (int i = 0; i < states.size(); i++) {
      for (int k = 0; k < states.size(); k++) {
        for (int j = 0; j < t[0][0].length; j++) {
          t[i][k][j] = (forward[i][j] * transitionProbabilities[i][k] * emissionProbabilities[k][symbolIndex.get(emittedSymbols.charAt(j + 1))] * backward[k][j + 1]) / (forwardToSink * emissionProbabilities[k][symbolIndex.get(emittedSymbols.charAt(j + 1))]);
        }
      }
    }
    e = new double[states.size()][emittedSymbols.length()];
    for (int i = 0; i < e.length; i++) {
      for (int j = 0; j < e[0].length; j++) {
        e[i][j] = (forward[i][j] * backward[i][j]) / (forwardToSink * emissionProbabilities[i][symbolIndex.get(emittedSymbols.charAt(j))]);
      }
    }
  }

  private static void estimateParameters() {
    double[][] tSum = new double[states.size()][states.size()];
    for (int i = 0; i < states.size(); i++) {
      for (int j = 0; j < states.size(); j++) {
        for (int k = 0; k < t[0][0].length; k++) {
          tSum[i][j] += t[i][j][k];
        }
      }
    }
    transitionProbabilities = new double[states.size()][states.size()];
    for (int i = 0; i < states.size(); i++) {
      for (int j = 0; j < states.size(); j++) {
        double a = 0.0;
        for (int k = 0; k < emittedSymbols.length() - 1; k++) {
          for (int l = 0; l < states.size(); l++) {
            a += t[i][l][k];
          }
        }
        if (emittedSymbols.length() == 1) {
          transitionProbabilities[i][j] = 1.0 / states.size();
        } else {
          transitionProbabilities[i][j] = tSum[i][j] / a;
        }
      }
    }

    double[] eSum = new double[states.size()];
    for (int i = 0; i < states.size(); i++) {
      for (int j = 0; j < emittedSymbols.length(); j++) {
        eSum[i] += e[i][j];
      }
    }
    emissionProbabilities = new double[states.size()][symbols.size()];
    for (int i = 0; i < states.size(); i++) {
      for (int j = 0; j < symbols.size(); j++) {
        double a = 0.0;
        for (int k = 0; k < emittedSymbols.length(); k++) {
          if (j == symbolIndex.get(emittedSymbols.charAt(k))) {
            a += e[i][k];
          }
        }
        emissionProbabilities[i][j] = a / eSum[i];
      }
    }
  }
}
