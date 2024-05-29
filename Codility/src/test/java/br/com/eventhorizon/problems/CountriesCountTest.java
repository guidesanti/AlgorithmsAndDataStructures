package br.com.eventhorizon.problems;

import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;

import static br.com.eventhorizon.common.utils.Utils.getRandomInteger;

public class CountriesCountTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "/test-dataset/countries-count.csv";

    public CountriesCountTest() {
        super(new CountriesCount(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .timeLimit(5000)
                .build());
    }

    @Override
    protected String generateInput(PATestType type, StringBuilder expectedOutput) {
        StringBuilder sb = new StringBuilder();

        int numRows = getRandomInteger(1, 3_000);
        int numCols = getRandomInteger(1, 3_000);

        sb.append(numRows).append(" ").append(numCols).append("\n");
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                sb.append(getRandomInteger(-1_000_000_000, 1_000_000_000)).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
