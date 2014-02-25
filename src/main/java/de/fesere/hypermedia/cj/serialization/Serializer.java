package de.fesere.hypermedia.cj.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.fesere.hypermedia.cj.exceptions.SerializationException;

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
        if(Wrapped.class.isAssignableFrom(responseClass)) {
            Object deserializedObject = deserializeWrapped(input);

            if(deserializedObject.getClass().isAssignableFrom(responseClass)) {
                return responseClass.cast(deserializedObject);
            }
            else {
                throw new SerializationException("input could not be serialized to " + responseClass.getCanonicalName());
            }
        }

        return defaultDeserialisation(input, responseClass);
    }

    private Object deserializeWrapped(String input) {
        return defaultDeserialisation(input, Wrapper.class).getElement();
    }


    public String serialize(Object obj) {
        if(obj instanceof Wrapped) {
           return serialize((Wrapped) obj);
        }

        return defaultSerialization(obj);
    }

    private String defaultSerialization(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Could not serilize " + obj.getClass().getName() + " to string", e);
        }
    }

    private String serialize(Wrapped wrapped) {
        return defaultSerialization(new Wrapper<>(wrapped));
    }

    private <T> T defaultDeserialisation(String input, Class<T> responseClass) {
        try {
            return mapper.readValue(input, responseClass);
        } catch (IOException e) {
            throw new SerializationException("Could not deseriliaze input to " + responseClass.getName(), e);
        }
    }
}