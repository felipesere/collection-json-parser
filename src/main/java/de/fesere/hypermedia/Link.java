package de.fesere.hypermedia;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.net.URI;

public class Link {

    private final String rel;
    private final URI href;
    private String prompt = null;
    private String render = null;

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

    public String getPrompt() {
        return prompt;
    }

    public String getRender() {
        return render;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public void setRender(String render) {
        this.render = render;
    }
}
