package br.com.eventhorizon.edx.ucsandiego.algs207x.pa1;

import br.com.eventhorizon.common.datastructures.strings.Alphabet;
import br.com.eventhorizon.common.datastructures.strings.UkkonenSuffixTree;
import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;
import java.util.stream.Collectors;

public class AssemblingPhiX174GenomeFromErrorProneReadsUsingOverlapGraphs implements PA {

  private static final double LIMIT = 0.05;

  private static final char[] ALPHABET_SYMBOLS = { 'A', 'C', 'G', 'T' };

  private static final Alphabet ALPHABET = new Alphabet(ALPHABET_SYMBOLS);

  private static List<String> reads;

  private static List<int[]> errorCount;

  private static int readLength;

  private static List<List<Edge>> adjacencies;

  private static List<Edge> path;

  private static void init() {
    reads = new ArrayList<>();
    errorCount = new ArrayList<>();
    readLength = 0;
    adjacencies = new ArrayList<>();
    path = new ArrayList<>();
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read reads
    String str = scanner.next();
    while (str != null) {
      reads.add(str);
      str = scanner.next();
    }

    // Set read length
    readLength = reads.get(0).length();

    for (int i = 0; i < reads.size(); i++) {
      errorCount.add(new int[readLength]);
    }
  }

  private static void writeOutput() {
    StringBuilder string = new StringBuilder();
    string.append(reads.get(path.get(0).from));
    for (Edge edge : path) {
      String label = reads.get(edge.to);
      string.append(label, edge.overlap.length, label.length());
    }
    int maxI = 0;
    for (int i = 1; i <= readLength; i++) {
      if (string.substring(0, i).equals(string.substring(string.length() - i))) {
        maxI = i;
      }
    }
    string.delete(string.length() - maxI, string.length());
    System.out.println(string.toString());
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    finalSolutionImpl();
    writeOutput();
  }

  private static void finalSolutionImpl() {
    long a = System.currentTimeMillis();
    buildOverlapGraph1();
    long diff1 = System.currentTimeMillis() - a;
    a = System.currentTimeMillis();
    findHamiltonianPath();
    long diff2 = System.currentTimeMillis() - a;
    a = System.currentTimeMillis();
    fixErrorReads();
    long diff3 = System.currentTimeMillis() - a;
  }

  private static void buildOverlapGraph1() {
    BurrowsWheelerTransform[] bwt = new BurrowsWheelerTransform[reads.size()];
    for (int i = 0; i < reads.size(); i++) {
      bwt[i] = new BurrowsWheelerTransform(reads.get(i));
      adjacencies.add(new ArrayList<>());
    }

    for (int i = 0; i < reads.size(); i++) {
      String read1 = reads.get(i);
      BurrowsWheelerTransform read1Bwt = bwt[i];
      for (int j = 0; j < reads.size(); j++) {
        if (i == j) {
          continue;
        }
        String read2 = reads.get(j);
        for (int k = 0; k <= 48; k += 12) {
          List<Integer> matches = read1Bwt.match(read2, k, 12);
          if (!matches.isEmpty()) {
            Collections.sort(matches);
            for (int offset : matches) {
              int potentialOverlapLength = readLength - offset + k;
              if (potentialOverlapLength >= readLength) {
                continue;
              }
              Overlap overlap = overlap(read1, read2, potentialOverlapLength);
              if (overlap != null) {
                adjacencies.get(i).add(new Edge(i, j, overlap));
                for (Pair<Integer> pair1 : overlap.errors) {
                  errorCount.get(i)[pair1.value1]++;
                  errorCount.get(j)[pair1.value2]++;
                }
                break;
              }
            }
            break;
          }
        }
      }
    }
  }

  private static void buildOverlapGraph2() {
    SuffixArray[] suffixArrays = new SuffixArray[reads.size()];
    for (int i = 0; i < reads.size(); i++) {
      suffixArrays[i] = new SuffixArray(reads.get(i));
      adjacencies.add(new ArrayList<>());
    }

    for (int i = 0; i < reads.size(); i++) {
      String read1 = reads.get(i);
      SuffixArray read1SuffixArray = suffixArrays[i];
      for (int j = 0; j < reads.size(); j++) {
        if (i == j) {
          continue;
        }
        String read2 = reads.get(j);
        for (int k = 0; k <= 48; k += 12) {
          List<Integer> matches = read1SuffixArray.match(read2, k, 12);
          if (!matches.isEmpty()) {
            Collections.sort(matches);
            for (int offset : matches) {
              int potentialOverlapLength = readLength - offset + k;
              if (potentialOverlapLength >= readLength) {
                continue;
              }
              Overlap overlap = overlap(read1, read2, potentialOverlapLength);
              if (overlap != null) {
                adjacencies.get(i).add(new Edge(i, j, overlap));
                for (Pair<Integer> pair1 : overlap.errors) {
                  errorCount.get(i)[pair1.value1]++;
                  errorCount.get(j)[pair1.value2]++;
                }
                break;
              }
            }
            break;
          }
        }
      }
    }
  }

  private static void buildOverlapGraph3() {
    long a = System.currentTimeMillis();
    SuffixTree[] suffixTrees = new SuffixTree[reads.size()];
    for (int i = 0; i < reads.size(); i++) {
      suffixTrees[i] = new SuffixTree(ALPHABET, reads.get(i));
      adjacencies.add(new ArrayList<>());
    }
    long diff1 = System.currentTimeMillis() - a;

    for (int i = 0; i < reads.size(); i++) {
      SuffixTree read1SuffixTree = suffixTrees[i];
      for (int j = 0; j < reads.size(); j++) {
        if (i == j) {
          continue;
        }
        String read2 = reads.get(j);
        Overlap overlap = read1SuffixTree.match(read2);
        if (overlap != null && overlap.length > 0) {
          adjacencies.get(i).add(new Edge(i, j, overlap));
          for (Pair<Integer> pair1 : overlap.errors) {
            errorCount.get(i)[pair1.value1]++;
            errorCount.get(j)[pair1.value2]++;
          }
        }
      }
    }
  }

  private static void buildOverlapGraph4() {
    SuffixTree[] suffixTrees = new SuffixTree[reads.size()];
    for (int i = 0; i < reads.size(); i++) {
      suffixTrees[i] = new SuffixTree(ALPHABET, reads.get(i));
      adjacencies.add(new ArrayList<>());
    }

    for (int i = 0; i < reads.size(); i++) {
      String read1 = reads.get(i);
      SuffixTree read1SuffixTree = suffixTrees[i];
      for (int j = 0; j < reads.size(); j++) {
        if (i == j) {
          continue;
        }
        String read2 = reads.get(j);
        for (int k = 0; k <= 48; k += 12) {
          List<Integer> matches = read1SuffixTree.match(read2, k, 12);
          if (!matches.isEmpty()) {
            Collections.sort(matches);
            for (int offset : matches) {
              int potentialOverlapLength = readLength - offset + k;
              if (potentialOverlapLength >= readLength) {
                continue;
              }
              Overlap overlap = overlap(read1, read2, potentialOverlapLength);
              if (overlap != null) {
                adjacencies.get(i).add(new Edge(i, j, overlap));
                for (Pair<Integer> pair1 : overlap.errors) {
                  errorCount.get(i)[pair1.value1]++;
                  errorCount.get(j)[pair1.value2]++;
                }
                break;
              }
            }
            break;
          }
        }
      }
    }
  }

  private static void findHamiltonianPath() {
    boolean[] marked = new boolean[adjacencies.size()];
    adjacencies.forEach(edges -> edges.sort(Comparator.comparingInt(o -> o.overlap.length)));
    marked[0] = true;
    findHamiltonianPathRecursive(0, marked);
  }

  private static boolean findHamiltonianPathRecursive(int curr, boolean[] marked) {
    if (isHamiltonianPath(marked)) {
      return true;
    }
    List<Edge> edges = adjacencies.get(curr);
    for (int i = edges.size() - 1; i >= 0; i--) {
      Edge edge = edges.get(i);
      if (marked[edge.to]) {
        continue;
      }
      marked[edge.to] = true;
      path.add(edge);
      if (findHamiltonianPathRecursive(edge.to, marked)) {
        return true;
      }
      marked[edge.to] = false;
      path.remove(path.size() - 1);
    }
    return false;
  }

  private static void fixErrorReads() {
    for (int i = 0; i < reads.size(); i++) {
      fixErrorReads(i);
    }
  }

  private static void fixErrorReads(int readIndex) {
    String read1 = reads.get(readIndex);
    Map<Character, Integer> symbolCount = new HashMap<>();

    // Find error index
    int errorIndex = -1;
    int max = 0;
    for (int i = 0; i < readLength; i++) {
      int count = errorCount.get(readIndex)[i];
      if (count > max) {
        max = count;
        errorIndex = i;
      }
    }
    if (errorIndex == -1) {
      return;
    }

    // Edges to 'readIndex'
    List<Edge> edgesTo = new ArrayList<>();
    adjacencies.forEach(edges1 -> edges1.stream()
        .filter(edge -> edge.to == readIndex)
        .filter(edge -> edge.overlap.length > 50)
        .forEach(edgesTo::add));
    for (Edge edge : edgesTo) {
      if (errorIndex < edge.overlap.length) {
        int read2ErrorIndex =  readLength - edge.overlap.length + errorIndex;
        String read2 = reads.get(edge.to);
        char symbol = read2.charAt(read2ErrorIndex);
        int count = symbolCount.getOrDefault(symbol, 0);
        count++;
        symbolCount.put(symbol, count);
      }
    }

    // Edges from 'readIndex'
    List<Edge> edgesFrom = adjacencies.get(readIndex).stream().filter(edge -> edge.overlap.length > 50).collect(Collectors.toList());
    for (Edge edge : edgesFrom) {
      if (errorIndex >= readLength - edge.overlap.length) {
        int read2ErrorIndex = errorIndex - (readLength - edge.overlap.length);
        String read2 = reads.get(edge.to);
        char symbol = read2.charAt(read2ErrorIndex);
        int count = symbolCount.getOrDefault(symbol, 0);
        count++;
        symbolCount.put(symbol, count);
      }
    }

    final char[] maxSymbol = new char[1];
    final int[] maxSymbolCount = { 0 };
    symbolCount.forEach((symbol, count) -> {
      if (maxSymbolCount[0] < count) {
        maxSymbol[0] = symbol;
        maxSymbolCount[0] = count;
      }
    });
    if (maxSymbol[0] != 0 && read1.charAt(errorIndex) != maxSymbol[0]) {
      String newRead = read1.substring(0, errorIndex) + maxSymbol[0] + read1.substring(errorIndex + 1);
      reads.set(readIndex, newRead);
    } else {
      int a = 10;
    }
  }

  private static boolean isHamiltonianPath(boolean[] marked) {
    for (boolean b : marked) {
      if (!b) {
        return false;
      }
    }
    return true;
  }

  private static Overlap overlap(String read1, String read2, int overlapLength) {
    int index1 = readLength - overlapLength;
    int index2 = 0;
    List<Pair<Integer>> errors = new ArrayList<>();
    while (index1 < readLength) {
      if (read1.charAt(index1) != read2.charAt(index2)) {
        errors.add(new Pair<>(index1, index2));
        if (errors.size() > 2) {
          return null;
        }
      }
      index1++;
      index2++;
    }
    double error = (double) errors.size() / overlapLength;
    if (error < LIMIT) {
      return new Overlap(overlapLength, errors);
    }
    return null;
  }

  private static class Edge {

    final int from;

    final int to;

    final Overlap overlap;

    Edge(int from, int to, Overlap overlap) {
      this.from = from;
      this.to = to;
      this.overlap = overlap;
    }

    @Override
    public String toString() {
      return from + "->" + to + ":" + overlap.length;
    }
  }

  public static class Overlap {

    private final int length;

    private final List<Pair<Integer>> errors;

    Overlap(int length, List<Pair<Integer>> errors) {
      this.length = length;
      this.errors = new ArrayList<>(errors);
    }
  }

  private static class Pair<T> {

    T value1;

    T value2;

    public Pair(T value1, T value2) {
      this.value1 = value1;
      this.value2 = value2;
    }

    @Override
    public boolean equals(Object obj) {
      Pair<T> o = (Pair<T>) obj;
      return value1.equals(o.value1) && value2.equals(o.value2);
    }

    @Override
    public int hashCode() {
      return (value1 + ":" + value2).hashCode();
    }
  }

  public static class BurrowsWheelerTransform {

    private static final char EOF = '$';

    private final String text;

    private final int[] suffixArray;

    private final String burrowsWheelerTransform;

    private final Map<Character, Integer> symbolCount;

    private final Map<Character, Integer> firstColumnSymbolOffsets;

    private final int[] lastToFirstColumnMap;

//    private final int[][] countArray;

    BurrowsWheelerTransform(String text) {
      this.text = text;
      suffixArray = buildSuffixArray();
      burrowsWheelerTransform = buildBurrowsWheelerTransform();
      symbolCount = countSymbols();
      firstColumnSymbolOffsets = calculateFirstColumnSymbolOffsets();
      lastToFirstColumnMap = calculateLastToFirstColumnMap();
//      countArray = computeCountArray();
    }

    private String buildBurrowsWheelerTransform() {
      StringBuilder burrowsWheelerTransform = new StringBuilder();
      for (int firstColumnIndex : suffixArray) {
        if (firstColumnIndex > 0) {
          burrowsWheelerTransform.append(charAt(text, firstColumnIndex - 1));
        } else {
          burrowsWheelerTransform.append(EOF);
        }
      }
      return burrowsWheelerTransform.toString();
    }

    private int[] buildSuffixArray() {
      int[] order = sortCharacters(text);
      int[] classes = computeCharacterClasses(text, order);
      for (int length = 1; length <= text.length(); length *= 2) {
        order = sortDoubledShifts(text, length, order, classes);
        classes = updateClasses(order, classes, length);
      }
      return order;
    }

    private int[] sortCharacters(String text) {
      int[] order = new int[text.length() + 1];
      Map<Character, Integer> count = new HashMap<>();

      // Discover all possible symbols in the text and count the occurrence of each one
      for (int i = 0; i <= text.length(); i++) {
        char symbol = charAt(text, i);
        count.put(symbol, count.getOrDefault(symbol, 0) + 1);
      }

      // For each possible symbol, calculate the position in the sorted array of all the symbols
      // of the text right after the last such symbol
      List<Character> sortedSymbols = count.keySet().stream().sorted().collect(Collectors.toList());
      for (int i = 1; i < sortedSymbols.size(); i++) {
        char prevSymbol = sortedSymbols.get(i - 1);
        char symbol = sortedSymbols.get(i);
        count.put(symbol, count.get(symbol) + count.get(prevSymbol));
      }

      // Calculate the order of the characters within text
      for (int i = text.length(); i >= 0; i--) {
        char symbol = charAt(text, i);
        int position = count.get(symbol) - 1;
        count.put(symbol, position);
        order[position] = i;
      }

      return order;
    }

    private int[] computeCharacterClasses(String text, int[] order) {
      int[] classes = new int[order.length];
      classes[order[0]] = 0;
      for (int i = 1; i < order.length; i++) {
        if (charAt(text, order[i]) == charAt(text, order[i - 1])) {
          classes[order[i]] = classes[order[i - 1]];
        } else {
          classes[order[i]] = classes[order[i - 1]] + 1;
        }
      }
      return classes;
    }

    private int[] sortDoubledShifts(String text, int length, int[] order, int[] classes) {
      int[] count = new int[order.length];
      int[] newOrder = new int[order.length];

      // Count the number of times each class occurs in the text
      for (int i = 0; i <= text.length(); i++) {
        count[classes[i]] = count[classes[i]] + 1;
      }

      // For each class, calculate the position in the sorted array of the last such class
      for (int i = 1; i <= text.length(); i++) {
        count[i] = count[i] + count[i - 1];
      }

      // Calculate the order of the doubled shifts
      for (int i = text.length(); i >= 0; i--) {
        int doubledShiftStart = (order[i] - length + text.length() + 1) % (text.length() + 1);
        int clazz = classes[doubledShiftStart];
        count[clazz] = count[clazz] - 1;
        newOrder[count[clazz]] = doubledShiftStart;
      }

      return newOrder;
    }

    private int[] updateClasses(int[] order, int[] classes, int length) {
      int[] newClasses = new int[order.length];
      newClasses[order[0]] = 0;
      for (int i = 1; i < order.length; i++) {
        int curr = order[i];
        int prev = order[i - 1];
        int midCurr = (curr + length) % order.length;
        int midPrev = (prev + length) % order.length;
        if (classes[curr] != classes[prev] ||
            classes[midCurr] != classes[midPrev]) {
          newClasses[curr] = newClasses[prev] + 1;
        } else {
          newClasses[curr] = newClasses[prev];
        }
      }
      return newClasses;
    }

    private char charAt(String text, int index) {
      return index < text.length() ? text.charAt(index) : EOF;
    }

    private int symbolIndex(char symbol) {
      switch (symbol) {
        case '$':
          return 0;
        case 'A':
          return 1;
        case 'C':
          return 2;
        case 'G':
          return 3;
        case 'T':
          return 4;
        default:
          throw new RuntimeException("Invalid symbol " + symbol);
      }
    }

    List<Integer> match(String pattern, int start, int length) {
      List<Integer> shifts = new ArrayList<>();
      int index = start + length - 1;
      char symbol = pattern.charAt(index--);
      if (!firstColumnSymbolOffsets.containsKey(symbol)) {
        return shifts;
      }
      int top = firstColumnSymbolOffsets.get(symbol);
      int bottom = top + symbolCount.get(symbol) - 1;
      while (top <= bottom) {
        if (index >= start) {
          symbol = pattern.charAt(index--);
          boolean stop = false;
          while (burrowsWheelerTransform.charAt(top) != symbol) {
            top++;
            if (top > bottom) {
              stop = true;
              break;
            }
          }
          if (stop) {
            break;
          }
          while (burrowsWheelerTransform.charAt(bottom) != symbol) {
            bottom--;
            if (bottom < top) {
              stop = true;
              break;
            }
          }
          if (stop) {
            break;
          }
          top = lastToFirstColumnMap[top];
          bottom = lastToFirstColumnMap[bottom];
        } else {
          for (int i = top; i <= bottom; i++) {
            shifts.add(suffixArray[i]);
          }
          break;
        }
      }
      return shifts;
    }

//    List<Integer> match2(String pattern, int start, int length) {
//      List<Integer> shifts = new ArrayList<>();
//      int index = start + length - 1;
//      int top = 0;
//      int bottom = burrowsWheelerTransform.length() - 1;
//      while (top <= bottom) {
//        if (index >= start) {
//          char symbol = pattern.charAt(index--);
//          int symbolIndex = symbolIndex(symbol);
//          top = firstColumnSymbolOffsets.get(symbol) + countArray[symbolIndex][top];
//          bottom = firstColumnSymbolOffsets.get(symbol) + countArray[symbolIndex][bottom + 1] - 1;
//        } else {
//          for (int i = top; i <= bottom; i++) {
//            shifts.add(suffixArray[i]);
//          }
//          break;
//        }
//      }
//      return shifts;
//    }

    private Map<Character, Integer> countSymbols() {
      Map<Character, Integer> symbolCount = new HashMap<>();
      for (int index = 0; index < burrowsWheelerTransform.length(); index++) {
        char symbol = burrowsWheelerTransform.charAt(index);
        symbolCount.put(symbol, symbolCount.getOrDefault(symbol, 0) + 1);
      }
      return symbolCount;
    }

    private Map<Character, Integer> calculateFirstColumnSymbolOffsets() {
      List<Character> symbols = symbolCount.keySet().stream().sorted().collect(Collectors.toList());
      symbols.remove((Character) '$');
      symbols.add(0, '$');
      Map<Character, Integer> symbolOffsets = new HashMap<>();
      int offset = 0;
      for (char symbol : symbols) {
        symbolOffsets.put(symbol, offset);
        offset += symbolCount.get(symbol);
      }
      return symbolOffsets;
    }

    private int[] calculateLastToFirstColumnMap() {
      Map<Character, Integer> symbolCount = new HashMap<>();
      int[] lastToFirstColumnMap = new int[burrowsWheelerTransform.length()];
      for (int index = 0; index < burrowsWheelerTransform.length(); index++) {
        char symbol = burrowsWheelerTransform.charAt(index);
        int count = symbolCount.getOrDefault(symbol, 0);
        lastToFirstColumnMap[index] = firstColumnSymbolOffsets.get(symbol) + count;
        count++;
        symbolCount.put(symbol, count);
      }
      return lastToFirstColumnMap;
    }

    private int[][] computeCountArray() {
      int [][] countArray = new int[5][burrowsWheelerTransform.length() + 1];
      for (int i = 1; i <= burrowsWheelerTransform.length(); i++) {
        for (int j = 0; j < 5; j++) {
          countArray[j][i] = countArray[j][i - 1];
        }
        countArray[symbolIndex(charAt(burrowsWheelerTransform, i - 1))][i]++;
      }
      return countArray;
    }
  }

  public static class SuffixArray {

    private static final char SPECIAL_SYMBOL = 0;

    private final String text;

    private final int[] suffixArray;

    SuffixArray(String text) {
      this.text = text;
      suffixArray = buildSuffixArray();
    }

    private int[] buildSuffixArray() {
      int[] order = sortCharacters(text);
      int[] classes = computeCharacterClasses(order);
      for (int length = 1; length <= text.length(); length *= 2) {
        order = sortDoubledShifts(text, length, order, classes);
        classes = updateClasses(order, classes, length);
      }
      return order;
    }

    private int[] sortCharacters(String text) {
      int[] order = new int[text.length() + 1];
      Map<Character, Integer> count = new HashMap<>();

      // Discover all possible symbols in the text and count the occurrence of each one
      for (int i = 0; i <= text.length(); i++) {
        char symbol = charAt(i);
        count.put(symbol, count.getOrDefault(symbol, 0) + 1);
      }

      // For each possible symbol, calculate the position in the sorted array of all the symbols
      // of the text right after the last such symbol
      List<Character> sortedSymbols = count.keySet().stream().sorted().collect(Collectors.toList());
      for (int i = 1; i < sortedSymbols.size(); i++) {
        char prevSymbol = sortedSymbols.get(i - 1);
        char symbol = sortedSymbols.get(i);
        count.put(symbol, count.get(symbol) + count.get(prevSymbol));
      }

      // Calculate the order of the characters within text
      for (int i = text.length(); i >= 0; i--) {
        char symbol = charAt(i);
        int position = count.get(symbol) - 1;
        count.put(symbol, position);
        order[position] = i;
      }

      return order;
    }

    private int[] computeCharacterClasses(int[] order) {
      int[] classes = new int[order.length];
      classes[order[0]] = 0;
      for (int i = 1; i < order.length; i++) {
        if (charAt(order[i]) == charAt(order[i - 1])) {
          classes[order[i]] = classes[order[i - 1]];
        } else {
          classes[order[i]] = classes[order[i - 1]] + 1;
        }
      }
      return classes;
    }

    private int[] sortDoubledShifts(String text, int length, int[] order, int[] classes) {
      int[] count = new int[order.length];
      int[] newOrder = new int[order.length];

      // Count the number of times each class occurs in the text
      for (int i = 0; i <= text.length(); i++) {
        count[classes[i]] = count[classes[i]] + 1;
      }

      // For each class, calculate the position in the sorted array of the last such class
      for (int i = 1; i <= text.length(); i++) {
        count[i] = count[i] + count[i - 1];
      }

      // Calculate the order of the doubled shifts
      for (int i = text.length(); i >= 0; i--) {
        int doubledShiftStart = (order[i] - length + text.length() + 1) % (text.length() + 1);
        int clazz = classes[doubledShiftStart];
        count[clazz] = count[clazz] - 1;
        newOrder[count[clazz]] = doubledShiftStart;
      }

      return newOrder;
    }

    private int[] updateClasses(int[] order, int[] classes, int length) {
      int[] newClasses = new int[order.length];
      newClasses[order[0]] = 0;
      for (int i = 1; i < order.length; i++) {
        int curr = order[i];
        int prev = order[i - 1];
        int midCurr = (curr + length) % order.length;
        int midPrev = (prev + length) % order.length;
        if (classes[curr] != classes[prev] ||
            classes[midCurr] != classes[midPrev]) {
          newClasses[curr] = newClasses[prev] + 1;
        } else {
          newClasses[curr] = newClasses[prev];
        }
      }
      return newClasses;
    }

    private char charAt(int index) {
      return index < text.length() ? text.charAt(index) : SPECIAL_SYMBOL;
    }

    public List<Integer> match(String pattern, int offset, int length) {
      List<Integer> matches = new ArrayList<>();
      int start = 0;
      int end = text.length();
      for (int i = 0; i < length; i++) {
        int minIndex = start;
        int maxIndex = end;
        while (minIndex < maxIndex) {
          int middleIndex = (minIndex + maxIndex) / 2;
          if (charAt(suffixArray[middleIndex] + i) < pattern.charAt(offset + i)) {
            minIndex = middleIndex + 1;
          } else {
            maxIndex = middleIndex;
          }
        }
        start = minIndex;
        maxIndex = end;
        while (minIndex < maxIndex) {
          int middleIndex = (minIndex + maxIndex) / 2;
          if (charAt(suffixArray[middleIndex] + i) > pattern.charAt(offset + i)) {
            maxIndex = middleIndex;
          } else {
            minIndex = middleIndex + 1;
          }
        }
        end = maxIndex;
        if (start >= end) {
          break;
        }
      }
      while (start < end) {
        matches.add(suffixArray[start++]);
      }
      return matches;
    }
  }

  public static class SuffixTree {

    private static final char EMPTY_CHAR = 0;

    private final Alphabet alphabet;

    private final String text;

    private final SuffixTree.Node root;

    SuffixTree(Alphabet alphabet, String text) {
      this.alphabet = new Alphabet(alphabet.symbols());
      this.alphabet.add(EMPTY_CHAR);
      this.text = text + EMPTY_CHAR;
      this.root = new SuffixTree.Node();
      buildSuffixTree();
    }

    List<Integer> match(String pattern, int patternIndex, int maxLength) {
      List<Integer> matches = new ArrayList<>();
      int symbolIndex = alphabet.symbolToIndex(pattern.charAt(patternIndex));
      if (root.children[symbolIndex] != null) {
        match(root.children[symbolIndex], pattern, patternIndex, 0, maxLength, matches);
      }
      return matches;
    }

    private void match(Node node, String pattern, int patternIndex, int length, int maxLength, List<Integer> matches) {
      if (length < maxLength) {
        int textIndex = node.start;
        int end = node.isLeaf() ? node.end.value - 1 : node.end.value;
        while (textIndex <= end) {
          char tch = text.charAt(textIndex);
          char pch = pattern.charAt(patternIndex);
          if (text.charAt(textIndex) != pattern.charAt(patternIndex)) {
            return;
          }
          textIndex++;
          patternIndex++;
          length++;
          if (length >= maxLength) {
            for (Node child : node.children) {
              if (child != null) {
                match(child, pattern, patternIndex, length, maxLength, matches);
              }
            }
            if (node.isLeaf()) {
              matches.add(node.suffixIndex);
            }
            return;
          }
        }
        int symbolIndex = alphabet.symbolToIndex(pattern.charAt(patternIndex));
        if (node.children[symbolIndex] != null) {
          match(node.children[symbolIndex], pattern, patternIndex, length, maxLength, matches);
        }
        return;
      }

      if (node.isLeaf()) {
        matches.add(node.suffixIndex);
        return;
      }
      for (Node child : node.children) {
        if (child != null) {
          match(child, pattern, patternIndex, length, maxLength, matches);
        }
      }
    }

    Overlap match(String pattern) {
      return match(root, pattern, 0, 0, new ArrayList<>(), new Overlap(0, new ArrayList<>()));
    }

    private Overlap match(Node node, String pattern, int patternIndex, int length, List<Pair<Integer>> mismatches, Overlap maxOverlap) {
      List<Pair<Integer>> localMismatches = new ArrayList<>(mismatches);

      if (!node.isRoot()) {
        int textIndex = node.start;
        int end = node.isLeaf() ? node.end.value - 1 : node.end.value;
        while (textIndex <= end) {
          if (text.charAt(textIndex) != pattern.charAt(patternIndex)) {
            localMismatches.add(new Pair<>(-1, patternIndex));
            if (localMismatches.size() >= 3) {
              return maxOverlap;
            }
          }
          textIndex++;
          patternIndex++;
          length++;
        }
        if (node.isLeaf()) {
          double error = (double) localMismatches.size() / length;
          if (length > maxOverlap.length && error < LIMIT) {
            int finalLength = length;
            localMismatches.forEach(p -> {
              p.value1 = (text.length() - 1 - finalLength + p.value2);
            });
            maxOverlap = new Overlap(length, localMismatches);
          }
          return maxOverlap;
        }
      }

      for (Node child : node.children) {
        if (child != null) {
          Overlap overlap = match(child, pattern, patternIndex, length, localMismatches, maxOverlap);
          if (overlap.length > maxOverlap.length) {
            maxOverlap = overlap;
          }
        }
      }

      return maxOverlap;
    }

    private void buildSuffixTree() {
      int leafCount = 0;
      SuffixTree.End end = new SuffixTree.End(0);

      // Initialize active point composed by activeNode, activeEdge and activeLength
      SuffixTree.Node activeNode = root;
      int activeEdge = -1;
      int activeLength = 0;

      int remainingSuffixCount = 0;
      SuffixTree.Node lastCreatedNode = null;

      for (int index = 0; index < text.length(); index++) {
        // Rule 1: add current character to all leaves
        end.set(index);

        // Increment remaining suffix count
        remainingSuffixCount++;

        while (remainingSuffixCount > 0) {
          // Handle active point change for active length zero
          if (activeLength == 0) {
            activeEdge = index;
          }

          int activeEdgeIndex = alphabet.symbolToIndex(text.charAt(activeEdge));

          if (activeNode.children[activeEdgeIndex] == null) {
            // Rule 2: add new leaf
            activeNode.children[activeEdgeIndex] = new SuffixTree.Node(index, end, leafCount);
            leafCount++;
            if (lastCreatedNode != null) {
              lastCreatedNode.suffixLink = activeNode;
              lastCreatedNode = null;
            }
          } else {
            // Handle active point for walk down
            SuffixTree.Node next = activeNode.children[activeEdgeIndex];
            int edgeLength = next.end.value - next.start + 1;
            if (activeLength >= edgeLength)  {
              activeEdge += edgeLength;
              activeLength -= edgeLength;
              activeNode = next;
              continue;
            }

            // Rule 3:
            if (text.charAt(next.start + activeLength) == text.charAt(index)) {
              if (lastCreatedNode != null) {
                lastCreatedNode.suffixLink = activeNode;
                lastCreatedNode = null;
              }

              // Handle active point for rule 3
              activeLength++;
              // Rule 3 is show stopper
              break;
            }

            // Rule 2: add new leaf ad new internal node
            SuffixTree.Node leaf = new SuffixTree.Node(index, end, leafCount);
            leafCount++;
            SuffixTree.Node intermediate = new SuffixTree.Node(next.start, new SuffixTree.End(next.start + activeLength - 1));
            next.start += activeLength;
            intermediate.children[alphabet.symbolToIndex(text.charAt(next.start))] = next;
            intermediate.children[alphabet.symbolToIndex(text.charAt(leaf.start))] = leaf;
            activeNode.children[alphabet.symbolToIndex(text.charAt(intermediate.start))] = intermediate;
            if (lastCreatedNode != null) {
              lastCreatedNode.suffixLink = intermediate;
            }
            lastCreatedNode = intermediate;
          }

          remainingSuffixCount--;

          if (activeNode == root && activeLength > 0) {
            // Handle active point change for rule 2 case 1
            activeLength--;
            activeEdge = index - remainingSuffixCount + 1;
          } else if (activeNode != root) {
            // Handle active point change for rule 2 case 2
            activeNode = activeNode.suffixLink;
          }
        }
      }
    }

    private static class End {

      int value;

      End(int value) {
        this.value = value;
      }

      public int get() {
        return value;
      }

      public void set(int value) {
        this.value = value;
      }

      @Override
      public String toString() {
        return "" + value;
      }
    }

    class Node {

      private final SuffixTree.Node[] children;

      private int start;

      private final SuffixTree.End end;

      private final int suffixIndex;

      private SuffixTree.Node suffixLink;

      public Node() {
        this.children = new SuffixTree.Node[alphabet.size()];
        this.start = -1;
        this.end = new SuffixTree.End(-1);
        this.suffixIndex = -1;
      }

      public Node(int start, SuffixTree.End end) {
        this.children = new SuffixTree.Node[alphabet.size()];
        this.start = start;
        this.end = end;
        this.suffixIndex = -1;
      }

      public Node(int start, SuffixTree.End end, int suffixIndex) {
        this.children = new SuffixTree.Node[alphabet.size()];
        this.start = start;
        this.end = end;
        this.suffixIndex = suffixIndex;
      }

      public boolean isRoot() {
        return start < 0;
      }

      public boolean isLeaf() {
        return suffixIndex >= 0;
      }

      @Override
      public String toString() {
        if (start == -1) {
          return "ROOT";
        }
        return "Node{" + text.substring(start, end.value + 1) + "}";
      }
    }
  }
}
