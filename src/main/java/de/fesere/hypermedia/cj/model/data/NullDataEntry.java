package de.fesere.hypermedia.cj.model.data;

public class NullDataEntry extends DataEntry {
    public NullDataEntry(String name, String prompt) {
        super(name, prompt);
    }

    @Override
    public Object getValue() {
        return null;
    }
}
