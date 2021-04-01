package br.com.eventhorizon.common.utils;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

public class StringToIntegerArrayConverter extends SimpleArgumentConverter {

  @Override
  protected Object convert(Object object, Class<?> targetType) throws ArgumentConversionException {
    if (!(object instanceof String)) {
      throw new IllegalArgumentException("object must be a String");
    }
    if (!int[].class.isAssignableFrom(targetType)) {
      throw new IllegalArgumentException("targetType must be int[]");
    }
    String[] strings = ((String) object).split(" ");
    int[] integers = new int[strings.length];
    for (int i = 0; i < strings.length; i++) {
      integers[i] = Integer.parseInt(strings[i]);
    }
    return integers;
  }
}
