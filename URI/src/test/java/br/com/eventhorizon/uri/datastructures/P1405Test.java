package br.com.eventhorizon.uri.datastructures;

import br.com.eventhorizon.common.pa.test.PASolution;
import br.com.eventhorizon.common.pa.test.PATestBase;
import br.com.eventhorizon.common.pa.test.PATestSettings;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.*;

public class P1405Test extends PATestBase {

  private static final String SIMPLE_DATA_SET = "src/test/resources/datastructures/p1405/p1405.csv";

  public P1405Test() {
    super(new P1405(), PATestSettings.builder()
            .simpleDataSetCsvFilePath(SIMPLE_DATA_SET)
            .build());
  }

  @ParameterizedTest
  @CsvSource(value = {
      "/datastructures/p1405/p1405-1.in,/datastructures/p1405/p1405-1.out",
      "/datastructures/p1405/p1405-2.in,/datastructures/p1405/p1405-2.out",
      "/datastructures/p1405/p1405-3.in,/datastructures/p1405/p1405-3.out",
      "/datastructures/p1405/p1405-4.in,/datastructures/p1405/p1405-4.out",
      "/datastructures/p1405/p1405-5.in,/datastructures/p1405/p1405-5.out",
      "/datastructures/p1405/p1405-6.in,/datastructures/p1405/p1405-6.out"
  })
  public void testFinalSolutionWithSimpleDataSet2(String inputFile, String expectedOutputFile) throws IOException {
    super.testSolutionFromFile(PASolution.FINAL, inputFile, expectedOutputFile);
  }
}
