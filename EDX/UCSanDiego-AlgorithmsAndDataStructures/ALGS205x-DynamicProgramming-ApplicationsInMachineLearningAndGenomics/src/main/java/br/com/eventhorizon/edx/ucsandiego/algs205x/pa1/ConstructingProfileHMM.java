package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstructingProfileHMM implements PA {

  private static final String DELIMITER = "--------";

  private static final String START = "S";

  private static final String END = "E";

  private static final String INSERTION = "I";

  private static final String MATCH = "M";

  private static final String DELETION = "D";

  private static final char GAP = '-';

  private static double theta;

  private static List<Character> symbols;

  private static List<String> states;

  private static List<List<Character>> alignments;

  private static List<Map<Character, Integer>> symbolCount;

  private static List<Map<Character, Double>> profile;

  private static List<Boolean> match;

  private static Map<String, Integer> transitionCount;

  private static Map<String, Integer> totalTransitionCount;

  private static Map<String, Double> transitionProbabilities;

  private static Map<String, Double> emissionProbabilities;

  private static void init() {
    theta = 0.0;
    symbols = new ArrayList<>();
    states = new ArrayList<>();
    alignments = new ArrayList<>();
    symbolCount = new ArrayList<>();
    profile = new ArrayList<>();
    match = new ArrayList<>();
    transitionCount = new HashMap<>();
    totalTransitionCount = new HashMap<>();
    transitionProbabilities = new HashMap<>();
    emissionProbabilities = new HashMap<>();
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read theta
    theta = scanner.nextDouble();
    readDelimiter(scanner);

    // Read symbols
    String state = scanner.next();
    while (!state.equals(DELIMITER)) {
      if (state.length() > 1) {
        throw new RuntimeException("Not expecting state name with more than one character");
      }
      symbols.add(state.charAt(0));
      state = scanner.next();
    }

    // Read alignments
    String alignmentString = scanner.next();
    for (int i = 0; i < alignmentString.length(); i++) {
      alignments.add(new ArrayList<>());
      symbolCount.add(new HashMap<>());
      profile.add(new HashMap<>());
    }
    while (alignmentString != null) {
      for (int i = 0; i < alignmentString.length(); i++) {
        Character symbol = alignmentString.charAt(i);
        alignments.get(i).add(symbol);
        int count = symbolCount.get(i).getOrDefault(symbol, 0);
        count++;
        symbolCount.get(i).put(symbol, count);
      }
      alignmentString = scanner.next();
    }
  }

  private static void readDelimiter(FastScanner scanner) {
    String line = scanner.next();
    if (!line.equals(DELIMITER)) {
      throw new RuntimeException("Expected delimiter '" + DELIMITER + "', but received " + line);
    }
  }

  private static void writeOutput() {
    for (String state : states) {
      System.out.print(state + " ");
    }
    System.out.println();
    for (String state : states) {
      System.out.print(state + " ");
      for (String s : states) {
        String key = state + s;
        System.out.printf("%6.3e ", transitionProbabilities.getOrDefault(key, 0.0));
      }
      System.out.println();
    }
    System.out.println(DELIMITER);
    for (Character symbol : symbols) {
      System.out.print(symbol + " ");
    }
    System.out.println();
    for (String state : states) {
      System.out.print(state + " ");
      for (Character symbol : symbols) {
        String key = state + symbol;
        System.out.printf("%6.3e ", emissionProbabilities.getOrDefault(key, 0.0));
      }
      System.out.println();
    }
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    finalSolutionImpl();
    writeOutput();
  }

  private static void finalSolutionImpl() {
    buildProfile();
    countTransitions();
    buildTransitionMatrix();
    buildEmissionMatrix();
  }

  private static void buildProfile() {
    double numberOfRows = alignments.get(0).size();
    for (int i = 0; i < alignments.size(); i++) {
      int gapCount = symbolCount.get(i).getOrDefault(GAP, 0);
      for (Character symbol : symbols) {
        double count = symbolCount.get(i).getOrDefault(symbol, 0);
        profile.get(i).put(symbol, count / (numberOfRows - gapCount));
      }
      if (gapCount / numberOfRows >= theta) {
        match.add(false);
      } else {
        match.add(true);
      }
    }
    int matchStateCount = match.stream().map(match -> match ? 1 : 0).reduce(Integer::sum).get();
    int stateCount = (3 * matchStateCount) + 3;
    for (int i = 0; i < stateCount; i++) {
      states.add(indexToState(i, stateCount));
    }
  }

  private static void countTransitions() {
    for (int i = 0; i < alignments.get(0).size(); i++) {
      String previousState = START;
      int index = 1;
      for (int j = 0; j < alignments.size(); j++) {
        char symbol = alignments.get(j).get(i);
        String currentState = MATCH + index;
        if (match.get(j)) {
          if (symbol == GAP) {
            currentState = DELETION + index;
          }
          index++;
        } else {
          if (symbol != GAP) {
            currentState = INSERTION + (index - 1);
          } else {
            continue;
          }
        }
        String key = previousState + currentState;
        int count = transitionCount.getOrDefault(key, 0);
        count++;
        transitionCount.put(key, count);
        count = totalTransitionCount.getOrDefault(previousState, 0);
        count++;
        totalTransitionCount.put(previousState, count);
        previousState = currentState;
      }
      String key = previousState + END;
      int count = transitionCount.getOrDefault(key, 0);
      count++;
      transitionCount.put(key, count);
      count = totalTransitionCount.getOrDefault(previousState, 0);
      count++;
      totalTransitionCount.put(previousState, count);
    }
  }

  private static void buildTransitionMatrix() {
    int stateCount = states.size();
    for (int i = 0; i < stateCount; i++) {
      for (int j = 0; j < stateCount; j++) {
        String fromState = indexToState(i, stateCount);
        String toState = indexToState(j, stateCount);
        String transitionKey = fromState + toState;
        double transitions = transitionCount.getOrDefault(transitionKey, 0);
        double total = totalTransitionCount.getOrDefault(fromState, 0);
        if (total > 0) {
          transitionProbabilities.put(transitionKey, transitions / total);
        }
      }
    }
  }

  private static void buildEmissionMatrix() {
    int index = 1;
    for (int j = 0; j < profile.size(); j++) {
      String state;
      if (match.get(j)) {
        state = MATCH + index;
        index++;
      } else {
        state = INSERTION + (index - 1);
      }
      for (Character symbol : symbols) {
        String emissionKey = state + symbol;
        emissionProbabilities.put(emissionKey, profile.get(j).get(symbol));
      }
    }
  }

  private static String indexToState(int index, int stateCount) {
    if (index == 0) {
      return START;
    } else if (index == 1) {
      return INSERTION + 0;
    } else if (index == stateCount - 1) {
      return END;
    } else {
      int aux1 = ((index - 2) / 3) + 1;
      int aux2 = (index - 2) % 3;
      if (aux2 == 0) {
        return MATCH + aux1;
      }
      if (aux2 == 1) {
        return DELETION + aux1;
      }
      return INSERTION + aux1;
    }
  }
}
