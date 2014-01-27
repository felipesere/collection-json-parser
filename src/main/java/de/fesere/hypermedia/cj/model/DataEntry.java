package de.fesere.hypermedia.cj.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    public String buildQueryRepresentation() {

        if (value != null && value.length() > 0) {
            return name + "=" + value;
        }
        return "";
    }

    @Override
    public boolean equals(Object obj) {
        if(EqualsUtils.isSameRef(this, obj)) {
            return true;
        }
        if (EqualsUtils.isSameTypeNotNull(this, obj)) {
            final DataEntry otherObject = (DataEntry) obj;
            return new EqualsBuilder()
                    .append(name, otherObject.name)
                    .append(value, otherObject.value)
                    .append(prompt, otherObject.prompt)
                    .isEquals();
        }
        else {
            return false;
        }

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(value).append(prompt).toHashCode();
    }
}
