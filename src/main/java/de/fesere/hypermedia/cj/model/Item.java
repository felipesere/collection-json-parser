package de.fesere.hypermedia.cj.model;

import de.fesere.hypermedia.cj.exceptions.ElementNotFoundException;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item {
    private final URI href;
    private final List<DataEntry> data = new ArrayList<>();
    private List<Link> links = new ArrayList<>();

    public Item(URI href) {
        this(href, null);
    }

    @JsonCreator
    public Item(@JsonProperty("href") URI href, @JsonProperty("links") List<Link> links) {
        this.href = href;
        this.links = links;
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
