package br.com.eventhorizon.edx.ucsandiego.algs201x.pa3;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import br.com.eventhorizon.common.pa.TestProperties;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class FindPatterInTextTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa3/find-pattern-in-text.csv";

  public FindPatterInTextTest() {
    super(new FindPatterInText());
  }

  @BeforeAll
  public static void setup() {
    System.setProperty(TestProperties.TIME_LIMIT_IN_MS_PROPERTY_KEY, "5000");
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @Test
  public void testFinalSolutionWithWorstCase() {
    StringBuilder input = new StringBuilder();
    for (int i = 0; i < 10000; i++) {
      input.append("a");
    }
    input.append(" ");
    for (int i = 0; i < 500000; i++) {
      input.append("a");
    }
    System.setIn(new ByteArrayInputStream(input.toString().getBytes()));
    assertTimeoutPreemptively(ofMillis(TestProperties.getTimeLimit()), pa::finalSolution);
  }

  @Override
  protected String generateInput(PATestType type) {
    int textLength;
    int patternLength;
    switch (type) {
      case TIME_LIMIT_TEST:
        textLength = Utils.getRandomInteger(1, 500000);
        patternLength = textLength == 1 ? textLength : Utils.getRandomInteger(1, textLength);
        return Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, patternLength) +
            " " + Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, textLength);
      case STRESS_TEST:
      default:
        textLength = Utils.getRandomInteger(1, 100);
        patternLength = textLength == 1 ? textLength : Utils.getRandomInteger(1, textLength);
        String pattern = Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, patternLength);
        String text = Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, textLength);
        if (textLength > patternLength) {
          StringBuilder str = new StringBuilder(text);
          int n = Utils.getRandomInteger(0, 1000);
          for (int i = 0; i < n; i++) {
            int index = Utils.getRandomInteger(0, textLength - patternLength);
            str.replace(index, index + patternLength, pattern);
          }
          text = str.toString();
        }
        return pattern + " " + text;
    }
  }
}
