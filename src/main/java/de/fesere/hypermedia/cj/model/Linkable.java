package de.fesere.hypermedia.cj.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Linkable {

    @JsonProperty("rel")
    String rel;

    public Linkable(String rel) {
        this.rel = rel;
    }

    public String getRel() {
        return this.rel;
    }
}
