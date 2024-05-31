package br.com.eventhorizon.edx.ucsandiego.algs206x.pa1;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;

public class ReconstructStringFromKMerCompositionTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/pa1/reconstruct-string-from-kmer-composition.csv";

    public ReconstructStringFromKMerCompositionTest() {
        super(new ReconstructStringFromKMerComposition(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .build());
    }
}
