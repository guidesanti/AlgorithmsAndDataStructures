package br.com.eventhorizon.sat;

import java.util.List;

public final class SatVerifier {

  public static boolean verify(Cnf cnf, List<Integer> solution) {
    for (List<Integer> clause : cnf.clauses()) {
      boolean result = false;
      for (Integer integer : clause) {
        if (integer == 0) {
          continue;
        }
        result |= integer.equals(solution.get(Math.abs(integer)));
      }
      if (!result) {
        return false;
      }
    }
    return true;
  }
}
