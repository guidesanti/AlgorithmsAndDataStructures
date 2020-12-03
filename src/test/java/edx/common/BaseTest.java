package edx.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.*;
import java.util.Random;

@ExtendWith(TimingExtension.class)
public class BaseTest {

  public static final int DEFAULT_TIME_LIMIT_IN_MS = 1500;

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

  protected int getRandomNumber(int min, int max) {
    if (min >= max) {
      throw new IllegalArgumentException("max must be greater than min");
    }
    Random random = new Random();
    return random.nextInt((max - min) + 1) + min;
  }
}
