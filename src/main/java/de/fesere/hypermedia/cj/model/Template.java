package de.fesere.hypermedia.cj.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import de.fesere.hypermedia.cj.serialization.Wrapped;

import java.util.LinkedList;
import java.util.List;

@JsonTypeName("template")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Template implements Wrapped {

    private final List<DataEntry> data;

    public Template(@JsonProperty("data") List<DataEntry> data) {
        this.data = data;
    }

    public List<DataEntry> getData() {
        return new LinkedList<>(data);
    }

    public void fill(Item item) {
        for(DataEntry entry : data) {
            String entryName = entry.getName();
            String value = item.getString(entryName);
            entry.set(value);
        }
    }
}
