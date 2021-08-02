package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

public class ProfileHMMWithPseudocounts implements PA {

  private static final String DELIMITER = "--------";

  private static final String START = "S";

  private static final String END = "E";

  private static final String INSERTION = "I";

  private static final String MATCH = "M";

  private static final String DELETION = "D";

  private static final char GAP = '-';

  private static double theta;

  private static double sigma;

  private static List<Character> symbols;

  private static Map<Character, Integer> symbolToIndex;

  private static List<String> states;

  private static List<List<Character>> alignments;

  private static List<Map<Character, Integer>> symbolCount;

  private static List<Boolean> match;

  private static int matchCount;

  private static int[][] transitionCount;

  private static int[] totalTransitionCount;

  private static Map<String, Integer>[] emissionCount;

  private static double[][] transitionProbabilities;

  private static double[][] emissionProbabilities;

  private static void init() {
    theta = 0.0;
    sigma = 0.0;
    symbols = new ArrayList<>();
    symbolToIndex = new HashMap<>();
    states = new ArrayList<>();
    alignments = new ArrayList<>();
    symbolCount = new ArrayList<>();
    match = new ArrayList<>();
    matchCount = 0;
    transitionCount = null;
    totalTransitionCount = null;
    emissionCount = null;
    emissionProbabilities = null;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read theta and sigma
    theta = scanner.nextDouble();
    sigma = scanner.nextDouble();
    readDelimiter(scanner);

    // Read symbols
    int index = 0;
    String line = scanner.next();
    while (!line.equals(DELIMITER)) {
      if (line.length() > 1) {
        throw new RuntimeException("Not expecting symbol with more than one character");
      }
      symbols.add(line.charAt(0));
      symbolToIndex.put(line.charAt(0), index);
      index++;
      line = scanner.next();
    }

    // Read first alignment
    String firstAlignment = scanner.next();
    for (int i = 0; i < firstAlignment.length(); i++) {
      Map<Character, Integer> count = new HashMap<>();
      count.put(firstAlignment.charAt(i), 1);
      symbolCount.add(count);
      List<Character> alignment = new ArrayList<>();
      alignment.add(firstAlignment.charAt(i));
      alignments.add(alignment);
    }
    // Read remaining alignments
    Character symbol = scanner.nextRawChar();
    while (symbol != null && symbol != '\n') {
      int i = 0;
      while (symbol != null && symbol != '\n') {
        alignments.get(i).add(symbol);
        int count = symbolCount.get(i).getOrDefault(symbol, 0);
        count++;
        symbolCount.get(i).put(symbol, count);
        symbol = scanner.nextRawChar();
        i++;
      }
      symbol = scanner.nextRawChar();
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
    finalSolutionImpl();
    writeOutput();
  }

  private static void finalSolutionImpl() {
    buildProfile();
    countTransitionsAndEmissions();
    buildTransitionMatrix();
    buildEmissionMatrix();
  }

  private static void buildProfile() {
    double numberOfRows = alignments.get(0).size();
    for (int i = 0; i < alignments.size(); i++) {
      int gapCount = symbolCount.get(i).getOrDefault(GAP, 0);
      if (gapCount / numberOfRows >= theta) {
        match.add(false);
      } else {
        match.add(true);
      }
    }
    matchCount = match.stream().map(match -> match ? 1 : 0).reduce(Integer::sum).get();
    int stateCount = (3 * matchCount) + 3;
    for (int i = 0; i < stateCount; i++) {
      states.add(indexToState(i, stateCount));
    }
    transitionCount = new int[stateCount][stateCount];
    totalTransitionCount = new int[stateCount];
    emissionCount = new Map[stateCount];
    for (int i = 0; i < stateCount; i++) {
      emissionCount[i] = new HashMap<>();
    }
    transitionProbabilities = new double[stateCount][stateCount];
    emissionProbabilities = new double[stateCount][symbols.size()];
  }

  private static void countTransitionsAndEmissions() {
    for (int i = 0; i < alignments.get(0).size(); i++) {
      int previousState = 0;
      int index = 0;
      for (int j = 0; j < alignments.size(); j++) {
        char symbol = alignments.get(j).get(i);
        int currentState = 2 + (3 * index); // Match
        if (match.get(j)) {
          if (symbol == GAP) {
            currentState = 3 + (3 * index); // Deletion
          }
          index++;
        } else {
          if (symbol != GAP) {
            currentState = 1 + (3 * index); // Insertion
          } else {
            continue;
          }
        }
        transitionCount[previousState][currentState]++;
        totalTransitionCount[previousState]++;
        int count = emissionCount[currentState].getOrDefault("" + symbol, 0);
        count++;
        emissionCount[currentState].put("" + symbol, count);
        previousState = currentState;
      }
      transitionCount[previousState][transitionCount[0].length - 1]++;
      totalTransitionCount[previousState]++;
    }
  }

  private static void buildTransitionMatrix() {
    int stateCount = states.size();
    for (int i = 0; i <= 1; i++) {
      for (int j = 1; j <= 3; j++) {
        buildTransition(i, j, false);
      }
    }
    for (int k = 0; k < matchCount - 1; k++) {
      int startI = 2 + (3 * k);
      for (int i = startI; i < startI + 3; i++) {
        int startJ = startI + 2;
        for (int j = startJ; j < startJ + 3; j++) {
          buildTransition(i, j, false);
        }
      }
    }
    for (int i = stateCount - 4; i <= stateCount - 2; i++) {
      for (int j = stateCount - 2; j <= stateCount - 1; j++) {
        buildTransition(i, j, true);
      }
    }
  }

  private static void buildTransition(int from, int to, boolean last) {
    double transitions = transitionCount[from][to];
    double total = totalTransitionCount[from];
    double probability;
    if (total == 0.0) {
      probability = last ? 1.0 / 2.0 : 1.0 / 3.0;
    } else {
      probability = transitions / total;
      probability += sigma;
      probability /= 1.0 + ((last ? 2.0 : 3.0)  * sigma);
    }
    transitionProbabilities[from][to] = probability;
  }

  private static void buildEmissionMatrix() {
    int insertionStates = matchCount + 1;
    for (int i = 0; i < insertionStates; i++) {
      int insertionState = 1 + (3 * i);
      int matchState = 2 + (3 * i);
      Map<String, Integer> insertionEmission = emissionCount[insertionState];
      Optional<Integer> totalInsertionOpt = insertionEmission.values().stream().reduce(Integer::sum);
      int totalInsertion = totalInsertionOpt.orElse(0);
      Map<String, Integer> matchEmission = emissionCount[matchState];
      Optional<Integer> totalMatchOpt = matchEmission.values().stream().reduce(Integer::sum);
      int totalMatch = totalMatchOpt.orElse(0);
      for (Character symbol : symbols) {
        double symbolCount = insertionEmission.getOrDefault("" + symbol, 0);
        double probability = 0;
        if (totalInsertion == 0) {
          probability = 1.0 / symbols.size();
        } else if (symbolCount > 0.0) {
          probability = symbolCount / totalInsertion;
        }
        probability += sigma;
        probability /= (1.0 + (sigma * symbols.size()));
        emissionProbabilities[insertionState][symbolToIndex.get(symbol)] = probability;

        if (i < insertionStates - 1) {
          symbolCount = matchEmission.getOrDefault("" + symbol, 0);
          probability = 0;
          if (totalMatch == 0) {
            probability = 1.0 / symbols.size();
          } else if (symbolCount > 0.0) {
            probability = symbolCount / totalMatch;
          }
          probability += sigma;
          probability /= (1.0 + (sigma * symbols.size()));
          emissionProbabilities[matchState][symbolToIndex.get(symbol)] = probability;
        }
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
