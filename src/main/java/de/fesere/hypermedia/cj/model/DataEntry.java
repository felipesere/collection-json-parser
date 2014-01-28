package de.fesere.hypermedia.cj.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataEntry {

    @JsonProperty(value = "name")
    private final String name;

    @JsonProperty(value = "value")
    private String value;

    @JsonProperty(value = "prompt")
    private final String prompt;

    public DataEntry(String name, String value) {
        this(name, value, null);
    }

    public DataEntry(String name) {
        this(name, null);
    }

    @JsonCreator
    public DataEntry(@JsonProperty("name") String name, @JsonProperty("value") String value, @JsonProperty("prompt") String prompt) {
        this.name = name;
        this.value = value;
        this.prompt = prompt;
    }

    public String getName() {
        return name;
    }


    public String getValue() {
        return value;
    }

    public String getPrompt() {
        return prompt;
    }

    public void set(String value) {
        this.value = value;
    }

    public void clear() {
        this.value = "";
    }

    public String buildQueryRepresentation() {

        if (value != null && value.length() > 0) {
            return name + "=" + value;
        }
        return "";
    }
}
