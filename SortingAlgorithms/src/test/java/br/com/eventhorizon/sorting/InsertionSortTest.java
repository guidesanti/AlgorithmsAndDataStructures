package br.com.eventhorizon.sorting;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class InsertionSortTest extends SortTest {

  private static final String DATA_SET = "/test-dataset/dataset.csv";

  public InsertionSortTest() {
    super(new InsertionSort());
  }

  @ParameterizedTest
  @CsvFileSource(resources = DATA_SET, numLinesToSkip = 1)
  public void testSort(String input, String expectedOutput) {
    super.testSort(input, expectedOutput);
  }
}
