package de.fesere.hypermedia.cj.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.fesere.hypermedia.cj.exceptions.SerializationException;
import de.fesere.hypermedia.cj.model.Collection;
import de.fesere.hypermedia.cj.model.Template;

import java.io.IOException;

public class Serializer {

    private final ObjectMapper mapper;

    public Serializer() {
        this(new ObjectMapperConfig().getConfiguredObjectMapper());
    }

    protected Serializer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> T deserialize(String input, Class<T> responseClass) {
        if(responseClass.equals(Collection.class)){
            return (T) deserializeCollection(input);
        }

        if(responseClass.equals(Template.class)){
            return (T) deseriliazeTemplate(input);
        }

        try {
            return mapper.readValue(input, responseClass);
        } catch (IOException e) {
            throw new SerializationException("Could not deseriliaze input to " + responseClass.getName(), e);
        }
    }

    public String serialize(Object obj) {

        if(obj instanceof Collection ) {
            return serialize((Collection) obj);
        }
        if(obj instanceof Template) {
            return serialize((Template) obj);
        }

        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Could not serilize " + obj.getClass().getName() + " to string", e);
        }
    }

    private Template deseriliazeTemplate(String givenJson) {
        return (Template) deserialize(givenJson, Wrapper.class).getElement();
    }

    private Collection deserializeCollection(String giveJson) {
        return (Collection) deserialize(giveJson, Wrapper.class).getElement();
    }

    private String serialize(Collection collection) {
        return serialize(new Wrapper<>(collection));
    }

    private String serialize(Template template) {
        return serialize(new Wrapper<>(template));
    }
}