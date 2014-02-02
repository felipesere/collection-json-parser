package de.fesere.hypermedia.cj.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.fesere.hypermedia.cj.exceptions.SerializationException;
import de.fesere.hypermedia.cj.model.Collection;
import de.fesere.hypermedia.cj.model.Template;

import java.io.IOException;

public class WrapperDeserializer extends StdDeserializer<Wrapper> {

    protected WrapperDeserializer() {
        super(Wrapper.class);
    }

    @Override
    public Wrapper deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        jsonParser.nextToken();
        String name = jsonParser.getCurrentName();

        return deserializeInner(jsonParser, name);
    }

    private Wrapper deserializeInner(JsonParser jsonParser, String name) throws IOException {
        jsonParser.nextToken();
        switch (name) {
            case "collection" :
                return deserializeInnerCollection(jsonParser);
            case "template" :
                return deserializeInnerTemplate(jsonParser);
            default: throw new SerializationException("Was expecting either a collection or a template!");
        }
    }

    private Wrapper<Template> deserializeInnerTemplate(JsonParser jsonParser) throws IOException {
        Template template = jsonParser.readValueAs(Template.class);
        return new Wrapper<>(template);
    }

    private Wrapper<Collection> deserializeInnerCollection(JsonParser jsonParser) throws IOException {
        Collection collection = jsonParser.readValueAs(Collection.class);
        return new Wrapper<>(collection);
    }
}
