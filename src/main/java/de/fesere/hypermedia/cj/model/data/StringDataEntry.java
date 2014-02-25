package de.fesere.hypermedia.cj.model.data;

public class StringDataEntry extends BaseDataEntry<String> {

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
    public String getValue() {
        return value;
    }
}
