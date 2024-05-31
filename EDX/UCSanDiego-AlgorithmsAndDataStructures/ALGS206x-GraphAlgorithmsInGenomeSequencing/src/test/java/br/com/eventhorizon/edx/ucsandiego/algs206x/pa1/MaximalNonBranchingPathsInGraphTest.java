package br.com.eventhorizon.edx.ucsandiego.algs206x.pa1;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MaximalNonBranchingPathsInGraphTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/pa1/maximal-non-branching-paths-in-graph.csv";

    public MaximalNonBranchingPathsInGraphTest() {
        super(new MaximalNonBranchingPathsInGraph(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .build());
    }

    @Override
    protected void verify(String input, String expectedOutput, String actualOutput) {
        // Process expected output
        List<List<String>> expectedPaths = new ArrayList<>();
        for (String pathStr : expectedOutput.split("\n")) {
            expectedPaths.add(new ArrayList<>(Arrays.asList(pathStr.split("->"))));
        }

        // Process actual output
        List<List<String>> actualPaths = new ArrayList<>();
        for (String pathStr : actualOutput.split("\n")) {
            actualPaths.add(new ArrayList<>(Arrays.asList(pathStr.split("->"))));
        }

        assertEquals(expectedPaths.size(), actualPaths.size());
        for (List<String> actualPath : actualPaths) {
            if (actualPath.get(0).equals(actualPath.get(actualPath.size() - 1))) {
                boolean found = false;
                for (int i = 0; i < actualPath.size(); i++) {
                    if (expectedPaths.contains(actualPath)) {
                        found = true;
                        break;
                    }
                    actualPath.remove(0);
                    actualPath.add(actualPath.get(0));
                }
                assertTrue(found);
            } else {
                assert(expectedPaths.contains(actualPath));
            }
        }
    }
}
