package mti.commons.json.job;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import mti.commons.model.MtiEnumerations.GeoidModel;

public class GeoIdModelJsonSerializer extends JsonSerializer<GeoidModel> {
	
    @Override
    public Class<GeoidModel> handledType() {
        return GeoidModel.class;
    }
    
    @Override
    public void serialize(GeoidModel value, JsonGenerator jgen,
            SerializerProvider provider) throws IOException,
            JsonProcessingException
    {
        jgen.writeStartObject();
        jgen.writeStringField("name", value.name());
        jgen.writeStringField("enum", value.to4607Value().toString());
        jgen.writeEndObject();
    }

}
