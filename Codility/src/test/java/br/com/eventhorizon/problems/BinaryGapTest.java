package br.com.eventhorizon.problems;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;

public class BinaryGapTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/binary-gap.csv";

  public BinaryGapTest() {
    super(new BinaryGap(), PATestSettings.builder()
            .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
            .build());
  }
}
