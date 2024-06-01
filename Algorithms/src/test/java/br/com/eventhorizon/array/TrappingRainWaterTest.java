package br.com.eventhorizon.array;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;

public class TrappingRainWaterTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/array/trapping-rain-water.csv";

    protected TrappingRainWaterTest() {
        super(new TrappingRainWater(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .stressTestEnabled(true)
                .compareTestEnabled(true)
                .build());
    }

    @Override
    protected String generateInput(PATestType type, StringBuilder expectedOutput) {
        var input = new StringBuilder();

        if (type == PATestType.TIME_LIMIT_TEST) {
            var n = 1_000_000;
            input.append(n).append(" ");
            for (int i = 0; i < n; i++) {
                input.append(Utils.getRandomInteger(900, 1_000)).append(" ");
            }
        } else if (type == PATestType.STRESS_TEST) {
            var n = Utils.getRandomInteger(3, 1_000);
            var total = 100 * (n - 2);
            input.append(n).append(" ");
            input.append(100).append(" ");
            for (int i = 1; i < n - 1; i++) {
                var curr = Utils.getRandomInteger(0, 100);
                input.append(curr).append(" ");
                total -= curr;
            }
            input.append(100).append(" ");
            expectedOutput.append(total);
        } else {
            var n = Utils.getRandomInteger(0, 1_000);
            input.append(n).append(" ");
            for (int i = 0; i < n; i++) {
                input.append(Utils.getRandomInteger(0, 1_00)).append(" ");
            }
        }

        return input.toString();
    }
}
