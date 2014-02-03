package de.fesere.hypermedia.cj.model.builder;

import de.fesere.hypermedia.cj.model.StringDataEntry;
import de.fesere.hypermedia.cj.model.Item;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class ItemBuilder {

    private List<StringDataEntry> dataEntries = new LinkedList<>();
    private UriConstructor constructor;
    private LinkBuilder linkBuilder;

    public ItemBuilder(URI href) {
        constructor = new UriConstructor(href);
        linkBuilder = new LinkBuilder(href);
    }

    public ItemBuilder addData(StringDataEntry entry) {
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
