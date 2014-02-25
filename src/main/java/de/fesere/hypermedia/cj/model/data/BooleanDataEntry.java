package de.fesere.hypermedia.cj.model.data;

public class BooleanDataEntry extends BaseDataEntry<Boolean> {
    private Boolean value;

    public BooleanDataEntry(String name, boolean booleanValue, String prompt) {
        super(name, prompt);
        this.value = booleanValue;
    }

    @Override
    public Boolean getValue() {
        return value;
    }
}