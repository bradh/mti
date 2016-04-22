package mti.commons.json.job;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import mti.commons.model.MtiEnumerations.TerrainElevationModel;

public class TerrainElevationModelJsonSerializer extends JsonSerializer<TerrainElevationModel> {
	
    @Override
    public Class<TerrainElevationModel> handledType() {
        return TerrainElevationModel.class;
    }
    
    @Override
    public void serialize(TerrainElevationModel value, JsonGenerator jgen,
            SerializerProvider provider) throws IOException,
            JsonProcessingException
    {
        jgen.writeStartObject();
        jgen.writeStringField("name", value.name());
        jgen.writeStringField("enum", value.to4607Value().toString());
        jgen.writeEndObject();
    }

}
