package br.com.eventhorizon.problems;

import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;

import static br.com.eventhorizon.common.utils.Utils.getRandomInteger;

public class CyclicRotationTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/cyclic-rotation.csv";

    public CyclicRotationTest() {
        super(new CyclicRotation(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .compareTestEnabled(true)
                .build());
    }

    @Override
    protected String generateInput(PATestType type, StringBuilder expectedOutput) {
        StringBuilder sb = new StringBuilder();
        int n = getRandomInteger(0, 100);
        sb.append(n).append(" ").append(getRandomInteger(0, 100)).append("\n");
        for (int i = 0; i < n; i++) {
            sb.append(getRandomInteger(-1000, 1000)).append(" ");
        }
        sb.append("\n");
        return sb.toString();
    }
}
