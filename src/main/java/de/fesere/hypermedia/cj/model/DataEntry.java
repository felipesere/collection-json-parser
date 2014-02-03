package de.fesere.hypermedia.cj.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.fesere.hypermedia.cj.serialization.DataEntryDeserializer;
import de.fesere.hypermedia.cj.serialization.DataEntrySerializer;

@JsonSerialize(using = DataEntrySerializer.class)
@JsonDeserialize(using = DataEntryDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
abstract public class DataEntry<T> {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("prompt")
    private String prompt;

    public DataEntry(String name) {
        this(name, null);
    }

    @JsonCreator
    public DataEntry(@JsonProperty("name") String name,  @JsonProperty("prompt") String prompt) {
        this.name = name;
        this.prompt = prompt;
    }

    public String getName() {
        return name;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

    public abstract void set(T value);

    public abstract void clear();

    public abstract T getValue();

    public abstract  String buildQueryRepresentation();
}
