package br.com.eventhorizon.edx.ucsandiego.algs206x.pa1;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstructDeBruijnGraphOfStringTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/pa1/construct-de-bruijn-graph-of-string.csv";

    public ConstructDeBruijnGraphOfStringTest() {
        super(new ConstructDeBruijnGraphOfString(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .build());
    }

    @Override
    protected void verify(String input, String expectedOutput, String actualOutput) {
        String[] values1 = expectedOutput.replace(";", ",").split("\n");
        List<String> values1List = new ArrayList<>(Arrays.asList(values1));
        Collections.sort(values1List);

        String[] values2 = actualOutput.trim().split("\n");
        List<String> values2List = new ArrayList<>(Arrays.asList(values2));
        Collections.sort(values2List);

        assertEquals(values1List.size(), values2List.size());
        for (int i = 0; i < values1List.size(); i++) {
            String[] v1 = values1List.get(i).split("->");
            String[] v2 = values2List.get(i).split("->");
            assertEquals(v1[0], v2[0]);
            v1 = v1[1].split(",");
            List<String> v1List = new ArrayList<>(Arrays.asList(v1));
            Collections.sort(v1List);
            v2 = v2[1].split(",");
            List<String> v2List = new ArrayList<>(Arrays.asList(v2));
            Collections.sort(v2List);
            assertEquals(v1List, v2List);
        }
    }
}
