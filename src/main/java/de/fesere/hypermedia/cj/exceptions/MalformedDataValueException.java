package de.fesere.hypermedia.cj.exceptions;

public class MalformedDataValueException extends RuntimeException {

    public MalformedDataValueException(String message) {
        super(message);
    }

    public MalformedDataValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
