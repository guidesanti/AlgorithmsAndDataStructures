package br.com.eventhorizon.geeksforgeeks.backtracking;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class NQueenProblemTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/backtracking/n-queen-problem.csv";

    protected NQueenProblemTest() {
        super(new NQueenProblem(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .build());
    }

    @Override
    protected String generateInput(PATestType paTestType, StringBuilder expectedOutput) {
        return "" + Utils.getRandomInteger(1, 10);
    }

    @Override
    protected void verify(String input, String expectedOutput, String actualOutput) {
        var list1 = new ArrayList<>(Arrays.stream(expectedOutput.split(",")).toList());
        Collections.sort(list1);
        var list2 = new ArrayList<>(Arrays.stream(actualOutput.split(",")).toList());
        Collections.sort(list2);
        assertEquals(list1, list2);
    }
}
