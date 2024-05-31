package br.com.eventhorizon.edx.ucsandiego.algs207x.pa2;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;

public class PuzzleAssemblyTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/pa2/puzzle-assembly.csv";

    public PuzzleAssemblyTest() {
        super(new PuzzleAssembly(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .build());
    }
}
