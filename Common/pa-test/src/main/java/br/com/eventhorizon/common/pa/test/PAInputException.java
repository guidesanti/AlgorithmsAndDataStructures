package br.com.eventhorizon.common.pa.test;

public class PAInputException extends RuntimeException {

  public PAInputException(String message) {
    super(message);
  }

  public PAInputException(String message, Throwable cause) {
    super(message, cause);
  }

  public PAInputException(Throwable cause) {
    super(cause);
  }
}
