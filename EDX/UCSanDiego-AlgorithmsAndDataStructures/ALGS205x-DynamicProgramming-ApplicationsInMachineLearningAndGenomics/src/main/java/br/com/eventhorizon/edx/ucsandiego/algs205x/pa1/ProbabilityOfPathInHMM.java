package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

public class ProbabilityOfPathInHMM implements PA {

  private static int numberOfStates;

  private static List<String> transitions;

  private static Map<String, Double> transitionProbabilities;

  private static double probability;

  private static void init() {
    numberOfStates = 0;
    transitions = new ArrayList<>();
    transitionProbabilities = new HashMap<>();
    probability = 0.0;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);
    String path = scanner.next();
    for (int i = 1; i < path.length(); i++) {
      transitions.add(path.substring(i - 1, i + 1));
    }
    scanner.next();
    String state = scanner.next();
    while (!state.equals("--------")) {
      numberOfStates++;
      state = scanner.next();
    }
    List<String> columnHeaders = new ArrayList<>();
    for (int i = 0; i < numberOfStates; i++) {
      columnHeaders.add(scanner.next());
    }
    for (int i = 0; i < numberOfStates; i++) {
      String rowHeader = scanner.next();
      for (int j = 0; j < numberOfStates; j++) {
        transitionProbabilities.put(rowHeader + columnHeaders.get(j), Math.log(Double.parseDouble(scanner.next())));
      }
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
    probability = Math.log((1.0 / numberOfStates));
    transitions.stream().map(t -> transitionProbabilities.get(t)).reduce((d1, d2) -> d1 + d2).ifPresent(d -> probability += d);
    probability = Math.pow(Math.E, probability);
  }
}
