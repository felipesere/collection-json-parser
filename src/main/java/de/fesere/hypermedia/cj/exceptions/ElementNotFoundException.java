package de.fesere.hypermedia.cj.exceptions;

public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String message) {
        super(message);
    }

    public ElementNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
