package de.fesere.hypermedia.cj.exceptions;

public class SerializationException extends RuntimeException {

    public SerializationException(String message) {
        super(message);
    }

    public SerializationException(String message, Throwable throwbale) {
        super(message, throwbale);
    }
}
