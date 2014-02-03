package de.fesere.hypermedia.cj.model.builder;

import de.fesere.hypermedia.cj.model.data.BooleanDataEntry;
import de.fesere.hypermedia.cj.model.data.DataEntry;
import de.fesere.hypermedia.cj.model.data.NumberDataEntry;
import de.fesere.hypermedia.cj.model.data.StringDataEntry;
import org.apache.commons.lang3.StringUtils;

public class DataEntryBuilder {

    private String name;
    private String stringValue = null;
    private Number numberValue = null;
    private String prompt = null;
    private Boolean booleanValue = null;


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
    public DataEntryBuilder setValue(boolean value) {
        booleanValue = value;
        return this;
    }

    public DataEntryBuilder setPrompt(String prompt) {
        this.prompt = prompt;
        return this;
    }

    public DataEntry build() {
        if(StringUtils.isNotBlank(stringValue)) {
            return new StringDataEntry(name, stringValue, prompt);
        }
        else if (numberValue != null) {
            return new NumberDataEntry(name, numberValue, prompt);
        }
        else if (booleanValue != null) {
            return new BooleanDataEntry(name, booleanValue, prompt);
        }
        else {
            return new StringDataEntry(name, "", prompt);
        }

    }
}
