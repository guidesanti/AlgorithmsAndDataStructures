package br.com.eventhorizon.edx.ucsandiego.algs203x.pa2;

import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import br.com.eventhorizon.common.pa.TestProperties;
import br.com.eventhorizon.sat.*;
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

public class IntegratedCircuitDesignTest extends PATest {

  private static final Logger LOGGER = Logger.getLogger(IntegratedCircuitDesignTest.class.getName());

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa2/integrated-circuit-design.csv";

  public IntegratedCircuitDesignTest() {
    super(new IntegratedCircuitDesign());
    TestProperties.setTimeLimit(18000);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    pa.naiveSolution();
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
    System.setIn(new ByteArrayInputStream(input.getBytes()));
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
  public void stressTest() {
    if (skipStressTest) {
      LOGGER.warning("Stress limit test skipped");
      return;
    }
    LOGGER.info("Stress test duration: " + TestProperties.getStressTestDuration());
    long startTime = System.currentTimeMillis();
    for (long i = 0; true; i++) {
      String input = generateInput(PATestType.STRESS_TEST);
      Cnf cnf = readCnf(new FastScanner(new ByteArrayInputStream(input.getBytes())));
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

      if ((result1.startsWith("UNSATISFIABLE") && !result2.startsWith("UNSATISFIABLE")) ||
          (result2.startsWith("UNSATISFIABLE") && !result1.startsWith("UNSATISFIABLE")) ||
          (result1.startsWith("SATISFIABLE") && result2.startsWith("SATISFIABLE") &&
              SatVerifier.verify(cnf, Arrays.stream(result1.split("\n")[1].split(" ")).map(Integer::parseInt).collect(Collectors.toList())) !=
                  SatVerifier.verify(cnf, Arrays.stream(result2.split("\n")[1].split(" ")).map(Integer::parseInt).collect(Collectors.toList())))) {
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
  protected String generateInput(PATestType type) {
    StringBuilder input = new StringBuilder();
    int numberOfVariables;
    int numberOfClauses;
    switch (type) {
      case TIME_LIMIT_TEST:
        numberOfVariables = Utils.getRandomInteger(1, 1000000);
        numberOfClauses = Utils.getRandomInteger(1, 1000000);
        break;
      case STRESS_TEST:
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
