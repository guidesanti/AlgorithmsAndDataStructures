package br.com.eventhorizon.common.utils;

import br.com.eventhorizon.common.datastructures.strings.Alphabet;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Utils {

  public enum CharType {
    NUMERICAL_CHARS(48, 57),
    LOWERCASE_ALPHABETICAL_CHARS(97, 122),
    UPPERCASE_ALPHABETICAL_CHARS(65, 90),
    ALPHABETICAL_CHARS(65, 122, 91, 92, 93, 94, 95, 96),
    ALPHA_NUMERICAL_CHARS(48, 122, 58, 59, 60, 61, 62, 63, 64, 91, 92, 93, 94, 95, 96),
    ALL_ASCII(32, 126),
    ALL(0, Character.MAX_VALUE);

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

  public static Character[] getRandomCharArray(CharType type, int length) {
    Character[] array = new Character[length];
    for (int i = 0; i < length; i++) {
      array[i] = getRandomChar(type);
    }
    return array;
  }

  public static Character[] getRandomCharArray(CharType type, int minLength, int maxLength) {
    return getRandomCharArray(type, getRandomInteger(minLength, maxLength));
  }

  public static String getRandomString(CharType type, int length) {
    StringBuilder string = new StringBuilder();
    for (int i = 0; i < length; i++) {
      string.append(getRandomChar(type));
    }
    return string.toString();
  }

  public static String getRandomString(char[] symbols, int length) {
    StringBuilder string = new StringBuilder();
    for (int i = 0; i < length; i++) {
      string.append(symbols[getRandomInteger(0, symbols.length - 1)]);
    }
    return string.toString();
  }

  public static String getRandomString(Alphabet alphabet, int length) {
    StringBuilder string = new StringBuilder();
    for (int i = 0; i < length; i++) {
      string.append(alphabet.indexToSymbol(getRandomInteger(0, alphabet.size() - 1)));
    }
    return string.toString();
  }

  public static String getRandomString(CharType type, int minLength, int maxLength) {
    return getRandomString(type, getRandomInteger(minLength, maxLength));
  }

  public static String getRandomString(Alphabet alphabet, int minLength, int maxLength) {
    return getRandomString(alphabet, getRandomInteger(minLength, maxLength));
  }

  public static String getRandomString(char[] symbols, int minLength, int maxLength) {
    return getRandomString(symbols, getRandomInteger(minLength, maxLength));
  }

  public static String[] getRandomStringArray(CharType type, int minStringLength, int maxStringLength, int minArrayLength, int maxArrayLength) {
    String[] array = new String[getRandomInteger(minArrayLength, maxArrayLength)];
    for (int i = 0; i < array.length; i++) {
      array[i] = getRandomString(type, getRandomInteger(minStringLength, maxStringLength));
    }
    return array;
  }

  public static List<String> getRandomStringList(
      CharType type, boolean distinct,
      int minStringLength, int maxStringLength,
      int minArrayLength, int maxArrayLength) {
    int size = getRandomInteger(minArrayLength, maxArrayLength);
    List<String> list = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      list.add(getRandomString(type, getRandomInteger(minStringLength, maxStringLength)));
    }
    if (distinct) {
      list = list.stream().distinct().collect(Collectors.toList());
    }
    return list;
  }

  public static int getRandomInteger() {
    return ThreadLocalRandom.current().nextInt();
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

  public static double getRandomDouble(double min, double max) {
    if (min == max) {
      return min;
    }
    if (min > max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
    return ThreadLocalRandom.current().nextDouble(min, max);
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

  public static char[] charBoxedArrayToPrimitiveArray(Character[] boxedArray) {
    char[] primitiveArray = new char[boxedArray.length];
    for (int i = 0; i < boxedArray.length; i++) {
      primitiveArray[i] = boxedArray[i];
    }
    return primitiveArray;
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
