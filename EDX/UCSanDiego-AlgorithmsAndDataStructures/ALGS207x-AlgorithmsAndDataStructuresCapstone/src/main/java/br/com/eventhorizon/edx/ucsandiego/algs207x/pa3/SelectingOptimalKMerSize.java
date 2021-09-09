package br.com.eventhorizon.edx.ucsandiego.algs207x.pa3;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

public class SelectingOptimalKMerSize implements PA {

  private static final Map<Character, Integer> ALPHABET = new HashMap<>();

  private static List<String> reads;

  private static SuffixTrie[] tries;

  private static int[][] overlaps;

  private static Map<String, List<Edge>> adjacencies;

  private static Map<String, Integer> inDegrees;

  private static List<String> kMers;

  private static Map<String, Integer> kmerCount;

  private static int edgeCount;

  private static int readLength;

  private static int optimalK;

  static {
    ALPHABET.put('A', 0);
    ALPHABET.put('C', 1);
    ALPHABET.put('G', 2);
    ALPHABET.put('T', 3);
  }

  private static void init() {
    reads = new ArrayList<>();
    tries = null;
    adjacencies = new HashMap<>();
    inDegrees = new HashMap<>();
    kMers = new ArrayList<>();
    kmerCount = new HashMap<>();
    edgeCount = 0;
    readLength = 0;
    optimalK = 0;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    String kmer = scanner.next();
    while (kmer != null) {
      reads.add(kmer);
      kmer = scanner.next();
    }
    readLength = reads.get(0).length();
  }

  private static void writeOutput() {
    System.out.print("" + (optimalK));
  }

  @Override
  public void finalSolution() {
    init();
    readInput();
    finalSolutionImpl();
    writeOutput();
  }

  private static void finalSolutionImpl() {
    buildSuffixTries();
    computeOverlaps();
    optimalK = 2;
    while (optimalK < readLength) {
      reset();
      generateKMers2();
      buildDeBruijnGraph();
      if (isPath()) {
        break;
      }
      optimalK++;
    }
  }

  private static void buildSuffixTries() {
    tries = new SuffixTrie[reads.size()];
    for (int i = 0; i < reads.size(); i++) {
      tries[i] = new SuffixTrie(reads.get(i));
    }
  }

  private static void computeOverlaps() {
    overlaps = new int[reads.size()][reads.size()];
    for (int i = 0; i < reads.size(); i++) {
      for (int j = 0; j < reads.size(); j++) {
        if (i == j) {
          continue;
        }

        SuffixTrie.Node node = tries[i].root;
        int index = 0;
        String read = reads.get(j);
        while (true) {
          char symbol = read.charAt(index);
          int symbolIndex = ALPHABET.get(symbol);
          if (node.next[symbolIndex] == null) {
            if (node.hasChildren()) {
              index = 0;
            }
            break;
          }
          index++;
          node = node.next[symbolIndex];
        }
        overlaps[i][j] = index;
      }
    }
  }

  private static void generateKMers1() {
    boolean [] processedReads = new boolean[reads.size()];
    Queue<Integer> unprocessedReads = new LinkedList<>();
    for (int i = 0; i < reads.size(); i++) {
      unprocessedReads.add(i);
    }
    int curr = unprocessedReads.remove();
    while (!unprocessedReads.isEmpty()) {
            int prev = -1;
            int prevOverlap = 0;
            for (int i = 0; i < overlaps.length; i++) {
              if (overlaps[i][curr] > prevOverlap) {
                prevOverlap = overlaps[i][curr];
                prev = i;
              }
            }
            boolean isPrevProcessed = prev != -1 && processedReads[prev];

      int next = -1;
      int nextOverlap = 0;
      for (int i = 0; i < overlaps[curr].length; i++) {
        if (overlaps[curr][i] > nextOverlap) {
          nextOverlap = overlaps[curr][i];
          next = i;
        }
      }
      boolean isNextProcessed = next != -1 && processedReads[next];

      if (!isPrevProcessed && !isNextProcessed) {
        generateKMers(reads.get(curr), 0, readLength - optimalK);
      } else if (isPrevProcessed && !isNextProcessed) {
        generateKMers(reads.get(curr), prevOverlap - optimalK + 1, readLength - optimalK);
      } else if (!isPrevProcessed) {
        generateKMers(reads.get(curr), 0, readLength - nextOverlap - 1);
      } else {
        generateKMers(reads.get(curr), prevOverlap - optimalK + 1, readLength - nextOverlap - 1);
      }

      generateKMers(reads.get(curr), prevOverlap - optimalK + 1, readLength - optimalK);
      processedReads[curr] = true;
      unprocessedReads.remove(curr);

      if (next != -1) {
        curr = next;
      } else if (!unprocessedReads.isEmpty()) {
        curr = unprocessedReads.peek();
      }
    }
  }

  private static void generateKMers2() {
    boolean [] processedReads = new boolean[reads.size()];
    Queue<Integer> unprocessedReads = new LinkedList<>();
    for (int i = 0; i < reads.size(); i++) {
      unprocessedReads.add(i);
    }
    int curr = unprocessedReads.remove();
    processedReads[curr] = true;
    generateKMers(reads.get(curr), 0, readLength - optimalK);
    while (true) {
      boolean stop = false;
      int next = -1;
      int overlap = 0;
      for (int i = 0; i < overlaps[curr].length; i++) {
        if (overlaps[curr][i] > overlap) {
          overlap = overlaps[curr][i];
          next = i;
        }
      }
      curr = next;
      int nextOverlap = 0;
      for (int i = 0; i < overlaps[curr].length; i++) {
        if (processedReads[i] && overlaps[curr][i] > nextOverlap) {
          nextOverlap = overlaps[curr][i];
        }
        if (processedReads[i] && overlaps[curr][i] >= optimalK) {
          stop = true;
        }
      }
      if (stop) {
        if (!processedReads[curr]) {
          generateKMers(reads.get(curr), overlap - optimalK + 1, readLength - nextOverlap - 1);
        }
        break;
      }
      generateKMers(reads.get(curr), overlap - optimalK + 1, readLength - optimalK);
      processedReads[curr] = true;
      unprocessedReads.remove(curr);
    }
  }

  private static void generateKMers(String read, int start, int end) {
    while (start <= end) {
      String kmer = read.substring(start, start + optimalK);
      int count = kmerCount.getOrDefault(kmer, 0);
      count++;
      kmerCount.put(kmer, count);
      start++;
      kMers.add(kmer);
    }
  }

  private static void buildDeBruijnGraph() {
    for (String kmer : kMers) {
      String from = kmer.substring(0, kmer.length() - 1);
      String to = kmer.substring(1);
      List<Edge> fromAdjacencies = adjacencies.getOrDefault(from, new ArrayList<>());
      fromAdjacencies.add(new Edge(from, to));
      adjacencies.put(from, fromAdjacencies);
      int count = inDegrees.getOrDefault(from, 0);
      inDegrees.put(from, count);
      count = inDegrees.getOrDefault(to, 0);
      count++;
      inDegrees.put(to, count);
      edgeCount++;
    }
  }

  private static boolean isPath() {
    List<String> sources = new ArrayList<>();
    inDegrees.forEach((key, count) -> {
      if (count == 0) {
        sources.add(key);
      }
    });
    if (sources.size() > 1) {
      return false;
    }

    for (List<Edge> edges : adjacencies.values()) {
      if (edges.size() > 1) {
        return false;
      }
    }

    int count = 0;
    String curr;
    if (!sources.isEmpty()) {
      curr = sources.get(0);
    } else {
      curr = adjacencies.values().stream().findFirst().get().stream().findFirst().get().from;
    }
    while (true) {
      List<Edge> adj = adjacencies.get(curr);
      if (count == edgeCount || adj == null || adj.size() == 0) {
        break;
      }
      if (adj.size() > 1) {
        return false;
      }
      curr = adj.get(0).to;
      count++;
    }

    return count == edgeCount;
  }

  private static void reset() {
    edgeCount = 0;
    adjacencies.clear();
    inDegrees.clear();
    kMers.clear();
    kmerCount.clear();
  }

  private static class SuffixTrie {

    SuffixTrie.Node root;

    SuffixTrie(String read) {
      root = new SuffixTrie.Node();
      for (int i = read.length() - 1; i >= 0; i--) {
        SuffixTrie.Node curr = root;
        for (int j = i; j < read.length(); j++) {
          char symbol = read.charAt(j);
          int symbolIndex = ALPHABET.get(symbol);
          if (curr.next[symbolIndex] == null) {
            curr.next[symbolIndex] = new SuffixTrie.Node();
          }
          curr = curr.next[symbolIndex];
        }
      }
    }

    public static class Node {

      final SuffixTrie.Node[] next;

      Node() {
        next = new SuffixTrie.Node[ALPHABET.size()];
      }

      boolean hasChildren() {
        for (SuffixTrie.Node child : next) {
          if (child != null) {
            return true;
          }
        }
        return false;
      }
    }
  }

  private static class Edge {

    final String from;

    final String to;

    public Edge(String from, String to) {
      this.from = from;
      this.to = to;
    }

    @Override
    public String toString() {
      return from + "->" + to;
    }
  }
}
