package br.com.eventhorizon.common;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {

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
}
