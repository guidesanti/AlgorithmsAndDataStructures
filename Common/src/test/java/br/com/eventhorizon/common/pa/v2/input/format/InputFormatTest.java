package br.com.eventhorizon.common.pa.v2.input.format;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class InputFormatTest {

  private static final String INPUT_FORMAT = "pa/input-format.json";

  @Test
  public void testDeserializationFromJson() throws IOException {
    InputFormat inputFormat = InputFormat.parse(INPUT_FORMAT);
    assertNotNull(inputFormat);
    assertNotNull(inputFormat.getLines());
    assertEquals(2, inputFormat.getLines().size());

    // Line 0
    Line line = inputFormat.getLines().get(0);
    assertNotNull(line.getCount());
    assertFalse(line.getCount().isReference());
    assertEquals(1, line.getCount().asValue());
    assertNotNull(line.getFields());
    assertEquals(5, line.getFields().size());

    Field field = line.getFields().get(0);
    assertEquals("field0", field.getKey());
    assertEquals(FieldType.INTEGER, field.getType());
    assertNull(field.getValue());
    assertNull(field.getLength());
    assertEquals(1, field.getMin());
    assertEquals(10, field.getMax());
    assertNull(field.getAlphabet());

    field = line.getFields().get(1);
    assertNull(field.getKey());
    assertEquals(FieldType.LONG, field.getType());
    assertNull(field.getValue());
    assertNull(field.getLength());
    assertEquals(100, field.getMin());
    assertEquals(1000000000000L, field.getMax());
    assertNull(field.getAlphabet());

    field = line.getFields().get(2);
    assertNull(field.getKey());
    assertEquals(FieldType.DOUBLE, field.getType());
    assertNull(field.getValue());
    assertNull(field.getLength());
    assertEquals(5.34, field.getMin());
    assertEquals(40.5, field.getMax());
    assertNull(field.getAlphabet());

    field = line.getFields().get(3);
    assertNull(field.getKey());
    assertEquals(FieldType.STRING, field.getType());
    assertNull(field.getValue());
    assertNull(field.getLength());
    assertEquals(10, field.getMin());
    assertEquals(100, field.getMax());
    assertNotNull(field.getAlphabet());
    char[] alphabet = { 'A', 'C', 'G', 'T' };
    assertArrayEquals(alphabet, field.getAlphabet());

    field = line.getFields().get(4);
    assertNull(field.getKey());
    assertEquals(FieldType.STRING, field.getType());
    assertNull(field.getValue());
    assertNotNull(field.getLength());
    assertFalse(field.getLength().isReference());
    assertEquals(1000, field.getLength().asValue());
    assertNull(field.getMin());
    assertNull(field.getMax());
    assertNotNull(field.getAlphabet());
    assertArrayEquals(alphabet, field.getAlphabet());

    // Line 1
    line = inputFormat.getLines().get(1);
    assertNotNull(line.getCount());
    assertTrue(line.getCount().isReference());
    assertEquals("$field0", line.getCount().asReference());
    assertNotNull(line.getFields());
    assertEquals(1, line.getFields().size());

    field = line.getFields().get(0);
    assertNull(field.getKey());
    assertEquals(FieldType.STRING, field.getType());
    assertNull(field.getValue());
    assertNotNull(field.getLength());
    assertTrue(field.getLength().isReference());
    assertEquals("$field0", field.getLength().asReference());
    assertNull(field.getMin());
    assertNull(field.getMax());
    assertNotNull(field.getAlphabet());
    alphabet = new char[]{ 'A', 'C', 'G', 'T' };
    assertArrayEquals(alphabet, field.getAlphabet());
  }
}
