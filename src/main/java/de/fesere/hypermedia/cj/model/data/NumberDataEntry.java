package de.fesere.hypermedia.cj.model.data;

public class NumberDataEntry extends DataEntry<Number> {

    private Number value;

    public NumberDataEntry(String name, Number value, String prompt) {
        super(name,  prompt);
        this.value = value;
    }

    @Override
    public void set(Number value) {
        this.value = value;
    }

    @Override
    public void clear() {
        value = null;
    }

    @Override
    public Number getValue() {
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
