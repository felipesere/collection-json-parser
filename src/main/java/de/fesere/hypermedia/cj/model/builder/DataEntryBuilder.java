package de.fesere.hypermedia.cj.model.builder;

import de.fesere.hypermedia.cj.model.DataEntry;
import de.fesere.hypermedia.cj.model.NumberDataEntry;
import de.fesere.hypermedia.cj.model.StringDataEntry;
import org.apache.commons.lang3.StringUtils;

public class DataEntryBuilder {

    private String name;
    private String stringValue = null;
    private Number numberValue = null;
    private String prompt = null;


    public DataEntryBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public DataEntryBuilder setValue(String value) {
        stringValue = value;
        return this;
    }

    public DataEntryBuilder setValue(Number value) {
        numberValue = value;
        return this;
    }

    public DataEntryBuilder setPrompt(String prompt) {
        this.prompt = prompt;
        return this;
    }

    public DataEntry build() {
        if(StringUtils.isBlank(name)) {
            throw new IllegalStateException("Trying to build a nameless DataEntry");
        }

        if(StringUtils.isNotBlank(stringValue)) {
            return new StringDataEntry(name, stringValue, prompt);
        }
        else if (numberValue != null) {
            return new NumberDataEntry(name, numberValue, prompt);
        }
        else {
            return new StringDataEntry(name, "", prompt);
        }

    }
}
