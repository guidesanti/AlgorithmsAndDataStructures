package br.com.eventhorizon.edx.ucsandiego.algs201x.pa1;

import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class NetworkPacketProcessingTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/network-packet-processing.csv";

  public NetworkPacketProcessingTest() {
    super(new NetworkPacketProcessing());
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
