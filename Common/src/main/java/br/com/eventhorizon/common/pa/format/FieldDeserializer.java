package br.com.eventhorizon.common.pa.format;

import br.com.eventhorizon.common.datastructures.strings.Alphabet;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class FieldDeserializer extends StdDeserializer<Field> {

  public FieldDeserializer() {
    this(null);
  }

  protected FieldDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Field deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
    ObjectCodec codec = jsonParser.getCodec();
    JsonNode node = codec.readTree(jsonParser);
    FieldType type = FieldType.valueOf(node.get("type").asText().toUpperCase());
    switch (type) {
      case BOOLEAN:
        return new BooleanField();
      case INTEGER:
        IntegerField integerField = new IntegerField();
        integerField.setMinimum(node.get("minimum").asInt(Integer.MIN_VALUE));
        integerField.setMaximum(node.get("maximum").asInt(Integer.MAX_VALUE));
        return integerField;
      case LONG:
        LongField longField = new LongField();
        longField.setMinimum(node.get("minimum").asLong(Long.MIN_VALUE));
        longField.setMaximum(node.get("maximum").asLong(Long.MAX_VALUE));
        return longField;
      case DOUBLE:
        DoubleField doubleField = new DoubleField();
        doubleField.setMinimum(node.get("minimum").asDouble(Double.MIN_VALUE));
        doubleField.setMaximum(node.get("maximum").asDouble(Double.MAX_VALUE));
        return doubleField;
      case STRING:
        StringField stringField = new StringField();
        if (node.has("length")) {
          stringField.setLength(node.get("length").asInt());
        }
        if (node.has("lengthRef")) {
          stringField.setLengthRef(new Reference(node.get("lengthRef").asText()));
        }
        if (node.has("minLength")) {
          stringField.setMinLength(node.get("minLength").asInt(0));
        }
        if (node.has("maxLength")) {
          stringField.setMaxLength(node.get("maxLength").asInt(Integer.MAX_VALUE));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectReader objectReader = objectMapper.readerFor(new TypeReference<List<Character>>() {
        });
        stringField.setAlphabet(new Alphabet((Collection) objectReader.readValue(node.get("alphabet"))));
        return stringField;
      default:
        throw new RuntimeException("Invalid field type " + type);
    }
  }
}
