package br.com.eventhorizon.string;

import br.com.eventhorizon.common.Utils;
import br.com.eventhorizon.common.pa.TestProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public abstract class BurrowsWheelerTransformTest {

  private static final Logger LOGGER = Logger.getLogger(BurrowsWheelerTransformTest.class.getName());

  private static final String DATA_SET = "/string/burrows-wheeler-transform.csv";

  private final BurrowsWheelerTransform burrowsWheelerTransform;

  public BurrowsWheelerTransformTest(BurrowsWheelerTransform burrowsWheelerTransform) {
    this.burrowsWheelerTransform = burrowsWheelerTransform;
  }

  @ParameterizedTest
  @CsvFileSource(resources = DATA_SET, numLinesToSkip = 1)
  void testTransform(String text, String expectedBurrowsWheelerTransform) {
    String result = burrowsWheelerTransform.transform(text);
    assertEquals(expectedBurrowsWheelerTransform, result);
  }

  @Test
  void stressTest() {
    LOGGER.info("Stress test duration: " + TestProperties.getStressTestDuration());
    if (burrowsWheelerTransform instanceof NaiveBurrowsWheelerTransform) {
      LOGGER.info("Skipping stress test for NaiveBurrowsWheelerTransform to avoid compare it to itself");
      return;
    }
    long startTime = System.currentTimeMillis();
    for (int test = 0; true; test++) {
      NaiveBurrowsWheelerTransform naiveBurrowsWheelerTransform = new NaiveBurrowsWheelerTransform();
      String text = Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 1, 1000);
      String result1 = naiveBurrowsWheelerTransform.transform(text);
      String result2 = burrowsWheelerTransform.transform(text);
      if (!result1.equals(result2)) {
        LOGGER.info("Stress test " + test + " status: FAILED");
        LOGGER.info("Stress test " + test + " text: " + text);
        LOGGER.info("Stress test " + test + " result1: " + result1);
        LOGGER.info("Stress test " + test + " result2: " + result2);
        fail();
      }
      LOGGER.info("Stress test " + test + " status: PASSED");

      // Check elapsed time
      long elapsedTime = System.currentTimeMillis() - startTime;
      if (elapsedTime > TestProperties.getStressTestDuration()) {
        return;
      }
    }
  }
}
