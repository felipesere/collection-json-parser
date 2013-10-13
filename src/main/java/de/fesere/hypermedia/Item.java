package de.fesere.hypermedia;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class Item {
    private URI href;
    private List<DataEntry> data = new ArrayList<DataEntry>();

    @JsonCreator
    public Item(@JsonProperty("href") URI href) {
        this.href = href;
    }

    public URI getHref() {
        return this.href;
    }

    public void addData(DataEntry dataEntry) {
        data.add(dataEntry);
    }


    public List<DataEntry> getData() {
        return new ArrayList<DataEntry>(data);
    }
}
