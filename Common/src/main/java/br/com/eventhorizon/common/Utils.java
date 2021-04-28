package br.com.eventhorizon.common;

import java.util.HashSet;
import java.util.List;
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

  public static Boolean getRandomBoolean() {
    return getRandomInteger(0, 1) == 0;
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
    return getRandomString(type, length);
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

  public static Integer[] getRandomIntegerArray(int minLength, int maxLength, int min, int max) {
    int n = getRandomInteger(minLength, maxLength);
    Integer[] values = new Integer[n];
    for (int i = 0; i < n; i++) {
      int value = Utils.getRandomInteger(min, max);
      values[i] = value;
    }
    return values;
  }

  public static Integer[] getSortedRandomIntegerArray(int minLength, int maxLength, int min, int max) {
    int n = getRandomInteger(minLength, maxLength);
    Integer[] values = new Integer[n];
    values[0] = min;
    for (int i = 1; i < n - 1; i++) {
      min = Utils.getRandomInteger(min, min + ((max - min) / (n - i)));
      values[i] = min;
    }
    values[n - 1] = max;
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

  public static Long[] getRandomLongArray(int minLength, int maxLength, long min, long max) {
    int n = getRandomInteger(minLength, maxLength);
    Long[] values = new Long[n];
    for (int i = 0; i < n; i++) {
      long value = Utils.getRandomLong(min, max);
      values[i] = value;
    }
    return values;
  }

  public static Long[] getSortedRandomLongArray(int minLength, int maxLength, long min, long max) {
    int n = getRandomInteger(minLength, maxLength);
    Long[] values = new Long[n];
    values[0] = min;
    for (int i = 1; i < n - 1; i++) {
      min = Utils.getRandomLong(min, min + ((max - min) / (n - i)));
      values[i] = min;
    }
    values[n - 1] = max;
    return values;
  }

  public static Object[] getRandomObjectArray(int minLength, int maxLength) {
    int n = getRandomInteger(minLength, maxLength);
    Object[] values = new Object[n];
    for (int i = 0; i < n; i++) {
      values[i] = new Object();
    }
    return values;
  }

  public static int[] listOfIntegersToArray(List<Integer> list) {
    int[] shifts = new int[list.size()];
    for (int i = 0; i < shifts.length; i++) {
      shifts[i] = list.get(i);
    }
    return shifts;
  }

  public static long[] listOfLongsToArray(List<Long> list) {
    long[] shifts = new long[list.size()];
    for (int i = 0; i < shifts.length; i++) {
      shifts[i] = list.get(i);
    }
    return shifts;
  }

  public static <T extends Comparable<T>> boolean isMinHeap(Object[] a) {
    for (int i = a.length - 1; i > 0; i--) {
      int parent = (i - 1) / 2;
      if (((T)a[i]).compareTo((T) a[parent]) < 0) {
        return false;
      }
    }
    return true;
  }

  public static <T extends Comparable<T>> boolean isMaxHeap(Object[] a) {
    for (int i = a.length - 1; i > 0; i--) {
      int parent = (i - 1) / 2;
      if (((T)a[i]).compareTo((T) a[parent]) > 0) {
        return false;
      }
    }
    return true;
  }
}
