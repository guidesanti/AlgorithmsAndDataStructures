package br.com.eventhorizon.uri.datastructures;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;

public class P1236Test extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/datastructures/p1236/p1236.csv";

    public P1236Test() {
        super(new P1236(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(false)
                .timeLimit(3000)
                .compareTestEnabled(false)
                .build());
    }
}
