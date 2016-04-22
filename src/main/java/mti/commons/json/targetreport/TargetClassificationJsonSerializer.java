package mti.commons.json.targetreport;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import mti.commons.model.MtiEnumerations.TargetClassification;

public class TargetClassificationJsonSerializer extends JsonSerializer<TargetClassification> {
	
    @Override
    public Class<TargetClassification> handledType() {
        return TargetClassification.class;
    }
    
    @Override
    public void serialize(TargetClassification value, JsonGenerator jgen,
            SerializerProvider provider) throws IOException,
            JsonProcessingException
    {
        jgen.writeStartObject();
        jgen.writeStringField("name", value.name());
        jgen.writeStringField("enum", value.to4607Value().toString());
        jgen.writeEndObject();
    }

}
