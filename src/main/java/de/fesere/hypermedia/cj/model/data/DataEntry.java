package de.fesere.hypermedia.cj.model.data;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.fesere.hypermedia.cj.serialization.DataEntryDeserializer;
import de.fesere.hypermedia.cj.serialization.DataEntrySerializer;
import org.apache.commons.lang3.StringUtils;

@JsonSerialize(using = DataEntrySerializer.class)
@JsonDeserialize(using = DataEntryDeserializer.class)
public abstract class DataEntry<T> {

    private final String name;
    private String prompt;

    public DataEntry(String name) {
        this(name, null);
    }

    public DataEntry(String name, String prompt) {
        this.name = name;
        this.prompt = prompt;
    }

    public String getName() {
        return name;
    }

    public String getPrompt() {
        return prompt;
    }

    public T getValue() {
        return null;
    }

    public String buildQueryRepresentation() {
        T value = getValue();

        if (value != null && StringUtils.isNotBlank(value.toString())) {
            return getName() + "=" + value.toString();
        }
        return "";
    }
}
