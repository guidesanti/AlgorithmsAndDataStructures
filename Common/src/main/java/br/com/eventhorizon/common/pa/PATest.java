package br.com.eventhorizon.common.pa;

import br.com.eventhorizon.common.pa.format.*;
import br.com.eventhorizon.common.utils.Utils;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.naming.OperationNotSupportedException;
import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @deprecated
 * This class was replaced by PAv2TestBase.
 * <p>Use {@link PAv2TestBase} instead.</p>
 */
@Deprecated
@ExtendWith(TimingExtension.class)
public abstract class PATest {

  private static final Logger LOGGER = Logger.getLogger(PATest.class.getName());

  protected final PA pa;

  protected final boolean skipTimeLimitTest;

  protected final boolean skipStressTest;

  private final InputFormat inputFormat;

  private OutputStream outputStream;

  public PATest(PA pa) {
    this.pa = pa;
    this.skipTimeLimitTest = false;
    this.skipStressTest = false;
    this.inputFormat = null;
  }

  public PATest(PA pa, boolean skipTimeLimitTest, boolean skipStressTest) {
    this.pa = pa;
    this.skipTimeLimitTest = skipTimeLimitTest;
    this.skipStressTest = skipStressTest;
    this.inputFormat = null;
  }

  public PATest(PA pa, boolean skipTimeLimitTest, boolean skipStressTest, String inputFormatSpecificationFile) throws IOException {
    this.pa = pa;
    this.skipTimeLimitTest = skipTimeLimitTest;
    this.skipStressTest = skipStressTest;
    this.inputFormat = loadInputFormat(inputFormatSpecificationFile);
  }

  @BeforeEach
  public void beforeEach() {
    resetOutput();
  }

  protected void testNaiveSolution(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    pa.naiveSolution();
    verify(input, expectedOutput, getActualOutput());
  }

  protected void testIntermediateSolution1(String input, String expectedOutput)
      throws OperationNotSupportedException {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    pa.intermediateSolution1();
    assertEquals(expectedOutput, getActualOutput());
  }

  protected void testIntermediateSolution2(String input, String expectedOutput)
      throws OperationNotSupportedException {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    pa.intermediateSolution2();
    assertEquals(expectedOutput, getActualOutput());
  }

  protected void testFinalSolution(String input, String expectedOutput) {
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    pa.finalSolution();
    verify(input, expectedOutput, getActualOutput());
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
    if (inputFormat == null) {
      throw new RuntimeException("Input format specification file was not set");
    }

    StringBuilder input = new StringBuilder();
    Map<Reference, Object> references = new HashMap<>();
    int lineNumber = 0;
    for (Line line : inputFormat.getLines()) {
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
            case BOOLEAN:
              boolean booleanValue = Utils.getRandomInteger(0, 9) < 5;
              references.put(reference, booleanValue);
              input.append(booleanValue);
              break;
            case INTEGER:
              IntegerField integerField = (IntegerField) field;
              int integerValue = Utils.getRandomInteger(integerField.getMinimum(), integerField.getMaximum());
              references.put(reference, integerValue);
              input.append(integerValue);
              break;
            case LONG:
              LongField longField = (LongField) field;
              long longValue = Utils.getRandomLong(longField.getMinimum(), longField.getMaximum());
              references.put(reference, longValue);
              input.append(longValue);
              break;
            case DOUBLE:
              DoubleField doubleField = (DoubleField) field;
              double doubleValue = Utils.getRandomDouble(doubleField.getMinimum(), doubleField.getMaximum());
              references.put(reference, doubleValue);
              input.append(doubleValue);
              break;
            case STRING:
              StringField stringField = (StringField) field;
              String stringValue;
              if (stringField.getLengthRef() != null) {
                stringValue = Utils.getRandomString(stringField.getAlphabet(), (int) references.get(stringField.getLengthRef()));
              } else if (stringField.getLength() > 0) {
                stringValue = Utils.getRandomString(stringField.getAlphabet(), stringField.getLength());
              } else {
                stringValue = Utils.getRandomString(stringField.getAlphabet(), stringField.getMinLength(), stringField.getMaxLength());
              }
              references.put(reference, stringValue);
              input.append(stringValue);
              break;
          }
          input.append(" ");
        }
        input.replace(input.length() - 1, input.length(), "\n");
        lineNumber++;
      }
    }

    return input.toString();
  }

  protected void verify(String input, String expectedOutput, String actualOutput) {
    assertNotNull(input);
    assertNotNull(expectedOutput);
    assertNotNull(actualOutput);
    assertEquals(expectedOutput, actualOutput);
  }

  private InputFormat loadInputFormat(String inputFormatFilePath) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule("Deserializer", new Version(1, 0, 0, null, null, null));
    module.addDeserializer(Field.class, new FieldDeserializer());
    objectMapper.registerModule(module);
    InputStream inputFormatFile = getClass().getClassLoader().getResourceAsStream(inputFormatFilePath);
    return objectMapper.readValue(inputFormatFile, InputFormat.class);
  }
}
