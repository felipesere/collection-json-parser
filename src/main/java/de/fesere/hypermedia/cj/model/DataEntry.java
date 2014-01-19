package de.fesere.hypermedia.cj.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataEntry {
    private final String name;
    private String value;
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


    @JsonProperty(value = "name")
    public String getName() {
        return name;
    }

    @JsonProperty(value = "value")
    public String getValue() {
        return value;
    }

    @JsonProperty(value = "prompt")
    public String getPrompt() {
        return prompt;
    }

    public void set(String s) {
        this.value = s;
    }

    @JsonIgnore
    public String getQueryRepresentation() {

        if(value != null && value.length() > 0) {
            return name + "=" + value;
        }
        return "";
    }
}
