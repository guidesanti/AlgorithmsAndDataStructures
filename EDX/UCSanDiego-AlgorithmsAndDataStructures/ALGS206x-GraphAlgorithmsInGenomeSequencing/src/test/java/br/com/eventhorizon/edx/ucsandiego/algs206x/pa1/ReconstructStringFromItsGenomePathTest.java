package br.com.eventhorizon.edx.ucsandiego.algs206x.pa1;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;

public class ReconstructStringFromItsGenomePathTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/pa1/reconstruct-string-from-its-genome-path.csv";

    public ReconstructStringFromItsGenomePathTest() {
        super(new ReconstructStringFromItsGenomePath(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .build());
    }
}
