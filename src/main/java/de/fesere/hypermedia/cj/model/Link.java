package de.fesere.hypermedia.cj.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.net.URI;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Link {

    private final URI href;
    private final String prompt;
    protected String rel;

    @JsonCreator
    public Link(@JsonProperty("rel") String rel, @JsonProperty("href") URI href, @JsonProperty("prompt") String prompt){
        this.rel = rel;
        this.href = href;
        this.prompt = prompt;
    }

    public String getRel() {
        return this.rel;
    }

    public boolean hasRel(String otherRel){
        return rel.equalsIgnoreCase(otherRel);
    }

    public URI getHref() {
        return href;
    }

    public String getPrompt() {
        return prompt;
    }
}
