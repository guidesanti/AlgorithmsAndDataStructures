package br.com.eventhorizon.uri.datastructures;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;

public class P1023Test extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/datastructures/p1023/p1023.csv";

    public P1023Test() {
        super(new P1023(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .inputFormatFile("datastructures/p1023/p1023-input-format.json")
                .build());
    }
}
