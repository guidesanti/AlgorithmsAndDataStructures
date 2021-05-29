package br.com.eventhorizon.common.utils;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This converter receives a single string and converts it to a list of strings.
 * The strings inside the initial string should be separated by a '%' char.
 * If a string contains a '%' char, then it should be escaped by being replaced by "%%".
 */
public class StringToListOfStringConverter extends SimpleArgumentConverter {

  @Override
  protected Object convert(Object object, Class<?> targetType) throws ArgumentConversionException {
    if (object == null) {
      return null;
    }
    if (!(object instanceof String)) {
      throw new IllegalArgumentException("object must be a String");
    }
    if (!List.class.isAssignableFrom(targetType)) {
      throw new IllegalArgumentException("targetType must be List");
    }
    if (((String) object).isEmpty()) {
      return Collections.emptyList();
    }
    String string = (String) object;
    List<String> strings = new ArrayList<>();
    int index = 0;
    while (index < string.length()) {
      StringBuilder str = new StringBuilder();
      while (index < string.length()) {
        char curr = string.charAt(index++);
        if (curr == '%') {
          if (index < string.length()) {
            char next = string.charAt(index);
            if (next != '%') {
              break;
            }
            index++;
          } else {
            break;
          }
        }
        str.append(curr);
      }
      strings.add(str.toString());
    }
    return strings;
  }
}
