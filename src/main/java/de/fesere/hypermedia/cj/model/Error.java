package de.fesere.hypermedia.cj.model;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Error {

    private final String title;
    private final String code;
    private final String message;

    @JsonCreator
    public Error(@JsonProperty("title") String title, @JsonProperty("code") String code, @JsonProperty("message") String message) {

        this.title = title;
        this.code = code;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
