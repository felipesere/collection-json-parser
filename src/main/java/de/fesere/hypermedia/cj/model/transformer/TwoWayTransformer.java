package de.fesere.hypermedia.cj.model.transformer;

import de.fesere.hypermedia.cj.model.Item;

public class TwoWayTransformer<T> implements ReadTransformation<T>, WriteTransformer<T>  {
    @Override
    public T convert(Item item) {
        throw new IllegalStateException(this.getClass().getName()+ " does not convert to operation" );
    }

    @Override
    public Item convert(T input) {
        throw new IllegalStateException(this.getClass().getName()+ " does not convert to operation" );
    }
}
