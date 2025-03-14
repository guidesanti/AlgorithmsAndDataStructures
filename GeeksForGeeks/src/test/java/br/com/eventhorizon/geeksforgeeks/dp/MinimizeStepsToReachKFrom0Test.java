package br.com.eventhorizon.geeksforgeeks.dp;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;

public class MinimizeStepsToReachKFrom0Test extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/dp/minimize-steps-to-reach-k-from-0.csv";

    protected MinimizeStepsToReachKFrom0Test() {
        super(new MinimizeStepsToReachKFrom0(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .compareTestEnabled(true)
                .build());
    }

    @Override
    protected String generateInput(PATestType paTestType, StringBuilder expectedOutput) {
        var input = new StringBuilder();

        if (paTestType == PATestType.TIME_LIMIT_TEST) {
            input.append(Utils.getRandomInteger(1000, 10_000));
        } else {
            input.append(Utils.getRandomInteger(1, 400));
        }

        return input.toString();
    }
}
