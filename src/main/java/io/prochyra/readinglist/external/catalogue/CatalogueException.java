package io.prochyra.readinglist.external.catalogue;

public class CatalogueException extends Exception {

    CatalogueException(String message, Throwable cause) {
        super(message, cause);
    }

    CatalogueException(String message) {
        super(message);
    }
}
