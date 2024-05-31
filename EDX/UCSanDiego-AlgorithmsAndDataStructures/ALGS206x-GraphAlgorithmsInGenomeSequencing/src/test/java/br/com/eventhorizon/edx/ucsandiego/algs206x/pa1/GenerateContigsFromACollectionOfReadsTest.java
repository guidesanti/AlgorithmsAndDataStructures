package br.com.eventhorizon.edx.ucsandiego.algs206x.pa1;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;

public class GenerateContigsFromACollectionOfReadsTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/pa1/generate-contigs-from-a-collection-of-reads.csv";

    public GenerateContigsFromACollectionOfReadsTest() {
        super(new GenerateContigsFromACollectionOfReads(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .build());
    }
}
