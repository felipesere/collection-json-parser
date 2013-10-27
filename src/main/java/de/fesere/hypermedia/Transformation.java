package de.fesere.hypermedia;

public interface Transformation<T> {

    T convert(Item item);
}
