package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.ByteArrayInputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfileHMMWithPseudocountsTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/profile-hmm-with-pseudocounts.csv";

  private static final char[] ALPHABET = { 'A', 'B', 'C', 'D', 'E', '-' };

  private static final double DELTA = 0.01;

  private static final String DELIMITER = "--------";

  public ProfileHMMWithPseudocountsTest() {
    super(new ProfileHMMWithPseudocounts(), false, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    input = input.replace(" ", "\n");
    System.setIn(new ByteArrayInputStream(input.getBytes()));
    pa.finalSolution();

    // Read expected values
    String[] rows = expectedOutput.split("%");
    List<String> expectedStates = new ArrayList<>(Arrays.asList(rows[0].trim().split("[ \\t]+")));
    Map<String, Double> expectedTransitionProbabilities = new HashMap<>();
    int i = 1;
    while (!rows[i].trim().equals(DELIMITER)) {
      String[] columns = rows[i].trim().split("[ \\t]+");
      for (int j = 1; j < columns.length; j++) {
        expectedTransitionProbabilities.put(columns[0] + expectedStates.get(j - 1), Double.valueOf(columns[j]));
      }
      i++;
    }
    i++;
    Map<String, Double> expectedEmissionProbabilities = new HashMap<>();
    List<String> expectedSymbols = new ArrayList<>(Arrays.asList(rows[i].trim().split("[ \\t]+")));
    for (i = i + 1; i < rows.length; i++) {
      String[] columns = rows[i].trim().split("[ \\t]+");
      for (int j = 1; j < columns.length; j++) {
        expectedEmissionProbabilities.put(columns[0] + expectedSymbols.get(j - 1), Double.valueOf(columns[j]));
      }
    }

    // Read actual values
    rows = getActualOutput().split("\n");
    List<String> actualStates = new ArrayList<>(Arrays.asList(rows[0].trim().split("[ \\t]+")));
    Map<String, Double> actualTransitionProbabilities = new HashMap<>();
    i = 1;
    while (!rows[i].trim().equals(DELIMITER)) {
      String[] columns = rows[i].trim().split("[ \\t]+");
      for (int j = 1; j < columns.length; j++) {
        actualTransitionProbabilities.put(columns[0] + actualStates.get(j - 1), Double.valueOf(columns[j]));
      }
      i++;
    }
    i++;
    Map<String, Double> actualEmissionProbabilities = new HashMap<>();
    List<String> actualSymbols = new ArrayList<>(Arrays.asList(rows[i].trim().split("[ \\t]+")));
    for (i = i + 1; i < rows.length; i++) {
      String[] columns = rows[i].trim().split("[ \\t]+");
      for (int j = 1; j < columns.length; j++) {
        actualEmissionProbabilities.put(columns[0] + actualSymbols.get(j - 1), Double.valueOf(columns[j]));
      }
    }

    assertEquals(expectedStates, actualStates);
    assertEquals(expectedSymbols, actualSymbols);
    expectedTransitionProbabilities.forEach((key, value) -> {
      assertTrue(actualTransitionProbabilities.containsKey(key));
      double expectedProbability = expectedTransitionProbabilities.get(key);
      double actualProbability = actualTransitionProbabilities.get(key);
      assertTrue(actualProbability >= expectedProbability - DELTA  &&
          actualProbability <= expectedProbability + DELTA);
    });
    expectedEmissionProbabilities.forEach((key, value) -> {
      assertTrue(actualEmissionProbabilities.containsKey(key));
      double expectedProbability = expectedEmissionProbabilities.get(key);
      double actualProbability = actualEmissionProbabilities.get(key);
      assertTrue(actualProbability >= expectedProbability - DELTA  &&
          actualProbability <= expectedProbability + DELTA);
    });
  }

  @Override
  protected String generateInput(PATestType type) {
    StringBuilder input = new StringBuilder();
    boolean stop = false;
    while (!stop) {
      input.append(Utils.getRandomDouble(0.2, 0.4)).append(" ");
      input.append(0.01).append(" ");
      input.append(DELIMITER).append(" ");
      for (int i = 0; i < ALPHABET.length - 1; i++) {
        input.append(ALPHABET[i]).append(" ");
      }
      input.append(DELIMITER).append(" ");
      for (int i = 0; i < 10; i++) {
        input.append(Utils.getRandomString(ALPHABET, 100)).append(" ");
      }
      String str = input.toString();
      stop = true;
      for (char symbol : ALPHABET) {
        if (!str.contains("" + symbol)) {
          stop = false;
          break;
        }
      }
    }
    return input.toString();
  }
}
