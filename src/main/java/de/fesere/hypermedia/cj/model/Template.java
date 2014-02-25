package de.fesere.hypermedia.cj.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import de.fesere.hypermedia.cj.model.builder.DataEntryFactory;
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

    private List<DataEntry> data;

    public Template(@JsonProperty("data") List<DataEntry> data) {
        this.data = data;
    }

    public List<DataEntry> getData() {
        return new LinkedList<>(data);
    }

    public void fill(Item item) {
        List<DataEntry> filledDataEntries = new LinkedList<>();

        for(DataEntry entry : data) {
            String name = entry.getName();
            String prompt = entry.getPrompt();
            Object value = item.getObject(name);

            DataEntry filled = DataEntryFactory.create(name, value, prompt);
            filledDataEntries.add(filled);
        }

        this.data = filledDataEntries;
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
