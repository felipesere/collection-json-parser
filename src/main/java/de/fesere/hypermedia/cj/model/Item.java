package de.fesere.hypermedia.cj.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.fesere.hypermedia.cj.exceptions.ElementNotFoundException;
import de.fesere.hypermedia.cj.exceptions.PropertyNotFoundException;
import de.fesere.hypermedia.cj.model.data.*;

import java.net.URI;
import java.util.*;

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
        if (data != null) {
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

    public List<DataEntry> getData() {
        return new ArrayList<>(data);
    }

    public List<Link> getLinks() {
        return links;
    }

    public String getString(String name) {
        DataEntry found = findDataEntry(name);

        if (found instanceof StringDataEntry) {
            return ((StringDataEntry) found).getValue();
        }
        throw new PropertyNotFoundException(name, href, String.class);
    }

    public boolean isNullValue(String name) {
       DataEntry found = findDataEntry(name);

       return found instanceof NullDataEntry;
    }

    public int getInt(String name) {
        DataEntry found = findDataEntry(name);

        if (found instanceof NumberDataEntry) {
            Number value = ((NumberDataEntry) found).getValue();
            return Integer.parseInt(value.toString());

        }

        throw new PropertyNotFoundException(name, href, Number.class);
    }

    public double getDouble(String name) {
        DataEntry found = findDataEntry(name);

        if (found instanceof NumberDataEntry) {
            Number value = ((NumberDataEntry) found).getValue();
            return Double.parseDouble(value.toString());
        }

        throw new PropertyNotFoundException(name, href, Number.class);
    }

    public boolean getBoolean(String name) {
        DataEntry entry = findDataEntry(name);
        if (entry instanceof BooleanDataEntry) {
            return (boolean) entry.getValue();
        }

        throw new PropertyNotFoundException(name, href, Boolean.class);
    }

    private DataEntry findDataEntry(String name) {
        DataEntry found = null;
        for (DataEntry entry : data) {
            if (entry.getName().equals(name)) {
                found = entry;
                break;
            }
        }
        if (found == null) {
            throw new ElementNotFoundException("Did not find property '" + name + "' in '" + href + "'");
        }
        return found;
    }

    public Map<String, Object> extractDataMap() {
        Map<String, Object> result = new HashMap<>();
        for (DataEntry entry : data) {
            result.put(entry.getName(), entry.getValue());
        }

        return result;
    }

    public Object getObject(String entryName) {
        DataEntry entry = findDataEntry(entryName);

        return entry.getValue();
    }
}
