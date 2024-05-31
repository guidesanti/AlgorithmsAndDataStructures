package br.com.eventhorizon.edx.ucsandiego.algs207x.pa2;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;

import java.io.ByteArrayInputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindingEulerianCycleInDirectedGraphTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/pa2/find-eulerian-cycle-in-directed-graph.csv";

    public FindingEulerianCycleInDirectedGraphTest() {
        super(new FindingEulerianCycleInDirectedGraph(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .build());
    }

    @Override
    protected void verify(String input, String expectedOutput, String actualOutput) {
        // Process input
        Map<Integer, List<Integer>> adjacencies = new HashMap<>();
        FastScanner scanner = new FastScanner(new ByteArrayInputStream(input.getBytes()));
        int verticesCount = scanner.nextInt();
        int edgesCount = scanner.nextInt();
        for (int i = 0; i < edgesCount; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            List<Integer> adj = adjacencies.getOrDefault(from, new ArrayList<>());
            adj.add(to);
            adjacencies.put(from, adj);
        }

        // Process expected output
        scanner = new FastScanner(new ByteArrayInputStream(expectedOutput.getBytes()));
        List<Integer> expectedCycle = new ArrayList<>();
        int expectedHasEulerianCycle = scanner.nextInt();
        if (expectedHasEulerianCycle == 1) {
            for (int i = 0; i < edgesCount; i++) {
                expectedCycle.add(scanner.nextInt());
            }
        }

        // Process actual output
        scanner = new FastScanner(new ByteArrayInputStream(actualOutput.getBytes()));
        List<Integer> actualCycle = new ArrayList<>();
        int actualHasEulerianCycle = scanner.nextInt();
        if (actualHasEulerianCycle == 1) {
            for (int i = 0; i < edgesCount; i++) {
                actualCycle.add(scanner.nextInt());
            }
        }

        // Verify actual output against expected output
        assertEquals(expectedHasEulerianCycle, actualHasEulerianCycle);
        assertEquals(expectedCycle.size(), actualCycle.size());
        assertTrue(expectedCycle.containsAll(actualCycle));
    }
}
