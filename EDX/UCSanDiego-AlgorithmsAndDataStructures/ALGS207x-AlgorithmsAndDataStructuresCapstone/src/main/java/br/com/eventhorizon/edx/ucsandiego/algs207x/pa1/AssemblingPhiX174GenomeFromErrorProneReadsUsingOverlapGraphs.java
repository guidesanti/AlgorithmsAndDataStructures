package br.com.eventhorizon.edx.ucsandiego.algs207x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;
import java.util.stream.Collectors;

public class AssemblingPhiX174GenomeFromErrorProneReadsUsingOverlapGraphs implements PA {

  private static final double LIMIT = 0.05;

  private static final Map<Character, Integer> ALPHABET = new HashMap<>();

  private static List<String> reads;

  private static List<int[]> errorCount;

  private static int readLength;

  private static List<List<Edge>> adjacencies;

  private static List<Edge> path;

  static {
    ALPHABET.put('A', 0);
    ALPHABET.put('C', 1);
    ALPHABET.put('G', 2);
    ALPHABET.put('T', 3);
  }

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
    long ini = System.currentTimeMillis();
    init();
    readInput();
    finalSolutionImpl();
    writeOutput();
    long end = System.currentTimeMillis();
    long diff = end - ini;
  }

  private static void finalSolutionImpl() {
    long ini = System.currentTimeMillis();
    buildOverlapGraph3();
    long end1 = System.currentTimeMillis();
    long diff1 = end1 - ini;
    findHamiltonianPath();
    long end2 = System.currentTimeMillis();
    long diff2 = end2 - end1;
    fixErrorReads();
    long end3 = System.currentTimeMillis();
    long diff3 = end3 - end2;
  }

  private static void buildOverlapGraph1() {
    for (int i = 0; i < reads.size(); i++) {
      String read1 = reads.get(i);
//      SuffixTrie trie = new SuffixTrie(read1);
      List<Edge> read1Adjacencies = new ArrayList<>();
      for (int j = 0; j < reads.size(); j++) {
        if (i == j) {
          continue;
        }
        String read2 = reads.get(j);
        Overlap overlap = findMaximumOverlap1(read1, read2);
//        Overlap overlap = findMaximumOverlap2(trie, read2);
        if (overlap.length > 0 && overlap.error() < LIMIT) {
          read1Adjacencies.add(new Edge(i, j, overlap));
          for (Pair<Integer> pair : overlap.errors) {
//            errorCount.get(i)[readLength - overlap.length + pair.value2]++;
            errorCount.get(i)[pair.value1]++;
            errorCount.get(j)[pair.value2]++;
          }
        }
      }
      adjacencies.add(read1Adjacencies);
    }
  }

  private static void buildOverlapGraph2() {
    Set<Pair<Integer>> pairs = new HashSet<>();
    for (int i = 0; i < reads.size(); i++) {
      adjacencies.add(new ArrayList<>());
      String read1 = reads.get(i);
      for (int j = 0; j < reads.size(); j++) {
        if (i == j) {
          continue;
        }
        String read2 = reads.get(j);
        for (int k = 0; k <= 15; k++) {
          int count = 0;
          for (int l = 0; l < 12; l++) {
            if (read2.charAt(l) != read1.charAt(k + l)) {
              count++;
              if (count > 2) {
                break;
              }
            }
          }
          if (count <= 2) {
            pairs.add(new Pair<>(i, j));
            break;
          }
        }
      }
    }

    for (Pair<Integer> pair : pairs) {
      int i = pair.value1;
      int j = pair.value2;
      String read1 = reads.get(i);
      String read2 = reads.get(j);
      Overlap overlap1 = findMaximumOverlap1(read1, read2);
      List<Edge> edges1 = adjacencies.get(i);
      if (overlap1.length > 0 && overlap1.error() <= LIMIT) {
        edges1.add(new Edge(i, j, overlap1));
        for (Pair<Integer> pair1 : overlap1.errors) {
          errorCount.get(i)[pair1.value1]++;
          errorCount.get(j)[pair1.value2]++;
        }
      }
    }
  }

  private static void buildOverlapGraph3() {
    SuffixTrie[] tries = new SuffixTrie[reads.size()];
    for (int i = 0; i < reads.size(); i++) {
      tries[i] = new SuffixTrie(reads.get(i));
      adjacencies.add(new ArrayList<>());
    }

    List<Pair<Integer>> pairs = new ArrayList<>();
    for (int i = 0; i < reads.size(); i++) {
      SuffixTrie read1SuffixTrie = tries[i];
      for (int j = 0; j < reads.size(); j++) {
        if (i == j) {
          continue;
        }
        String read2 = reads.get(j);
        if (suffixTrieMatch1(read1SuffixTrie.root, read2, 0, 12, 0, 2)) {
          pairs.add(new Pair<>(i, j));
        }
      }
    }

    for (Pair<Integer> pair : pairs) {
      int i = pair.value1;
      int j = pair.value2;
      String read1 = reads.get(i);
      String read2 = reads.get(j);
      Overlap overlap = findMaximumOverlap1(read1, read2);
      if (overlap.length > 0 && overlap.error() < LIMIT) {
        adjacencies.get(i).add(new Edge(i, j, overlap));
        for (Pair<Integer> pair1 : overlap.errors) {
          errorCount.get(i)[pair1.value1]++;
          errorCount.get(j)[pair1.value2]++;
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

  private static Overlap findMaximumOverlap1(String read1, String read2) {
    Overlap maxOverlap = new Overlap(0, Collections.emptyList());
    for (int i = readLength - 1; i >= 0; i--) {
      Overlap overlap = compare(read1, read2, i);
      if (overlap.percentage <= LIMIT) {
        maxOverlap = overlap;
        break;
      }
    }
    return maxOverlap;
  }

  public static Overlap findMaximumOverlap2(SuffixTrie read1SuffixTrie, String read2) {
    Overlap maxOverlap = new Overlap(0, Collections.emptyList());
    findMaximumOverlapRecursive(read1SuffixTrie.root, read2, 0, new Overlap(0, Collections.emptyList()), maxOverlap);
    return maxOverlap;
  }

  private static void findMaximumOverlapRecursive(SuffixTrie.Node node, String read2, int read2Index, Overlap overlap, Overlap maxOverlap) {
    if (overlap.errors.size() >= 5) {
      return;
    }
    if (node.present) {
      double error = overlap.error();
      if (overlap.length > maxOverlap.length && error <= LIMIT) {
        maxOverlap.length = overlap.length;
        maxOverlap.errors.clear();
        maxOverlap.errors.addAll(overlap.errors);
      }
    }
    if (!node.hasChildren()) {
      return;
    }
    char symbol = read2.charAt(read2Index);
    int symbolIndex = ALPHABET.get(symbol);
    for (int i = 0; i < 4; i++) {
      if (node.next[i] != null) {
        Overlap copy = overlap.copy();
        copy.length++;
        if (i != symbolIndex) {
          copy.errors.add(new Pair<>(-1, read2Index));
        }
        findMaximumOverlapRecursive(node.next[i], read2, read2Index + 1, copy, maxOverlap);
      }
    }
  }

  private static Overlap compare(String read1, String read2, int overlapLength) {
    int index1 = readLength - overlapLength;
    int index2 = 0;
    List<Pair<Integer>> errors = new ArrayList<>();
    while (index1 < readLength) {
      if (read1.charAt(index1) != read2.charAt(index2)) {
        errors.add(new Pair<>(index1, index2));
        if (errors.size() > 5) {
          break;
        }
      }
      index1++;
      index2++;
    }
    return new Overlap(overlapLength, errors);
  }

  public static boolean suffixTrieMatch1(SuffixTrie.Node node, String pattern, int patternIndex, int length, int currentMismatch, int mismatchLimit) {
    if (currentMismatch > mismatchLimit) {
      return false;
    }
    if (patternIndex >= length) {
      return true;
    }
    char symbol = pattern.charAt(patternIndex);
    int symbolIndex = ALPHABET.get(symbol);
    patternIndex++;
    if (node.next[symbolIndex] != null) {
      if (suffixTrieMatch1(node.next[symbolIndex], pattern, patternIndex, length, currentMismatch, mismatchLimit)) {
        return true;
      }
    }
    currentMismatch++;
    for (int i = 0; i < ALPHABET.size(); i++) {
      if (node.next[i] != null && i != symbolIndex) {
        if (suffixTrieMatch1(node.next[i], pattern, patternIndex, length, currentMismatch, mismatchLimit)) {
          return true;
        }
      }
    }
    return false;
  }

  public static boolean suffixTrieMatch2(SuffixTrie.Node node, String pattern, int patternIndex, int length, int currentMismatch, int mismatchLimit) {
    while (true) {
      if (currentMismatch > mismatchLimit) {
        return false;
      }
      if (patternIndex >= length) {
        return true;
      }
      char symbol = pattern.charAt(patternIndex);
      int symbolIndex = ALPHABET.get(symbol);
      patternIndex++;
      if (node.next[symbolIndex] != null) {
        node = node.next[symbolIndex];
      }
      currentMismatch++;
      for (int i = 0; i < ALPHABET.size(); i++) {
        if (node.next[i] != null && i != symbolIndex) {
          if (suffixTrieMatch1(node.next[i], pattern, patternIndex, length, currentMismatch, mismatchLimit)) {
            return true;
          }
        }
      }
      return false;
    }
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

  private static class Overlap {

    double percentage;

    int length;

    List<Pair<Integer>> errors;

    public Overlap(int length, List<Pair<Integer>> errors) {
      this.percentage = length == 0 ? 0.0 : (double) errors.size() / length;
      this.length = length;
      this.errors = new ArrayList<>(errors);
    }

    boolean hasErrors() {
      return !errors.isEmpty();
    }

    double error() {
      return (double) errors.size() / length;
    }

    Overlap copy() {
      return new Overlap(length, errors);
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

  public static class SuffixTrie {

    Node root;

    SuffixTrie(String read) {
      root = new Node();
      for (int i = read.length() - 1; i >= 0; i--) {
        Node curr = root;
        for (int j = i; j < read.length(); j++) {
          char symbol = read.charAt(j);
          int symbolIndex = ALPHABET.get(symbol);
          if (curr.next[symbolIndex] == null) {
            curr.next[symbolIndex] = new Node();
          }
          curr = curr.next[symbolIndex];
          curr.chainLength[symbolIndex]++;
        }
        curr.present = true;
      }
    }

    public static class Node {

      private boolean present;

      private final int[] chainLength;

      private final Node[] next;

      public Node() {
        chainLength = new int[ALPHABET.size()];
        next = new Node[ALPHABET.size()];
      }

      public boolean hasChildren() {
        for (Node child : next) {
          if (child != null) {
            return true;
          }
        }
        return false;
      }
    }
  }
}
