package br.com.eventhorizon.problems;

import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;

import static br.com.eventhorizon.common.utils.Utils.getRandomInteger;

public class MaxProductOfThreeInArrayTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/max-product-of-three-in-array.csv";

    public MaxProductOfThreeInArrayTest() {
        super(new MaxProductOfThreeInArray(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .compareTestEnabled(true)
                .build());
    }

    @Override
    protected String generateInput(PATestType type, StringBuilder expectedOutput) {
        StringBuilder sb = new StringBuilder();

        int n;

        if (type == PATestType.TIME_LIMIT_TEST) {
            n = 100_000;
        } else {
            n = getRandomInteger(3, 1000);
        }

        sb.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            sb.append(getRandomInteger(-1_000, 1_000)).append(" ");
        }
        sb.append("\n");

        return sb.toString();
    }
}
