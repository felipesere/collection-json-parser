package de.fesere.hypermedia.cj.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.fesere.hypermedia.cj.exceptions.ElementNotFoundException;
import de.fesere.hypermedia.cj.model.builder.DataEntryFactory;
import de.fesere.hypermedia.cj.model.data.BaseDataEntry;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Query extends Link {

    private final List<DataEntry> data;

    @JsonCreator
    public Query(@JsonProperty("href") URI href,
                 @JsonProperty("rel") String rel,
                 @JsonProperty("prompt") String prompt,
                 @JsonProperty("data") List<DataEntry> data) {
        super(rel, href, prompt);
        this.data = new LinkedList<>(data);
    }

    public Query(String rel, String prompt,  List<DataEntry> data) {
       this(null, rel, prompt, data);
    }

    public List<DataEntry> getData() {
        return data;
    }


    public Query set(String name, Object o) {
        DataEntry found = findDataEntry(name);

        DataEntry replacement = DataEntryFactory.create(name, o, found.getPrompt());


        data.remove(found);
        data.add(replacement);
        return this;
    }

    private DataEntry findDataEntry(String name) {
        for (DataEntry dataEntry : data) {
            if (dataEntry.getName().equals(name)) {
                return dataEntry;
            }
        }
        throw new ElementNotFoundException("Did not find element '"+name+"' in query '"+getRel()+"'");
    }

    public URI buildURI() {
        String queryString = getDataForUri();
        if (StringUtils.isBlank(queryString)) {
            return getHref();
        } else {
            return URI.create(getHref() + "?" + this.getDataForUri());
        }
    }


    private String getDataForUri() {
        List<String> entryPairs = new LinkedList<>();
        for (DataEntry entry : data) {
            String entryPair = entry.buildQueryRepresentation();
            if (StringUtils.isNotBlank(entryPair)) {
                entryPairs.add(entryPair);
            }
        }

        return StringUtils.join(entryPairs, "&");
    }
}
