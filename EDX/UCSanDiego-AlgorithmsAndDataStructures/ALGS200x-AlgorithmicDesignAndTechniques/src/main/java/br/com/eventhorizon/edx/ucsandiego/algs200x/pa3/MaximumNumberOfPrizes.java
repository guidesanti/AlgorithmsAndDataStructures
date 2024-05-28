package br.com.eventhorizon.edx.ucsandiego.algs200x.pa3;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.List;

public class MaximumNumberOfPrizes implements PA {

  @Override
  public void trivialSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    List<Integer> summands = naiveSummands(n);
    System.out.println(summands.size());
    for (Integer summand : summands) {
      System.out.print(summand + " ");
    }
  }

  private List<Integer> naiveSummands(int n) {
    List<Integer> summands = new ArrayList<>();
    int k = 1;
    n -= k;
    summands.add(k);
    while (true) {
      if (n < k + 1) {
        summands.remove(Integer.valueOf(k));
        summands.add(k + n);
        break;
      }
      k++;
      n -= k;
      summands.add(k);
    }
    return summands;
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    List<Integer> summands = naiveSummands(n);
    System.out.println(summands.size());
    for (Integer summand : summands) {
      System.out.print(summand + " ");
    }
  }
}
