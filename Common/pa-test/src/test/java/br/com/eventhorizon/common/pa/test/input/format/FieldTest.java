package br.com.eventhorizon.common.pa.test.input.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FieldTest {

  @Test
  public void testBooleanFieldDeserialization() throws JsonProcessingException {
    String str = "{\"key\":\"boolean-field\",\"type\":\"boolean\",\"value\":true}";
    ObjectMapper objectMapper = new ObjectMapper();
    Field field = objectMapper.readValue(str, Field.class);
    assertNotNull(field);
    assertEquals("boolean-field", field.getKey());
    assertEquals(FieldType.BOOLEAN, field.getType());
    assertEquals(Boolean.TRUE, field.getValue());
  }

  @Test
  public void testIntegerFieldDeserialization() throws JsonProcessingException {
    String str = "{\"key\":\"integer-field\",\"type\":\"integer\",\"value\":1024}";
    ObjectMapper objectMapper = new ObjectMapper();
    Field field = objectMapper.readValue(str, Field.class);
    assertNotNull(field);
    assertEquals("integer-field", field.getKey());
    assertEquals(FieldType.INTEGER, field.getType());
    assertEquals(Integer.valueOf(1024), field.getValue());
  }

  @Test
  public void testLongFieldDeserialization() throws JsonProcessingException {
    String str = "{\"key\":\"long-field\",\"type\":\"long\",\"value\":1000000000000}";
    ObjectMapper objectMapper = new ObjectMapper();
    Field field = objectMapper.readValue(str, Field.class);
    assertNotNull(field);
    assertEquals("long-field", field.getKey());
    assertEquals(FieldType.LONG, field.getType());
    assertEquals(Long.valueOf(1000000000000L), field.getValue());
  }

  @Test
  public void testDoubleFieldDeserialization() throws JsonProcessingException {
    String str = "{\"key\":\"double-field\",\"type\":\"double\",\"value\":1234.5678}";
    ObjectMapper objectMapper = new ObjectMapper();
    Field field = objectMapper.readValue(str, Field.class);
    assertNotNull(field);
    assertEquals("double-field", field.getKey());
    assertEquals(FieldType.DOUBLE, field.getType());
    assertEquals(Double.valueOf(1234.5678), field.getValue());
  }

  @Test
  public void testStringFieldDeserialization() throws JsonProcessingException {
    String str = "{\"key\":\"string-field\",\"type\":\"string\",\"value\":\"some string\"}";
    ObjectMapper objectMapper = new ObjectMapper();
    Field field = objectMapper.readValue(str, Field.class);
    assertNotNull(field);
    assertEquals("string-field", field.getKey());
    assertEquals(FieldType.STRING, field.getType());
    assertEquals("some string", field.getValue());
  }
}
