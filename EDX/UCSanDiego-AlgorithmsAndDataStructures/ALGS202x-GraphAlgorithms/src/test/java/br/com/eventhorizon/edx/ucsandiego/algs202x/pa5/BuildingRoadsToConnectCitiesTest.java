package br.com.eventhorizon.edx.ucsandiego.algs202x.pa5;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.datastructures.sets.ArraySet;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class BuildingRoadsToConnectCitiesTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa5/building-roads-to-connect-cities.csv";

  public BuildingRoadsToConnectCitiesTest() {
    super(new BuildingRoadsToConnectCities());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testTrivialSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.TRIVIAL, input, expectedOutput);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.FINAL, input, expectedOutput);
  }

  @Override
  protected String generateInput(PATestType type, StringBuilder expectedOutput) {
    int n = Utils.getRandomInteger(1, 200);
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
