package br.com.eventhorizon.edx.ucsandiego.algs207x.pa3;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BubbleDetectionTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/pa3/bubble-detection.csv";

    private static final char[] ALPHABET = {'A', 'C', 'G', 'T'};

    public BubbleDetectionTest() {
        super(new BubbleDetection(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .build());
    }

    @Override
    protected String generateInput(PATestType type, StringBuilder expectedOutput) {
        String text = Utils.getRandomString(ALPHABET, 100);
        List<String> reads = generateReads(text, 50);
        StringBuilder str = new StringBuilder();
        reads.forEach(read -> str.append(read).append("\n"));
        return 5 + " " + 5 + "\n" + str;
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
            index = Utils.getRandomInteger(index + 1, index + 5);
            count++;
        }
        //    Collections.shuffle(reads);
        readInfo.forEach(log::info);
        return reads;
    }
}
