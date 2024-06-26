package br.com.eventhorizon.edx.ucsandiego.algs205x.pa1;

import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import br.com.eventhorizon.common.pa.test.PATestType;
import br.com.eventhorizon.common.utils.Utils;

import java.util.*;

public class LongestPathInDAGTest extends PATestBase {

  private static final String SIMPLE_DATA_SET = "src/test/resources/test-dataset/pa1/longest-path-in-dag.csv";

  public LongestPathInDAGTest() {
    super(new LongestPathInDAG(), PATestSettings.builder()
            .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
            .timeLimitTestEnabled(true)
            .compareTestEnabled(true)
            .build());
  }

//  @ParameterizedTest
//  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
//  public void testNaiveSolutionWithSimpleDataSet(String input, String expectedOutput) {
//    super.testNaiveSolution(input, expectedOutput == null ? "" : expectedOutput.replace("%", "\n").replace("!", ""));
//  }
//
//  @ParameterizedTest
//  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
//  public void testIntermediateSolution1WithSimpleDataSet(String input, String expectedOutput) throws OperationNotSupportedException {
//    super.testIntermediateSolution1(input, expectedOutput == null ? "" : expectedOutput.replace("%", "\n").replace("!", ""));
//  }
//
//  @ParameterizedTest
//  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
//  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
//    super.testFinalSolution(input, expectedOutput == null ? "" : expectedOutput.replace("%", "\n").replace("!", ""));
//  }

  @Override
  protected String generateInput(PATestType type, StringBuilder expectedOutput) {
    StringBuilder input = new StringBuilder();
    int source = Utils.getRandomInteger(0, 5);
    int sink = Utils.getRandomInteger(source + 1, 10);
    input.append(source).append(" ").append(sink);
    List<Integer> vertices = new ArrayList<>();
    for (int i = source + 1; i < sink; i++) {
      vertices.add(i);
    }
    Collections.shuffle(vertices);
    vertices.add(0, source);
    vertices.add(sink);
    for (int i = 0; i < vertices.size(); i++) {
      int fromVertex = vertices.get(i);
      for (int j = i + 1; j < vertices.size(); j++) {
        int toVertex = vertices.get(j);
        input.append(" ").append(fromVertex).append("->").append(toVertex).append(":").append(Utils.getRandomInteger(-100, 100));
      }
    }
    return input.toString();
  }
}
