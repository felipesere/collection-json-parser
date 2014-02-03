package de.fesere.hypermedia.cj.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NumberDataEntry extends DataEntry<Number> {

    @JsonProperty("value")
    private Number value;

    @JsonCreator
    public NumberDataEntry(@JsonProperty("name") String name, @JsonProperty("value") Number value, @JsonProperty("prompt") String prompt) {
        super(name,  prompt);
        this.value = value;
    }

    public void set(Number value) {
        this.value = value;
    }

    public void clear() {
        value = null;
    }

    public Number getValue() {
        return value;
    }


    public String buildQueryRepresentation() {

        if (value != null) {
            return getName() + "=" + value;
        }
        return "";
    }
}
