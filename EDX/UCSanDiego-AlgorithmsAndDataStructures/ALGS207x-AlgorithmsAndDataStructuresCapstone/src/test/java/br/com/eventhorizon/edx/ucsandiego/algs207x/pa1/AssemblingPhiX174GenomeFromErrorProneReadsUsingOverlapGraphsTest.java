package br.com.eventhorizon.edx.ucsandiego.algs207x.pa1;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Alphabet;
import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class AssemblingPhiX174GenomeFromErrorProneReadsUsingOverlapGraphsTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/pa1/assembling-phi-x174-genome-from-error-prone-reads-using-overlap-graphs.csv";

    private static final char[] ALPHABET = { 'A', 'C', 'G', 'T' };

    public AssemblingPhiX174GenomeFromErrorProneReadsUsingOverlapGraphsTest() {
        super(new AssemblingPhiX174GenomeFromErrorProneReadsUsingOverlapGraphs(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .timeLimit(4500)
                .stressTestEnabled(true)
                .build());
    }

    @Test
    public void testSuffixArray() {
        AssemblingPhiX174GenomeFromErrorProneReadsUsingOverlapGraphs.SuffixArray sa =
                new AssemblingPhiX174GenomeFromErrorProneReadsUsingOverlapGraphs.SuffixArray("bananas");
        sa.match("ana", 0, 3);
    }

    @Test
    public void test1() {
        for (int i = 0; i < 10000; i++) {
            String text = Utils.getRandomString(ALPHABET, 100, 1000);
            AssemblingPhiX174GenomeFromErrorProneReadsUsingOverlapGraphs.BurrowsWheelerTransform bwt =
                    new AssemblingPhiX174GenomeFromErrorProneReadsUsingOverlapGraphs.BurrowsWheelerTransform(text);
            for (int j = 0; j < 100; j++) {
                int start = Utils.getRandomInteger(0, text.length() - 1);
                int length = Math.min(text.length() - start, Utils.getRandomInteger(1, text.length()));
                List<Integer> matches = bwt.match(text, start, length);
                assertFalse(matches.isEmpty());
                assertTrue(matches.contains(start));
            }
        }
    }

    @Test
    public void test2() {
        String text = "CCGCAGCACGCAGACGGCAAGGTTCCAGTGTAGTGAGGACGAGCAACGGGATCAGCTACGCCCACGGCGGCGGGCCGTTGATGTCTAATCTTAAGCGTTC";
        String pattern = "GACGGCAATGTTCCAGTGGAGTGAGGACGAGCAACGGGATCAGCTACGCCCACGGCGGCGGGCCGTTGATGTCTAATCTTAAGCGTTCCGCGAATGCGCC";
        AssemblingPhiX174GenomeFromErrorProneReadsUsingOverlapGraphs.BurrowsWheelerTransform bwt =
                new AssemblingPhiX174GenomeFromErrorProneReadsUsingOverlapGraphs.BurrowsWheelerTransform(text);
        for (int i = 0; i <= 25; i++) {
            List<Integer> matches = bwt.match(pattern, i, 12);
            int a = 10;
        }
    }

    @Test
    public void test3() {
        String text = "TATGGGAGTGGAAACTTCCCCACTTCTAGACAAAGAAATCCGCACTGCTGGGCGGGACTAATTATAAGCACATACGCTAAGCCCAACGGCGGAAACTAAC";
        String pattern = "AACTTCCCCACTTCTAGAGAAAGAAATCCGCACTGCTGGGCCGGACTAATTATAAGCACATACGCTAAGCCCAACGGCGGAAACTAACGCATACGTAAAG";
        AssemblingPhiX174GenomeFromErrorProneReadsUsingOverlapGraphs.SuffixTree st =
                new AssemblingPhiX174GenomeFromErrorProneReadsUsingOverlapGraphs.SuffixTree(new Alphabet(ALPHABET), text);
        AssemblingPhiX174GenomeFromErrorProneReadsUsingOverlapGraphs.Overlap overlap = st.match(pattern);
        int a = 10;
    }

    @Test
    public void test4() {
        String text = "CCAGTTAGTGGTGCGGCACGTAAAGTCTCTTAACCTCATAGGCGAACACTACTGGCCTCTATCACGCTCTTATAATAACGCAGATAAGCACAACTCGTTC";
        String pattern = "AGTTACTGGTGCGGCACGTAAAGTCTCTTAACCTCATAGGCGAACACTACTGGCCACTATCACGCTCTTATAATAACGCAGATAAGCACAACTCGTTCTT";
        AssemblingPhiX174GenomeFromErrorProneReadsUsingOverlapGraphs.SuffixTree st =
                new AssemblingPhiX174GenomeFromErrorProneReadsUsingOverlapGraphs.SuffixTree(new Alphabet(ALPHABET), text);
        List<Integer> matches = st.match(pattern, 12, 12);
        int a = 10;
    }

    private List<String> generateReads(String text, int readLength) {
        List<String> reads = new ArrayList<>();
        int index = 0;
        List<String> readInfo = new ArrayList<>();
        int count = 0;
        while (index < text.length()) {
            // Generate read
            int end = index + readLength;
            String read;
            if (end <= text.length()) {
                read = text.substring(index, index + readLength);
            } else {
                int overflow = end - text.length();
                read = text.substring(index);
                read += text.substring(0, overflow);
            }
            // Simulate error on read
            int m = Utils.getRandomInteger(0, readLength - 1);
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
            readInfo.add("Read " + count + ", index " + index + ", error index " + m + ", absolute error index " + (index + m));
            // Next read index
            index = Utils.getRandomInteger(index + 1, index + 12);
            count++;
        }
//    Collections.sort(reads);
//    readInfo.forEach(LOGGER::info);
        return reads;
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

    @Override
    protected String generateInput(PATestType type, StringBuilder expectedOutput) {
        String text = Utils.getRandomString(ALPHABET, 10000);
        if (Objects.nonNull(expectedOutput)) {
            expectedOutput.append(text);
        }

        List<String> reads = generateReads(text, 100);
        StringBuilder input = new StringBuilder();
        reads.forEach(read -> input.append(read).append("\n"));
        return input.toString();
    }
}
