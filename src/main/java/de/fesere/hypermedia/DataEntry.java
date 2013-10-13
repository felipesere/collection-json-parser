package de.fesere.hypermedia;

public class DataEntry {
    private final String name;
    private final String value;

    public DataEntry(String name, String value) {
        this.name = name;
        this.value = value;
    }


    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
