package de.fesere.hypermedia.cj.model.data;

public class BooleanDataEntry extends DataEntry<Boolean> {
    private Boolean value;

    public BooleanDataEntry(String name, boolean booleanValue, String prompt) {
        super(name, prompt);
        this.value = booleanValue;
    }

    @Override
    public void set(Boolean value) {
        if (value == null) {
            throw new IllegalArgumentException("Can not set null for BooleanDataEntry '" + getName() + "'");
        }
        this.value = value;
    }

    @Override
    public void clear() {
        value = null;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public String buildQueryRepresentation() {

        if (value != null) {
            return getName() + "=" + value;
        }
        return "";
    }
}