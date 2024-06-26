package br.com.eventhorizon.problems;

import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static br.com.eventhorizon.common.utils.Utils.getRandomInteger;

public class OddOccurrencesInArrayTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/odd-occurrence-in-array.csv";

    public OddOccurrencesInArrayTest() {
        super(new OddOccurrencesInArray(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .timeLimit(5000)
                .compareTestEnabled(true)
                .stressTestEnabled(true)
                .build());
    }

    @Override
    protected String generateInput(PATestType type, StringBuilder expectedOutput) {
        StringBuilder sb = new StringBuilder();

        int max = 1_000_000_000;
        int n = getRandomInteger(1, 1_000_000);
        if (n % 2 == 0) {
            n++;
        }

        int oddOccurrence = getRandomInteger(1, max);
        Integer[] array = new Integer[n];
        for (int i = 0; i < n - 1; i += 2) {
            int value = getRandomInteger(1, max);
            while (value == oddOccurrence) {
                value = getRandomInteger(1, max);
            }
            array[i] = array[i + 1] = value;
        }
        array[array.length - 1] = oddOccurrence;
        List<Integer> numbers = Arrays.asList(array);
        Collections.shuffle(numbers);

        sb.append(n).append("\n");
        for (Integer number : numbers) {
            sb.append(number).append(" ");
        }
        sb.append("\n");

        if (expectedOutput != null) {
            expectedOutput.append(oddOccurrence);
        }

        return sb.toString();
    }
}
