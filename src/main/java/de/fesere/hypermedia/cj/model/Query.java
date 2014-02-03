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
public class Query extends Link {

    private final List<StringDataEntry> data;

    @JsonCreator
    public Query(@JsonProperty("href") URI href, @JsonProperty("rel") String rel, @JsonProperty("prompt") String prompt, @JsonProperty("data") List<StringDataEntry> data) {
        super(rel, href, prompt);
        this.data = data;
    }

    public Query(String rel, String prompt,  List<StringDataEntry> data) {
       this(null, rel, prompt, data);
    }

    public List<StringDataEntry> getData() {
        return data;
    }

    public Query set(String name, String value) {
        StringDataEntry found = findDataEntry(name);

        found.set(value);
        return this;
    }

    private StringDataEntry findDataEntry(String name) {
        for (StringDataEntry dataEntry : data) {
            if (dataEntry.getName().equals(name)) {
                return dataEntry;
            }
        }
        throw new ElementNotFoundException("Did not find element '"+name+"' in query '"+getRel()+"'");
    }

    public URI buildURI() {
        String queryStering = getDataForUri();
        if (StringUtils.isBlank(queryStering)) {
            return getHref();
        } else {
            return URI.create(getHref() + "?" + this.getDataForUri());
        }
    }


    private String getDataForUri() {
        List<String> entryPairs = new LinkedList<>();
        for (StringDataEntry entry : data) {
            String entryPair = entry.buildQueryRepresentation();
            if (StringUtils.isNotBlank(entryPair)) {
                entryPairs.add(entryPair);
            }
        }

        return StringUtils.join(entryPairs, "&");
    }
}
