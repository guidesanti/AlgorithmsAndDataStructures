package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.PATest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LikelihoodOfAnOutcomeTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/likelihood-of-an-outcome.csv";

  private static final double DELTA = 0.00001;

  public LikelihoodOfAnOutcomeTest() {
    super(new LikelihoodOfAnOutcome(), true, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    double expectedProbability = Double.parseDouble(expectedOutput);

    System.setIn(new ByteArrayInputStream(input.getBytes()));
    pa.finalSolution();

    double actualProbability = Double.parseDouble(getActualOutput());

    assertTrue(actualProbability + DELTA >= expectedProbability &&
        actualProbability - DELTA <= expectedProbability);
  }
}
