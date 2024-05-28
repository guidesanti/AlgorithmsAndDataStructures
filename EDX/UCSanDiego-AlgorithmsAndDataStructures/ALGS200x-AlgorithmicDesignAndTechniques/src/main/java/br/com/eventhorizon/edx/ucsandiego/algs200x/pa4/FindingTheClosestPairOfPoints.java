package br.com.eventhorizon.edx.ucsandiego.algs200x.pa4;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.List;

public class FindingTheClosestPairOfPoints implements PA {

  @Override
  public void trivialSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    List<Point> points = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      points.add(new Point(scanner.nextLong(), scanner.nextLong()));
    }
    System.out.format("%.4f", naiveMinimalDistance(points));
  }

  private static double naiveMinimalDistance(List<Point> points) {
    double minDistance = Double.POSITIVE_INFINITY;
    while (points.size() >= 2) {
      Point p = points.remove(0);
      for (int i = 0; i < points.size(); i++) {
        double distance = p.distance(points.get(i));
        if (distance < minDistance) {
          minDistance = distance;
        }
      }
    }
    return minDistance;
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    List<Point> points = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      points.add(new Point(scanner.nextLong(), scanner.nextLong()));
    }
    points.sort(Point::compareToByX);
    System.out.format("%.4f", finalMinimalDistance(points));
  }

  private static double finalMinimalDistance(List<Point> points) {
    if (points.size() <= 1) {
      return Double.POSITIVE_INFINITY;
    }
    if (points.size() == 2) {
      return points.get(0).distance(points.get(1));
    }
    int middle = points.size() / 2;
    List<Point> subPoints1 = points.subList(0, middle);
    List<Point> subPoints2 = points.subList(middle, points.size());
    double minDistance1 = finalMinimalDistance(subPoints1);
    double minDistance2 = finalMinimalDistance(subPoints2);
    return combine(subPoints1, subPoints2, Math.min(minDistance1, minDistance2));
  }

  private static double combine(List<Point> points1, List<Point> points2, double d) {
    double middleX = (points1.get(points1.size() - 1).x + points2.get(0).x) / 2.0;
    List<Point> points = new ArrayList<>();
    for (int i = points1.size() - 1; i >= 0; i--) {
      if (middleX - points1.get(i).x <= d) {
        points.add(points1.get(i));
      }
    }
    for (int i = 0; i < points2.size(); i++) {
      if (points2.get(i).x - middleX <= d) {
        points.add(points2.get(i));
      }
    }
    points.sort(Point::compareToByY);
    return Math.min(d, minimalDistance(points));
  }

  private static double minimalDistance(List<Point> points) {
    double minDistance = Double.POSITIVE_INFINITY;
    while (points.size() >= 2) {
      Point p = points.remove(0);
      for (int i = 0; i < points.size() && i <= 7; i++) {
        double distance = p.distance(points.get(i));
        if (distance < minDistance) {
          minDistance = distance;
        }
      }
    }
    return minDistance;
  }

  private static class Point {

    long x;

    long y;

    public Point(long x, long y) {
      this.x = x;
      this.y = y;
    }

    public double distance(Point point) {
      long xDiff = this.x - point.x;
      long yDiff = this.y - point.y;
      long xDiffSq = xDiff * xDiff;
      long yDiffSq = yDiff * yDiff;
      return Math.sqrt(xDiffSq + yDiffSq);
    }

    public int compareToByY(Point point) {
      return point.y == y ? Long.signum(x - point.x) : Long.signum(y - point.y);
    }

    public int compareToByX(Point point) {
      return point.x == x ? Long.signum(y - point.y) : Long.signum(x - point.x);
    }

    @Override
    public String toString() {
      return "{" + this.x + ", " + this.y + "}";
    }
  }
}
