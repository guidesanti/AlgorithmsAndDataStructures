package br.com.eventhorizon.edx.ucsandiego.algs207x.pa1;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssemblingPhiX174GenomeFromErrorFreeReadsUsingOverlapGraphsTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/pa1/assembling-phi-x174-genome-from-error-free-reads-using-overlap-graphs.csv";

    private static final char[] ALPHABET = {'A', 'C', 'G', 'T'};

    public AssemblingPhiX174GenomeFromErrorFreeReadsUsingOverlapGraphsTest() {
        super(new AssemblingPhiX174GenomeFromErrorFreeReadsUsingOverlapGraphs(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .timeLimit(4500)
                .stressTestEnabled(true)
                .build());
    }

    private List<String> generateReads(String text, int readLength) {
        List<String> reads = new ArrayList<>();
        int index = 0;
        while (index < text.length() - readLength) {
            reads.add(text.substring(index, index + readLength));
            index = Utils.getRandomInteger(index + 1, index + readLength - 1);
        }
        reads.add(text.substring(text.length() - readLength));
        return reads;
    }

    @Override
    protected String generateInput(PATestType paTestType, StringBuilder expectedOutput) {
        String text = Utils.getRandomString(ALPHABET, 8);
        if (Objects.nonNull(expectedOutput)) {
            expectedOutput.append(text);
        }

        List<String> reads = generateReads(text, 3);
        StringBuilder input = new StringBuilder();
        reads.forEach(read -> input.append(read).append("\n"));
        return input.toString();
    }

    @Override
    protected void verify(String input, String expectedOutput, String actualOutput) {
        assertEquals(expectedOutput.length(), actualOutput.length());
        boolean circularEqual = false;
        for (int i = 0; i < expectedOutput.length(); i++) {
            if (expectedOutput.equals(actualOutput)) {
                circularEqual = true;
                break;
            }
            actualOutput = actualOutput.substring(1) + actualOutput.charAt(0);
        }
        assertTrue(circularEqual);
    }
}
