package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;

import java.util.*;

public class MinimumNumberOfCoinsToMakeChangeTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/pa1/minimum-number-of-coins-to-make-change.csv";

  public MinimumNumberOfCoinsToMakeChangeTest() {
    super(new MinimumNumberOfCoinsToMakeChange(), PATestSettings.builder()
            .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
            .timeLimitTestEnabled(true)
            .build());
  }

  @Override
  protected String generateInput(PATestType type, StringBuilder expectedOutput) {
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
