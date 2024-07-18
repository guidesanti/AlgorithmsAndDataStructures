package br.com.eventhorizon.geeksforgeeks.dp;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;

public class LongestCommonSubsequenceTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/dp/longest-common-subsequence.csv";

    protected LongestCommonSubsequenceTest() {
        super(new LongestCommonSubsequence(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .compareTestEnabled(true)
                .build());
    }

    @Override
    protected String generateInput(PATestType paTestType, StringBuilder expectedOutput) {
        var input = new StringBuilder();

        if (paTestType == PATestType.TIME_LIMIT_TEST) {
            input
                    .append(Utils.getRandomString(Utils.CharType.UPPERCASE_ALPHABETICAL_CHARS, 10_000))
                    .append(" ")
                    .append(Utils.getRandomString(Utils.CharType.UPPERCASE_ALPHABETICAL_CHARS, 10_000));
        } else {
            input
                    .append(Utils.getRandomString(Utils.CharType.UPPERCASE_ALPHABETICAL_CHARS, 1, 10))
                    .append(" ")
                    .append(Utils.getRandomString(Utils.CharType.UPPERCASE_ALPHABETICAL_CHARS, 1, 10));
        }

        return input.toString();
    }
}
