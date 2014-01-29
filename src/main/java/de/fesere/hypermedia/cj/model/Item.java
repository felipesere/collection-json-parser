package de.fesere.hypermedia.cj.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.fesere.hypermedia.cj.exceptions.ElementNotFoundException;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"href", "data", "links"})
public class Item {
    private final URI href;
    private final List<DataEntry> data = new ArrayList<>();
    private List<Link> links = new ArrayList<>();


    public Item() {
        this(Collections.<DataEntry>emptyList());
    }

    public Item(List<DataEntry> data) {
        this((URI) null);
        if(data != null) {
            this.data.addAll(data);
        }
    }

    public Item(URI href) {
        this(href, Collections.<Link>emptyList());
    }

    @JsonCreator
    public Item(@JsonProperty("href") URI href, @JsonProperty("links") List<Link> links) {
        this.href = href;
        this.links = links;
    }

    public Item(URI href, List<DataEntry> entries, List<Link> links) {
        this(href, links);
        this.data.addAll(entries);
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
        throw new ElementNotFoundException("Did not find property '"+name+"' in '"+href+"'");
    }

    public int getInt(String name) {
        for(DataEntry entry : data) {
            if(entry.getName().equals(name)) {
                try {
                    return Integer.parseInt(entry.getValue());
                } catch (NumberFormatException nfe) {
                    throw new ElementNotFoundException("Did not find property '"+name+"' in '"+href+"'");
                }
            }
        }
        throw new ElementNotFoundException("Did not find property '"+name+"' in '"+href+"'");

    }

    public List<DataEntry> getData() {
        return new ArrayList<>(data);
    }

    public List<Link> getLinks() {
        return links;
    }
}
