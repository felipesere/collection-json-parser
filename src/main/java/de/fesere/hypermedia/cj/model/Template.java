package de.fesere.hypermedia.cj.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.LinkedList;
import java.util.List;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Template {

    private final List<DataEntry> data;

    public Template(@JsonProperty("data") List<DataEntry> data) {
        this.data = data;
    }

    public List<DataEntry> getData() {
        return new LinkedList<>(data);
    }
}
