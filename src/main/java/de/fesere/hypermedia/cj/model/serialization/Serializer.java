package de.fesere.hypermedia.cj.model.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Serializer {

    private final ObjectMapper mapper;

    public Serializer() {
        mapper = (new ObjectMapperConfig()).getConfiguredObjectMapper();

    }

    public <T> T deserialize(String input, Class<T> responseClass) {

        try {
            return mapper.readValue(input, responseClass);
        } catch (IOException e) {
            throw new RuntimeException("Could not deseriliaze input to " + responseClass.getName(),e);
        }
    }

    public String serialize(Object obj) {

        try {
            return mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException("Could not serilize " + obj.getClass().getName() + " to string",e );
        }
    }
}
