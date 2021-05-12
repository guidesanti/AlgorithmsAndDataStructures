package br.com.eventhorizon.common.pa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.naming.OperationNotSupportedException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

@ExtendWith(TimingExtension.class)
public abstract class PATest {

  private static final Logger LOGGER = Logger.getLogger(PATest.class.getName());

  protected final PA pa;

  protected final boolean skipTimeLimitTest;

  protected final boolean skipStressTest;

  private OutputStream outputStream;

  public PATest(PA pa) {
    this.pa = pa;
    this.skipTimeLimitTest = false;
    this.skipStressTest = false;
  }

  public PATest(PA pa, boolean skipTimeLimitTest, boolean skipStressTest) {
    this.pa = pa;
    this.skipTimeLimitTest = skipTimeLimitTest;
    this.skipStressTest = skipStressTest;
  }

  @BeforeEach
  public void beforeEach() {
    resetOutput();
  }

  protected void testNaiveSolution(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    pa.naiveSolution();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  protected void testIntermediateSolution1(String input, String expectedOutput)
      throws OperationNotSupportedException {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    pa.intermediateSolution1();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  protected void testIntermediateSolution2(String input, String expectedOutput)
      throws OperationNotSupportedException {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    pa.intermediateSolution2();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  protected void testFinalSolution(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    pa.finalSolution();
    Assertions.assertEquals(expectedOutput, getActualOutput());
  }

  @Test
  public void timeLimitTest() {
    if (skipTimeLimitTest) {
      LOGGER.warning("Time limit test skipped");
      return;
    }
    LOGGER.info("Time limit test duration: " + TestProperties.getTimeLimitTestDuration());
    long maxTime = 0;
    long minTime = Integer.MAX_VALUE;
    List<Long> times = new ArrayList<>();
    long totalStartTime = System.currentTimeMillis();
    for (long i = 0; true; i++) {
      String input = generateInput(PATestType.TIME_LIMIT_TEST);
      LOGGER.info("Time limit test " + i + " input: " + input);

      System.setIn(new ByteArrayInputStream(input.getBytes()));
      resetOutput();
      long startTime = System.currentTimeMillis();
      assertTimeoutPreemptively(ofMillis(TestProperties.getTimeLimit()), pa::finalSolution);
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
      if (totalElapsedTime > TestProperties.getTimeLimitTestDuration()) {
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
      if (result1.equals(result2)) {
        LOGGER.info("Stress test " + i + " status: PASSED");
      } else {
        LOGGER.info("Stress test " + i + " status: FAILED");
        LOGGER.info("Stress test " + i + " result 1:  " + result1);
        LOGGER.info("Stress test " + i + " result 2:  " + result2);
        throw new RuntimeException("Stress test failed");
      }

      // Check elapsed time
      long elapsedTime = System.currentTimeMillis() - startTime;
      if (elapsedTime > TestProperties.getStressTestDuration()) {
        return;
      }
    }
  }

  protected String getActualOutput() {
    return outputStream.toString().trim();
  }

  protected void resetOutput() {
    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
  }

  protected int getRandomInteger(int min, int max) {
    if (min >= max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
    Random random = new Random();
    return random.nextInt((max - min) + 1) + min;
  }

  protected long getRandomLong(long min, long max) {
    if (min >= max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
    return ThreadLocalRandom.current().nextLong(min, max);
  }

  protected String generateInput(PATestType type) {
    return null;
  }
}
