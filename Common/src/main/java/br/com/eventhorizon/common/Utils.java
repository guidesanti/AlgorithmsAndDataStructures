package br.com.eventhorizon.common;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

  public static char getRandomChar() {
    Random random = new Random();
    return (char) (random.nextInt(95) + 32);
  }

  public static int getRandomInteger(int min, int max) {
    if (min >= max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
    Random random = new Random();
    return random.nextInt((max - min) + 1) + min;
  }

  public static long getRandomLong(long min, long max) {
    if (min >= max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
    return ThreadLocalRandom.current().nextLong(min, max);
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
