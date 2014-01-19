package de.fesere.hypermedia.cj.model.transformer;

import de.fesere.hypermedia.cj.model.Item;

public interface WriteTransformer<T> {

    Item convert(T input);
}
