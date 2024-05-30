package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;

public class SequenceAlignmentWithProfileHMMTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/pa1/sequence-alignment-with-profile-hmm.csv";

  public SequenceAlignmentWithProfileHMMTest() {
    super(new SequenceAlignmentWithProfileHMM(), PATestSettings.builder()
            .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
            .build());
  }
}
