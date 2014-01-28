package de.fesere.hypermedia.cj.model.server;

import de.fesere.hypermedia.cj.model.DataEntry;
import de.fesere.hypermedia.cj.model.Item;
import de.fesere.hypermedia.cj.model.Template;

import java.util.LinkedList;
import java.util.List;

public class TemplateBuilder {
    private List<DataEntry> entries = new LinkedList<>();

    public TemplateBuilder filledFromItem(Item item) {
        setEntries(item.getData());
        return  this;
    }

    private void setEntries(List<DataEntry> entries) {
        this.entries.clear();
        this.entries.addAll(entries);
    }


    public Template build() {
        return new Template(entries);
    }

    public TemplateBuilder emptyFromItem(Item item) {
        List<DataEntry> entries = getClearedDataEntries(item);
        setEntries(entries);

        return this;
    }

    private List<DataEntry> getClearedDataEntries(Item item) {
        List<DataEntry> entries = item.getData();
        for(DataEntry entry : entries) {
            entry.clear();
        }
        return entries;
    }
}
