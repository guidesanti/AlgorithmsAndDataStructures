package br.com.eventhorizon.edx.ucsandiego.algs201x.pa4;

import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import br.com.eventhorizon.common.pa.TestProperties;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class RopeTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa4/rope.csv";

  public RopeTest() {
    super(new Rope());
  }

  @BeforeAll
  public static void setup() {
    System.setProperty(TestProperties.TIME_LIMIT_IN_MS_PROPERTY_KEY, "6000");
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

  @Override
  protected String generateInput(PATestType type) {
    StringBuilder input = new StringBuilder();
    int textLength;
    int numberOfQueries;
    switch (type) {
      case TIME_LIMIT_TEST:
        textLength = Utils.getRandomInteger(1, 300000);
        numberOfQueries = 100000;
        break;
      case STRESS_TEST:
      default:
        textLength = Utils.getRandomInteger(1, 1000);
        numberOfQueries = 100;
        break;
    }
    input
        .append(Utils.getRandomString(Utils.CharType.LOWERCASE_ALPHABETICAL_CHARS, textLength))
        .append(" ").append(numberOfQueries);
    while (numberOfQueries-- > 0) {
      int i = Utils.getRandomInteger(0, textLength - 1);
      int j = Utils.getRandomInteger(i, textLength - 1);
      int k = Utils.getRandomInteger(0, textLength - (j - i + 1));
      input.append(" ").append(i);
      input.append(" ").append(j);
      input.append(" ").append(k);
    }
    return input.toString();
  }
}
