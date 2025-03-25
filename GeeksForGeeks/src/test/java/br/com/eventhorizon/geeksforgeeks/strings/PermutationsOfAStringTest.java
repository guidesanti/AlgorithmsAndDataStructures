package br.com.eventhorizon.geeksforgeeks.strings;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PermutationsOfAStringTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/strings/permutations-of-a-string.csv";

    protected PermutationsOfAStringTest() {
        super(new PermutationsOfAString(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .build());
    }

    @Override
    protected String generateInput(PATestType paTestType, StringBuilder expectedOutput) {
        return Utils.getRandomString(Utils.CharType.UPPERCASE_ALPHABETICAL_CHARS, 9);
    }

    @Override
    protected void verify(String input, String expectedOutput, String actualOutput) {
        var s1 = new HashSet<>(Arrays.stream(expectedOutput.split(" ")).toList());
        var s2 = new HashSet<>(Arrays.stream(expectedOutput.split(" ")).toList());
        assertEquals(s1, s2);
    }
}
