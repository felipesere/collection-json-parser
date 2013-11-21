package de.fesere.hypermedia;

import com.sun.deploy.util.StringUtils;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;

public class Query {

    private final URI href;
    private final String rel;
    private String name;
    private final List<DataEntry> data;
    private String prompt = null;

    @JsonCreator
    public Query(@JsonProperty("href") URI href, @JsonProperty("rel") String rel,@JsonProperty("name") String name, @JsonProperty("data")List<DataEntry> data) {
        this.href = href;
        this.rel = rel;
        this.name = name;
        this.data = data;
    }

    public URI getHref() {
        return href;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getRel() {
        return rel;
    }

    public List<DataEntry> getData() {
        return data;
    }

    @JsonProperty("prompt")
    public String getPrompt() {
        return prompt;
    }

    public Query set(String name, String value) {
       for(DataEntry dataEntry : data) {
           if(dataEntry.getName().equals(name)) {
               dataEntry.set(value);
           }
       }

        return this;
    }


    public String getDataForUri() {
        List<String> entryPairs = new LinkedList<String>();
        for(DataEntry entry : data) {
            String entryPair = entry.getQueryRepresentation();
            if(entryPair.length() > 0) {
                entryPairs.add(entryPair);
            }

        }

        return StringUtils.join(entryPairs, "&");



    }
}
