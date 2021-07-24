package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultipleAlignmentTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/multiple-alignment.csv";

  public MultipleAlignmentTest() {
    super(new MultipleAlignment(), false, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    String[] values = expectedOutput.split("%");
    int expectedScore = Integer.parseInt(values[0]);
    String expectedAlignment1 = values[1];
    String expectedAlignment2 = values[2];
    String expectedAlignment3 = values[3];

    System.setIn(new ByteArrayInputStream(input.getBytes()));
    pa.finalSolution();

    values = getActualOutput().split("\n");
    int actualScore = Integer.parseInt(values[0]);
    String actualAlignment1 = values[1];
    String actualAlignment2 = values[2];
    String actualAlignment3 = values[3];

    assertEquals(expectedScore, actualScore, "Score not match 1");
    assertEquals(expectedScore, score(expectedAlignment1, expectedAlignment2, expectedAlignment3), "Score not match 2: " + actualAlignment1 + " / " + actualAlignment2 + " /" + actualAlignment3);
    assertEquals(expectedScore, score(actualAlignment1, actualAlignment2, actualAlignment3), "Score not match 3");
  }

  private static int score(String alignment1, String alignment2, String alignment3) {
    int score = 0;
    for (int i = 0; i < alignment1.length(); i++) {
      if (alignment1.charAt(i) == alignment2.charAt(i) && alignment1.charAt(i) == alignment3.charAt(i)) {
        score++;
      }
    }
    return score;
  }

  @Override
  protected String generateInput(PATestType type) {
    return Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 10) + " " +
        Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 10) + " " +
        Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 10);
  }
}
