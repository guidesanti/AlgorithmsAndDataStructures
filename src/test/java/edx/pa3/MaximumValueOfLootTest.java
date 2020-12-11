package edx.pa3;

import edx.common.PATest;
import edx.common.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

public class MaximumValueOfLootTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa3/maximum-value-of-loot.csv";

  public MaximumValueOfLootTest() {
    super(new MaximumValueOfLoot());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput);
  }

  @ParameterizedTest
  @CsvSource({
    "1 10 500 30,166.6667"
  })
  public void testFinalSolutionWithComplexDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput);
  }

  @Override
  protected String generateInput(PATestType type) {
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
