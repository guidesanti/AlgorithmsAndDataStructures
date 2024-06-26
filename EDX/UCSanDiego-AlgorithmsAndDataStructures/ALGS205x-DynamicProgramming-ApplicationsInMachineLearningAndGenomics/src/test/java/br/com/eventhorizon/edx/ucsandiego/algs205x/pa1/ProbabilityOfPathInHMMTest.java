package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.test.PATestBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProbabilityOfPathInHMMTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/probability-of-path-in-hmm.csv";

  private static final double DELTA = 0.00001;

  public ProbabilityOfPathInHMMTest() {
    super(new ProbabilityOfPathInHMM());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    double expectedProbability = Double.parseDouble(expectedOutput);

    reset(input);
    pa.finalSolution();

    double actualProbability = Double.parseDouble(getActualOutput());

    assertTrue(actualProbability + DELTA >= expectedProbability &&
        actualProbability - DELTA <= expectedProbability);
  }
}
