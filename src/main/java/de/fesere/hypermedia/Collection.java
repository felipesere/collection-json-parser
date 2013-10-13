package de.fesere.hypermedia;


import org.codehaus.jackson.annotate.*;

import java.net.URI;

@JsonTypeName("collection")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Collection {

    private URI href;

    @JsonCreator()
    public Collection(@JsonProperty("href") URI href) {
        this.href = href;
    }

    public URI getHref() {
        return this.href;
    }

    public String getVersion() {
        return "1.0";
    }

}
