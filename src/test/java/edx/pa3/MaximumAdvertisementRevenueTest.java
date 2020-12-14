package edx.pa3;

import edx.common.PATest;
import edx.common.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

public class MaximumAdvertisementRevenueTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa3/maximum-advertisement-revenue.csv";

  public MaximumAdvertisementRevenueTest() {
    super(new MaximumAdvertisementRevenue());
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
      "3 -100 10 100 100 0 -100,20000",
      "8 -76008 67014 -87850 -47909 35180 -20170 -93418 57269 45842 -82994 -44989 77293 18139 -49661 -67490 -88006,32371952927",
      "3 -77625 61721 -33340 1423 55661 98207,1693306302"
  })
  public void testNaveSolutionWithComplexDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput);
  }

  @ParameterizedTest
  @CsvSource({
      "3 -100 10 100 100 0 -100,20000",
      "8 -76008 67014 -87850 -47909 35180 -20170 -93418 57269 45842 -82994 -44989 77293 18139 -49661 -67490 -88006,32371952927",
      "3 -77625 61721 -33340 1423 55661 98207,1693306302"
  })
  public void testFinalSolutionWithComplexDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput);
  }

  @Override
  protected String generateInput(PATestType type) {
    StringBuilder stringBuilder = new StringBuilder();
    int n = getRandomInteger(1, 1000);
    stringBuilder.append(n);
    for (int i = 0; i < n; i++) {
      stringBuilder.append(" ").append(getRandomInteger(1, 200000) - 100000);
    }
    for (int i = 0; i < n; i++) {
      stringBuilder.append(" ").append(getRandomInteger(1, 200000) - 100000);
    }
    return stringBuilder.toString();
  }
}
