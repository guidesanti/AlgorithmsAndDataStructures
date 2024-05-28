package br.com.eventhorizon.sat;

import br.com.eventhorizon.common.pa.test.Defaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.*;

public class DpllSatSolverTest {

  private static final Logger LOGGER = Logger.getLogger(DpllSatSolverTest.class.getName());

  private static final String DATA_SET = "/sat/sat-solver.csv";

  @ParameterizedTest
  @CsvFileSource(resources = DATA_SET, numLinesToSkip = 1)
  public void testDpllSatSolver(
      String satCnfFileName,
      boolean satisfiable) {
    Cnf cnf = SatUtils.readCnfFromFile("src/test/resources/sat/" + satCnfFileName);
    DpllSatSolver satSolver = new DpllSatSolver();
    List<Integer> solution = satSolver.solve(cnf);
    if (satisfiable) {
      assertNotNull(solution);
      assertTrue(SatVerifier.verify(cnf, solution));
    } else {
      assertNull(solution);
    }
  }

  @Test
  public void timeLimitTest() {
    LOGGER.info("Time limit test duration: " + Defaults.TIME_LIMIT_TEST_DURATION);
    long maxTime = 0;
    long minTime = Integer.MAX_VALUE;
    List<Long> times = new ArrayList<>();
    long totalStartTime = System.currentTimeMillis();
    for (long i = 0; true; i++) {
      Cnf cnf = SatUtils.getRandomCnf(2, 5, 1, 100);
      LOGGER.info("Time limit test " + i + " input: " + cnf);

      DpllSatSolver satSolver = new DpllSatSolver();
      long startTime = System.currentTimeMillis();
      assertTimeoutPreemptively(ofMillis(Defaults.TIME_LIMIT_TEST_DURATION), () -> satSolver.solve(cnf));
      long elapsedTime = System.currentTimeMillis() - startTime;
      times.add(elapsedTime);
      if (elapsedTime > maxTime) {
        maxTime = elapsedTime;
      }
      if (elapsedTime < minTime) {
        minTime = elapsedTime;
      }
      LOGGER.info("Time limit test " + i + " status: PASSED");

      // Check elapsed time
      long totalElapsedTime = System.currentTimeMillis() - totalStartTime;
      if (totalElapsedTime > Defaults.TIME_LIMIT_TEST_DURATION) {
        LOGGER.info("Time limit test total tests executed: " + i + 1);
        LOGGER.info("Time limit test min time: " + minTime);
        LOGGER.info("Time limit test max time: " + maxTime);
        Optional<Long> sum = times.stream().reduce(Long::sum);
        if (sum.isPresent()) {
          LOGGER.info("Time limit test average time: " + (double) sum.get() / (i + 1));
        }
        return;
      }
    }
  }

  @Test
  public void stressTest() {
    LOGGER.info("Stress test duration: " + Defaults.STRESS_TEST_DURATION);
    long startTime = System.currentTimeMillis();
    NaiveSatSolver naiveSatSolver = new NaiveSatSolver();
    DpllSatSolver satSolver = new DpllSatSolver();
    for (int test = 0; true; test++) {
      Cnf cnf = SatUtils.getRandomCnf(2, 10, 1, 30);
      LOGGER.info("Stress test " + test + ": " + cnf.toString());
      List<Integer> naiveSolution = naiveSatSolver.solve(cnf);
      List<Integer> solution = satSolver.solve(cnf);
      if (naiveSolution == null) {
        assertNull(solution);
      } else {
        boolean result = SatVerifier.verify(cnf, solution);
        if (!result) {
          LOGGER.info("Stress test " + test + " status: FAILED");
        }
        assertTrue(result);
      }
      LOGGER.info("Stress test " + test + " status: PASSED");

      // Check elapsed time
      long elapsedTime = System.currentTimeMillis() - startTime;
      if (elapsedTime > Defaults.STRESS_TEST_DURATION) {
        return;
      }
    }
  }
}
