package br.com.eventhorizon.edx.ucsandiego.algs203x.pa2;

import br.com.eventhorizon.common.pa.test.*;
import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.sat.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class IntegratedCircuitDesignTest extends PATestBase {

    private static final Logger LOGGER = Logger.getLogger(IntegratedCircuitDesignTest.class.getName());

    private static final String SIMPLE_DATA_SET = "/test-dataset/pa2/integrated-circuit-design.csv";

    public IntegratedCircuitDesignTest() {
        super(new IntegratedCircuitDesign(), PATestSettings.builder()
                .timeLimitTestEnabled(true)
                .timeLimit(18000)
                .compareTestEnabled(true)
                .build());
    }

    @ParameterizedTest
    @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
    public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
        reset(input);
        pa.trivialSolution();
        Cnf cnf = readCnf(new FastScanner(new ByteArrayInputStream(input.getBytes())));
        String[] output = getActualOutput().split("\n");
        if (expectedOutput.startsWith("SATISFIABLE")) {
            assertEquals("SATISFIABLE", output[0]);
            List<Integer> solution = Arrays.stream(output[1].split(" ")).map(Integer::valueOf).collect(Collectors.toList());
            assertTrue(SatVerifier.verify(cnf, solution));
        } else {
            assertEquals("UNSATISFIABLE", output[0]);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
    public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
        reset(input);
        pa.finalSolution();
        Cnf cnf = readCnf(new FastScanner(new ByteArrayInputStream(input.getBytes())));
        String[] output = getActualOutput().split("\n");
        if (expectedOutput.startsWith("SATISFIABLE")) {
            assertEquals("SATISFIABLE", output[0]);
            List<Integer> solution = Arrays.stream(output[1].split(" ")).map(Integer::valueOf).collect(Collectors.toList());
            assertTrue(SatVerifier.verify(cnf, solution));
        } else {
            assertEquals("UNSATISFIABLE", output[0]);
        }
    }

    @Test
    @Override
    public void compareTest() {
        if (!PASystemSettings.isCompareTestEnabled().orElse(settings.isCompareTestEnabled())) {
            log.warn("Compare test status: {}", Status.SKIPPED);
            return;
        }
        long startTime = System.currentTimeMillis();
        int count = 0;
        while (System.currentTimeMillis() - startTime < settings.getCompareTestDuration()) {
            String message = null;
            try {
                // Generate input
                String input = generateInput(PATestType.COMPARE_TEST, null);
                Cnf cnf = readCnf(new FastScanner(new ByteArrayInputStream(input.getBytes())));
                message = String.format("""
        Compare test %d status: %s
        Compare test failed for input:
        %s
        """, count, Status.FAILED, input);

                // Run
                reset(input);
                pa.trivialSolution();
                String trivialSolutionOutput = getActualOutput();
                reset(input);
                pa.finalSolution();
                String finalSolutionOutput = getActualOutput();

                // Compare results
                if ((trivialSolutionOutput.startsWith("UNSATISFIABLE") && !finalSolutionOutput.startsWith("UNSATISFIABLE")) ||
                        (finalSolutionOutput.startsWith("UNSATISFIABLE") && !trivialSolutionOutput.startsWith("UNSATISFIABLE")) ||
                        (trivialSolutionOutput.startsWith("SATISFIABLE") && finalSolutionOutput.startsWith("SATISFIABLE") &&
                                SatVerifier.verify(cnf, Arrays.stream(trivialSolutionOutput.split("\n")[1].split(" ")).map(Integer::parseInt).collect(Collectors.toList())) !=
                                        SatVerifier.verify(cnf, Arrays.stream(finalSolutionOutput.split("\n")[1].split(" ")).map(Integer::parseInt).collect(Collectors.toList())))) {
                    LOGGER.info("Stress test " + count + " status: FAILED");
                    LOGGER.info("Stress test " + count + " result 1:  " + trivialSolutionOutput);
                    LOGGER.info("Stress test " + count + " result 2:  " + finalSolutionOutput);
                    throw new RuntimeException("Stress test failed");
                }

                log.info("Compare test {} status: {}}", count, Status.SUCCESS);
                count++;
            } catch (Exception ex) {
                log.error(message, ex);
                fail(message);
            }
        }
        log.warn("Compare test status: {}", Status.SUCCESS);
    }

    public static Cnf readCnf(FastScanner scanner) {
        int numberOfVariables = scanner.nextInt();
        int numberOfClauses = scanner.nextInt();
        List<List<Integer>> clauses = new ArrayList<>();
        for (int i = 0; i < numberOfClauses; i++) {
            List<Integer> clause = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                clause.add(scanner.nextInt());
            }
            clauses.add(clause);
        }
        return new Cnf(numberOfVariables, clauses);
    }

    @Override
    protected String generateInput(PATestType type, StringBuilder expectedOutput) {
        StringBuilder input = new StringBuilder();
        int numberOfVariables;
        int numberOfClauses;
        switch (type) {
            case TIME_LIMIT_TEST:
                numberOfVariables = Utils.getRandomInteger(1, 1000000);
                numberOfClauses = Utils.getRandomInteger(1, 1000000);
                break;
            default:
                numberOfVariables = Utils.getRandomInteger(1, 20);
                numberOfClauses = Utils.getRandomInteger(1, 20);
                break;
        }
        input.append(numberOfVariables).append(" ").append(numberOfClauses);
        for (int i = 0; i < numberOfClauses; i++) {
            int literal1 = Utils.getRandomBoolean() ? Utils.getRandomInteger(1, numberOfVariables) : -Utils.getRandomInteger(1, numberOfVariables);
            int literal2 = Utils.getRandomBoolean() ? Utils.getRandomInteger(1, numberOfVariables) : -Utils.getRandomInteger(1, numberOfVariables);
            input.append(" ").append(literal1).append(" ").append(literal2);
        }
        return input.toString();
    }
}
