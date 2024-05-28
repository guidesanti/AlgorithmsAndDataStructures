package br.com.eventhorizon.edx.ucsandiego.algs200x.pa4;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static br.com.eventhorizon.common.utils.Utils.getRandomInteger;

public class OrganizingALotteryTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa4/organizing-a-lottery.csv";

  public OrganizingALotteryTest() {
    super(new OrganizingALottery(), PATestSettings.builder()
            .timeLimitTestEnabled(true)
            .compareTestEnabled(true)
            .build());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.TRIVIAL, input, expectedOutput);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testSolution(PASolution.FINAL, input, expectedOutput);
  }

  @Override
  protected String generateInput(PATestType type, StringBuilder expectedOutput) {
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
