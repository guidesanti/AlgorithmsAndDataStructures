package br.com.eventhorizon.common.pa.format;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class InputFormatTest {

  private static final String INPUT_FORMAT = "pa/input-format.json";

  @Test
  public void testDeserializationFromJson() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule("Deserializer", new Version(1, 0, 0, null, null, null));
    module.addDeserializer(Field.class, new FieldDeserializer());
    objectMapper.registerModule(module);
    InputStream inputFormatFile = getClass().getClassLoader().getResourceAsStream(INPUT_FORMAT);
    InputFormat inputFormat = objectMapper.readValue(inputFormatFile, InputFormat.class);
    assertNotNull(inputFormat);
  }
}
