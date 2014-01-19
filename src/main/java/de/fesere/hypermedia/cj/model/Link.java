package de.fesere.hypermedia.cj.model;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

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
