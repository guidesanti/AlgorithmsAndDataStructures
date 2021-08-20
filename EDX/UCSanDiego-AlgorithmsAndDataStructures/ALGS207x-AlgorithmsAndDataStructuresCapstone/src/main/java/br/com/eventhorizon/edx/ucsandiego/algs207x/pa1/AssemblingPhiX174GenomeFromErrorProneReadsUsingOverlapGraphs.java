package br.com.eventhorizon.edx.ucsandiego.algs207x.pa1;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

public class AssemblingPhiX174GenomeFromErrorProneReadsUsingOverlapGraphs implements PA {

  private static final double LIMIT = 0.05;

  private static final Map<Character, Integer> ALPHABET = new HashMap<>();

  private static List<String> reads;

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
  }

  private static void writeOutput() {
    StringBuilder string = new StringBuilder();
    string.append(reads.get(path.get(0).from));
    for (Edge edge : path) {
      String label = reads.get(edge.to);
      string.append(label, edge.overlap.length, label.length());
    }
    for (int i = reads.get(0).length(); i > 1; i--) {
      if (string.substring(0, i).equals(string.substring(string.length() - i))) {
        string.delete(string.length() - i, string.length());
        break;
      }
    }
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
    long ini = System.currentTimeMillis();
    buildOverlapGraph();
    long end1 = System.currentTimeMillis();
    long diff1 = end1 - ini;
    findHamiltonianPath();
    long end2 = System.currentTimeMillis();
    long diff2 = end2 - end1;
    fixErrorReads();
    long end3 = System.currentTimeMillis();
    long diff3 = end3 - end2;
  }

  private static void buildOverlapGraph() {
    long trieMatchTime = 0;
    long ini;
    long end;
    long diff1;
    for (int i = 0; i < reads.size(); i++) {
      String read1 = reads.get(i);
      SuffixTrie trie = new SuffixTrie(read1);
      List<Edge> read1Adjacencies = new ArrayList<>();
      for (int j = 0; j < reads.size(); j++) {
        if (i == j) {
          continue;
        }
        String read2 = reads.get(j);
        ini = System.currentTimeMillis();
//        Overlap overlap = findMaximumOverlap1(read1, read2);
        Overlap overlap = findMaximumOverlap2(trie, read2);
        end = System.currentTimeMillis();
        diff1 = end - ini;
        trieMatchTime += diff1;
        if (overlap.length > 0) {
          read1Adjacencies.add(new Edge(i, j, overlap));
        }
      }
      adjacencies.add(read1Adjacencies);
    }
    long a = trieMatchTime;
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
    path.stream().filter(edge -> edge.overlap.hasErrors()).forEach(edge -> {
      int overlapLength = edge.overlap.length;
      edge.overlap.errors.forEach(pair -> {
        String read1 = reads.get(edge.from);
        String read2 = reads.get(edge.to);
        int index2 = pair.value2;
        int index1 = readLength - overlapLength + index2;
        Pair<String> kmers = buildKMers(read1, index1, read2, index2);
        String kmer1 = kmers.value1;
        String kmer2 = kmers.value2;
        if (read1.charAt(index1) != read2.charAt(index2)) {
          int count1 = countKMers(kmer1);
          int count2 = countKMers(kmer2);
          if (count1 > count2) {
            StringBuilder newRead2 = new StringBuilder(read2);
            newRead2.setCharAt(index2, read1.charAt(index1));
            reads.set(edge.to, newRead2.toString());
          } else {
            StringBuilder newRead1 = new StringBuilder(read1);
            newRead1.setCharAt(index1, read2.charAt(index2));
            reads.set(edge.from, newRead1.toString());
          }
        }
      });
    });
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
    for (int i = 1; i <= readLength; i++) {
      Overlap overlap = compare(read1, read2, i);
      if (overlap.percentage <= LIMIT) {
        maxOverlap = overlap;
      }
    }
    return maxOverlap;
  }

  private static Overlap findMaximumOverlap2(SuffixTrie read1SuffixTrie, String read2) {
    Overlap overlap = new Overlap(0, Collections.emptyList());
    findMaximumOverlapRecursive(read1SuffixTrie.root, read2, 0, overlap);
    return overlap;
  }

  private static boolean findMaximumOverlapRecursive(SuffixTrie.Node node, String read2, int read2Index, Overlap overlap) {
    if (!node.hasChildren()) {
      return true;
    }
    if (overlap.errors.size() >= 2) {
      return false;
    }
    char symbol = read2.charAt(read2Index);
    int symbolIndex = ALPHABET.get(symbol);
    if (node.next[symbolIndex] != null) {
      overlap.length++;
      findMaximumOverlapRecursive(node.next[symbolIndex], read2, read2Index + 1, overlap);
    } else {
      Overlap maxOverlap = null;
      for (int i = 0; i < 4; i++) {
        if (node.next[i] != null) {
          Overlap copy = overlap.copy();
          copy.length++;
          copy.errors.add(new Pair<>(-1, read2Index));
          if (findMaximumOverlapRecursive(node.next[i], read2, read2Index + 1, copy)) {
            if (copy.error() < LIMIT) {
              if (maxOverlap == null) {
                maxOverlap = copy;
              } else if (copy.length > maxOverlap.length) {
                maxOverlap = copy;
              }
            }
          }
        }
      }
      if (maxOverlap != null) {
        overlap.length = maxOverlap.length;
        overlap.errors = maxOverlap.errors;
      } else {
        overlap.length = 0;
      }
    }
    return true;
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

  private static Pair<String> buildKMers(String read1, int index1, String read2, int index2) {
    for (int i = 0; i < 6; i++) {
      if (index1 == 0 || index2 == 0) {
        break;
      }
      index1--;
      index2--;
    }
    String kmer1 = read1.substring(index1, index1 + 12);
    String kmer2 = read2.substring(index2, index2 + 12);
    return new Pair<>(kmer1, kmer2);
  }

  private static int countKMers(String kmer) {
    int count = 0;
    for (String read : reads) {
      if (read.contains(kmer)) {
        count++;
      }
    }
    return count;
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
  }

  private static class SuffixTrie {

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
        }
      }
    }

    private static class Node {

      private final Node[] next;

      public Node() {
        next = new Node[4];
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
