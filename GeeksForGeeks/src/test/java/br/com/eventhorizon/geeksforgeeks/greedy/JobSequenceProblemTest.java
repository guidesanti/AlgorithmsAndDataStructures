package br.com.eventhorizon.geeksforgeeks.greedy;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;

public class JobSequenceProblemTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/greedy/job-sequence-problem.csv";

    protected JobSequenceProblemTest() {
        super(new JobSequenceProblem(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .build());
    }
}
