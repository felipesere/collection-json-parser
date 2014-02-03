package de.fesere.hypermedia.cj.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.fesere.hypermedia.cj.model.DataEntry;
import de.fesere.hypermedia.cj.model.NumberDataEntry;
import de.fesere.hypermedia.cj.model.StringDataEntry;

import java.io.IOException;

public class DataEntryDeserializer extends StdDeserializer<DataEntry> {

    public DataEntryDeserializer(){
        super(DataEntry.class);
    }

    @Override
    public DataEntry deserialize(JsonParser jParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        DataEntry entry = null;
        String entryName = "";
        String prompt = null;

        while (jParser.nextToken() != JsonToken.END_OBJECT ) {

            String fieldname = jParser.getCurrentName();
            if ("name".equals(fieldname)) {

                // current token is "name",
                // move to next, which is "name"'s value
                jParser.nextToken();
                entryName = jParser.getText();

            }

            if ("value".equals(fieldname)) {

                // current token is "age",
                // move to next, which is "name"'s value
                JsonToken token = jParser.nextToken();
                if(token.isNumeric()) {
                    entry = new NumberDataEntry(entryName, jParser.getNumberValue(), prompt);
                }
                else {
                    entry = new StringDataEntry(entryName, jParser.getText(), prompt);
                }
            }

            if("prompt".equals(fieldname) && entry != null) {
                jParser.nextToken();
                entry.setPrompt(jParser.getText());

            }
        }

        return entry;
    }
}
