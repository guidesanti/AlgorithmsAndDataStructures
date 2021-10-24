package br.com.eventhorizon.common.utils.converters;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Converts a string to a list of integers.
 * The integers inside the string shall be separated by ' ' (space) char.
 * Example: String "0 1 2 3" will result in list with integers 0, 1, 2 and 3.
 */
public class StringToListOfIntegersConverter extends SimpleArgumentConverter {

  @Override
  protected Object convert(Object object, Class<?> targetType) throws ArgumentConversionException {
    if (object == null) {
      return null;
    }
    if (!(object instanceof String)) {
      throw new IllegalArgumentException("object must be a String");
    }
    String string = (String) object;
    if (string.isEmpty() || string.equalsIgnoreCase("null")) {
      return Collections.emptyList();
    }
    if (!List.class.isAssignableFrom(targetType)) {
      throw new IllegalArgumentException("targetType must be List");
    }
    String[] strings = string.split(" ");
    List<Integer> integers = new ArrayList<>();
    for (String s : strings) {
      integers.add(Integer.parseInt(s));
    }
    return integers;
  }
}
