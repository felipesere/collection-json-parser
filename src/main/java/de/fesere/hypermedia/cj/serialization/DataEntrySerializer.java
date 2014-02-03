package de.fesere.hypermedia.cj.serialization;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.fesere.hypermedia.cj.model.data.BooleanDataEntry;
import de.fesere.hypermedia.cj.model.data.DataEntry;
import de.fesere.hypermedia.cj.model.data.NumberDataEntry;
import de.fesere.hypermedia.cj.model.data.StringDataEntry;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class DataEntrySerializer extends StdSerializer<DataEntry> {
    protected DataEntrySerializer() {
        super(DataEntry.class);
    }

    @Override
    public void serialize(DataEntry value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        jgen.writeStartObject();

        writeName(value, jgen);
        writeValue(value, jgen);
        writePrompt(value, jgen);

        jgen.writeEndObject();

    }

    private void writePrompt(DataEntry value, JsonGenerator jgen) throws IOException {
        String prompt = value.getPrompt();
        if (StringUtils.isNotBlank(prompt)) {
            jgen.writeStringField("prompt", prompt);
        }
    }

    private void writeName(DataEntry value, JsonGenerator jgen) throws IOException {
        jgen.writeStringField("name", value.getName());
    }

    private void writeValue(DataEntry value, JsonGenerator jgen) throws IOException {
        jgen.writeFieldName("value");
        if (hasRealValue(value)) {
            writeRealValue(value, jgen);
        } else {
         jgen.writeString("");
        }
    }

    private void writeRealValue(DataEntry value, JsonGenerator jgen) throws IOException {
        if (value instanceof StringDataEntry) {
            StringDataEntry stringEntry = (StringDataEntry) value;
            jgen.writeString(stringEntry.getValue());
        } else if (value instanceof NumberDataEntry) {
            writeNumber((NumberDataEntry) value, jgen);
        } else if(value instanceof BooleanDataEntry) {
            jgen.writeBoolean((boolean) value.getValue());
        }
    }

    private void writeNumber(NumberDataEntry value, JsonGenerator jgen) throws IOException {
        Number number = value.getValue();
        if (number instanceof Double) {
            jgen.writeNumber(number.doubleValue());
        } else {
            jgen.writeNumber(number.intValue());
        }
    }

    private boolean hasRealValue(DataEntry value) {
        return value.getValue() != null;
    }
}
