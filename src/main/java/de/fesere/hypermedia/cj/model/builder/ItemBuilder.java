package de.fesere.hypermedia.cj.model.builder;

import de.fesere.hypermedia.cj.model.DataEntry;
import de.fesere.hypermedia.cj.model.Item;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class ItemBuilder {

    private List<DataEntry> dataEntries = new LinkedList<>();
    private UriConstructor constructor;
    private LinkBuilder linkBuilder;

    public ItemBuilder(URI href) {
        constructor = new UriConstructor(href);
        linkBuilder = new LinkBuilder(href);
    }

    public ItemBuilder addData(DataEntry entry) {
        dataEntries.add(entry);
        return this;
    }

    public LinkBuilder getRelativeLinkBuilder() {
        return linkBuilder;
    }

    public Item build() {
        return new Item(constructor.getBase(), dataEntries, linkBuilder.build());
    }
}
