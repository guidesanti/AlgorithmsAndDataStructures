package br.com.eventhorizon;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import org.junit.jupiter.api.AssertionFailureBuilder;
import org.opentest4j.AssertionFailedError;

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
    protected AssertionFailedError verify(String input, String expectedOutput, String actualOutput) {
        if (!expectedOutput.equals(actualOutput)) {
            return AssertionFailureBuilder.assertionFailure()
                    .message("Expected output and actual output are not equivalent")
                    .expected(expectedOutput)
                    .actual(actualOutput)
                    .build();
        }
        return null;
    }
}
