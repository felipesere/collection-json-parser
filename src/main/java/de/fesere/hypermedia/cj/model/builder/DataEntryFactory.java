package de.fesere.hypermedia.cj.model.builder;

import de.fesere.hypermedia.cj.exceptions.MalformedDataValueException;
import de.fesere.hypermedia.cj.exceptions.SerializationException;
import de.fesere.hypermedia.cj.model.DataEntry;
import de.fesere.hypermedia.cj.model.data.*;
import org.apache.commons.lang3.StringUtils;

public final class DataEntryFactory {

    public static DataEntry create(String name, Object value, String prompt) {

        if(StringUtils.isBlank(name)){
            return createNone();
        }

        if(value == null) {
           throw new MalformedDataValueException("value of '"+name+"' can not be null");
        }

        Class type = value.getClass();

        if (TypeUtils.isString(type)) {
            return new StringDataEntry(name, (String) value, prompt);
        } else if (TypeUtils.isNumber(type)) {
            return new NumberDataEntry(name, (Number) value, prompt);
        } else if (TypeUtils.isBoolean(type)) {
            return new BooleanDataEntry(name, (Boolean) value, prompt);
        } else {
           throw new SerializationException("'"+name+"' of type " + type + " can not be converted to DataEntry");
        }
    }

    public static DataEntry create(String name, Object value) {
        return create(name, value, null);
    }

    private static DataEntry createNone() {
        return new NoneDataEntry("none");
    }

    public static DataEntry createNull(String name, String prompt) {
        return new NullDataEntry(name,prompt);
    }

    public static DataEntry createNull(String name) {
        return createNull(name, null);
    }

    public static DataEntry createEmpty(String name, String prompt) {
        return new StringDataEntry(name, "", prompt);
    }

    public static DataEntry createEmpty(String name) {
        return new StringDataEntry(name);
    }
}
