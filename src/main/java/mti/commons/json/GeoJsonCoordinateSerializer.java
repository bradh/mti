package mti.commons.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.vividsolutions.jts.geom.Coordinate;

public class GeoJsonCoordinateSerializer extends JsonSerializer<Coordinate> {
	
    @Override
    public Class<Coordinate> handledType() {
        return Coordinate.class;
    }
    
    @Override
    public void serialize(Coordinate value, JsonGenerator jgen,
            SerializerProvider provider) throws IOException,
            JsonProcessingException
    {

        writeCoordinate(jgen, value);
    }

    public void writeCoordinate(JsonGenerator jgen, Coordinate value)
            throws IOException {
        jgen.writeStartObject();
        jgen.writeStringField("x", String.valueOf(value.x));
        jgen.writeStringField("y", String.valueOf(value.y));
        if(!Double.isNaN(value.z)) {
            jgen.writeStringField("z", String.valueOf(value.z));
        }
        jgen.writeEndObject();
    }

}
