package br.com.eventhorizon.common.datastructures.tries;

public class UnsupportedSymbolException extends RuntimeException {

  public UnsupportedSymbolException() {
  }

  public UnsupportedSymbolException(String message) {
    super(message);
  }
}
