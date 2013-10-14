package de.fesere.hypermedia;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.LinkedList;
import java.util.List;

public class Template {

    private List<DataEntry> data;

    public Template(@JsonProperty("data") List<DataEntry> data) {
        this.data = data;
    }

    public List<DataEntry> getData() {
        return new LinkedList<DataEntry>(data);
    }
}
