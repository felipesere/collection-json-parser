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

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public void setRender(String render) {
        this.render = render;
    }


   public String toString() {
       StringBuilder builder = new StringBuilder();
       builder.append("Link [").append("rel=").append(rel).append(", href=").append(href);
       if(prompt != null) {
           builder.append(", prompt=").append(prompt);
       }
       if(render != null) {
           builder.append(", render=").append(render);
       }
       builder.append("]");
       return builder.toString();
   }
}
