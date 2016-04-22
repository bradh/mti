package mti.commons.json.mission;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import mti.commons.model.MtiEnumerations.PlatformType;

public class PlatformTypeJsonSerializer extends JsonSerializer<PlatformType> {
	
    @Override
    public Class<PlatformType> handledType() {
        return PlatformType.class;
    }
    
    @Override
    public void serialize(PlatformType value, JsonGenerator jgen,
            SerializerProvider provider) throws IOException,
            JsonProcessingException
    {
        jgen.writeStartObject();
        jgen.writeStringField("name", value.name());
        jgen.writeStringField("enum", value.to4607Value().toString());
        jgen.writeEndObject();
    }

}
