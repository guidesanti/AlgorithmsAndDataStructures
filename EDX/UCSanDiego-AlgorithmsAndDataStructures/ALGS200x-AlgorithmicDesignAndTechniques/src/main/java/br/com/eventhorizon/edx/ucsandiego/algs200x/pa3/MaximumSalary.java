package br.com.eventhorizon.edx.ucsandiego.algs200x.pa3;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.List;

public class MaximumSalary implements PA {

  @Override
  public void naiveSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    List<String> a = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      a.add(scanner.next());
    }
    System.out.println(naiveLargestNumber(a));
  }

  private String naiveLargestNumber(List<String> a) {
    List<String> permutations = permutations(a);
    String max = permutations.get(0);
    for (String str : permutations) {
      if (isGreaterOrEqual1(str, max)) {
        max = str;
      }
    }
    return max;
  }

  private List<String> permutations(List<String> a) {
    if (a.size() == 1) {
      return a;
    }
    List<String> result = new ArrayList<>();
    for (String str : a) {
      List<String> temp = new ArrayList<>(a);
      temp.remove(str);
      List<String> permutations = permutations(temp);
      for (String permutation : permutations) {
        result.add(str.concat(permutation));
      }
    }
    return result;
  }

  private boolean isGreaterOrEqual1(String source, String target) {
    if (source.length() > target.length()) {
      return true;
    }
    if (source.length() < target.length()) {
      return false;
    }
    if (source.charAt(0) > target.charAt(0)) {
      return true;
    }
    if (source.charAt(0) < target.charAt(0)) {
      return false;
    }
    if (source.length() == 1) {
      return true;
    }
    return isGreaterOrEqual1(source.substring(1), target.substring(1));
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    int n = scanner.nextInt();
    List<String> a = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      a.add(scanner.next());
    }
    System.out.println(greedyLargestNumber(a));
  }

  private String greedyLargestNumber(List<String> a) {
    StringBuilder answer = new StringBuilder();
    while (!a.isEmpty()) {
      String max = a.get(0);
      for (String s : a) {
        if (isGreaterOrEqual2(s, max)) {
          max = s;
        }
      }
      answer.append(max);
      a.remove(max);
    }
    return answer.toString();
  }

  private boolean isGreaterOrEqual2(String source, String target) {
    String a = source.concat(target);
    String b = target.concat(source);
    return Integer.parseInt(a) >= Integer.parseInt(b);
  }
}
