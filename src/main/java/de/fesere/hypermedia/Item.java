package de.fesere.hypermedia;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class Item {
    private URI href;
    private List<DataEntry> data = new ArrayList<DataEntry>();
    private List<Link> links = new ArrayList<Link>();

    public Item(URI href) {
        this(href, null);
    }

    @JsonCreator
    public Item(@JsonProperty("href") URI href, @JsonProperty("links") List<Link> links) {
        this.href = href;
        this.links = links;
    }

    public void setLinks(List<Link> links) {
        links = new ArrayList<Link>(links);
    }

    public URI getHref() {
        return this.href;
    }

    public void addData(DataEntry dataEntry) {
        data.add(dataEntry);
    }

    public String getString(String name) {
        for(DataEntry entry : data) {
            if(entry.getName().equals(name)){
                return entry.getValue();
            }
        }

        return null;
    }


    public List<DataEntry> getData() {
        return new ArrayList<DataEntry>(data);
    }

    public List<Link> getLinks() {
        return links;
    }
}
