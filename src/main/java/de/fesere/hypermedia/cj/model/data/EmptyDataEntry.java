package de.fesere.hypermedia.cj.model.data;

public class EmptyDataEntry extends DataEntry {

    public EmptyDataEntry(String name) {
        super(name);
    }

    @Override
    public void set(Object value) {
    }

    @Override
    public Object getValue() {
        return "";
    }
}
