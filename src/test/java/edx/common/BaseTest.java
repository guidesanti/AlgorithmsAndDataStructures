package edx.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@ExtendWith(TimingExtension.class)
public class BaseTest {

  private OutputStream outputStream;

  @BeforeEach
  public void beforeEach() {
    resetOutput();
  }

  protected String getActualOutput() {
    return outputStream.toString().trim();
  }

  protected void resetOutput() {
    outputStream = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outputStream));
  }

  protected int getRandomInteger(int min, int max) {
    if (min >= max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
    Random random = new Random();
    return random.nextInt((max - min) + 1) + min;
  }

  protected long getRandomLong(long min, long max) {
    if (min >= max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
    return ThreadLocalRandom.current().nextLong(min, max);
  }
}
