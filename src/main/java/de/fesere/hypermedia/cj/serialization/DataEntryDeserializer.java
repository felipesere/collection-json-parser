package de.fesere.hypermedia.cj.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.fesere.hypermedia.cj.model.DataEntry;
import de.fesere.hypermedia.cj.model.builder.DataEntryBuilder;

import java.io.IOException;

public class DataEntryDeserializer extends StdDeserializer<DataEntry> {

    public DataEntryDeserializer() {
        super(DataEntry.class);
    }

    @Override
    public DataEntry deserialize(JsonParser jParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        DataEntryBuilder builder = new DataEntryBuilder();

        while (jParser.nextToken() != JsonToken.END_OBJECT) {

            String fieldname = jParser.getCurrentName();
            if ("name".equals(fieldname)) {
                jParser.nextToken();
                builder.setName(jParser.getText());
            }

            if ("value".equals(fieldname)) {
                JsonToken token = jParser.nextToken();
                if (token.isNumeric()) {
                    builder.setValue(jParser.getNumberValue());
                } else {
                    builder.setValue(jParser.getText());
                }
            }

            if ("prompt".equals(fieldname)) {
                jParser.nextToken();
                builder.setPrompt(jParser.getText());

            }
        }

        return builder.build();
    }
}
