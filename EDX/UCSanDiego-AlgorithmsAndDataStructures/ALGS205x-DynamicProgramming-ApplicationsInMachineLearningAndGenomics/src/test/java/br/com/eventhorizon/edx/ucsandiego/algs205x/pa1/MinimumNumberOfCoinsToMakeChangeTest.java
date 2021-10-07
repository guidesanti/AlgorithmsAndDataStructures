package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.*;

public class MinimumNumberOfCoinsToMakeChangeTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/minimum-number-of-coins-to-make-change.csv";

  public MinimumNumberOfCoinsToMakeChangeTest() {
    super(new MinimumNumberOfCoinsToMakeChange(), false, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1, delimiter = ';')
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput == null ? "" : expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @Override
  protected String generateInput(PATestType type) {
    StringBuilder input = new StringBuilder();
    input.append(Utils.getRandomInteger(0, 20000)).append(" ");
    int numberOfCoins = Utils.getRandomInteger(1, 7);
    Set<Integer> coins = new HashSet<>();
    coins.add(1);
    while (coins.size() < numberOfCoins) {
      coins.add(Utils.getRandomInteger(2, 100));
    }
    StringJoiner coinsStr = new StringJoiner(",");
    for (int coin : coins) {
      coinsStr.add("" + coin);
    }
    input.append(coinsStr.toString());
    return input.toString();
  }
}
