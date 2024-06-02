package br.com.eventhorizon.geeksforgeeks.array;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;

public class EquilibriumIndexOfAnArrayTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/array/equilibirum-index-of-an-array.csv";

    protected EquilibriumIndexOfAnArrayTest() {
        super(new EquilibriumIndexOfAnArray(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .compareTestEnabled(true)
                .build());
    }

    @Override
    protected String generateInput(PATestType type, StringBuilder expectedOutput) {
        var input = new StringBuilder();

        var n = 0;
        if (type == PATestType.TIME_LIMIT_TEST) {
            n = Utils.getRandomInteger(1, 1_000_000);
        } else {
            n = Utils.getRandomInteger(1, 1_0);
        }

        input.append(n).append("\n");
        for (int i = 0; i < n; i++) {
            input.append(Utils.getRandomInteger(-10, 10)).append(" ");
        }

        return input.toString();
    }
}
