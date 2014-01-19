package de.fesere.hypermedia.cj.model;

import com.sun.deploy.util.StringUtils;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Query {

    private final URI href;
    private final String rel;
    private final List<DataEntry> data;

    @JsonCreator
    public Query(@JsonProperty("href") URI href, @JsonProperty("rel") String rel, @JsonProperty("data") List<DataEntry> data) {
        this.href = href;
        this.rel = rel;
        this.data = data;
    }

    public URI getHref() {
        return href;
    }

    public String getRel() {
        return rel;
    }

    public List<DataEntry> getData() {
        return data;
    }

    public Query set(String name, String value) {
        for (DataEntry dataEntry : data) {
            if (dataEntry.getName().equals(name)) {
                dataEntry.set(value);
            }
        }

        return this;
    }

    public URI buildURI() {
        return URI.create(this.getHref() + "?" + this.getDataForUri());
    }


    private String getDataForUri() {
        List<String> entryPairs = new LinkedList<>();
        for (DataEntry entry : data) {
            String entryPair = entry.getQueryRepresentation();
            if (entryPair.length() > 0) {
                entryPairs.add(entryPair);
            }

        }

        return StringUtils.join(entryPairs, "&");
    }
}