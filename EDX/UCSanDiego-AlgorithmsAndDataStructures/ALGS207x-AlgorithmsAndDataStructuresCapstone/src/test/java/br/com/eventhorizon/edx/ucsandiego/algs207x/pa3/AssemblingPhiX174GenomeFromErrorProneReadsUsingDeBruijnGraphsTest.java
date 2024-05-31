package br.com.eventhorizon.edx.ucsandiego.algs207x.pa3;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssemblingPhiX174GenomeFromErrorProneReadsUsingDeBruijnGraphsTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/pa3/assembling-phi-x174-genome-from-error-prone-reads-using-debruijn-graphs.csv";

    private static final char[] ALPHABET = { 'A', 'C', 'G', 'T' };

    private static final int GENOME_LENGTH = 1000;

    private static final int READ_LENGTH = 100;

    private static final int K = 15;

    public AssemblingPhiX174GenomeFromErrorProneReadsUsingDeBruijnGraphsTest() {
        super(new AssemblingPhiX174GenomeFromErrorProneReadsUsingDeBruijnGraphs(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .timeLimit(15000)
                .stressTestEnabled(true)
                .build());
    }

    private List<String> generateReads(String text) {
        List<String> reads = new ArrayList<>();
        int index = 0;
        //    List<String> readInfo = new ArrayList<>();
        int count = 0;
        while (index < text.length()) {
            // Generate read
            int end = index + READ_LENGTH;
            String read;
            if (end <= text.length()) {
                read = text.substring(index, index + READ_LENGTH);
            } else {
                int overflow = end - text.length();
                read = text.substring(index);
                read += text.substring(0, overflow);
            }
            // Simulate error on read
            int m = Utils.getRandomInteger(0, READ_LENGTH - 1);
            char symbol = read.charAt(m);
            if (symbol == 'A') {
                symbol = 'C';
            } else if (symbol == 'C') {
                symbol = 'G';
            } else if (symbol == 'G') {
                symbol = 'T';
            } else {
                symbol = 'A';
            }
            read = read.substring(0, m) + symbol + read.substring(m + 1);
            reads.add(read);
            //      readInfo.add("Read " + count + ", index " + index + ", error index " + m + ", absolute error index " + (index + m));
            // Next read index
            index = Utils.getRandomInteger(index + 1, index + (K / 2));
            count++;
        }
        //    Collections.shuffle(reads);
        //    readInfo.forEach(LOGGER::info);
        return reads;
    }

    @Override
    protected String generateInput(PATestType paTestType, StringBuilder expectedOutput) {
        String text = Utils.getRandomString(ALPHABET, GENOME_LENGTH);
        if (Objects.nonNull(expectedOutput)) {
            expectedOutput.append(text);
        }

        List<String> reads = generateReads(text);
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
