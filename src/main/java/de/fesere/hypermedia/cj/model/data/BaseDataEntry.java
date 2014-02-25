package de.fesere.hypermedia.cj.model.data;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.fesere.hypermedia.cj.serialization.DataEntryDeserializer;
import de.fesere.hypermedia.cj.serialization.DataEntrySerializer;
import org.apache.commons.lang3.StringUtils;

@JsonSerialize(using = DataEntrySerializer.class)
@JsonDeserialize(using = DataEntryDeserializer.class)
public abstract class BaseDataEntry<T> implements de.fesere.hypermedia.cj.model.DataEntry{

    private final String name;
    private String prompt;

    public BaseDataEntry(String name) {
        this(name, null);
    }

    public BaseDataEntry(String name, String prompt) {
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

        if (hasRealValue(value)) {
            return getName() + "=" + value.toString();
        }
        return "";
    }

    private boolean hasRealValue(T value) {
        return value != null && StringUtils.isNotBlank(value.toString());
    }
}
