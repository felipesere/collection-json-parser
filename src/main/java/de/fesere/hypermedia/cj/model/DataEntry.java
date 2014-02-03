package de.fesere.hypermedia.cj.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
abstract public class DataEntry<T> {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("prompt")
    private final String prompt;

    @JsonProperty("value")
    private T value;

    public DataEntry(String name) {
        this(name, null, null);
    }

    @JsonCreator
    public DataEntry(@JsonProperty("name") String name, @JsonProperty("value") T value,  @JsonProperty("prompt") String prompt) {
        this.name = name;
        this.prompt = prompt;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getPrompt() {
        return prompt;
    }

    public abstract void set(T value);

    public abstract void clear();

    public T getValue() {
        return value;
    }

    public abstract  String buildQueryRepresentation();
}
