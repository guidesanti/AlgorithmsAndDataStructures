package br.com.eventhorizon.edx.ucsandiego.algs203x.pa1;

import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import br.com.eventhorizon.common.pa.TestProperties;
import br.com.eventhorizon.sat.Cnf;
import br.com.eventhorizon.sat.NaiveSatSolver;
import br.com.eventhorizon.sat.SatSolverAlgorithm;
import br.com.eventhorizon.sat.SatVerifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class BudgetAllocationTest extends PATest {

  private static final Logger LOGGER = Logger.getLogger(BudgetAllocationTest.class.getName());

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/budget-allocation.csv";

  public BudgetAllocationTest() {
    super(new BudgetAllocation(), true, false);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    boolean expectedResult = Boolean.parseBoolean(expectedOutput);
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    pa.naiveSolution();
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
    System.setIn(new ByteArrayInputStream(input.getBytes()));
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
  public void stressTest() {
    if (skipStressTest) {
      LOGGER.warning("Stress limit test skipped");
      return;
    }
    LOGGER.info("Stress test duration: " + TestProperties.getStressTestDuration());
    long startTime = System.currentTimeMillis();
    for (long i = 0; true; i++) {
      String input = generateInput(PATestType.STRESS_TEST);
      LOGGER.info("Stress test " + i + " input: " + input);

      // Run and compare results
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      resetOutput();
      pa.naiveSolution();
      String result1 = getActualOutput();
      resetOutput();
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      pa.finalSolution();
      String result2 = getActualOutput();

      SatSolverAlgorithm satSolver = new NaiveSatSolver();
      Cnf cnf1 = stringToCnf(result1);
      List<Integer> solution1 = satSolver.solve(cnf1);
      Cnf cnf2 = stringToCnf(result2);
      List<Integer> solution2 = satSolver.solve(cnf2);

      if ((solution1 == null && solution2 != null) ||
          (solution1 != null && solution2 == null) ||
          (solution1 != null && SatVerifier.verify(cnf1, solution1) != SatVerifier.verify(cnf2, solution2))) {
        LOGGER.info("Stress test " + i + " status: FAILED");
        LOGGER.info("Stress test " + i + " result 1:  " + result1);
        LOGGER.info("Stress test " + i + " result 2:  " + result2);
        throw new RuntimeException("Stress test failed");
      }
      LOGGER.info("Stress test " + i + " status: PASSED");

      // Check elapsed time
      long elapsedTime = System.currentTimeMillis() - startTime;
      if (elapsedTime > TestProperties.getStressTestDuration()) {
        return;
      }
    }
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
  protected String generateInput(PATestType type) {
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
