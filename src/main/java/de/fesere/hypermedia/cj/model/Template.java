package de.fesere.hypermedia.cj.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import de.fesere.hypermedia.cj.model.data.DataEntry;
import de.fesere.hypermedia.cj.serialization.Wrapped;
import de.fesere.hypermedia.cj.transformer.DataEntryTransformer;
import de.fesere.hypermedia.cj.transformer.ReadTransformation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@JsonTypeName("template")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Template implements Wrapped {

    private final List<DataEntry> data;

    public Template(@JsonProperty("data") List<DataEntry> data) {
        this.data = data;
    }

    public List<DataEntry> getData() {
        return new LinkedList<>(data);
    }

    public void fill(Item item) {
        for(DataEntry entry : data) {
            String entryName = entry.getName();
            String value = item.getString(entryName);
            entry.set(value);
        }
    }

    public <T> T convert(ReadTransformation<T> transformation) {
        DataEntryTransformer<T> dataEntryTransformer = new DataEntryTransformer<>(transformation);
        return dataEntryTransformer.transform(data);
    }

    public Map<String, Object> getDataMap() {
        Map<String, Object> dataEntries = new HashMap<>();
        for(DataEntry entry : data) {
            dataEntries.put(entry.getName(), entry.getValue());
        }

        return dataEntries;
    }
}
