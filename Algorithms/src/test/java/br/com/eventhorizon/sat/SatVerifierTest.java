package br.com.eventhorizon.sat;

import br.com.eventhorizon.common.utils.StringToIntegerArrayConverter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SatVerifierTest {

  private static final String DATA_SET = "/sat/sat-verifier.csv";

  @ParameterizedTest
  @CsvFileSource(resources = DATA_SET, numLinesToSkip = 1)
  public void testVerifier(
      String satCnfFileName,
      boolean satisfiable,
      @ConvertWith(StringToIntegerArrayConverter.class) int[] solution) {
    List<Integer> sol = new ArrayList<>();
    for (int i : solution) {
      sol.add(i);
    }
    Cnf cnf = SatUtils.readCnfFromFile("src/test/resources/sat/" + satCnfFileName);
    assertEquals(satisfiable, SatVerifier.verify(cnf, sol));
  }
}
