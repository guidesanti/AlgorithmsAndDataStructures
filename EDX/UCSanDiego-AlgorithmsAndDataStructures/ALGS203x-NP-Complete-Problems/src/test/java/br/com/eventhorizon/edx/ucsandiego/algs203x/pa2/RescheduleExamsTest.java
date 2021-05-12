package br.com.eventhorizon.edx.ucsandiego.algs203x.pa2;

import br.com.eventhorizon.common.pa.PATest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class RescheduleExamsTest extends PATest {

  private static final String SIMPLE_DATA_SET = "/test-dataset/pa2/reschedule-exams.csv";

  public RescheduleExamsTest() {
    super(new RescheduleExams(), true, true);
  }

  @ParameterizedTest
  @CsvFileSource(resources = SIMPLE_DATA_SET, numLinesToSkip = 1)
  public void testFinalSolutionWithSimpleDataSet(String input, String expectedOutput) {
    super.testFinalSolution(input, expectedOutput.replace("%", "\n").replace("!", ""));
  }
}
