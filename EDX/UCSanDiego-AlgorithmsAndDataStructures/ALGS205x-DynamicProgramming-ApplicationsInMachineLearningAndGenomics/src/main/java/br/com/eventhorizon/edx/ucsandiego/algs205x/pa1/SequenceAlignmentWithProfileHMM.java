package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

public class SequenceAlignmentWithProfileHMM implements PA {

  private static final String DELIMITER = "--------";

  private static final String START = "S";

  private static final String END = "E";

  private static final String INSERTION = "I";

  private static final String MATCH = "M";

  private static final String DELETION = "D";

  private static final char GAP = '-';

  private static String text;

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

  private static double[][] score;

  private static Vertex[][] edgeTo;

  private static Vertex edgeToEnd;

  private static List<String> alignment;

  private static void init() {
    text = "";
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
    alignment = new ArrayList<>();
    edgeToEnd = null;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read text
    text = scanner.next();
    readDelimiter(scanner);

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
    for (String state : alignment) {
      System.out.print(state + " ");
    }
    System.out.println();
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
    alignText();
    buildAlignment();
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
    score = new double[stateCount - 2][text.length() + 1];
    edgeTo = new Vertex[stateCount - 2][text.length() + 1];
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

  private static void alignText() {
    // Initialize S state (start)
    score[0][0] = 1.0;

    // Initialize first line I0s
    for (int i = 1; i < score[0].length; i++) {
      char symbol = text.charAt(i - 1);
      int symbolIndex = symbolToIndex.get(symbol);
      if (i == 1) {
        // S -> I0
        score[0][i] = score[0][i - 1] * transitionProbabilities[0][1] * emissionProbabilities[1][symbolIndex];
      } else {
        // I0 -> I0
        score[0][i] = score[0][i - 1] * transitionProbabilities[1][1] * emissionProbabilities[1][symbolIndex];
      }
      edgeTo[0][i] = new Vertex(0, i - 1);
    }

    // Initialize first column D1 to Dn
    for (int i = 1; i < score.length; i += 3) {
      if (i == 1) {
        score[i + 1][0] = score[0][0] * transitionProbabilities[0][3];
        edgeTo[i + 1][0] = new Vertex(0, 0);
      } else {
        score[i + 1][0] = score[i - 2][0] * transitionProbabilities[i - 1][i + 2];
        edgeTo[i + 1][0] = new Vertex(i - 2, 0);
      }
    }

    // Process remaining states
    for (int i = 1; i < score.length; i += 3) {
      for (int j = 1; j < score[0].length; j++) {
        char symbol = text.charAt(j - 1);
        int symbolIndex = symbolToIndex.get(symbol);

        // Match states
        if (i == 1) {
          if (j == 1) {
            // S -> M1
            score[i][j] = score[i - 1][j - 1] * transitionProbabilities[0][2] * emissionProbabilities[2][symbolIndex];
          } else {
            // I0 -> M1
            score[i][j] = score[i - 1][j - 1] * transitionProbabilities[1][2] * emissionProbabilities[2][symbolIndex];
          }
          edgeTo[i][j] = new Vertex(0, j - 1);
        } else {
          if (j == 1) {
            // D(k) -> M(k + 1)
            score[i][j] = score[i - 2][j - 1] * transitionProbabilities[i - 1][i + 1] * emissionProbabilities[i + 1][symbolIndex];
            edgeTo[i][j] = new Vertex(i - 2, 0);
          } else {
            // Max:
            //  M(k) -> M(k + 1)
            //  D(k) -> M(k + 1)
            //  I(k) -> M(k + 1)
            double p1 = score[i - 3][j - 1] * transitionProbabilities[i - 2][i + 1] * emissionProbabilities[i + 1][symbolIndex];
            double p2 = score[i - 2][j - 1] * transitionProbabilities[i - 1][i + 1] * emissionProbabilities[i + 1][symbolIndex];
            double p3 = score[i - 1][j - 1] * transitionProbabilities[i][i + 1] * emissionProbabilities[i + 1][symbolIndex];
            score[i][j] = Math.max(p1, Math.max(p2, p3));
            if (p1 == score[i][j]) {
              edgeTo[i][j] = new Vertex(i - 3, j - 1);
            } else if (p2 == score[i][j]) {
              edgeTo[i][j] = new Vertex(i - 2, j - 1);
            } else if (p3 == score[i][j]) {
              edgeTo[i][j] = new Vertex(i - 1, j - 1);
            }
          }
        }

        // Deletion states
        if (i == 1) {
          // T0 -> D1
          score[i + 1][j] = score[0][j] * transitionProbabilities[1][3];
          edgeTo[i + 1][j] = new Vertex(0, j);
        } else {
          double p1 = score[i - 3][j] * transitionProbabilities[i - 2][i + 2];
          double p2 = score[i - 2][j] * transitionProbabilities[i - 1][i + 2];
          double p3 = score[i - 1][j] * transitionProbabilities[i][i + 2];
          score[i + 1][j] = Math.max(p1, Math.max(p2, p3));
          if (p1 == score[i + 1][j]) {
            edgeTo[i + 1][j] = new Vertex(i - 3, j);
          } else if (p2 == score[i + 1][j]) {
            edgeTo[i + 1][j] = new Vertex(i - 2, j);
          } else if (p3 == score[i + 1][j]) {
            edgeTo[i + 1][j] = new Vertex(i - 1, j);
          }
        }

        // Insertion states
        if ( j == 1) {
          // D(k) -> I(k)
          score[i + 2][j] = score[i + 1][j - 1] * transitionProbabilities[i + 2][i] * emissionProbabilities[i][symbolIndex];
          edgeTo[i + 2][j] = new Vertex(i + 1, 0);
        } else {
          double p1 = score[i][j - 1] * transitionProbabilities[i + 1][i + 3] * emissionProbabilities[i + 3][symbolIndex];
          double p2 = score[i + 1][j - 1] * transitionProbabilities[i + 2][i + 3] * emissionProbabilities[i + 3][symbolIndex];
          double p3 = score[i + 2][j - 1] * transitionProbabilities[i + 4][i] * emissionProbabilities[i + 3][symbolIndex];
          score[i + 2][j] = Math.max(p1, Math.max(p2, p3));
          if (p1 == score[i + 2][j]) {
            edgeTo[i + 2][j] = new Vertex(i, j - 1);
          } else if (p2 == score[i + 2][j]) {
            edgeTo[i + 2][j] = new Vertex(i + 1, j - 1);
          } else if (p3 == score[i + 2][j]) {
            edgeTo[i + 2][j] = new Vertex(i + 2, j - 1);
          }
        }
      }
    }

    // Process E state (end)
    int i = score.length;
    int j = score[0].length - 1;
    double p1 = score[i - 3][j] * transitionProbabilities[transitionProbabilities.length - 4][transitionProbabilities[0].length - 1];
    double p2 = score[i - 2][j] * transitionProbabilities[transitionProbabilities.length - 3][transitionProbabilities[0].length - 1];
    double p3 = score[i - 1][j] * transitionProbabilities[transitionProbabilities.length - 2][transitionProbabilities[0].length - 1];
    double score = Math.max(p1, Math.max(p2, p3));
    if (p1 == score) {
      edgeToEnd = new Vertex(i - 3, j);
    } else     if (p2 == score) {
      edgeToEnd = new Vertex(i - 2, j);
    } else if (p3 == score) {
      edgeToEnd = new Vertex(i - 1, j);
    }
  }

  private static void buildAlignment() {
    Vertex curr = edgeToEnd;
    while (curr.i > 0 || curr.j > 0) {
      int a = curr.i / 3;
      int b = curr.i % 3;
      String state;
      if (curr.i == 0) {
        state = INSERTION + '0';
      } else if (b == 0) {
        state = INSERTION + a;
      } else if (b == 1) {
        state = MATCH + (a + 1);
      } else {
        state = DELETION + (a + 1);
      }
      alignment.add(0, state);
      curr = edgeTo[curr.i][curr.j];
    }
  }

  private static class Vertex {

    final int i;

    final int j;

    public Vertex(int i, int j) {
      this.i = i;
      this.j = j;
    }
  }
}
