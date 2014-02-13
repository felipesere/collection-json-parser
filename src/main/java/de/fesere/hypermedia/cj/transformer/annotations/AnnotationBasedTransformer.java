package de.fesere.hypermedia.cj.transformer.annotations;

import de.fesere.hypermedia.cj.exceptions.TransformationException;
import de.fesere.hypermedia.cj.model.Item;
import de.fesere.hypermedia.cj.model.builder.DataEntryFactory;
import de.fesere.hypermedia.cj.model.builder.ItemBuilder;
import de.fesere.hypermedia.cj.model.data.DataEntry;
import de.fesere.hypermedia.cj.transformer.WriteTransformer;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.*;

public class AnnotationBasedTransformer implements WriteTransformer {

    FieldExtractor extractor = new FieldExtractor();

    @Override
    public Item transform(Object input) {
        ItemBuilder builder = new ItemBuilder(URI.create("http://bla.blu"));

        List<Field> declaredFields = extractor.annotatedFieldsFromType(input.getClass(), Data.class);

        for (Field field : declaredFields) {
            Data data = field.getAnnotation(Data.class);
            DataEntry entry = constructDataEntry(input, field, data);
            builder.addData(entry);
        }
        return builder.build();
    }

    private DataEntry constructDataEntry(Object input, Field field, Data data) {
        String name = data.name();
        String prompt = getPrompt(data);

        Class fieldValueType = field.getType();
        Object value = getValue(input, field);

        if(value == null) {
            return constructNullDataEntry(name, prompt, input.getClass());
        }
        else {
            return constructValueDataEntry(name, prompt, fieldValueType, value);
        }
    }

    private DataEntry constructNullDataEntry(String name, String prompt, Class type) {
        NullWriteStrategy nullStrategy = extractNullWriteStrategy(type);
        switch(nullStrategy) {
            case AS_NULL:  return DataEntryFactory.createNull(name, prompt);
            case AS_EMPTY: return DataEntryFactory.createEmpty(name, prompt);
            case IGNORE:   return DataEntryFactory.createNone();
            default: throw new TransformationException("Unrecognized NullWriteStrategy " + nullStrategy);
        }
    }

    private NullWriteStrategy extractNullWriteStrategy(Class<?> type) {
        ItemConfig config = type.getAnnotation(ItemConfig.class);
        if(config == null) {
            return NullWriteStrategy.defautlNullWrite();
        }
        return config.writeNull();
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

    private Object getValue(Object input, Field field) {
        try {
            field.setAccessible(true);
            return field.get(input);
        } catch (IllegalAccessException e) {
            throw new TransformationException("Was not able to access " + field.getName() + " from " + input, e);
        }
    }


    private String getPrompt(Data data) {
        String prompt = data.prompt();
        if (prompt.equalsIgnoreCase("none")) {
            return null;
        }

        return prompt;
    }
}
