package br.com.eventhorizon.uri.datastructures;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;

public class P1215Test extends PATestBase {

  private static final String SIMPLE_DATA_SET = "src/test/resources/datastructures/p1215/p1215.csv";

  public P1215Test() {
    super(new P1215(), PATestSettings.builder()
            .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
            .build());
  }
}
