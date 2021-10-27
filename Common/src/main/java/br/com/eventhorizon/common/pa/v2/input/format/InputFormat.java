package br.com.eventhorizon.common.pa.v2.input.format;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class InputFormat {

  private static final Logger LOGGER = Logger.getLogger(InputFormat.class.getName());

  private static final String REFERENCE_START = "$";

  private final List<Line> lines;

  public InputFormat() {
    lines = new ArrayList<>();
  }

  public List<Line> getLines() {
    return lines;
  }

  public static InputFormat parse(String file) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      SimpleModule module = new SimpleModule();
      module.addDeserializer(ValueOrReference.class, new ValueOrReferenceDeserializer());
      objectMapper.registerModule(module);
      InputStream inputFormatFile = InputFormat.class.getClassLoader().getResourceAsStream(file);
      InputFormat inputFormat = objectMapper.readValue(inputFormatFile, InputFormat.class);
      validate(inputFormat);
      return inputFormat;
    } catch (Exception ex) {
      LOGGER.severe("Failed to load input format: " + ex.getMessage());
      throw new RuntimeException(ex);
    }
  }

  private static void validate(InputFormat inputFormat) {
    Set<String> keys = new HashSet<>();
    inputFormat.lines.forEach(line -> {
      ValueOrReference count = line.getCount();
      if (count.isReference()) {
        String countStr = count.asReference();
        if (!NumberUtils.isParsable(countStr) && !countStr.startsWith(REFERENCE_START)) {
          throw new InputFormatException("Invalid count, references should start with " + REFERENCE_START);
        }
        if (!NumberUtils.isParsable(countStr) && !keys.contains(countStr.substring(1))) {
          throw new InputFormatException("Invalid count, reference not found: " + countStr);
        }
      } else {
        int countInt = count.asValue();
        if (countInt <= 0) {
          throw new InputFormatException("Invalid count, it should be greater than zero: " + countInt);
        }
      }

      line.getFields().forEach(field -> validateField(keys, field));
    });
  }

  private static void validateField(Set<String> keys, Field field) {
    // Key
    if (field.getKey() != null && keys.contains(field.getKey())) {
      throw new InputFormatException("Duplicate field key: " + field.getKey());
    }
    keys.add(field.getKey());

    // Type
    if (field.getType() == null) {
      throw new InputFormatException("Field type cannot be null");
    }

    switch (field.getType()) {
      case BOOLEAN -> validateBooleanField(field);
      case INTEGER -> validateIntegerField(field);
      case LONG -> validateLongField(field);
      case DOUBLE -> validateDoubleField(field);
      case STRING -> validateStringField(field);
    }
  }

  private static void validateBooleanField(Field field) {
    // Value
    try {
      if (field.getValue() != null) {
        boolean value = field.getValue();
      }
    } catch (Exception ex) {
      throw new InputFormatException("Invalid boolean value: " + field.getValue());
    }
  }

  private static void validateIntegerField(Field field) {
    // Value
    try {
      if (field.getValue() != null) {
        int value = field.getValue();
      }
    } catch (Exception ex) {
      throw new InputFormatException("Invalid integer value: " + field.getValue());
    }
  }

  private static void validateLongField(Field field) {
    // Value
    try {
      if (field.getValue() != null) {
        long value = field.getValue();
      }
    } catch (Exception ex) {
      throw new InputFormatException("Invalid long value: " + field.getValue());
    }
  }

  private static void validateDoubleField(Field field) {
    // Value
    try {
      if (field.getValue() != null) {
        double value = field.getValue();
      }
    } catch (Exception ex) {
      throw new InputFormatException("Invalid double value: " + field.getValue());
    }
  }

  private static void validateStringField(Field field) {
    // Value
    try {
      if (field.getValue() != null) {
        String value = field.getValue();
      }
    } catch (Exception ex) {
      throw new InputFormatException("Invalid string value: " + field.getValue());
    }
  }
}
