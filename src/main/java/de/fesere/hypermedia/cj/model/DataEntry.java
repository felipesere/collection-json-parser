package de.fesere.hypermedia.cj.model;

public interface DataEntry<T> {
    String getName();

    String getPrompt();

    T getValue();

    String buildQueryRepresentation();
}
