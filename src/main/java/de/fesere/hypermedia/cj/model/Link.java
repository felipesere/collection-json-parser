package de.fesere.hypermedia.cj.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.net.URI;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Link {

    private final URI href;
    private final String prompt;
    private String rel;

    @JsonCreator
    public Link(@JsonProperty("rel") String rel, @JsonProperty("href") URI href, @JsonProperty("prompt") String prompt){
        this.rel = rel;
        this.href = href;
        this.prompt = prompt;
    }

    public String getRel() {
        return this.rel;
    }

    public URI getHref() {
        return href;
    }

    public String getPrompt() {
        return prompt;
    }
}
