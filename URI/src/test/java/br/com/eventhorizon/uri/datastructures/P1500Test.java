package br.com.eventhorizon.uri.datastructures;

import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;

public class P1500Test extends PATestBase {

    private static final String DATASET_DIR = "/datastructures/p1500/";

    private static final String SIMPLE_DATA_SET = "src/test/resources/datastructures/p1500/p1500.csv";

    public P1500Test() {
        super(new P1500(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .timeLimit(6000)
                .compareTestEnabled(true)
                .build());
    }

    @ParameterizedTest
    @CsvSource(value = {
            DATASET_DIR + "p1500-1.in" + "," + DATASET_DIR + "p1500-1.out"
    })
    public void testTrivialSolutionWithSimpleDataSet2(String inputFile, String expectedOutputFile) throws IOException {
        super.testSolutionFromFile(PASolution.TRIVIAL, inputFile, expectedOutputFile);
    }

    @ParameterizedTest
    @CsvSource(value = {
            DATASET_DIR + "p1500-1.in" + "," + DATASET_DIR + "p1500-1.out"
    })
    public void testFinalSolutionWithSimpleDataSet2(String inputFile, String expectedOutputFile) throws IOException {
        super.testSolutionFromFile(PASolution.FINAL, inputFile, expectedOutputFile);
    }

    @Override
    protected String generateInput(PATestType type, StringBuilder expectedOutput) {
        StringBuilder input = new StringBuilder();
        int testCaseCount = 1;
        int numberCount;
        int commandCount;
        if (type == PATestType.TIME_LIMIT_TEST) {
            numberCount = 100000;
            commandCount = 100000;
        } else {
            numberCount = Utils.getRandomInteger(1, 1000);
            commandCount = Utils.getRandomInteger(0, 1000);
        }
        input.append(testCaseCount).append('\n').append(numberCount).append(" ").append(commandCount).append('\n');
        for (int i = 0; i < commandCount; i++) {
            input.append(generateCommand(type, numberCount)).append('\n');
        }
        return input.toString();
    }

    private String generateCommand(PATestType type, int numberCount) {
        int command = Utils.getRandomInteger(0, 1);
        int p = Utils.getRandomInteger(1, numberCount);
        int q = Utils.getRandomInteger(p, numberCount);
        if (type == PATestType.TIME_LIMIT_TEST) {
            if (command == 0) {
                return "0 " + p + " " + q + " " + Utils.getRandomInteger(1, 10000000);
            } else {
                return "1 " + p + " " + q;
            }
        } else {
            if (command == 0) {
                return "0 " + p + " " + q + " " + Utils.getRandomInteger(1, 1000);
            } else {
                return "1 " + p + " " + q;
            }
        }
    }
}
