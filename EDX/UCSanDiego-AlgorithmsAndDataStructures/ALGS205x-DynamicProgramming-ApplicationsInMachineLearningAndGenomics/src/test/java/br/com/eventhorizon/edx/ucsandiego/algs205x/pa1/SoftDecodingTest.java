package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.PATest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SoftDecodingTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/soft-decoding.csv";

  private static final double DELTA = 0.01;

  private static final String DELIMITER = "--------";

  public SoftDecodingTest() {
    super(new SoftDecoding(), true, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    input = input.replace(" ", "\n");
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    pa.finalSolution();

    // Read expected values
    String[] rows = expectedOutput.split("%");
    List<String> expectedStates = new ArrayList<>(Arrays.asList(rows[0].trim().split("[ \\t]+")));
    double[][] expectedProbabilities = null;
    for (int i = 1; i < rows.length; i++) {
      String[] columns = rows[i].trim().split("[ \\t]+");
      if (expectedProbabilities == null) {
        expectedProbabilities = new double[rows.length - 1][columns.length];
      }
      for (int j = 0; j < columns.length; j++) {
        expectedProbabilities[i - 1][j] = Double.parseDouble(columns[j]);
      }
    }

    // Read actual values
    rows = getActualOutput().split("\n");
    List<String> actualStates = new ArrayList<>(Arrays.asList(rows[0].trim().split("[ \\t]+")));
    double[][] actualProbabilities = null;
    for (int i = 1; i < rows.length; i++) {
      String[] columns = rows[i].trim().split("[ \\t]+");
      if (actualProbabilities == null) {
        actualProbabilities = new double[rows.length - 1][columns.length];
      }
      for (int j = 0; j < columns.length; j++) {
        actualProbabilities[i - 1][j] = Double.parseDouble(columns[j]);
      }
    }

    assertEquals(expectedStates, actualStates);
    for (int i = 0; i < expectedProbabilities.length; i++) {
      for (int j = 0; j < expectedProbabilities[0].length; j++) {
        double expectedProbability = expectedProbabilities[i][j];
        double actualProbability = actualProbabilities[i][j];
        assertTrue(actualProbability >= expectedProbability - DELTA  &&
            actualProbability <= expectedProbability + DELTA);
      }
    }
  }
}
