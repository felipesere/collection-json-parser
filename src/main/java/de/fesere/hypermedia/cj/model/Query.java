package de.fesere.hypermedia.cj.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.fesere.hypermedia.cj.exceptions.ElementNotFoundException;
import org.apache.commons.lang3.StringUtils;

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
        DataEntry found = findDataEntry(name);

        found.set(value);
        return this;
    }

    private DataEntry findDataEntry(String name) {
        for (DataEntry dataEntry : data) {
            if (dataEntry.getName().equals(name)) {
                return dataEntry;
            }
        }
        throw new ElementNotFoundException("Did not find element '"+name+"' in query '"+rel+"'");
    }

    public URI buildURI() {
        String queryStering = getDataForUri();
        if (StringUtils.isBlank(queryStering)) {
            return href;
        } else {
            return URI.create(this.getHref() + "?" + this.getDataForUri());
        }
    }


    private String getDataForUri() {
        List<String> entryPairs = new LinkedList<>();
        for (DataEntry entry : data) {
            String entryPair = entry.getQueryRepresentation();
            if (StringUtils.isNotBlank(entryPair)) {
                entryPairs.add(entryPair);
            }
        }

        return StringUtils.join(entryPairs, "&");
    }
}
