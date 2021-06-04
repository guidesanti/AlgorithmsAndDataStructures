package br.com.eventhorizon.edx.ucsandiego.algs204x.pa1;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ShortestNonSharedSubstringTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/shortest-non-shared-substring.csv";

  public ShortestNonSharedSubstringTest() {
    super(new ShortestNonSharedSubstring(), false, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, int expectedShortestNonSharedSubstringLength) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    pa.finalSolution();
    String actualOutput = getActualOutput();
    if (expectedShortestNonSharedSubstringLength == 0) {
      assertTrue(actualOutput.isEmpty());
    } else {
      String[] texts = input.split(" ");
      assertEquals(expectedShortestNonSharedSubstringLength, actualOutput.length());
      assertTrue(match(texts[0], actualOutput));
      assertFalse(match(texts[1], actualOutput));
    }
  }

  @Override
  protected String generateInput(PATestType type) {
    return Utils.getRandomString(Utils.CharType.UPPERCASE_ALPHABETICAL_CHARS, 1, 2000) + " "
        + Utils.getRandomString(Utils.CharType.UPPERCASE_ALPHABETICAL_CHARS, 1, 2000);
  }

  private boolean match(String text, String pattern) {
    for (int textIndex = 0; textIndex <= text.length() - pattern.length(); textIndex++) {
      boolean match = true;
      for (int offset = 0; offset < pattern.length(); offset++) {
        if (text.charAt(textIndex + offset) != pattern.charAt(offset)) {
          match = false;
          break;
        }
      }
      if (match) {
        return true;
      }
    }
    return false;
  }
}
