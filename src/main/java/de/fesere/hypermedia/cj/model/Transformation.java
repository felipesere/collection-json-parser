package de.fesere.hypermedia.cj.model;

public interface Transformation<T> {

    T convert(Item item);
}
