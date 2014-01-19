package de.fesere.hypermedia.cj.model.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperConfig {

    public ObjectMapper getConfiguredObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new WrapperModule());

        return mapper;
    }
}
