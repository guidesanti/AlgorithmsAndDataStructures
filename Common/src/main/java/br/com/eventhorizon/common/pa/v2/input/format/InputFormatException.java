package br.com.eventhorizon.common.pa.v2.input.format;

public class InputFormatException extends RuntimeException {

  public InputFormatException(String message) {
    super(message);
  }

  public InputFormatException(String message, Throwable cause) {
    super(message, cause);
  }

  public InputFormatException(Throwable cause) {
    super(cause);
  }
}
