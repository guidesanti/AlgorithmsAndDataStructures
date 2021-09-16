package br.com.eventhorizon.edx.ucsandiego.algs207x.pa3;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.TestProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BubbleDetectionTest extends PATest {

  private static final Logger LOGGER = Logger.getLogger(BubbleDetectionTest.class.getName());

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa3/bubble-detection.csv";

  private static final char[] ALPHABET = { 'A', 'C', 'G', 'T' };

  public BubbleDetectionTest() {
    super(new BubbleDetection(), true, true);
    TestProperties.setTimeLimit(30000);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    input = input.replace("%", "\n");
    expectedOutput = expectedOutput.replace("%", "\n");
    super.testFinalSolution(input, expectedOutput);
  }

  @Test
  public void stressTest() {
    LOGGER.info("Stress test duration: " + TestProperties.getStressTestDuration());
    long startTime = System.currentTimeMillis();
    for (long i = 0; true; i++) {
      String text = Utils.getRandomString(ALPHABET, 20);
      List<String> reads = generateReads(text, 10);
      StringBuilder str = new StringBuilder();
      reads.forEach(read -> str.append(read).append("\n"));
      String input = 5 + " " + 5 + "\n" + str.toString();
      LOGGER.info("Stress test " + i + " text: " + text);
      LOGGER.info("Stress test " + i + " input: " + input);

      // Run and compare results
      resetOutput();
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      pa.finalSolution();
      String output = getActualOutput();
      LOGGER.info("Stress test " + i + " output: " + output);
//      verify(input, text, output);

      // Check elapsed time
      long elapsedTime = System.currentTimeMillis() - startTime;
      if (elapsedTime > TestProperties.getStressTestDuration()) {
        return;
      }
    }
  }

  private List<String> generateReads(String text, int readLength) {
    List<String> reads = new ArrayList<>();
    int index = 0;
    List<String> readInfo = new ArrayList<>();
    int count = 0;
    while (index < text.length()) {
      // Generate read
      int end = index + readLength;
      String read;
      if (end <= text.length()) {
        read = text.substring(index, index + readLength);
      } else {
        int overflow = end - text.length();
        read = text.substring(index);
        read += text.substring(0, overflow);
      }
      // Simulate error on read
      int m = Utils.getRandomInteger(0, readLength - 1);
      char symbol = read.charAt(m);
      if (symbol == 'A') {
        symbol = 'C';
      } else if (symbol == 'C') {
        symbol = 'G';
      } else if (symbol == 'G') {
        symbol = 'T';
      } else {
        symbol = 'A';
      }
      read = read.substring(0, m) + symbol + read.substring(m + 1);
      reads.add(read);
      readInfo.add("Read " + count + ", index " + index + ", error index " + m + ", absolute error index " + (index + m));
      // Next read index
      index = Utils.getRandomInteger(index + 1, index + 5);
      count++;
    }
    //    Collections.shuffle(reads);
    readInfo.forEach(LOGGER::info);
    return reads;
  }
}
