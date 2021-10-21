package br.com.eventhorizon.common.pa.format;

import br.com.eventhorizon.common.datastructures.strings.Alphabet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FieldDeserializerTest {

  @Test
  public void testDeserializeBooleanField() throws JsonProcessingException {
    String json = "{\"type\":\"boolean\"}";
    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule("FieldDeserializer", new Version(1, 0, 0, null, null, null));
    module.addDeserializer(Field.class, new FieldDeserializer());
    objectMapper.registerModule(module);
    Field field = objectMapper.readValue(json, Field.class);
    assertNotNull(field);
    assertEquals(FieldType.BOOLEAN, field.getType());
    assertTrue(field instanceof BooleanField);
  }

  @Test
  public void testDeserializeIntegerField() throws JsonProcessingException {
    String json = "{\"type\":\"integer\",\"minimum\":10,\"maximum\":20}";
    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule("FieldDeserializer", new Version(1, 0, 0, null, null, null));
    module.addDeserializer(Field.class, new FieldDeserializer());
    objectMapper.registerModule(module);
    Field field = objectMapper.readValue(json, Field.class);
    assertNotNull(field);
    assertEquals(FieldType.INTEGER, field.getType());
    assertTrue(field instanceof IntegerField);
    IntegerField integerField = (IntegerField) field;
    assertEquals(10, integerField.getMinimum());
    assertEquals(20, integerField.getMaximum());
  }

  @Test
  public void testDeserializeLongField() throws JsonProcessingException {
    String json = "{\"type\":\"long\",\"minimum\":10,\"maximum\":20}";
    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule("FieldDeserializer", new Version(1, 0, 0, null, null, null));
    module.addDeserializer(Field.class, new FieldDeserializer());
    objectMapper.registerModule(module);
    Field field = objectMapper.readValue(json, Field.class);
    assertNotNull(field);
    assertEquals(FieldType.LONG, field.getType());
    assertTrue(field instanceof LongField);
    LongField longField = (LongField) field;
    assertEquals(10, longField.getMinimum());
    assertEquals(20, longField.getMaximum());
  }

  @Test
  public void testDeserializeDoubleField() throws JsonProcessingException {
    String json = "{\"type\":\"double\",\"minimum\":10.5,\"maximum\":20.34}";
    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule("FieldDeserializer", new Version(1, 0, 0, null, null, null));
    module.addDeserializer(Field.class, new FieldDeserializer());
    objectMapper.registerModule(module);
    Field field = objectMapper.readValue(json, Field.class);
    assertNotNull(field);
    assertEquals(FieldType.DOUBLE, field.getType());
    assertTrue(field instanceof DoubleField);
    DoubleField doubleField = (DoubleField) field;
    assertEquals(10.5, doubleField.getMinimum());
    assertEquals(20.34, doubleField.getMaximum());
  }

  @Test
  public void testDeserializeStringField() throws JsonProcessingException {
    String json = "{\"type\":\"string\",\"lengthRef\":\"$line[1].field[3]\",\"minLength\":5,\"maxLength\":25,\"alphabet\":[\"A\",\"B\",\"C\",\"D\"]}";
    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule("FieldDeserializer", new Version(1, 0, 0, null, null, null));
    module.addDeserializer(Field.class, new FieldDeserializer());
    objectMapper.registerModule(module);
    Field field = objectMapper.readValue(json, Field.class);
    assertNotNull(field);
    assertEquals(FieldType.STRING, field.getType());
    assertTrue(field instanceof StringField);
    StringField stringField = (StringField) field;
    assertNotNull(stringField.getLengthRef());
    assertEquals(1, stringField.getLengthRef().getLine());
    assertEquals(3, stringField.getLengthRef().getField());
    assertEquals(5, stringField.getMinLength());
    assertEquals(25, stringField.getMaxLength());
    Alphabet alphabet = stringField.getAlphabet();
    assertNotNull(alphabet);
    assertEquals(4, alphabet.size());
    assertTrue(alphabet.symbols().containsAll(Arrays.asList('A', 'B', 'C', 'D')));
  }
}
