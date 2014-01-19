package de.fesere.hypermedia.cj.model.serialization;

import com.fasterxml.jackson.databind.module.SimpleModule;

public class WrapperModule extends SimpleModule {
    public WrapperModule() {
        addSerializer(Wrapper.class, new WrapperSerializer());
        addDeserializer(Wrapper.class, new WrapperDeserializer());
    }
}
