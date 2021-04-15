package br.com.eventhorizon.common.utils;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

public class StringToDoubleConverter extends SimpleArgumentConverter {

  @Override
  protected Object convert(Object object, Class<?> targetType) throws ArgumentConversionException {
    if (object == null) {
      return null;
    }
    if (!(object instanceof String)) {
      throw new IllegalArgumentException("object must be a String");
    }
    String string = (String) object;
    if (string.isEmpty()) {
      return null;
    }
    if (string.equals("infinity")) {
      return Double.POSITIVE_INFINITY;
    }
    if (string.equals("-infinity")) {
      return Double.NEGATIVE_INFINITY;
    }
    if (!Double.class.isAssignableFrom(targetType) &&
        !double.class.isAssignableFrom(targetType)) {
      throw new IllegalArgumentException("targetType must be double");
    }
    return Double.valueOf(string);
  }
}
