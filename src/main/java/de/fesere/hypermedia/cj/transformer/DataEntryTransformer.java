package de.fesere.hypermedia.cj.transformer;

import de.fesere.hypermedia.cj.model.Item;
import de.fesere.hypermedia.cj.model.data.DataEntry;

import java.util.List;

public class DataEntryTransformer<T> implements ReadTransformation<T> {


    private ReadTransformation<T> innerTransformer;

    public DataEntryTransformer(ReadTransformation<T> innerTransformer) {
        this.innerTransformer = innerTransformer;
    }


    public T  transform(List<DataEntry> dataEntryList) {
        Item item = new Item(dataEntryList);
        return transform(item);
    }

    @Override
    public T transform(Item item) {
        return innerTransformer.transform(item) ;
    }
}
