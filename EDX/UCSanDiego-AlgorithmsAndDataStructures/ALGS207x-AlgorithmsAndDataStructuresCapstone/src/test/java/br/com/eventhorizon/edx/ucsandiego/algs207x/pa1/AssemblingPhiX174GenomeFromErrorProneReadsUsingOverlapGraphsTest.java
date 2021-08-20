package br.com.eventhorizon.edx.ucsandiego.algs207x.pa1;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import br.com.eventhorizon.common.pa.TestProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssemblingPhiX174GenomeFromErrorProneReadsUsingOverlapGraphsTest extends PATest {

  private static final Logger LOGGER = Logger.getLogger(AssemblingPhiX174GenomeFromErrorProneReadsUsingOverlapGraphsTest.class.getName());

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/assembling-phi-x174-genome-from-error-prone-reads-using-overlap-graphs.csv";

  private static final char[] ALPHABET = { 'A', 'C', 'G', 'T' };

  public AssemblingPhiX174GenomeFromErrorProneReadsUsingOverlapGraphsTest() {
    super(new AssemblingPhiX174GenomeFromErrorProneReadsUsingOverlapGraphs(), false, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    TestProperties.setTimeLimit(4500);
    input = input.replace("%", "\n").replace(";", ",");
    expectedOutput = expectedOutput.replace("%", "\n").replace(";", ",");
    super.testNaiveSolution(input, expectedOutput);
  }

  @Test
  public void stressTest() {
    LOGGER.info("Stress test duration: " + TestProperties.getStressTestDuration());
    long startTime = System.currentTimeMillis();
    for (long i = 0; true; i++) {
      String text = Utils.getRandomString(ALPHABET, 5000);
      List<String> reads = generateReads(text, 100);
      StringBuilder str = new StringBuilder();
      reads.forEach(read -> str.append(read).append("\n"));
      String input = str.toString();
      LOGGER.info("Stress test " + i + " text: " + text);
      LOGGER.info("Stress test " + i + " input: " + input);

      // Run and compare results
      resetOutput();
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      pa.finalSolution();
      verify(input, text, getActualOutput());

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
    while (index < text.length()) {
      String read;
      int end = index + readLength;
      if (end <= text.length()) {
        read = text.substring(index, index + readLength);
      } else {
        int overflow = end - text.length();
        read = text.substring(index);
        read += text.substring(0, overflow);
      }
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
      index = Utils.getRandomInteger(index + 1, index + 11);
    }
    reads.add(text.substring(text.length() - readLength));
//    Collections.shuffle(reads);
    return reads;
  }

  @Override
  protected void verify(String input, String expectedOutput, String actualOutput) {
    assertEquals(expectedOutput.length(), actualOutput.length());
    boolean circularEqual = false;
    for (int i = 0; i < expectedOutput.length(); i++) {
      if (expectedOutput.equals(actualOutput)) {
        circularEqual = true;
        break;
      }
      actualOutput = actualOutput.substring(1);
      actualOutput += actualOutput.charAt(0);
    }
    assertTrue(circularEqual);
  }

  @Override
  protected String generateInput(PATestType type) {
    String text = Utils.getRandomString(ALPHABET, 1000);
    List<String> reads = generateReads(text, 100);
    StringBuilder str = new StringBuilder();
    reads.forEach(read -> str.append(read).append("\n"));
    return str.toString();
  }
}
