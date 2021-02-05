package br.com.eventhorizon.edx.ucsandiego.algs200x.pa3;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectingSignatures implements PA {

  @Override
  public void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    Segment[] segments = new Segment[n];
    for (int i = 0; i < n; i++) {
      int start, end;
      start = scanner.nextInt();
      end = scanner.nextInt();
      segments[i] = new Segment(start, end);
    }
    int[] points = naiveOptimalPoints(segments);
    System.out.println(points.length);
    for (int point : points) {
      System.out.print(point + " ");
    }
  }

  private int[] naiveOptimalPoints(Segment[] segments) {
    int maxLengthSegmentIndex = findMaxSegment(segments);
    int[] points = new int[100];
    int nPoints = 0;
    while (maxLengthSegmentIndex >= 0) {
      points[nPoints] = findMaxPoint1(segments, maxLengthSegmentIndex);
      nPoints++;
      maxLengthSegmentIndex = findMaxSegment(segments);
    }
    int [] result = Arrays.copyOf(points, nPoints);
    return result;
  }

  private int findMaxSegment(Segment[] segments) {
    int maxLength = 0;
    int maxLengthSegmentIndex = -1;
    for (int i = 0; i < segments.length; i++) {
      if (segments[i] != null && segments[i].length >= maxLength) {
        maxLength = segments[i].length;
        maxLengthSegmentIndex = i;
      }
    }
    return maxLengthSegmentIndex;
  }

  private int findMaxPoint1(Segment[] segments, int segment) {
    int start = segments[segment].start;
    int end = segments[segment].end;
    int max = 0;
    int maxPoint = -1;
    segments[segment] = null;
    for (int i = start; i <= end; i++) {
      int current = 0;
      for (int j = 0; j < segments.length; j++) {
        if (segments[j] != null && segments[j].contains(i)) {
          current++;
        }
      }
      if (current >= max) {
        max = current;
        maxPoint = i;
      }
    }
    for (int i = 0; i < segments.length; i++) {
      if (segments[i] != null && segments[i].contains(maxPoint)) {
        segments[i] = null;
      }
    }
    return maxPoint >= 0 ? maxPoint : end;
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    List<Segment> segments = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      int start, end;
      start = scanner.nextInt();
      end = scanner.nextInt();
      segments.add(new Segment(start, end));
    }
    List<Integer> points = greedyOptimalPoints(segments);
    System.out.println(points.size());
    for (int point : points) {
      System.out.print(point + " ");
    }
  }

  private List<Integer> greedyOptimalPoints(List<Segment> segments) {
    Segment currentSegment = findNextSegment(segments);
    List<Integer> points = new ArrayList<>();
    while (currentSegment != null) {
      segments.remove(currentSegment);
      points.add(findMaxPoint2(segments, currentSegment.start, currentSegment.end));
      currentSegment = findNextSegment(segments);
    }
    return points;
  }

  private Segment findNextSegment(List<Segment> segments) {
    int start = Integer.MAX_VALUE;
    Segment firstSegment = null;
    for (Segment segment : segments) {
      if (segment.start < start) {
        start = segment.start;
        firstSegment = segment;
      }
    }
    return firstSegment;
  }

  private int findMaxPoint2(List<Segment> segments, int start, int end) {
    int maxPoint = end;
    while (!segments.isEmpty()) {
      Segment nextSegment = findNextSegment(segments);
      if (nextSegment.start <= end) {
        if (nextSegment.end < end) {
          end = nextSegment.end;
          maxPoint = end;
        }
        segments.remove(nextSegment);
      } else {
        break;
      }
    }
    return maxPoint;
  }

  private static class Segment {
    int start;
    int end;
    int length;

    Segment(int start, int end) {
      this.start = start;
      this.end = end;
      this.length = end - start;
    }

    public boolean contains(int x) {
      if (x >= this.start && x <= this.end) {
        return true;
      }
      return false;
    }
  }
}
