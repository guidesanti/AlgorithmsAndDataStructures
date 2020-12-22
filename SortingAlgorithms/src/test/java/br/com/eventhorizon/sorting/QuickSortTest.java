package br.com.eventhorizon.sorting;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class QuickSortTest extends SortAlgorithmTest {

  private static final String DATA_SET = "/test-dataset/dataset.csv";

  public QuickSortTest() {
    super(new QuickSort());
  }

  @ParameterizedTest
  @CsvFileSource(resources = DATA_SET, numLinesToSkip = 1)
  public void testSort(String input, String expectedOutput) {
    super.testSort(input, expectedOutput);
  }
}
