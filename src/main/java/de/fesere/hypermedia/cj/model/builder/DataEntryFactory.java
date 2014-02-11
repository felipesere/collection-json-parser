package de.fesere.hypermedia.cj.model.builder;

import de.fesere.hypermedia.cj.model.data.*;

public final class DataEntryFactory {

    private DataEntryFactory() {}

    public static DataEntry createNone() {
        return new NoneDataEntry("none");
    }

    public static DataEntry createNull(String name, String prompt) {
        return new NullDataEntry(name,prompt);
    }

    public static DataEntry createEmpty(String name, String prompt) {
        return new StringDataEntry(name, "", prompt);
    }

    public static DataEntry create(String name) {
        return new StringDataEntry(name);
    }

    public static DataEntry create(String name, String value) {
        return new StringDataEntry(name, value);
    }

    public static DataEntry create(String name, String value, String prompt) {
        return new StringDataEntry(name,value,prompt);
    }

    public static DataEntry create(String name, Number number) {
        return create(name, number, null);
    }

    public static DataEntry create(String name, Number number, String prompt ) {
        return new NumberDataEntry(name, number, prompt);
    }

    public static DataEntry create(String name, boolean value) {
        return create(name, value, null);
    }

    public static DataEntry create(String name, boolean value, String prompt) {
        return new BooleanDataEntry(name, value, prompt);
    }
}
