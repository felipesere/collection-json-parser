package de.fesere.hypermedia.cj.model.data;

public class StringDataEntry extends DataEntry<String> {

    private String value;

    public StringDataEntry(String name) {
        this(name, "");
    }

    public StringDataEntry(String name, String value) {
        super(name);
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
