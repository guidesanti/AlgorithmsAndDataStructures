package br.com.eventhorizon.common.pa.test;

public class PACsvFormatException extends RuntimeException {

    public PACsvFormatException(String message) {
        super(message);
    }

    public PACsvFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
