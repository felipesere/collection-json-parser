package de.fesere.hypermedia.cj.model.server;

import de.fesere.hypermedia.cj.model.DataEntry;
import de.fesere.hypermedia.cj.model.Item;
import de.fesere.hypermedia.cj.model.Link;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class ItemBuilder {

    private List<DataEntry> dataEntries = new LinkedList<>();
    private List<Link> links = new LinkedList<>();
    private UriConstructor constructor;

    public ItemBuilder(URI href) {
        constructor = new UriConstructor(href);
    }

    public ItemBuilder addData(DataEntry entry) {
        dataEntries.add(entry);
        return this;
    }

    public ItemBuilder addLink(String rel, URI uri, String prompt) {
        links.add(new Link(rel, uri, prompt));
        return this;
    }

    public Item build() {
        return new Item(constructor.getBase(), dataEntries, links);
    }
}
