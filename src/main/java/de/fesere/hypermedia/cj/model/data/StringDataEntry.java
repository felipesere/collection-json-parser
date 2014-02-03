package de.fesere.hypermedia.cj.model.data;

public class StringDataEntry extends DataEntry<String> {

    private String value;

    public StringDataEntry(String name) {
        super(name);
    }

    public StringDataEntry(String name, String value) {
        this(name);
        this.value = value;
    }

    public StringDataEntry(String name, String value, String prompt) {
        super(name, prompt);
        this.value = value;
    }

    @Override
    public void set(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
