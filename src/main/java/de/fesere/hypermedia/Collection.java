package de.fesere.hypermedia;


import org.codehaus.jackson.annotate.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@JsonTypeName("collection")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Collection {

    private URI href;
    private List<Item> items = new ArrayList<Item>();

    @JsonCreator()
    public Collection(@JsonProperty("href") URI href) {
        this.href = href;
    }

    public URI getHref() {
        return this.href;
    }

    public String getVersion() {
        return "1.0";
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public ArrayList<Item> getItems() {
        return new ArrayList<Item>(items);
    }
}
