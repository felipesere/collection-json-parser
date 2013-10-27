package de.fesere.hypermedia;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

public class DataEntry {
    private final String name;
    private String value;
    private final String prompt;

    public DataEntry(String name, String value) {
        this(name, value, null);
    }

    @JsonCreator
    public DataEntry(@JsonProperty("name") String name, @JsonProperty("value") String value, @JsonProperty("prompt") String prompt) {
        this.name = name;
        this.value = value;
        this.prompt = prompt;
    }


    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public String getValue() {
        return value;
    }

    @JsonProperty
    public String getPrompt() {
        return prompt;
    }

    public String toString() {
        return "DataEntry [name="+name + ", value="+value+"]";
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
