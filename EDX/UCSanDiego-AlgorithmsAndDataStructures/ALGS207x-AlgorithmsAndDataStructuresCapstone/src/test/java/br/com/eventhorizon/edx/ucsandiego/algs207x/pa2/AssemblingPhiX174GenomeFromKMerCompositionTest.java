package br.com.eventhorizon.edx.ucsandiego.algs207x.pa2;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import br.com.eventhorizon.common.pa.TestProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AssemblingPhiX174GenomeFromKMerCompositionTest extends PATest {

  private static final Logger LOGGER = Logger.getLogger(AssemblingPhiX174GenomeFromKMerCompositionTest.class.getName());

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa2/assembling-phi-x174-genome-from-k-mer-composition.csv";

  private static final char[] ALPHABET = { 'A', 'C', 'G', 'T' };

  public AssemblingPhiX174GenomeFromKMerCompositionTest() {
    super(new AssemblingPhiX174GenomeFromKMerComposition(), false, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    TestProperties.setTimeLimit(4500);
    input = input.replace("%", "\n");
    expectedOutput = expectedOutput.replace("%", "\n");
    super.testFinalSolution(input, expectedOutput);
  }

  @Test
  public void stressTest() {
    LOGGER.info("Stress test duration: " + TestProperties.getStressTestDuration());
    long startTime = System.currentTimeMillis();
    for (long i = 0; true; i++) {
      String text = Utils.getRandomString(ALPHABET, 1000);
      List<String> reads = generateReads(text, 10);
      StringBuilder str = new StringBuilder();
      reads.forEach(read -> str.append(read).append("\n"));
      String input = str.toString();
      LOGGER.info("Stress test " + i + " text: " + text);
      LOGGER.info("Stress test " + i + " input: " + input);

      // Run and compare results
      resetOutput();
      System.setIn(new ByteArrayInputStream(input.getBytes()));
      pa.finalSolution();
      String output = getActualOutput();
      LOGGER.info("Stress test " + i + " output: " + output);
      verify(input, text, output);

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
      reads.add(read);
      // Next read index
      index++;
    }
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
      actualOutput = actualOutput.substring(1) + actualOutput.charAt(0);
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
