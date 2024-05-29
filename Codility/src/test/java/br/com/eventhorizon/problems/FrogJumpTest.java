package br.com.eventhorizon.problems;

import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import static br.com.eventhorizon.common.utils.Utils.getRandomInteger;

public class FrogJumpTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/frog-jump.csv";

    public FrogJumpTest() {
        super(new FrogJump(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .memoryLimitTestEnabled(true)
                .timeLimitTestEnabled(true)
                .compareTestEnabled(true)
                .build());
    }

    @Override
    protected String generateInput(PATestType type, StringBuilder expectedOutput) {
        StringBuilder sb = new StringBuilder();

        int x = getRandomInteger(1, 1_000_000_000);
        int y = getRandomInteger(x, 1_000_000_000);
        int d = getRandomInteger(1, 1_000_000_000);

        switch (type) {
            case COMPARE_TEST:
                x = getRandomInteger(1, 1_000_000);
                y = getRandomInteger(x, 1_000_000);
                d = getRandomInteger(1, 1_000_000);
                break;
            case STRESS_TEST:
            case TIME_LIMIT_TEST:
                x = getRandomInteger(1, 1_000_000_000);
                y = getRandomInteger(x, 1_000_000_000);
                d = getRandomInteger(1, 1_000_000_000);
                break;
        }

        sb.append(x).append(" ").append(y).append(" ").append(d).append("\n");

        return sb.toString();
    }
}
