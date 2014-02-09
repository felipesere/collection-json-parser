package de.fesere.hypermedia.cj.annotations;

import de.fesere.hypermedia.cj.model.Item;
import de.fesere.hypermedia.cj.model.builder.DataEntryFactory;
import de.fesere.hypermedia.cj.model.builder.ItemBuilder;
import de.fesere.hypermedia.cj.model.data.DataEntry;
import de.fesere.hypermedia.cj.transformer.WriteTransformer;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AnnotationBasedTransformer implements WriteTransformer {
    @Override
    public Item transform(Object input) {
        ItemBuilder builder = new ItemBuilder(URI.create("http://bla.blu"));

        Field[] declaredFields = getAnnotatedFields(input);

        for (Field field : declaredFields) {
            Data data = field.getAnnotation(Data.class);
            DataEntry entry = constructDataEntry(input, field, data);
            builder.addData(entry);
        }
        return builder.build();
    }

    private Field[] getAnnotatedFields(Object input) {
        List<Field> fields = getAnnotatedFielsOfType(input.getClass());

        return fields.toArray(new Field[fields.size()]);
    }


    private List<Field> getAnnotatedFielsOfType(Class type) {
        if(type.getName().equalsIgnoreCase(Object.class.getName())) {
            return Collections.emptyList();
        }
        Field [] fields =  type.getDeclaredFields();
        List<Field> result = new LinkedList<>();
        for(Field field : fields) {
            Data data = field.getAnnotation(Data.class);
            if (data != null) {
                result.add(field);
            }
        }

        result.addAll(getAnnotatedFielsOfType(type.getSuperclass()));

        return result;
    }

    private NullWriteStrategy extractNullWriteStrategy(Object input) {
        ItemConfig config = input.getClass().getAnnotation(ItemConfig.class);
        if(config == null) {
            return NullWriteStrategy.defautlNullWrite();
        }
        return config.writeNull();
    }

    private DataEntry constructDataEntry(Object input, Field field, Data data) {
        String name = data.value();
        String prompt = getPrompt(data);

        Class type = field.getType();
        Object value = getValue(input, field);

        if(value == null) {
            return constructNullDataEntry(input, name, prompt);
        }

        return constructValueDataEntry(name, prompt, type, value);
    }

    private DataEntry constructValueDataEntry(String name, String prompt, Class type, Object value) {
        if (isString(type)) {
           return DataEntryFactory.create(name, (String) value, prompt);
        } else if (isNumber(type)) {
            return DataEntryFactory.create(name, (Number) value, prompt);
        } else if (isBoolean(type)) {
            return DataEntryFactory.create(name, (Boolean) value, prompt);
        } else {
            return DataEntryFactory.createNone();
        }
    }

    private DataEntry constructNullDataEntry(Object input, String name, String prompt) {
        NullWriteStrategy nullStrategy = extractNullWriteStrategy(input);
        switch(nullStrategy) {
            case AS_NULL:  return DataEntryFactory.createNull(name, prompt);
            case AS_EMPTY: return DataEntryFactory.createEmpty(name, prompt);
            case IGNORE:   return DataEntryFactory.createNone();
            default: throw new RuntimeException("Unrecognized NullWriteStrategy " + nullStrategy);
        }
    }

    private Object getValue(Object input, Field field) {
        try {
            field.setAccessible(true);
            return field.get(input);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Was not able to access " + field.getName() + " from " + input);
        }
    }


    private boolean isBoolean(Class type) {
        return Boolean.class.isAssignableFrom(type) || Boolean.TYPE.isAssignableFrom(type);
    }

    private boolean isNumber(Class type) {
        return Integer.class.isAssignableFrom(type) ||
               Integer.TYPE.isAssignableFrom(type)  ||
               Long.class.isAssignableFrom(type)   ||
               Long.TYPE.isAssignableFrom(type)    ||
               Float.class.isAssignableFrom(type)  ||
               Float.TYPE.isAssignableFrom(type)   ||
               Double.class.isAssignableFrom(type) ||
               Double.TYPE.isAssignableFrom(type);


    }

    private boolean isString(Class type) {
        return String.class.isAssignableFrom(type);
    }

    private String getPrompt(Data data) {
        String promot = data.prompt();
        if (promot.equalsIgnoreCase("none")) {
            return null;
        }

        return promot;
    }
}
