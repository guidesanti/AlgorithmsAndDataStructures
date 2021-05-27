package br.com.eventhorizon.edx.ucsandiego.algs204x.pa1;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class BuildTrieTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/build-trie.csv";

  private static final char[] ALPHABET = { 'A', 'C', 'G', 'T' };

  public BuildTrieTest() {
    super(new BuildTrie(), false, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @Override
  protected String generateInput(PATestType type) {
    StringBuilder input = new StringBuilder();
    int numberOfKeys = Utils.getRandomInteger(1, 100);
    input.append(numberOfKeys).append(" ");
    for (int i = 0; i < numberOfKeys; i++) {
      input.append(generateKey()).append(" ");
    }
    return input.toString();
  }

  private String generateKey() {
    char[] key = new char[Utils.getRandomInteger(1, 100)];
    for (int i = 0; i < key.length; i++) {
      key[i] = ALPHABET[Utils.getRandomInteger(0, ALPHABET.length - 1)];
    }
    return String.valueOf(key);
  }
}
