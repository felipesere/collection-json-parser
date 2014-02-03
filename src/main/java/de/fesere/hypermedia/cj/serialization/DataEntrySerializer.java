package de.fesere.hypermedia.cj.serialization;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import de.fesere.hypermedia.cj.model.DataEntry;
import de.fesere.hypermedia.cj.model.NumberDataEntry;
import de.fesere.hypermedia.cj.model.StringDataEntry;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class DataEntrySerializer extends StdSerializer<DataEntry> {
    protected DataEntrySerializer() {
        super(DataEntry.class);
    }

    @Override
    public void serialize(DataEntry value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        jgen.writeStartObject();
        jgen.writeStringField("name", value.getName());


        jgen.writeFieldName("value");
        if (value.getValue() != null) {
            if (value instanceof StringDataEntry) {
                StringDataEntry stringEntry = (StringDataEntry) value;
                jgen.writeString(stringEntry.getValue());
            } else if (value instanceof NumberDataEntry) {
                NumberDataEntry numberEntry = (NumberDataEntry) value;
                Number number = numberEntry.getValue();
                if (number instanceof Double) {
                    jgen.writeNumber(number.doubleValue());
                } else {
                    jgen.writeNumber(number.intValue());
                }
            }
        } else {
         jgen.writeString("");
        }

        String prompt = value.getPrompt();
        if (StringUtils.isNotBlank(prompt)) {
            jgen.writeStringField("prompt", prompt);
        }

        jgen.writeEndObject();

    }
}
