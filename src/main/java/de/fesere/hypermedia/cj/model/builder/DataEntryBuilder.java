package de.fesere.hypermedia.cj.model.builder;

import de.fesere.hypermedia.cj.model.DataEntry;

public class DataEntryBuilder {

    private String name;
    private String prompt = null;
    private boolean writeNullValue = false;

    private Object value;

    public DataEntryBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public DataEntryBuilder setValue(Object value) {
        this.value = value;
        return this;
    }

    public DataEntryBuilder setNullValue() {
        writeNullValue = true;
        return this;
    }

    public DataEntryBuilder setPrompt(String prompt) {
        this.prompt = prompt;
        return this;
    }

    public DataEntry build() {
        if(writeNullValue) {
            return DataEntryFactory.createNull(name, prompt);
        }

        if(value == null) {
            return DataEntryFactory.create(name, "", prompt);
        }

        return DataEntryFactory.create(name, value, prompt);

    }
}
