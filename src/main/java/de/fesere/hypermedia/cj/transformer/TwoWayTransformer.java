package de.fesere.hypermedia.cj.transformer;

import de.fesere.hypermedia.cj.model.Item;

public abstract class TwoWayTransformer<T> implements ReadTransformation<T>, WriteTransformer<T>  {
    @Override
    public T transform(Item item) {
        throw new IllegalStateException(this.getClass().getName()+ " does not support operation" );
    }

    @Override
    public Item transform(T input) {
        throw new IllegalStateException(this.getClass().getName()+ " does not support operation" );
    }
}
