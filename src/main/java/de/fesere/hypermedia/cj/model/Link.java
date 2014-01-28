package de.fesere.hypermedia.cj.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.net.URI;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Link extends Linkable{

    private final URI href;

    @JsonCreator
    public Link(@JsonProperty("rel") String rel, @JsonProperty("href") URI href){
        super(rel);
        this.href = href;
    }

    public URI getHref() {
        return href;
    }
}
