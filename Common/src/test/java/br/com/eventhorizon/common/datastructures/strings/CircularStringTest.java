package br.com.eventhorizon.common.datastructures.strings;

import br.com.eventhorizon.common.utils.Utils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CircularStringTest {

  @Test
  public void testHashCode() {
    for (int i = 0; i < 100; i++) {
      String string = Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 100, 300);
      CircularString circularString = new CircularString(string);
      int hashCode = circularString.hashCode();
      for (int j = 0; j < string.length(); j++) {
        string = string.substring(1) + string.charAt(0);
        assertEquals(hashCode, new CircularString(string).hashCode());
      }
    }
  }

  @Test
  public void testEquals() {
    for (int i = 0; i < 100; i++) {
      String string = Utils.getRandomString(Utils.CharType.ALPHA_NUMERICAL_CHARS, 100, 200);
      CircularString circularString = new CircularString(string);
      assertNotEquals(circularString, null);
      assertNotEquals(circularString, string);
      for (int j = 0; j < string.length(); j++) {
        string = string.substring(1) + string.charAt(0);
        assertEquals(new CircularString(string), circularString);
      }
    }
  }
}
