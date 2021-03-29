package br.com.eventhorizon.string.matching;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class RabinKarpTest extends StringMatchingAlgorithmTest {

  private static final String DATA_SET = "/test-dataset/string-matching.csv";

  public RabinKarpTest() {
    super(new RabinKarp());
  }

  @ParameterizedTest
  @CsvFileSource(resources = DATA_SET, numLinesToSkip = 1)
  public void testSort(String input, String expectedOutput) {
    super.testMatch(input, expectedOutput);
  }
}
