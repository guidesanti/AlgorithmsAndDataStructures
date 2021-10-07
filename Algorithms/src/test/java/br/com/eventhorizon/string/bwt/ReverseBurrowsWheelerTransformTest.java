package br.com.eventhorizon.string.bwt;

import br.com.eventhorizon.common.utils.Utils;
import br.com.eventhorizon.common.pa.TestProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class ReverseBurrowsWheelerTransformTest {

  private static final Logger LOGGER = Logger.getLogger(ReverseBurrowsWheelerTransformTest.class.getName());

  private static final String DATA_SET = "/string/bwt/reverse-burrows-wheeler-transform.csv";

  private final ReverseBurrowsWheelerTransform reverseBurrowsWheelerTransform;

  public ReverseBurrowsWheelerTransformTest(ReverseBurrowsWheelerTransform reverseBurrowsWheelerTransform) {
    this.reverseBurrowsWheelerTransform = reverseBurrowsWheelerTransform;
  }

  @ParameterizedTest
  @CsvFileSource(resources = DATA_SET, numLinesToSkip = 1)
  void testTransform(String burrowsWheelerTransform, String expectedText) {
    String actualText = reverseBurrowsWheelerTransform.reverse(burrowsWheelerTransform);
    assertEquals(expectedText, actualText);
  }

  @Test
  void stressTest() {
    LOGGER.info("Stress test duration: " + TestProperties.getStressTestDuration());
    if (reverseBurrowsWheelerTransform instanceof NaiveReverseBurrowsWheelerTransform) {
      LOGGER.info("Skipping stress test for NaiveReverseBurrowsWheelerTransform to avoid compare it to itself");
      return;
    }
    long startTime = System.currentTimeMillis();
    for (int test = 0; true; test++) {
      NaiveBurrowsWheelerTransform naiveBurrowsWheelerTransform = new NaiveBurrowsWheelerTransform();
      NaiveReverseBurrowsWheelerTransform naiveReverseBurrowsWheelerTransform = new NaiveReverseBurrowsWheelerTransform();
      String text = Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 1, 1000);
      String burrowsWheelerTransform = naiveBurrowsWheelerTransform.transform(text);
      String result = reverseBurrowsWheelerTransform.reverse(burrowsWheelerTransform);
      if (!result.equals(text)) {
        LOGGER.info("Stress test " + test + " status: FAILED");
        LOGGER.info("Stress test " + test + " text: " + text);
        LOGGER.info("Stress test " + test + " Burrows-Wheeler transform: " + burrowsWheelerTransform);
        LOGGER.info("Stress test " + test + " reconstructed text: " + result);
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
