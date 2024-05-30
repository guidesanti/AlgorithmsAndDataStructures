package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LongestCommonSubsequenceOfTwoStringsTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/longest-common-subsequence-of-two-strings.csv";

  public LongestCommonSubsequenceOfTwoStringsTest() {
    super(new LongestCommonSubsequenceOfTwoStrings(), PATestSettings.builder()
            .timeLimitTestEnabled(true)
            .build());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    reset(input);
    pa.finalSolution();
    String actualOutput = getActualOutput();
    assertEquals(expectedOutput.length(), actualOutput.length());
    String[] strings = input.split(" ");
    for (String string : strings) {
      assertTrue(isSubsequence(string, actualOutput));
    }
  }

  private boolean isSubsequence(String string, String subsequence) {
    assertTrue(subsequence.length() <= string.length());
    int stringIndex = 0;
    int subsequenceIndex = 0;
    while (stringIndex < string.length() && subsequenceIndex < subsequence.length()) {
      if (subsequence.charAt(subsequenceIndex) == string.charAt(stringIndex)) {
        subsequenceIndex++;
      }
      stringIndex++;
    }
    return subsequenceIndex == subsequence.length();
  }

  @Override
  protected String generateInput(PATestType type, StringBuilder expectedOutput) {
    return Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 1, 1000)
        + " "
        + Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 1, 1000);
  }
}
