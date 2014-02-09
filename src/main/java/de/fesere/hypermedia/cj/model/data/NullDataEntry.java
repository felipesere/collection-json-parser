package de.fesere.hypermedia.cj.model.data;

public class NullDataEntry extends DataEntry {
    public NullDataEntry(String name) {
        super(name);
    }

    public NullDataEntry(String name, String prompt) {
        super(name, prompt);
    }

    @Override
    public void set(Object value) {
    }

    @Override
    public Object getValue() {
        return null;
    }
}
