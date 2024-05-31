package br.com.eventhorizon;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PAExampleTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/pa-example.csv";

    public PAExampleTest() {
        super(new PAExample(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .memoryLimitTestEnabled(true)
                .memoryLimit(10_000_000)
                .memoryLimitTestDuration(5000)
                .timeLimitTestEnabled(true)
                .timeLimit(5000)
                .timeLimitTestDuration(5000)
                .compareTestEnabled(true)
                .compareTestDuration(5000)
                .stressTestEnabled(true)
                .stressTestDuration(5000)
                .build());
    }

    @Override
    protected String generateInput(PATestType type, StringBuilder expectedOutput) {
        if (expectedOutput != null) {
            expectedOutput.append("OK-");
        }
        return "Some input here";
    }

    @Override
    protected void verify(String input, String expectedOutput, String actualOutput) {
        assertEquals(expectedOutput, actualOutput, "Expected output and actual output are not equivalent");
    }
}
