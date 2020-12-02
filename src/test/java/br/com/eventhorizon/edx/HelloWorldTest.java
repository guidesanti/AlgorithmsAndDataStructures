package br.com.eventhorizon.edx;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorldTest extends BaseTest {

  @Test
  public void testMain() {
    HelloWorld.main(null);
    assertEquals("Hello, World!", getActualOutput());
  }
}
