package io.prochyra.readinglist.domain;

public class CatalogueException extends Exception {

    public CatalogueException(String message, Throwable cause) {
        super(message, cause);
    }

    public CatalogueException(String message) {
        super(message);
    }
}
