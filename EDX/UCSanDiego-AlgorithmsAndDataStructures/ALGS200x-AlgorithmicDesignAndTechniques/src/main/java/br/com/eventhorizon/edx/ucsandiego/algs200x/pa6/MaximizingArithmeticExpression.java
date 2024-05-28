package br.com.eventhorizon.edx.ucsandiego.algs200x.pa6;

import br.com.eventhorizon.common.pa.FastScanner;
import br.com.eventhorizon.common.pa.PA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaximizingArithmeticExpression implements PA {

  private static Map<String, Long[]> cache;

  @Override
  public void trivialSolution() {
    FastScanner scanner = new FastScanner(System.in);
    String expression = scanner.next();
    List<Long> values = new ArrayList<>();
    List<Character> operations = new ArrayList<>();
    splitExpression(expression, values, operations);
    if (values.size() != operations.size() + 1) {
      throw new RuntimeException("Number of values is different from number of operations plus one: values = " + values.size() + " and operations = " + operations.size());
    }
    System.out.println(naiveMaximumValue(values, operations));
  }

  private static long naiveMaximumValue(List<Long> values, List<Character> operations) {
    if (values.size() == 1) {
      return values.get(0);
    }
    if (values.size() == 2) {
      return evaluate(values.get(0), values.get(1), operations.get(0));
    }

    long max = Long.MIN_VALUE;
    for (int i = 0; i < operations.size(); i++) {
      long value = evaluate(values.get(i), values.get(i + 1), operations.get(i));
      List<Long> newValues = new ArrayList<>(values);
      newValues.set(i, value);
      newValues.remove(i + 1);
      List<Character> newOperations = new ArrayList<>(operations);
      newOperations.remove(i);
      long newMax = naiveMaximumValue(newValues, newOperations);
      if (newMax > max) {
        max = newMax;
      }
    }

    return max;
  }

  private static void splitExpression(String expression, List<Long> values, List<Character> operations) {
    for (int i = 0; i < expression.length(); i++) {
      char ch = expression.charAt(i);
      if (ch >= '0' && ch <= '9') {
        values.add(Long.valueOf(String.valueOf(ch)));
      } else {
        operations.add(ch);
      }
    }
  }

  @Override
  public void finalSolution() {
    FastScanner scanner = new FastScanner(System.in);
    cache = new HashMap<>();
    String expression = scanner.next();
    System.out.println(memoizedMaximumValue(expression, 0, expression.length() - 1)[1]);
  }

  private static Long[] memoizedMaximumValue(String expression, int i, int j) {
    if (i >= j) {
      long value = Long.parseLong(String.valueOf(expression.charAt(i)));
      Long[] values = { value, value };
      return values;
    }
    String key = expression + ":" + i + ":" + j;
    if (cache.containsKey(key)) {
      return cache.get(key);
    }

    long absoluteMin = Long.MAX_VALUE;
    long absoluteMax = Long.MIN_VALUE;
    for (int k = i + 1; k < j; k += 2) {
      char op = expression.charAt(k);
      Long[] exp1 = memoizedMaximumValue(expression, i, k - 1);
      Long[] exp2 = memoizedMaximumValue(expression, k + 1, j);

      long min1 = exp1[0];
      long max1 = exp1[1];
      long min2 = exp2[0];
      long max2 = exp2[1];

      long v1 = evaluate(min1, min2, op);
      long v2 = evaluate(min1, max2, op);
      long v3 = evaluate(max1, min2, op);
      long v4 = evaluate(max1, max2, op);

      long min = Math.min(v1, Math.min(v2, Math.min(v3, v4)));
      long max = Math.max(v1, Math.max(v2, Math.max(v3, v4)));
      if (min < absoluteMin) {
        absoluteMin = min;
      }
      if (max > absoluteMax) {
        absoluteMax = max;
      }
    }
    Long[] values = { absoluteMin, absoluteMax };
    cache.put(key, values);

    return values;
  }

  private static long evaluate(long a, long b, char op) {
    if (op == '+') {
      return a + b;
    } else if (op == '-') {
      return a - b;
    } else if (op == '*') {
      return a * b;
    } else {
      assert false;
      return 0;
    }
  }
}
