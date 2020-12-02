package br.com.eventhorizon.edx;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SumOfTwoDigitsTest extends BaseTest {

  @ParameterizedTest
  @CsvFileSource(resources = "/test-dataset/sum-of-two-digits.csv", numLinesToSkip = 1)
  public void testMain(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    SumOfTwoDigits.main(null);
    assertEquals(expectedOutput, getActualOutput());
  }
}
