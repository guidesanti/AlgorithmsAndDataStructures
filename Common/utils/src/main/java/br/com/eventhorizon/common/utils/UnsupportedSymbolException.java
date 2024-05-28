package br.com.eventhorizon.common.utils;

public class UnsupportedSymbolException extends RuntimeException {

  public UnsupportedSymbolException() {
  }

  public UnsupportedSymbolException(String message) {
    super(message);
  }
}
