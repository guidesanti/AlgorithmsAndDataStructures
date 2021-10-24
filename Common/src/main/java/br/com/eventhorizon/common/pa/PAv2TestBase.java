package br.com.eventhorizon.common.pa;

import br.com.eventhorizon.common.pa.format.*;
import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TimingExtension.class)
public abstract class PAv2TestBase {

  private static final Logger LOGGER = Logger.getLogger(PAv2TestBase.class.getName());

  protected final PAv2 pa;

  private final PATestSettings settings;

  private OutputStream outputStream;

  protected PAv2TestBase(PAv2 pa) {
    this.pa = pa;
    this.settings = PATestSettings.Builder.defaultSettings();
  }

  protected PAv2TestBase(PAv2 pa, PATestSettings settings) {
    this.pa = pa;
    this.settings = settings;
  }

  /**
   * Run a time limit test over the final solution by generating random input through the
   * generateInput() method and verify the time consumed by the execution.
   * This test does not verify if the output is correct, it does only verify the time consumed.
   */
  @Test
  public void timeLimitTest() {
    if (settings.isSkipTimeLimitTest()) {
      LOGGER.warning("Time limit test skipped");
      return;
    }
    LOGGER.info("Time limit test duration: " + settings.getTimeLimitTestDuration());
    long maxTime = 0;
    long minTime = Integer.MAX_VALUE;
    List<Long> times = new ArrayList<>();
    long totalStartTime = System.currentTimeMillis();
    for (long i = 0; true; i++) {
      // Generate input
      String input = generateInput(PATestType.TIME_LIMIT_TEST, null);
      LOGGER.info("Time limit test " + i + " input: " + input);

      // Run and verify time consumed
      reset(input);
      long startTime = System.currentTimeMillis();
      assertTimeoutPreemptively(ofMillis(settings.getTimeLimit()), pa::finalSolution);
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
      if (totalElapsedTime > settings.getTimeLimitTestDuration()) {
        LOGGER.info("Time limit test total tests executed: " + i + 1);
        LOGGER.info("Time limit test min time: " + minTime);
        LOGGER.info("Time limit test max time: " + maxTime);
        Optional<Long> sum = times.stream().reduce(Long::sum);
        LOGGER.info("Time limit test average time: " + (double) sum.get() / (i + 1));
        return;
      }
    }
  }

  /**
   * Run a stress test over the final solution by generating random input through the
   * generateInput() method and verify the output through the verify() method.
   */
  @Test
  public void stressTest() {
    if (settings.isSkipCompareTest()) {
      LOGGER.warning("Stress test skipped");
      return;
    }
    LOGGER.info("Stress test duration: " + settings.getStressTestDuration());
    long startTime = System.currentTimeMillis();
    for (long i = 0; true; i++) {
      // Generate input
      StringBuilder expectedOutputTemp = new StringBuilder();
      String input = generateInput(PATestType.STRESS_TEST, expectedOutputTemp);
      String expectedOutput = expectedOutputTemp.toString();
      LOGGER.info("Stress test " + i + " input: " + input);

      // Run and verify result
      reset(input);
      pa.finalSolution();
      String actualOutput = getActualOutput();
      if (verify(input, expectedOutput, actualOutput)) {
        LOGGER.info("Stress test " + i + " status: PASSED");
      } else {
        LOGGER.info("Stress test " + i + " status: FAILED");
        LOGGER.info("Stress test " + i + " expected output:  " + expectedOutput);
        LOGGER.info("Stress test " + i + " actual output:  " + actualOutput);
        fail("Stress test failed");
      }

      // Check elapsed time
      long elapsedTime = System.currentTimeMillis() - startTime;
      if (elapsedTime > settings.getStressTestDuration()) {
        return;
      }
    }
  }

  /**
   * Run both the trivial solution and final solution and compare the results.
   * This test does not verify if the outputs are correct, it does only compare if they are equal.
   */
  @Test
  public void compareTest() {
    if (settings.isSkipCompareTest()) {
      LOGGER.warning("Compare test skipped");
      return;
    }
    LOGGER.info("Compare test duration: " + settings.getStressTestDuration());
    long startTime = System.currentTimeMillis();
    for (long i = 0; true; i++) {
      String input = generateInput(PATestType.COMPARE_TEST, null);
      LOGGER.info("Compare test " + i + " input: " + input);

      // Run and compare results
      reset(input);
      pa.trivialSolution();
      String trivialSolutionOutput = getActualOutput();
      reset(input);
      pa.finalSolution();
      String finalSolutionOutput = getActualOutput();
      if (trivialSolutionOutput.equals(finalSolutionOutput)) {
        LOGGER.info("Compare test " + i + " status: PASSED");
      } else {
        LOGGER.info("Compare test " + i + " status: FAILED");
        LOGGER.info("Compare test " + i + " trivial solution output:  " + trivialSolutionOutput);
        LOGGER.info("Compare test " + i + " final solution output:  " + finalSolutionOutput);
        fail("Compare test failed");
      }

      // Check elapsed time
      long elapsedTime = System.currentTimeMillis() - startTime;
      if (elapsedTime > settings.getStressTestDuration()) {
        return;
      }
    }
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
    if (!verify(input, expectedOutput, getActualOutput())) {
      fail("Test failed");
    }
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
    if (solution == PASolution.TRIVIAL) {
      pa.trivialSolution();
    } else {
      pa.finalSolution();
    }
  }

  protected String getActualOutput() {
    return outputStream.toString().trim();
  }

  protected String generateInput(PATestType type, StringBuilder expectedOutput) {
    if (settings.getInputFormat() == null) {
      throw new RuntimeException("Input format specification file was not set");
    }

    StringBuilder input = new StringBuilder();
    Map<Reference, Object> references = new HashMap<>();
    int lineNumber = 0;
    for (Line line : settings.getInputFormat().getLines()) {
      int count;
      if (line.getCountRef() != null) {
        count = (int) references.get(line.getCountRef());
      } else {
        count = line.getCount();
      }

      for (int i = 0; i < count; i++) {
        for (int fieldNumber = 0; fieldNumber < line.getFields().size(); fieldNumber++) {
          Reference reference = new Reference(lineNumber, fieldNumber);
          Field field = line.getFields().get(fieldNumber);
          switch (field.getType()) {
            case BOOLEAN -> {
              boolean booleanValue = Utils.getRandomInteger(0, 9) < 5;
              references.put(reference, booleanValue);
              input.append(booleanValue);
            }
            case INTEGER -> {
              IntegerField integerField = (IntegerField) field;
              int integerValue =
                  Utils.getRandomInteger(integerField.getMinimum(), integerField.getMaximum());
              references.put(reference, integerValue);
              input.append(integerValue);
            }
            case LONG -> {
              LongField longField = (LongField) field;
              long longValue = Utils.getRandomLong(longField.getMinimum(), longField.getMaximum());
              references.put(reference, longValue);
              input.append(longValue);
            }
            case DOUBLE -> {
              DoubleField doubleField = (DoubleField) field;
              double doubleValue =
                  Utils.getRandomDouble(doubleField.getMinimum(), doubleField.getMaximum());
              references.put(reference, doubleValue);
              input.append(doubleValue);
            }
            case STRING -> {
              StringField stringField = (StringField) field;
              String stringValue;
              if (stringField.getLengthRef() != null) {
                stringValue =
                    Utils.getRandomString(stringField.getAlphabet(), (int) references.get(stringField.getLengthRef()));
              } else if (stringField.getLength() > 0) {
                stringValue =
                    Utils.getRandomString(stringField.getAlphabet(), stringField.getLength());
              } else {
                stringValue =
                    Utils.getRandomString(stringField.getAlphabet(), stringField.getMinLength(), stringField.getMaxLength());
              }
              references.put(reference, stringValue);
              input.append(stringValue);
            }
          }
          input.append(" ");
        }
        input.replace(input.length() - 1, input.length(), "\n");
        lineNumber++;
      }
    }

    return input.toString();
  }

  protected boolean verify(String input, String expectedOutput, String actualOutput) {
    assertNotNull(input, "Input is null");
    assertNotNull(expectedOutput, "Expected output is null");
    assertNotNull(actualOutput, "Actual output is null");
    return expectedOutput.equals(actualOutput);
  }

  private void reset(String input) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
    pa.reset();
  }
}
