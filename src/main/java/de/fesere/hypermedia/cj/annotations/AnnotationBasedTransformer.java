package de.fesere.hypermedia.cj.annotations;

import de.fesere.hypermedia.cj.exceptions.TransformationException;
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
        String name = data.name();
        String prompt = getPrompt(data);

        Class type = field.getType();
        Object value = getValue(input, field);

        if(value == null) {
            return constructNullDataEntry(input, name, prompt);
        }
        else {
            return constructValueDataEntry(name, prompt, type, value);
        }
    }

    private DataEntry constructValueDataEntry(String name, String prompt, Class type, Object value) {
        if (TypeUtils.isString(type)) {
           return DataEntryFactory.create(name, (String) value, prompt);
        } else if (TypeUtils.isNumber(type)) {
            return DataEntryFactory.create(name, (Number) value, prompt);
        } else if (TypeUtils.isBoolean(type)) {
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
            default: throw new TransformationException("Unrecognized NullWriteStrategy " + nullStrategy);
        }
    }

    private Object getValue(Object input, Field field) {
        try {
            field.setAccessible(true);
            return field.get(input);
        } catch (IllegalAccessException e) {
            throw new TransformationException("Was not able to access " + field.getName() + " from " + input, e);
        }
    }


    private String getPrompt(Data data) {
        String promot = data.prompt();
        if (promot.equalsIgnoreCase("none")) {
            return null;
        }

        return promot;
    }
}
