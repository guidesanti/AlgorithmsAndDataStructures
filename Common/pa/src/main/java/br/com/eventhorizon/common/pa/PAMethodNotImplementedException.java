package br.com.eventhorizon.common.pa;

import lombok.Getter;

@Getter
public class PAMethodNotImplementedException extends RuntimeException {

    private final String method;

    public PAMethodNotImplementedException(String method) {
        super("PA method " + method + " not implemented");
        this.method = method;
    }
}
