package br.com.eventhorizon.edx.ucsandiego.algs201x.pa1;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class NetworkPacketProcessingTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/network-packet-processing.csv";

  public NetworkPacketProcessingTest() {
    super(new NetworkPacketProcessing(), PATestSettings.builder()
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
    int bufferCapacity;
    int packetsCount;
    if (type == PATestType.TIME_LIMIT_TEST) {
      bufferCapacity = Utils.getRandomInteger(1, 100000);
      packetsCount = Utils.getRandomInteger(1, 100000);
    } else {
      bufferCapacity = Utils.getRandomInteger(1, 10);
      packetsCount = Utils.getRandomInteger(1, 100);
    }
    input.append(bufferCapacity);
    input.append(" ").append(packetsCount);
    for (int i = 0; i < packetsCount; i++) {
      input.append(" ").append(Utils.getRandomInteger(0, 1000000));
      input.append(" ").append(Utils.getRandomInteger(0, 1000));
    }
    return input.toString();
  }
}
