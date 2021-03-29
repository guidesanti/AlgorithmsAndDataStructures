package br.com.eventhorizon.string.matching;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.TestProperties;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class StringMatchingAlgorithmTest {

  private static final Logger LOGGER = Logger.getLogger(StringMatchingAlgorithmTest.class.getName());

  protected final StringMatchingAlgorithm stringMatchingAlgorithm;

  public StringMatchingAlgorithmTest(StringMatchingAlgorithm stringMatchingAlgorithm) {
    this.stringMatchingAlgorithm = stringMatchingAlgorithm;
  }

  protected void testMatch(String input, String expectedOutput) {
    String[] inputArray = input.split(" ");
    LOGGER.info("Testing algorithm: " + stringMatchingAlgorithm.getClass());
    int[] result = stringMatchingAlgorithm.match(inputArray[0].toCharArray(), inputArray[1].toCharArray());
    assertEquals(expectedOutput, Arrays.toString(result)
        .replace("]", "")
        .replace("[", "")
        .replace(",", ""));
  }

  @Test
  public void stressTest() {
    LOGGER.info("Stress test duration: " + TestProperties.getStressTestDuration());
    long startTime = System.currentTimeMillis();
    for (int test = 0; true; test++) {
      char[] pattern = Utils.getRandomString(Utils.CharType.ALL, 0, 1000).toCharArray();
      char[] text = Utils.getRandomString(Utils.CharType.ALL, pattern.length, 10000).toCharArray();
      int[] expectedShifts = makeShifts(text, pattern);
      int[] actualShifts = stringMatchingAlgorithm.match(text, pattern);
      if (!Arrays.equals(expectedShifts, actualShifts)) {
        LOGGER.info("Stress test " + test + " status: FAILED");
        LOGGER.info("Stress test " + test + " text: " + Arrays.toString(text));
        LOGGER.info("Stress test " + test + " pattern: " + Arrays.toString(pattern));
        assertArrayEquals(expectedShifts, actualShifts);
      }
      LOGGER.info("Stress test " + test + " status: PASSED");

      // Check elapsed time
      long elapsedTime = System.currentTimeMillis() - startTime;
      if (elapsedTime > TestProperties.getStressTestDuration()) {
        return;
      }
    }
  }

  private int[] makeShifts(char[] text, char[] pattern) {
    if (text.length == 0 || pattern.length == 0) {
      int[] shifts = new int[text.length];
      for (int i = 0; i < shifts.length; i++) {
        shifts[i] = i;
      }
      return shifts;
    }
    int index = Utils.getRandomInteger(0, text.length);
    while (index + pattern.length <= text.length) {
      System.arraycopy(pattern, 0, text, index, pattern.length);
      index = Utils.getRandomInteger(index + pattern.length + 1, text.length + 1);
    }
    return match(text, pattern);
  }

  public int[] match(char[] text, char[] pattern) {
    if (pattern.length == 0) {
      int[] shifts = new int[text.length];
      for (int i = 0; i < shifts.length; i++) {
        shifts[i] = i;
      }
      return shifts;
    }
    List<Integer> shifts = new ArrayList<>();
    int n = text.length - pattern.length;
    for (int i = 0; i <= n; i++) {
      boolean match = true;
      for (int j = 0; j < pattern.length; j++) {
        if (pattern[j] != text[i + j]) {
          match = false;
          break;
        }
      }
      if (match) {
        shifts.add(i);
      }
    }
    return Utils.listOfIntegersToArray(shifts);
  }
}
