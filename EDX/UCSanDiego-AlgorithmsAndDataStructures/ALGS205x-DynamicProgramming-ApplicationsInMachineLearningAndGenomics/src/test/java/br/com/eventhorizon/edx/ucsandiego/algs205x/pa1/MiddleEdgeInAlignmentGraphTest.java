package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;

public class MiddleEdgeInAlignmentGraphTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/pa1/middle-edge-in-alignment-graph.csv";

  public MiddleEdgeInAlignmentGraphTest() {
    super(new MiddleEdgeInAlignmentGraph(), PATestSettings.builder()
            .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
            .timeLimitTestEnabled(true)
            .build());
  }

  @Override
  protected String generateInput(PATestType type, StringBuilder expectedOutput) {
    return Utils.getRandomInteger(1,10) + " " +
        Utils.getRandomInteger(1, 10) + " " +
        Utils.getRandomInteger(1, 10) + " " +
        Utils.getRandomString(Utils.CharType.ALPHABETICAL_CHARS, 1000) + " " +
        Utils.getRandomString(Utils.CharType.ALPHABETICAL_CHARS, 1000);
  }
}
