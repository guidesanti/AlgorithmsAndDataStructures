package br.com.eventhorizon.common.utils;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

import java.util.ArrayList;
import java.util.List;

public class StringToListOfListOfIntegers extends SimpleArgumentConverter {

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
      return null;
    }
    List<List<Integer>> components = new ArrayList<>();
    String[] componentsStr = ((String) object).split("%");
    for (String componentStr : componentsStr) {
      List<Integer> vertices = new ArrayList<>();
      String[] verticesStr = componentStr.split(" ");
      for (String vertexStr : verticesStr) {
        vertices.add(Integer.valueOf(vertexStr));
      }
      components.add(vertices);
    }
    return components;
  }
}
