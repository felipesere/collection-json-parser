package de.fesere.hypermedia.cj.model.transformer;

import de.fesere.hypermedia.cj.model.Item;

public interface ReadTransformation<T> {

    T convert(Item item);
}
