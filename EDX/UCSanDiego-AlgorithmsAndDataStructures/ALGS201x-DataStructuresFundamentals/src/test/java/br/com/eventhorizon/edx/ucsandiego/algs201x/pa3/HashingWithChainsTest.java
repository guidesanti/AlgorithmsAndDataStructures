package br.com.eventhorizon.edx.ucsandiego.algs201x.pa3;

import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.pa.PATest;
import br.com.eventhorizon.common.pa.PATestType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class HashingWithChainsTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa3/hashing-with-chains.csv";

  private static final String[] QUERY_TYPES = { "add", "del", "find", "check" };

  public HashingWithChainsTest() {
    super(new HashingWithChains());
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testNaiveSolution(input, expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput.replace("%", "\n").replace("!", ""));
  }

  @Override
  protected String generateInput(PATestType type) {
    StringBuilder input = new StringBuilder();
    String[] strings = generateStrings(Utils.getRandomInteger(10, 100000));
    int numberOfQueries;
    switch (type) {
      case TIME_LIMIT_TEST:
        numberOfQueries = Utils.getRandomInteger(6, 100000);
        break;
      case STRESS_TEST:
      default:
        numberOfQueries = Utils.getRandomInteger(6, 100);
        break;
    }
    int numberOfBuckets = Utils.getRandomInteger(2, numberOfQueries);
    input.append(numberOfBuckets).append(" ").append(numberOfQueries);
    for (int i = 0; i < numberOfQueries; i++) {
      input.append(" ").append(generateQuery(strings, numberOfBuckets));
    }
    return input.toString();
  }

  private String generateQuery(String[] strings, int numberOfBuckets) {
    String query = QUERY_TYPES[Utils.getRandomInteger(0, QUERY_TYPES.length - 1)];
    String string = strings[Utils.getRandomInteger(0, strings.length - 1)];
    if (query.equals("check")) {
      query += " " + Utils.getRandomInteger(0, numberOfBuckets - 1);
    } else {
      query += " " + string;
    }
    return query;
  }

  private String[] generateStrings(int numberOfStrings) {
    String[] strings = new String[numberOfStrings];
    for (int i = 0; i < numberOfStrings; i++) {
      int stringLength = Utils.getRandomInteger(1, 15);
      StringBuilder string = new StringBuilder();
      for (int j = 0; j < stringLength; j++) {
        string.append((char)Utils.getRandomInteger(97, 122));
      }
      strings[i] = string.toString();
    }
    return strings;
  }
}
