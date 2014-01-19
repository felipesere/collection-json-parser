package de.fesere.hypermedia.cj.model.serialization;


import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;


import java.io.IOException;



public class WrapperSerializer extends StdSerializer<Wrapper> {

    protected WrapperSerializer() {
        super(Wrapper.class);
    }

    @Override
    public void serialize(Wrapper value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        String name = value.element.getClass().getAnnotation(JsonTypeName.class).value();
        jgen.writeFieldName(name);

        provider.defaultSerializeValue(value.element, jgen);
        jgen.writeEndObject();

    }
}
