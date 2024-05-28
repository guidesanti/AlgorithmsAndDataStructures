package br.com.eventhorizon.problems;

import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static br.com.eventhorizon.common.utils.Utils.getRandomInteger;

public class CountriesCountTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/countries-count.csv";

  public CountriesCountTest() {
    super(new CountriesCount(), PATestSettings.builder()
        .compareTestEnabled(false)
        .stressTestEnabled(false)
        .timeLimitTestEnabled(true)
        .timeLimit(5000)
        .build());
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
    StringBuilder sb = new StringBuilder();

    int numRows = getRandomInteger(1, 3_000);
    int numCols = getRandomInteger(1, 3_000);

    sb.append(numRows).append(" ").append(numCols).append("\n");
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        sb.append(getRandomInteger(-1_000_000_000, 1_000_000_000)).append(" ");
      }
      sb.append("\n");
    }

    return sb.toString();
  }
}
