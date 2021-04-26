package br.com.eventhorizon.sat;

import br.com.eventhorizon.common.pa.FastScanner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SatUtils {

  public static Cnf readCnfFromFile(String file) {
    try {
      FastScanner scanner = new FastScanner(new FileInputStream(file));
      String p = scanner.next();
      String cnf = scanner.next();
      if (!p.equals("p") || !cnf.equals("cnf")) {
        throw new RuntimeException("Invalid CNF file, first line does not start with \"p cnf\"");
      }
      int numberOfVariables = scanner.nextInt();
      int numberOfClauses = scanner.nextInt();
      List<List<Integer>> clausses = new ArrayList<>();
      for (int i = 0; i < numberOfClauses; i++) {
        List<Integer> clause = new ArrayList<>();
        int next = scanner.nextInt();
        while (next != 0) {
          clause.add(next);
          next = scanner.nextInt();
        }
        clausses.add(clause);
      }
      return new Cnf(numberOfVariables, clausses);
    } catch (IOException exception) {
      throw new RuntimeException("Failed to read CNF file", exception);
    }
  }
}
