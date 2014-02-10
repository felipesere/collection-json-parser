package de.fesere.hypermedia.cj.serialization;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.fesere.hypermedia.cj.model.data.DataEntry;
import de.fesere.hypermedia.cj.model.data.NoneDataEntry;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class DataEntrySerializer extends StdSerializer<DataEntry> {

    private JsonGenerator jgen;

    protected DataEntrySerializer() {
        super(DataEntry.class);
    }

    @Override
    public void serialize(DataEntry entry, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        if (entry instanceof NoneDataEntry) {
            return;
        }
        this.jgen = jgen;

        jgen.writeStartObject();

        writeName(entry.getName());
        writeValue(entry.getValue());
        writePrompt(entry.getPrompt());

        jgen.writeEndObject();

    }

    private void writeName(String name) throws IOException {
        jgen.writeStringField("name", name);
    }

    private void writeValue(Object value) throws IOException {
        jgen.writeFieldName("value");
        writeContent(value);
    }


    private void writeContent(Object value) throws IOException {
        if (value instanceof String) {
            jgen.writeString((String) value);
        } else if (value instanceof Number) {
            Number number = (Number) value;
            writeNumber(number);
        } else if (value instanceof Boolean) {
            jgen.writeBoolean((boolean) value);
        } else if (value == null) {
            jgen.writeNull();
        }
    }

    private void writeNumber(Number number) throws IOException {
        if (number instanceof Double) {
            jgen.writeNumber(number.doubleValue());
        } else {
            jgen.writeNumber(number.intValue());
        }
    }

    private void writePrompt(String prompt) throws IOException {
        if (StringUtils.isNotBlank(prompt)) {
            jgen.writeStringField("prompt", prompt);
        }
    }
}