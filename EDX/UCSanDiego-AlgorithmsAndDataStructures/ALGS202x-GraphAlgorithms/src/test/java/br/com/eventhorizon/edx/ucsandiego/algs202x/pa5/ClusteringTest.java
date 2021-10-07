package br.com.eventhorizon.edx.ucsandiego.algs202x.pa5;

import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.datastructures.sets.ArraySet;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class ClusteringTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa5/clustering.csv";

  public ClusteringTest() {
    super(new Clustering());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @Override
  protected String generateInput(PATestType type) {
    int n = Utils.getRandomInteger(2, 200);
    ArraySet<Point> points = new ArraySet<>();
    int[] x = new int[n];
    int[] y = new int[n];
    for (int i = 0; i < n; i++) {
      Point point = new Point(Utils.getRandomInteger(-1000, 1000), Utils.getRandomInteger(-1000, 1000));
      while (points.contains(point)) {
        point = new Point(Utils.getRandomInteger(-1000, 1000), Utils.getRandomInteger(-1000, 1000));
      }
      points.add(point);
      x[i] = point.x;;
      y[i] = point.y;
    }
    StringBuilder str = new StringBuilder();
    str.append(n);
    for (int i = 0; i < n; i++) {
      str.append(" ").append(x[i]).append(" ").append(y[i]);
    }
    str.append(" ").append(Utils.getRandomInteger(2, n));
    return str.toString();
  }

  private static class Point {

    int x;

    int y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
      return (x == ((Point)obj).x && y == ((Point)obj).y);
    }
  }
}
