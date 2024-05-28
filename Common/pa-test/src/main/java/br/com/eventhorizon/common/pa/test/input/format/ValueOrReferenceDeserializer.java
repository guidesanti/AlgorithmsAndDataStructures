package br.com.eventhorizon.common.pa.test.input.format;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class ValueOrReferenceDeserializer extends StdDeserializer<ValueOrReference> {

  public ValueOrReferenceDeserializer() {
    this(null);
  }

  public ValueOrReferenceDeserializer(Class<?> clazz) {
    super(clazz);
  }

  @Override
  public ValueOrReference deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
      throws IOException {
    ObjectCodec objectCodec = jsonParser.getCodec();
    JsonNode node = objectCodec.readTree(jsonParser);
    if (node.isTextual()) {
      return new ValueOrReference(node.asText());
    } else if (node.isNumber()) {
      return new ValueOrReference(node.asInt());
    } else {
      throw new InputFormatException("Invalid value: " + node);
    }
  }
}
