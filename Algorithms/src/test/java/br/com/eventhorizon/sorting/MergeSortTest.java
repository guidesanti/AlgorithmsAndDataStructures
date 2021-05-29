package br.com.eventhorizon.sorting;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class MergeSortTest extends SortAlgorithmTest {

  private static final String DATA_SET = "/sorting/sorting.csv";

  public MergeSortTest() {
    super(new MergeSort());
  }

  @ParameterizedTest
  @CsvFileSource(resources = DATA_SET, numLinesToSkip = 1)
  public void testSort(String input, String expectedOutput) {
    super.testSort(input, expectedOutput);
  }
}
