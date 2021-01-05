package br.com.eventhorizon.edx.ucsandiego.algs200x.pa5;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.List;

public class PrimitiveCalculator implements PA {

  @Override
  public void naiveSolution() {
    finalSolution();
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    List<Integer> sequence = finalOptimalSequence(n);
    System.out.println(sequence.size() - 1);
    for (Integer x : sequence) {
      System.out.print(x + " ");
    }
  }

  private static List<Integer> finalOptimalSequence(int n) {
    List<Integer>[] cache = new List[n];
    cache[0] = new ArrayList<>();
    cache[0].add(1);
    for (int i = 0; i < n; i++) {
      int a = cache[i].get(cache[i].size() - 1);
      int a1 = a + 1;
      int a2 = a * 2;
      int a3 = a * 3;
      if (a1 <= n) {
        List<Integer> s1 = new ArrayList<>(cache[i]);
        s1.add(a1);
        cache[a1 - 1] = smallestSequence(s1, cache[a1 - 1]);
      }
      if (a2 <= n) {
        List<Integer> s2 = new ArrayList<>(cache[i]);
        s2.add(a2);
        cache[a2 - 1] = smallestSequence(s2, cache[a2 - 1]);
      }
      if (a3 <= n) {
        List<Integer> s3 = new ArrayList<>(cache[i]);
        s3.add(a3);
        cache[a3 - 1] = smallestSequence(s3, cache[a3 - 1]);
      }
    }
    return cache[n - 1];
  }

  private static List<Integer> smallestSequence(List<Integer> s1, List<Integer> s2) {
    if (s1 != null && s2 != null) {
      return s1.size() < s2.size() ? s1 : s2;
    }
    if (s1 != null && s2 == null) {
      return s1;
    }
    if (s1 == null && s2 != null) {
      return s2;
    }
    return null;
  }
}
