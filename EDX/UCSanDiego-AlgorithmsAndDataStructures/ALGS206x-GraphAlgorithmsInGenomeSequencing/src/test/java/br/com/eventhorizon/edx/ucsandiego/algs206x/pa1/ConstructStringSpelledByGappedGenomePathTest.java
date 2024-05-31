package br.com.eventhorizon.edx.ucsandiego.algs206x.pa1;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;

public class ConstructStringSpelledByGappedGenomePathTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/pa1/construct-string-spelled-by-gapped-genome-path.csv";

    public ConstructStringSpelledByGappedGenomePathTest() {
        super(new ConstructStringSpelledByGappedGenomePath(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .build());
    }
}
