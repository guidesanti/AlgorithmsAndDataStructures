package br.com.eventhorizon.uri.datastructures;

import br.com.eventhorizon.common.pa.PATestType;
import br.com.eventhorizon.common.pa.v2.PASolution;
import br.com.eventhorizon.common.pa.v2.PATestBase;
import br.com.eventhorizon.common.pa.v2.PATestSettings;
import br.com.eventhorizon.common.utils.Utils;
import org.apache.commons.lang3.BooleanUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class P2559Test extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/datastructures/p2559/p2559.csv";

  public P2559Test() {
    super(new P2559(), PATestSettings.builder()
        .memoryLimitTestEnabled(false)
        .timeLimitTestEnabled(true)
        .timeLimit(6000)
        .compareTestEnabled(true)
        .build());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testTrivialSolutionWithSimpleDataSet(String input, String expectedOutput) {
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
    int numberCount;
    int operationCount;
    if (type == PATestType.MEMORY_USAGE_TEST || type == PATestType.TIME_LIMIT_TEST) {
      numberCount = 200000;
      operationCount = 200000;
    } else {
      numberCount = Utils.getRandomInteger(1, 1000);
      operationCount = Utils.getRandomInteger(1, 1000);
    }
    input.append(numberCount).append("\n");
    for (int i = 0; i < numberCount; i++) {
      int number = Utils.getRandomInteger(0, 1000);
      input.append(number).append(" ");
    }
    input.replace(input.length() - 1, input.length(), "\n");
    input.append(operationCount).append("\n");
    for (int i = 0; i < operationCount; i++) {
      input.append(generateOperation(Utils.getRandomInteger(1, 3), numberCount)).append("\n");
    }
    return input.toString();
  }

  private String generateOperation(int opCode, int numberCount) {
    StringBuilder operation = new StringBuilder();
    if (opCode == 1) {
      int i = Utils.getRandomInteger(1, numberCount);
      int v = Utils.getRandomInteger(0, 1000);
      operation.append("1 ")
          .append(i).append(" ")
          .append(v);
    } else if (opCode == 2) {
      int i = Utils.getRandomInteger(1, numberCount);
      int j = Utils.getRandomInteger(i, numberCount);
      int z = BooleanUtils.toInteger(Utils.getRandomBoolean(), 7, 13);
      int v = Utils.getRandomInteger(0, 1000);
      operation.append("2 ")
          .append(i).append(" ")
          .append(j).append(" ")
          .append(z).append(" ")
          .append(v);
    } else {
      int i = Utils.getRandomInteger(1, numberCount);
      int j = Utils.getRandomInteger(i, numberCount);
      operation.append("3 ")
          .append(i).append(" ")
          .append(j);
    }
    return operation.toString();
  }
}
