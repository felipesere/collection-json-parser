package de.fesere.hypermedia.cj.model.builder;

import de.fesere.hypermedia.cj.exceptions.MalformedDataValueException;
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
            return DataEntryFactory.create(name, (String) value, prompt);
        } else if (TypeUtils.isNumber(type)) {
            return DataEntryFactory.create(name, (Number) value, prompt);
        } else if (TypeUtils.isBoolean(type)) {
            return DataEntryFactory.create(name, (Boolean) value, prompt);
        } else {
            return DataEntryFactory.create(name, value.toString(), prompt);
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

    private static DataEntry create(String name, String value, String prompt) {
        return new StringDataEntry(name,value,prompt);
    }

    private static DataEntry create(String name, Number number, String prompt ) {
        return new NumberDataEntry(name, number, prompt);
    }

    private static DataEntry create(String name, Boolean value, String prompt) {
        return new BooleanDataEntry(name, value, prompt);
    }
}
