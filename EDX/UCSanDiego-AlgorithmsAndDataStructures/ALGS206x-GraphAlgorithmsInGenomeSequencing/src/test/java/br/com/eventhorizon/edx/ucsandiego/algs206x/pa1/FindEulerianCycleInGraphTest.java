package br.com.eventhorizon.edx.ucsandiego.algs206x.pa1;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;
import org.opentest4j.AssertionFailedError;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class FindEulerianCycleInGraphTest extends PATestBase {

    private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/pa1/find-eulerian-cycle-in-graph.csv";

    public FindEulerianCycleInGraphTest() {
        super(new FindEulerianCycleInGraph(), PATestSettings.builder()
                .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
                .timeLimitTestEnabled(true)
                .stressTestEnabled(true)
                .build());
    }

    @Override
    protected String generateInput(PATestType type, StringBuilder expectedOutput) {
        Map<String, List<String>> adjacencies = new HashMap<>();
        Integer[] verticesAux = Utils.getRandomIntegerArray(10, 30, 0, 100);
        List<Integer> vertices = Arrays.stream(verticesAux).distinct().toList();
        for (int i = 0; i < vertices.size(); i++) {
            String from = "" + vertices.get(i);
            String to = "" + vertices.get((i + 1) % vertices.size());
            List<String> fromAdjacencies = new ArrayList<>();
            fromAdjacencies.add(to);
            adjacencies.put(from, fromAdjacencies);
        }
        for (int i = 0; i < 5; i++) {
            Integer start = vertices.get(Utils.getRandomInteger(0, vertices.size() - 1));
            Integer from = start;
            Integer to = vertices.get(Utils.getRandomInteger(0, vertices.size() - 1));
            while (!to.equals(start)) {
                String fromStr = "" + from;
                String toStr = "" + to;
                List<String> fromAdjacencies = adjacencies.get(fromStr);
                fromAdjacencies.add(toStr);
                adjacencies.put(fromStr, fromAdjacencies);
                from = to;
                to = vertices.get(Utils.getRandomInteger(0, vertices.size() - 1));
            }
            String fromStr = "" + from;
            String toStr = "" + to;
            List<String> fromAdjacencies = adjacencies.get(fromStr);
            fromAdjacencies.add(toStr);
            adjacencies.put(fromStr, fromAdjacencies);
        }
        StringBuilder str = new StringBuilder();
        adjacencies.forEach((from, toList) -> {
            str.append(from).append(" -> ").append(toList.get(0));
            for (int i = 1; i < toList.size(); i++) {
                str.append(",").append(toList.get(i));
            }
            str.append("\n");
        });
        return str.toString();
    }

    @Override
    protected AssertionFailedError verify(String input, String expectedOutput, String actualOutput) {
        // Process input
        input = input.replace(" ", "").replace("%", "\n").replace(";", ",");
        String[] line = input.split("\n");
        Map<String, List<String>> adjacencies = new HashMap<>();
        int adjacenciesCount = 0;
        for (String value : line) {
            String[] values = value.split("->");
            String from = values[0].trim();
            List<String> adj = adjacencies.getOrDefault(from, new ArrayList<>());
            String[] toList = values[1].split(",");
            adjacenciesCount += toList.length;;
            Collections.addAll(adj, toList);
            adjacencies.put(from, adj);
        }

        // Process output
        String[] vertices = actualOutput.split("->");

        // Verify output
        String from = vertices[0];
        assertEquals(adjacenciesCount, vertices.length - 1);
        for (int i = 1; i < vertices.length; i++) {
            String to = vertices[i];
            List<String> fromAdjacencies = adjacencies.get(from);
            assertNotNull(fromAdjacencies);
            assertTrue(fromAdjacencies.remove(to));
            if (fromAdjacencies.isEmpty()) {
                adjacencies.remove(from);
            } else {
                adjacencies.put(from, fromAdjacencies);
            }
            from = to;
        }
        assertTrue(adjacencies.isEmpty());

        return null;
    }
}
