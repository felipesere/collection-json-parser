package de.fesere.hypermedia.cj.model.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.fesere.hypermedia.cj.model.Collection;
import de.fesere.hypermedia.cj.model.Template;

import java.io.IOException;

public class WrapperDeserializer extends StdDeserializer<Wrapper> {

    protected WrapperDeserializer() {
        super(Wrapper.class);
    }

    @Override
    public Wrapper deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        jsonParser.getCurrentToken();
        String name = jsonParser.getCurrentName();
        while(name == null || (!name.equalsIgnoreCase("collection") && !name.equalsIgnoreCase("template"))) {
            jsonParser.nextToken();
            name = jsonParser.getCurrentName();
        }

        return deserializeInner(jsonParser, name);
    }

    private Wrapper deserializeInner(JsonParser jsonParser, String name) throws IOException {
        switch (name) {
            case "collection" :
                return deserializeInnerCollection(jsonParser);
            case "template" :
                return deserializeInnerTemplate(jsonParser);
            default: throw new RuntimeException("foooo!");
        }
    }

    private Wrapper deserializeInnerTemplate(JsonParser jsonParser) throws IOException {
        jsonParser.nextToken();
        Template template = jsonParser.readValueAs(Template.class);
        return new Wrapper(template);
    }

    private Wrapper deserializeInnerCollection(JsonParser jsonParser) throws IOException {
        jsonParser.nextToken();
        Collection collection = jsonParser.readValueAs(Collection.class);
        return new Wrapper(collection);
    }
}
