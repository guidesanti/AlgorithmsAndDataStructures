package br.com.eventhorizon.edx.ucsandiego.algs200x.pa4;

import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class OrganizingALotteryTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa4/organizing-a-lottery.csv";

  public OrganizingALotteryTest() {
    super(new OrganizingALottery());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput.replace("%", "\n"));
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput.replace("%", "\n"));
  }

  @Override
  protected String generateInput(PATestType type) {
    StringBuilder input = new StringBuilder();
    int s;
    int p;

    switch (type) {
      case TIME_LIMIT_TEST:
        s = getRandomInteger(1, 50000);
        p = getRandomInteger(1, 50000);
        input.append(s).append(" ").append(p);
        for (int i = 0; i < s; i++) {
          int start = getRandomInteger(1, 200000001) - 100000001;
          input.append(" ").append(start);
          input.append(" ").append(getRandomInteger(start + 1, 200000001));
        }
        for (int i = 0; i < p; i++) {
          input.append(" ").append(getRandomInteger(1, 200000001) - 100000001);
        }
        break;

      case STRESS_TEST:
      default:
        s = getRandomInteger(1, 10);
        p = getRandomInteger(1, 10);
        input.append(s).append(" ").append(p);
        for (int i = 0; i < s; i++) {
          int start = getRandomInteger(1, 21) - 11;
          input.append(" ").append(start);
          input.append(" ").append(getRandomInteger(start + 1, 21));
        }
        for (int i = 0; i < p; i++) {
          input.append(" ").append(getRandomInteger(1, 21) - 11);
        }
        break;
    }

    return input.toString();
  }
}
