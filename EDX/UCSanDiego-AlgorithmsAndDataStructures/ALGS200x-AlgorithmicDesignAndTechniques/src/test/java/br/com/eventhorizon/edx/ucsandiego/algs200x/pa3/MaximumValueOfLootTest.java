package br.com.eventhorizon.edx.ucsandiego.algs200x.pa3;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

import static br.com.eventhorizon.common.utils.Utils.getRandomInteger;

public class MaximumValueOfLootTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa3/maximum-value-of-loot.csv";

  public MaximumValueOfLootTest() {
    super(new MaximumValueOfLoot(), PATestSettings.builder()
            .timeLimitTestEnabled(true)
            .compareTestEnabled(true)
            .build());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.TRIVIAL, input, expectedOutput);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.FINAL, input, expectedOutput);
  }

  @ParameterizedTest
  @CsvSource({
    "1 10 500 30,166.6667"
  })
  public void testFinalSolutionWithComplexDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.FINAL, input, expectedOutput);
  }

  @Override
  protected String generateInput(PATestType type, StringBuilder expectedOutput) {
    int n = getRandomInteger(1, 1000);
    int capacity = getRandomInteger(0, 2000000);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(n).append(" ").append(capacity);
    for (int i = 0; i < n; i++) {
      int value = getRandomInteger(0, 2000000);
      int weight = getRandomInteger(0, 2000000);
      stringBuilder.append(" ").append(value).append(" ").append(weight);
    }
    return stringBuilder.toString();
  }
}
