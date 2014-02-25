package de.fesere.hypermedia.cj.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.fesere.hypermedia.cj.model.DataEntry;
import de.fesere.hypermedia.cj.model.builder.DataEntryBuilder;

import java.io.IOException;

import static com.fasterxml.jackson.core.JsonToken.*;

public class DataEntryDeserializer extends StdDeserializer<DataEntry> {

    public DataEntryDeserializer() {
        super(DataEntry.class);
    }

    @Override
    public DataEntry deserialize(JsonParser jParser, DeserializationContext ctxt) throws IOException {
        DataEntryBuilder builder = new DataEntryBuilder();

        while (jParser.nextToken() != END_OBJECT) {
            String fieldname = jParser.getCurrentName();
            if ("name".equals(fieldname)) {
                parseName(jParser, builder);
            }

            if ("value".equals(fieldname)) {
                parseValue(jParser, builder);
            }

            if ("prompt".equals(fieldname)) {
                parsePrompt(jParser, builder);
            }
        }
        return builder.build();
    }

    private void parsePrompt(JsonParser jParser, DataEntryBuilder builder) throws IOException {
        jParser.nextToken();
        builder.setPrompt(jParser.getText());
    }

    private void parseValue(JsonParser jParser, DataEntryBuilder builder) throws IOException {
        JsonToken token = jParser.nextToken();
        if(token.equals(VALUE_TRUE) || token.equals(VALUE_FALSE)) {
            builder.setValue(token.equals(VALUE_TRUE));
        } else if (token.isNumeric()) {
            builder.setValue(jParser.getNumberValue());
        } else if(token.equals(VALUE_NULL)) {
            builder.setNullValue();
        } else {
            builder.setValue(jParser.getText());
        }
    }

    private void parseName(JsonParser jParser, DataEntryBuilder builder) throws IOException {
        jParser.nextToken();
        builder.setName(jParser.getText());
    }
}
