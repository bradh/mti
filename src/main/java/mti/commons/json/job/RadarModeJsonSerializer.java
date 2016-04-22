package mti.commons.json.job;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import mti.commons.model.MtiEnumerations.RadarMode;

public class RadarModeJsonSerializer extends JsonSerializer<RadarMode> {
	
    @Override
    public Class<RadarMode> handledType() {
        return RadarMode.class;
    }
    
    @Override
    public void serialize(RadarMode value, JsonGenerator jgen,
            SerializerProvider provider) throws IOException,
            JsonProcessingException
    {
        jgen.writeStartObject();
        jgen.writeStringField("name", value.name());
        jgen.writeStringField("enum", value.to4607Value().toString());
        jgen.writeEndObject();
    }

}
