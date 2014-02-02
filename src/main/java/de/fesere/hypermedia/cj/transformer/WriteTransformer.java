package de.fesere.hypermedia.cj.transformer;

import de.fesere.hypermedia.cj.model.Item;

public interface WriteTransformer<T> {

    Item transform(T input);
}
