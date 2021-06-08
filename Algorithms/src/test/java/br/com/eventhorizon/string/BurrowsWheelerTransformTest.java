package br.com.eventhorizon.string;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

public abstract class BurrowsWheelerTransformTest {

//  private static final Logger LOGGER = Logger.getLogger(BurrowsWheelerTransformTest.class.getName());

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
}
