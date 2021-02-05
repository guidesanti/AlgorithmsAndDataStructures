package br.com.eventhorizon.edx.ucsandiego.algs200x.pa4;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.List;

public class OrganizingALottery implements PA {

  @Override
  public void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n, m;
    n = scanner.nextInt();
    m = scanner.nextInt();
    int[] starts = new int[n];
    int[] ends = new int[n];
    int[] points = new int[m];
    for (int i = 0; i < n; i++) {
      starts[i] = scanner.nextInt();
      ends[i] = scanner.nextInt();
    }
    for (int i = 0; i < m; i++) {
      points[i] = scanner.nextInt();
    }
    int[] cnt = naiveCountSegments(starts, ends, points);
    for (int x : cnt) {
      System.out.print(x + " ");
    }
  }

  private static int[] naiveCountSegments(int[] starts, int[] ends, int[] points) {
    int[] cnt = new int[points.length];
    for (int i = 0; i < points.length; i++) {
      for (int j = 0; j < starts.length; j++) {
        if (starts[j] <= points[i] && points[i] <= ends[j]) {
          cnt[i]++;
        }
      }
    }
    return cnt;
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int s = scanner.nextInt();
    int p = scanner.nextInt();
    List<Segment> segments = new ArrayList<>();
    int[] points = new int[p];
    for (int i = 0; i < s; i++) {
      segments.add(new Segment(scanner.nextInt(), scanner.nextInt(), 1));
    }
    for (int i = 0; i < p; i++) {
      points[i] = scanner.nextInt();
    }
    int[] counts = fastCountSegments(segments, points);
    for (int count : counts) {
      System.out.print(count + " ");
    }
  }

  private static int[] fastCountSegments(List<Segment> segments, int[] points) {
    List<Segment> sortedSegments = sortSegments(segments);
    int[] counts = new int[points.length];
    for (int i = 0; i < points.length; i++) {
      counts[i] = searchAndCountSegments(sortedSegments, 0, sortedSegments.size() - 1, points[i]);
    }
    return counts;
  }

  private static List<Segment> sortSegments(List<Segment> segments) {
    if (segments.size() <= 1) {
      return segments;
    }
    int middle = segments.size() / 2;
    List<Segment> lowSegments = sortSegments(segments.subList(0, middle));
    List<Segment> highSegments = sortSegments(segments.subList(middle, segments.size()));
    return sortSegmentsMerge(lowSegments, highSegments);
  }

  private static List<Segment> sortSegmentsMerge(List<Segment> lowSegments, List<Segment> highSegments) {
    lowSegments = new ArrayList<>(lowSegments);
    highSegments = new ArrayList<>(highSegments);
    List<Segment> mergedSegments = new ArrayList<>();
    while (!lowSegments.isEmpty() && !highSegments.isEmpty()) {
      Segment l = lowSegments.get(0);
      Segment h = highSegments.get(0);
      Segment s;
      if (l.end < h.start) {
        lowSegments.remove(l);
        s = l;
      } else if (h.end < l.start) {
        highSegments.remove(0);
        s = h;
      } else if (l.start == h.start) {
        if (l.end == h.end) {
          l.weight += h.weight;
          highSegments.remove(0);
          lowSegments.remove(0);
          s = l;
        } else if (l.end > h.end) {
          l.start = h.end + 1;
          h.weight += l.weight;
          highSegments.remove(0);
          s = h;
        } else {
          h.start = l.end + 1;
          l.weight += h.weight;
          lowSegments.remove(0);
          s = l;
        }
      } else {
        if (l.start < h.start) {
          s = new Segment(l.start, h.start - 1, l.weight);
          l.start = h.start;
        } else {
          s = new Segment(h.start, l.start - 1, h.weight);
          h.start = l.start;
        }
      }
      mergedSegments.add(s);
    }
    if (!lowSegments.isEmpty()) {
      mergedSegments.addAll(lowSegments);
    }
    if (!highSegments.isEmpty()) {
      mergedSegments.addAll(highSegments);
    }
    return mergedSegments;
  }

  private static int searchAndCountSegments(List<Segment> segments, int left, int right, int point) {
    int count = 0;
    int middle = (left + right) / 2;
    if (segments.get(middle).contains(point)) {
      return count + segments.get(middle).weight;
    }
    if (right - left == 0) {
      return count;
    }
    if (right - left == 1) {
      if (segments.get(middle + 1).contains(point)) {
        count += segments.get(middle + 1).weight;
      }
      return count;
    }
    if (segments.get(middle).start > point) {
      right = middle;
    } else {
      left = middle;
    }
    return searchAndCountSegments(segments, left, right, point);
  }

  public static class Segment {

    int start;

    int end;

    int weight;

    public Segment(int start, int end, int weight) {
      this.start = start;
      this.end = end;
      this.weight = weight;
    }

    boolean contains(int point) {
      return point >= start && point <= end;
    }
  }
}
