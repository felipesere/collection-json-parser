package de.fesere.hypermedia.cj.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.fesere.hypermedia.cj.exceptions.ElementNotFoundException;
import de.fesere.hypermedia.cj.exceptions.MalformedDataValueException;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
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
        DataEntry found = findDataEntry(name);
        return found.getValue();
    }

    public int getInt(String name) {
        DataEntry found = findDataEntry(name);

        try {
            return Integer.parseInt(found.getValue());
        } catch (NumberFormatException nfe) {
            throw new MalformedDataValueException("Did not find property '"+name+"' in '"+href+"'", nfe);
        }

    }

    private DataEntry findDataEntry(String name) {
        DataEntry found = null;
        for(DataEntry entry : data) {
            if(entry.getName().equals(name)) {
                found = entry;
                break;
            }
        }
        if(found == null) {
            throw new ElementNotFoundException("Did not find property '"+name+"' in '"+href+"'");
        }
        return found;
    }

    public List<DataEntry> getData() {
        return new ArrayList<>(data);
    }

    public List<Link> getLinks() {
        return links;
    }

    public double getDouble(String name) {
       DataEntry found = findDataEntry(name);

        try {
            return Double.parseDouble(found.getValue());
        } catch (NumberFormatException nfe) {
            throw new MalformedDataValueException("Did not find property '"+name+"' in '"+href+"'", nfe);
        }

    }
}
