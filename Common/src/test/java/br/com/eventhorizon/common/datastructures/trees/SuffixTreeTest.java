package br.com.eventhorizon.common.datastructures.trees;

import br.com.eventhorizon.common.datastructures.Alphabet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SuffixTreeTest {

  @Test
  public void testWithSimpleAlphabet() {
    char[] symbols = { 'A', 'B', 'C', '$' };
//    Alphabet alphabet = new Alphabet(symbols);
    Alphabet alphabet = new Alphabet();
    SuffixTree suffixTree = new SuffixTree(alphabet, "panamabananas");
    assertNotNull(suffixTree);
  }
}
