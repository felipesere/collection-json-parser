package de.fesere.hypermedia;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class Query {

    private final String href;
    private final String rel;
    private final List<DataEntry> data;
    private String prompt = null;

    @JsonCreator
    public Query(@JsonProperty("href") String href, @JsonProperty("rel") String rel, @JsonProperty("data")List<DataEntry> data) {
        this.href = href;
        this.rel = rel;
        this.data = data;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getHref() {
        return href;
    }

    public String getRel() {
        return rel;
    }

    public List<DataEntry> getData() {
        return data;
    }

    public String getPrompt() {
        return prompt;
    }
}
