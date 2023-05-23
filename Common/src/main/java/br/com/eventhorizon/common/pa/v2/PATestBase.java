package br.com.eventhorizon.common.pa.v2;

import br.com.eventhorizon.common.pa.PATestType;
import br.com.eventhorizon.common.pa.TimingExtension;
import br.com.eventhorizon.common.pa.v2.input.format.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.platform.commons.JUnitException;
import org.junit.platform.commons.util.ExceptionUtils;
import org.opentest4j.AssertionFailedError;

import java.io.*;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TimingExtension.class)
public abstract class PATestBase {

  private static final Logger LOGGER = Logger.getLogger(PATestBase.class.getName());

  private final PA pa;

  private final PATestSettings settings;

  private final InputFormat inputFormat;

  private OutputStream outputStream;

  protected PATestBase(PA pa) {
    this(pa, PATestSettings.Builder.defaultSettings());
  }

  protected PATestBase(PA pa, PATestSettings settings) {
    this.pa = pa;
    this.settings = settings;
    this.inputFormat = settings.getInputFormatFile() != null
        ? InputFormat.parse(settings.getInputFormatFile()) : null;
  }

  @Test
  public void voidMemoryLimitTest() throws IOException {
    if (!settings.isMemoryLimitTestEnabled()) {
      LOGGER.warning("Memory usage test status: " + Status.DISABLED);
      return;
    }
    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    double memoryLimit = (double) settings.getMemoryLimit() / 1000000;
    Runtime runtime = Runtime.getRuntime();
    runtime.gc();
    long initialUsedMemory = runtime.totalMemory() - runtime.freeMemory();
    long startTime = System.currentTimeMillis();
    int count = 0;
    while (System.currentTimeMillis() - startTime < settings.getMemoryLimitTestDuration()) {
      // Generate input
      String input = generateInput(PATestType.MEMORY_USAGE_TEST, null);
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      // Run and verify time consumed
      pa.finalSolution();
      long usedMemory = runtime.totalMemory() - runtime.freeMemory();
      String message = String.format("""
        Memory limit test status: %s
        Memory limit: %.2f MB
        Memory used: %.2f MB
        """, Status.FAILED, memoryLimit, (double) usedMemory / 1000000);
      assertTrue(usedMemory - initialUsedMemory < settings.getMemoryLimit(), message);
      LOGGER.info(String.format("Memory limit test %d memory: %.2f", count, (double) usedMemory / 1000000));
      count++;
      runtime.gc();
    }
    LOGGER.warning("Memory limit test status: " + Status.SUCCESS);
  }

  /**
   * Run a time limit test over the final solution by generating random input through the
   * generateInput() method and verify the time consumed by the execution.
   * This test does not verify if the output is correct, it does only verify the time consumed.
   */
  @Test
  public void timeLimitTest() {
    if (!settings.isTimeLimitTestEnabled()) {
      LOGGER.warning("Time limit test status: " + Status.DISABLED);
      return;
    }
    long startTime = System.currentTimeMillis();
    int count = 0;
    while (System.currentTimeMillis() - startTime < settings.getTimeLimitTestDuration()) {
      // Generate input
      String input = generateInput(PATestType.TIME_LIMIT_TEST, null);
      // Run and verify time consumed
      reset(input);
      assertTimeoutPreemptively(ofMillis(
          settings.getTimeLimit()),
          pa::finalSolution,
          String.format("Time limit test %d status: %s", count, Status.FAILED));
      LOGGER.info(String.format("Time limit test %d status: %s", count, Status.SUCCESS));
      count++;
    }
    LOGGER.warning("Time limit test status: " + Status.SUCCESS);
  }

  /**
   * Run a stress test over the final solution by generating random input through the
   * generateInput() method and verify the output through the verify() method.
   */
  @Test
  public void stressTest() {
    if (!settings.isStressTestEnabled()) {
      LOGGER.warning("Stress test status: " + Status.DISABLED);
      return;
    }
    long startTime = System.currentTimeMillis();
    int count = 0;
    while (System.currentTimeMillis() - startTime < settings.getStressTestDuration()) {
      // Generate input and expected output
      StringBuilder expectedOutput = new StringBuilder();
      String input = generateInput(PATestType.STRESS_TEST, expectedOutput);
      // Run and verify result
      reset(input);
      pa.finalSolution();
      String message = String.format("""
        Stress test %d status: %s
        Stress test failed for input:
        %s
        """, count, Status.FAILED, input);
      verify(input, expectedOutput.toString(), getActualOutput(), message);
      LOGGER.info(String.format("Stress test %d status: %s", count, Status.SUCCESS));
      count++;
    }
    LOGGER.warning("Stress test status: " + Status.SUCCESS);
  }

  /**
   * Run both the trivial solution and final solution and compare the results.
   * This test does not verify if the outputs are correct, it does only compare if they are equal.
   */
  @Test
  public void compareTest() {
    if (!settings.isCompareTestEnabled()) {
      LOGGER.warning("Compare test status: " + Status.DISABLED);
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
        verify(input, trivialSolutionOutput, finalSolutionOutput, message);
        LOGGER.info(String.format("Compare test %d status: %s", count, Status.SUCCESS));
        count++;
      } catch (Exception ex) {
        LOGGER.info(message);
        ex.printStackTrace();
        fail(message);
      }
    }
    LOGGER.warning("Compare test status: " + Status.SUCCESS);
  }

  protected void testSolution(PASolution solution, String input, String expectedOutput) {
    // Normalize input and output
    input = input.replace(";", ",").replace("%", "\n");
    expectedOutput = expectedOutput.replace(";", ",").replace("%", "\n");
    reset(input);
    // Run test
    if (solution == PASolution.TRIVIAL) {
      pa.trivialSolution();
    } else {
      pa.finalSolution();
    }
    // Verify output
    verify(input, expectedOutput, getActualOutput(), null);
  }

  protected void testSolutionFromFile(PASolution solution, String inputFile, String expectedOutputFile) throws IOException {
    InputStream inputStream = new BufferedInputStream(Objects.requireNonNull(this.getClass().getResourceAsStream(
        inputFile)));
    String input = new String(inputStream.readAllBytes()).trim();
    inputStream.close();
    inputStream = new BufferedInputStream(Objects.requireNonNull(this.getClass().getResourceAsStream(
        expectedOutputFile)));
    String expectedOutput = new String(inputStream.readAllBytes()).trim();
    inputStream.close();
    reset(input);
    // Run test
    testSolution(solution, input, expectedOutput);
  }

  protected String getActualOutput() {
    return outputStream.toString().trim();
  }

  protected String generateInput(PATestType type, StringBuilder expectedOutput) {
    if (inputFormat == null) {
      throw new RuntimeException("Input format specification file was not set");
    }
    return InputGenerator.generate(inputFormat);
  }

  protected void verify(String input, String expectedOutput, String actualOutput, String message) {
    assertNotNull(input, "Input is null");
    assertNotNull(expectedOutput, "Expected output is null");
    assertNotNull(actualOutput, "Actual output is null");
    assertEquals(expectedOutput, actualOutput, message != null ? message : "Actual output doesn't match the expected output");
  }

  private void reset(String input) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    pa.reset();
  }

  private static void assertTimeoutPreemptively(Duration timeout, Executable executable, String message) {
    AtomicReference<Thread> threadReference = new AtomicReference<>();
    ExecutorService executorService = Executors.newSingleThreadExecutor(new TimeoutThreadFactory());

    try {
      Future<Void> future = executorService.submit(() -> {
        try {
          threadReference.set(Thread.currentThread());
          executable.execute();
          return null;
        } catch (Throwable var3) {
          throw ExceptionUtils.throwAsUncheckedException(var3);
        }
      });
      long timeoutInMillis = timeout.toMillis();

      try {
        future.get(timeoutInMillis, TimeUnit.MILLISECONDS);
      } catch (TimeoutException var17) {
//        String message = AssertionUtils.buildPrefix(AssertionUtils.nullSafeGet(messageOrSupplier)) + "execution timed out after " + timeoutInMillis + " ms";
        LOGGER.info(message);
        Thread thread = threadReference.get();
        if (thread != null) {
          ExecutionTimeoutException exception = new ExecutionTimeoutException("Execution timed out in thread " + thread.getName());
          exception.setStackTrace(thread.getStackTrace());
          throw new AssertionFailedError(message, exception);
        }
        throw new AssertionFailedError(message);
      } catch (ExecutionException var18) {
        throw ExceptionUtils.throwAsUncheckedException(var18.getCause());
      } catch (Throwable var19) {
        throw ExceptionUtils.throwAsUncheckedException(var19);
      }
    } finally {
      executorService.shutdownNow();
    }
  }

  private static class TimeoutThreadFactory implements ThreadFactory {
    private static final AtomicInteger threadNumber = new AtomicInteger(1);

    private TimeoutThreadFactory() {
    }

    public Thread newThread(Runnable r) {
      return new Thread(r, "junit-timeout-thread-" + threadNumber.getAndIncrement());
    }
  }

  private static class ExecutionTimeoutException extends JUnitException {
    private static final long serialVersionUID = 1L;

    ExecutionTimeoutException(String message) {
      super(message);
    }
  }

  private enum Status {
    DISABLED,
    ABORTED,
    FAILED,
    SUCCESS;
  }
}
