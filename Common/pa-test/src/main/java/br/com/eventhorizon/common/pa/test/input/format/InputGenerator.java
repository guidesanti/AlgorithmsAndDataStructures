package br.com.eventhorizon.common.pa.test.input.format;

import br.com.eventhorizon.common.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public final class InputGenerator {

  private static Map<String, Object> references;

  public static String generate(InputFormat inputFormat) {
    StringBuilder input = new StringBuilder();
    references = new HashMap<>();
    int lineNumber = 0;
    for (Line line : inputFormat.getLines()) {
      int count = line.getCount().isReference()
          ? (int) references.get(line.getCount().asReference().substring(1))
          : line.getCount().asValue();
      input.append(generateLines(line, lineNumber, count));
      lineNumber += count;
    }
    return input.toString();
  }

  private static String generateLines(Line line, int baseLineNumber, int count) {
    StringBuilder generatedLines = new StringBuilder();
    for (int i = 0; i < count; i++) {
      generatedLines.append(generateLine(line, baseLineNumber + i)).append("\n");
    }
    return generatedLines.toString();
  }

  private static String generateLine(Line line, int lineNumber) {
    StringBuilder generatedLine = new StringBuilder();
    int fieldNumber = 0;
    for (Field field : line.getFields()) {
      int count = field.getCount().isReference()
          ? (int) references.get(field.getCount().asReference().substring(1))
          : field.getCount().asValue();
      generatedLine.append(generateFields(field, lineNumber, fieldNumber, count)).append(" ");
      fieldNumber += count;
    }
    generatedLine.delete(generatedLine.length() - 1, generatedLine.length());
    return generatedLine.toString();
  }

  private static String generateFields(Field field, int lineNumber, int baseFieldNumber, int count) {
    StringBuilder generatedFields = new StringBuilder();
    for (int i = 0; i < count; i++) {
      generatedFields.append(generateField(field, lineNumber, baseFieldNumber + i)).append(" ");
    }
    generatedFields.delete(generatedFields.length() - 1, generatedFields.length());
    return generatedFields.toString();
  }

  private static Object generateField(Field field, int lineNumber, int fieldNumber) {
    String key = "line[" + lineNumber + "].field[" + fieldNumber + "]";
    String customKey = field.getKey() != null ? field.getKey() : null;
    Object value;
    switch (field.getType()) {
      case BOOLEAN -> value = generateBooleanField(field);
      case INTEGER -> value = generateIntegerField(field);
      case LONG -> value = generateLongField(field);
      case DOUBLE -> value = generateDoubleField(field);
      case STRING -> value = generateStringField(field);
      default -> throw new InputFormatException("Invalid field type: " + field.getType());
    }
    references.put(key, value);
    if (customKey != null) {
      references.put(customKey, value);
    }
    return value;
  }

  private static Boolean generateBooleanField(Field field) {
    return field.getValue() != null ? field.getValue() : Utils.getRandomInteger(0, 9) < 5;
  }

  private static Integer generateIntegerField(Field field) {
    int min = field.getMin() != null ? (int) field.getMin() : Integer.MIN_VALUE;
    int max = field.getMax() != null ? (int) field.getMax() : Integer.MAX_VALUE;
    return field.getValue() != null ? field.getValue() : Utils.getRandomInteger(min, max);
  }

  private static Long generateLongField(Field field) {
    long min = field.getMin() != null ? (int) field.getMin() : Long.MIN_VALUE;
    long max = field.getMax() != null ? (int) field.getMax() : Long.MAX_VALUE;
    return field.getValue() != null ? field.getValue() : Utils.getRandomLong(min, max);
  }

  private static Double generateDoubleField(Field field) {
    double min = field.getMin() != null ? (int) field.getMin() : Double.MIN_VALUE;
    double max = field.getMax() != null ? (int) field.getMax() : Double.MAX_VALUE;
    return field.getValue() != null ? field.getValue() : Utils.getRandomDouble(min, max);
  }

  private static String generateStringField(Field field) {
    return field.getValue() != null
        ? field.getValue()
        : field.getLength().isReference()
          ? Utils.getRandomString(field.getAlphabet(), (int) references.get(field.getLength().asReference().substring(1)))
          : field.getLength().asValue() > 0
            ? Utils.getRandomString(field.getAlphabet(), field.getLength().asValue())
            : Utils.getRandomString(field.getAlphabet(), (int) field.getMin(), (int) field.getMax());
  }
}
