package br.com.eventhorizon.problems;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.*;

class Range implements Comparable<Range> {

  public final long start;
  public final long end;

  public Range(long start, long end) {
    this.start = start;
    this.end = end;
  }

  @Override
  public int compareTo(Range other) {
    if (this.start != other.start) {
      return Long.compare(this.start, other.start);
    } else {
      return Long.compare(this.end, other.end);
    }
  }
}

public class NumberOfDistinctIntersections implements PA {

  private static int n;

  private static Long[] array;

  private static int numberOfIntersections;

  private static void init() {
    n = 0;
    array = null;
    numberOfIntersections = 0;
  }

  private static void readInput() {
    FastScanner scanner = new FastScanner(System.in);

    // Read N
    n = scanner.nextInt();
    if (n == 0) {
      return;
    }

    // Read the original array
    array = new Long[n];
    for (int i = 0; i < n; i++) {
      array[i] = (long)scanner.nextInt();
    }
  }

  private static void writeOutput() {
    System.out.print(numberOfIntersections);
  }

  @Override
  public void trivialSolution() {
    init();
    readInput();
    trivialSolutionImpl();
    writeOutput();
  }

  private static void trivialSolutionImpl() {
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        long d = Math.abs(i - j);
        long r1 = array[i];
        long r2 = array[j];
        if (d < r1 + r2) {
          numberOfIntersections++;
          if (numberOfIntersections > 10_000_000) {
            numberOfIntersections = -1;
            return;
          }
        }
      }
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
    List<Range> list = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      long r = array[i];
      list.add(new Range(i - r, i + r));
    }
    Collections.sort(list);
    while (list.size() > 1) {
      Range range1 = list.remove(0);
      for (Range range2 : list) {
        if (range2.start <= range1.end) {
          numberOfIntersections++;
          if (numberOfIntersections > 10_000_000) {
            numberOfIntersections = -1;
            return;
          }
        } else {
          break;
        }
      }
    }
  }
}
