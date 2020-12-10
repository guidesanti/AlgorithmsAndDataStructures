package edx.pa1;

import edx.common.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;

public class SumOfTwoDigitsTest extends BaseTest {

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/pa1/sum-of-two-digits.csv", numLinesToSkip = 1)
  public void testMain(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    SumOfTwoDigits.main(null);
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }
}
