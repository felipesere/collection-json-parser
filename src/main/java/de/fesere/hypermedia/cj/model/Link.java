package de.fesere.hypermedia.cj.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.net.URI;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Link {

    private final String rel;
    private final URI href;

    @JsonCreator
    public Link(@JsonProperty("rel") String rel, @JsonProperty("href") URI href){
        this.rel = rel;
        this.href = href;
    }

    public String getRel() {
        return this.rel;
    }

    public URI getHref() {
        return href;
    }
}
