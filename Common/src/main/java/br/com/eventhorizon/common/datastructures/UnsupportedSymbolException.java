package br.com.eventhorizon.common.datastructures;

public class UnsupportedSymbolException extends RuntimeException {

  public UnsupportedSymbolException() {
  }

  public UnsupportedSymbolException(String message) {
    super(message);
  }
}
