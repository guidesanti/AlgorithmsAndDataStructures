package br.com.eventhorizon.edx.ucsandiego.algs206x.pa1;

import br.com.eventhorizon.common.pa.PATest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MaximalNonBranchingPathsInDebruijnGraphTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa1/maximal-non-branching-paths-in-debruijn-graph.csv";

  public MaximalNonBranchingPathsInDebruijnGraphTest() {
    super(new MaximalNonBranchingPathsInDebruijnGraph(), true, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    input = input.replace("%", "\n").replace(";", ",");
    expectedOutput = expectedOutput.replace("%", "\n").replace(";", ",");
    super.testNaiveSolution(input, expectedOutput);
  }

  @Override
  protected void verify(String input, String expectedOutput, String actualOutput) {
    // Process expected output
    List<String> expectedContigs = new ArrayList<>(Arrays.asList(expectedOutput.split(" ")));

    // Process actual output
    List<String> actualContigs = new ArrayList<>(Arrays.asList(actualOutput.split(" ")));

    assertEquals(expectedContigs.size(), actualContigs.size());
    for (String actualContig : actualContigs) {
      if (actualContig.charAt(0) == actualContig.charAt(actualContig.length() - 1)) {
        boolean found = false;
        for (int i = 0; i < actualContig.length(); i++) {
          if (expectedContigs.contains(actualContig)) {
            found = true;
            break;
          }
          actualContig = actualContig.substring(1);
          actualContig += actualContig.charAt(0);
        }
        assertTrue(found);
      } else {
        assert(expectedContigs.contains(actualContig));
      }
    }
  }
}
