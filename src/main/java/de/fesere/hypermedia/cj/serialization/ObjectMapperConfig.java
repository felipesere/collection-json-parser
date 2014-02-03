package de.fesere.hypermedia.cj.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.fesere.hypermedia.cj.model.data.DataEntry;

public class ObjectMapperConfig {

    public ObjectMapper getConfiguredObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new WrapperModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        return mapper;
    }

    public static class WrapperModule extends SimpleModule {
        public WrapperModule() {
            addSerializer(Wrapper.class, new WrapperSerializer());
            addDeserializer(Wrapper.class, new WrapperDeserializer());
            addSerializer(DataEntry.class, new DataEntrySerializer());
            addDeserializer(DataEntry.class, new DataEntryDeserializer());
        }
    }
}
