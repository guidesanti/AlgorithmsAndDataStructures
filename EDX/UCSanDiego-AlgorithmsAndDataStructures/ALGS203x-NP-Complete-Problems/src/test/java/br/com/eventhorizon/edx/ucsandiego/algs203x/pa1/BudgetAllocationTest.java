package br.com.eventhorizon.edx.ucsandiego.algs203x.pa1;

import br.com.eventhorizon.common.pa.test.PASystemSettings;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.sat.Cnf;
import br.com.eventhorizon.sat.NaiveSatSolver;
import br.com.eventhorizon.sat.SatSolverAlgorithm;
import br.com.eventhorizon.sat.SatVerifier;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class BudgetAllocationTest extends PATestBase {

    private static final Logger LOGGER = Logger.getLogger(BudgetAllocationTest.class.getName());

    private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/budget-allocation.csv";

    public BudgetAllocationTest() {
        super(new BudgetAllocation(), PATestSettings.builder()
                .timeLimitTestEnabled(true)
                .compareTestEnabled(true)
                .build());
    }

    @ParameterizedTest
    @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
    public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
        boolean expectedResult = Boolean.parseBoolean(expectedOutput);
        reset(input);
        pa.trivialSolution();
        Cnf cnf = stringToCnf(getActualOutput());
        SatSolverAlgorithm satSolver = new NaiveSatSolver();
        List<Integer> solution = satSolver.solve(cnf);
        if (solution != null) {
            assertTrue(expectedResult);
            assertTrue(SatVerifier.verify(cnf, solution));
        } else {
            assertFalse(expectedResult);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
    public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
        boolean expectedResult = Boolean.parseBoolean(expectedOutput);
        reset(input);
        pa.finalSolution();
        Cnf cnf = stringToCnf(getActualOutput());
        SatSolverAlgorithm satSolver = new NaiveSatSolver();
        List<Integer> solution = satSolver.solve(cnf);
        if (solution != null) {
            assertTrue(expectedResult);
            assertTrue(SatVerifier.verify(cnf, solution));
        } else {
            assertFalse(expectedResult);
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
                message = String.format("""
        Compare test %d status: %s
        Compare test failed for input:
        %s
        """, count, Status.FAILED, input);
                // Run and compare results
                reset(input);
                pa.trivialSolution();
                String trivialSolutionOutput = getActualOutput();
                reset(input);
                pa.finalSolution();
                String finalSolutionOutput = getActualOutput();

                SatSolverAlgorithm satSolver = new NaiveSatSolver();
                Cnf cnf1 = stringToCnf(trivialSolutionOutput);
                List<Integer> solution1 = satSolver.solve(cnf1);
                Cnf cnf2 = stringToCnf(finalSolutionOutput);
                List<Integer> solution2 = satSolver.solve(cnf2);

                if ((solution1 == null && solution2 != null)
                        || (solution1 != null && solution2 == null)
                        || (solution1 != null && SatVerifier.verify(cnf1, solution1) != SatVerifier.verify(cnf2, solution2))) {
                    LOGGER.info("Stress test " + count + " status: FAILED");
                    LOGGER.info("Stress test " + count + " result 1:  " + trivialSolutionOutput);
                    LOGGER.info("Stress test " + count + " result 2:  " + finalSolutionOutput);
                    throw new RuntimeException("Compare test failed");
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

    private Cnf stringToCnf(String str) {
        String[] lines = str.split("\n");
        String[] line = lines[0].split(" ");
        int numberOfClauses = Integer.parseInt(line[0]);
        assertEquals(numberOfClauses, lines.length - 1);
        int numberOfVariables = Integer.parseInt(line[1]);
        List<List<Integer>> clauses = new ArrayList<>();
        for (int i = 1; i < lines.length; i++) {
            line = lines[i].split(" ");
            List<Integer> clause = new ArrayList<>();
            for (int j = 0; j < line.length; j++) {
                clause.add(Integer.parseInt(line[j]));
            }
            clauses.add(clause);
        }
        return new Cnf(numberOfVariables, clauses);
    }

    @Override
    protected String generateInput(PATestType type, StringBuilder expectedOutput) {
        StringBuilder input = new StringBuilder();
        int numberOfInequalities;
        int numberOfVariables;
        if (type == PATestType.TIME_LIMIT_TEST) {
            numberOfInequalities = Utils.getRandomInteger(1, 500);
            numberOfVariables = Utils.getRandomInteger(1, 500);
        } else {
            numberOfInequalities = Utils.getRandomInteger(1, 10);
            numberOfVariables = Utils.getRandomInteger(1, 10);
        }
        input.append(numberOfInequalities).append(" ").append(numberOfVariables);
        for (int i = 0; i < numberOfInequalities; i++) {
            int n = Utils.getRandomInteger(0, 3);
            for (int j = 0; j < numberOfVariables; j++) {
                boolean b = false;
                if (n > 0) {
                    b = Utils.getRandomBoolean();
                }
                if (b) {
                    input.append(" ").append(Utils.getRandomInteger(-100, 100));
                    n--;
                } else {
                    input.append(" " + 0);
                }
            }
        }
        for (int i = 0; i < numberOfInequalities; i++) {
            input.append(" ").append(Utils.getRandomInteger(-1000000, 1000000));
        }
        return input.toString();
    }
}
