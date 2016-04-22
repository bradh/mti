package mti.commons.json.mission;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import mti.commons.model.MtiEnumerations.ExerciseIndicator;;

public class ExerciseIndicatorJsonSerializer extends JsonSerializer<ExerciseIndicator> {
	
    @Override
    public Class<ExerciseIndicator> handledType() {
        return ExerciseIndicator.class;
    }
    
    @Override
    public void serialize(ExerciseIndicator value, JsonGenerator jgen,
            SerializerProvider provider) throws IOException,
            JsonProcessingException
    {
        jgen.writeStartObject();
        jgen.writeStringField("name", value.name());
        jgen.writeStringField("enum", value.to4607Value().toString());
        jgen.writeEndObject();
    }

}
