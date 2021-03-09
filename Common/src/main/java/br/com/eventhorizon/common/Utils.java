package br.com.eventhorizon.common;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

  public enum CharType {
    NUMERICAL_CHARS(48, 57),
    LOWERCASE_ALPHABETICAL_CHARS(97, 122),
    UPPERCASE_ALPHABETICAL_CHARS(65, 90),
    ALPHABETICAL_CHARS(65, 122, 91, 92, 93, 94, 95, 96),
    ALPHA_NUMERICAL_CHARS(48, 122, 58, 59, 60, 61, 62, 63, 64, 91, 92, 93, 94, 95, 96),
    ALL(32, 126);

    private int min;

    private int max;

    private Set<Integer> exclude;

    CharType(int min, int max, int ... exclude) {
      this.min = min;
      this.max = max;
      this.exclude = new HashSet<>();
      for (int value : exclude) {
        this.exclude.add(value);
      }
    }
  }

  public static char getRandomChar(CharType type) {
    int ch = getRandomInteger(type.min, type.max);
    while (type.exclude.contains(ch)) {
      ch = getRandomInteger(type.min, type.max);
    }
    return (char) ch;
  }

  public static String getRandomString(CharType type, int length) {
    StringBuilder string = new StringBuilder();
    for (int i = 0; i < length; i++) {
      string.append(getRandomChar(type));
    }
    return string.toString();
  }

  public static String getRandomString(CharType type, int minLength, int maxLength) {
    int length = getRandomInteger(minLength, maxLength);
    StringBuilder string = new StringBuilder();
    for (int i = 0; i < length; i++) {
      string.append(getRandomChar(type));
    }
    return string.toString();
  }

  public static int getRandomInteger(int min, int max) {
    if (min == max) {
      return min;
    }
    if (min > max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
    return ThreadLocalRandom.current().nextInt(min, max < Integer.MAX_VALUE ? max + 1 : max);
  }

  public static int[] getRandomIntegerArray(int minLength, int maxLength, int min, int max) {
    int n = getRandomInteger(minLength, maxLength);
    int[] values = new int[n];
    for (int i = 0; i < n; i++) {
      int value = Utils.getRandomInteger(min, max);
      values[i] = value;
    }
    return values;
  }

  public static long getRandomLong(long min, long max) {
    if (min == max) {
      return min;
    }
    if (min > max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
    return ThreadLocalRandom.current().nextLong(min, max < Long.MAX_VALUE ? max + 1 : max);
  }

  public static long[] getRandomLongArray(int minLength, int maxLength, long min, long max) {
    int n = getRandomInteger(minLength, maxLength);
    long[] values = new long[n];
    for (int i = 0; i < n; i++) {
      long value = Utils.getRandomLong(min, max);
      values[i] = value;
    }
    return values;
  }

  public static boolean isMinHeap(long[] a) {
    for (int i = a.length - 1; i > 0; i--) {
      int parent = (i - 1) / 2;
      if (a[i] < a[parent]) {
        return false;
      }
    }
    return true;
  }

  public static boolean isMaxHeap(long[] a) {
    for (int i = a.length - 1; i > 0; i--) {
      int parent = (i - 1) / 2;
      if (a[i] > a[parent]) {
        return false;
      }
    }
    return true;
  }
}
