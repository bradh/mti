package mti.commons.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.vividsolutions.jts.geom.Coordinate;

public class GeoJsonCoordinateDeserializer extends JsonDeserializer<Coordinate> {

    @Override
    public Coordinate deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException
    {
        ObjectCodec oc = jp.getCodec();
        JsonNode root = oc.readTree(jp);
        return parse(root);
    }

    private Coordinate parse(JsonNode root) {
    	double x = root.get("x").asDouble();
    	double y = root.get("y").asDouble();
    	JsonNode znode = root.get("z");
    	if (znode == null) {
    		return new Coordinate(x, y);
    	} else {
        	double z = znode.asDouble();
    		return new Coordinate(x, y, z);
    	}
    }

}
