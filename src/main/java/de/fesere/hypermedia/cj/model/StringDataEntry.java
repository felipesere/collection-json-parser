package de.fesere.hypermedia.cj.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

public class StringDataEntry extends DataEntry<String> {

    @JsonProperty("value")
    private String value;

    public StringDataEntry(String name) {
        super(name);
    }

    public StringDataEntry(String name, String value) {
        this(name);
        this.value = value;
    }

    @JsonCreator
    public StringDataEntry(@JsonProperty("name") String name, @JsonProperty("value") String value, @JsonProperty("prompt") String prompt) {
        super(name, value,  prompt);
        this.value = value;
    }

    public void set(String value) {
        this.value = value;
    }

    public void clear() {
        value = null;
    }

    @Override
    public String getValue() {
        return value;
    }

    public String buildQueryRepresentation() {
        if (StringUtils.isNotBlank(value)) {
            return getName() + "=" + value;
        }
        return "";
    }
}
