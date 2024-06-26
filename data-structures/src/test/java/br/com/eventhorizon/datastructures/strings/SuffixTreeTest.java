package br.com.eventhorizon.datastructures.strings;

import br.com.eventhorizon.common.utils.Alphabet;
import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

public class SuffixTreeTest {

  private static final char[] ALPHABET_SYMBOLS = { 'A', 'B', 'C' };

  private static final char EOF = '$';

  @Test
  public void testWithSimpleAlphabet() {
    Alphabet alphabet = new Alphabet();
    SuffixTree suffixTree = new SuffixTree(alphabet, "panamabananas");
    assertNotNull(suffixTree);
  }

  @Test
  public void testBuildTimeWithLongString() {
    Alphabet alphabet = new Alphabet(ALPHABET_SYMBOLS);
    String text = Utils.getRandomString(ALPHABET_SYMBOLS, 1000000);
    alphabet.add(EOF);
    assertTimeoutPreemptively(Duration.ofSeconds(5), () -> new SuffixTree(alphabet, text));
  }
}
