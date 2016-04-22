package mti.commons.json.job;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import mti.commons.model.MtiEnumerations.TerrainElevationModel;

public class TerrainElevationModelJsonDeserializer extends JsonDeserializer<TerrainElevationModel> {

    @Override
    public TerrainElevationModel deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException
    {
        ObjectCodec oc = jp.getCodec();
        JsonNode root = oc.readTree(jp);
        return TerrainElevationModel.getElevationModel(Short.parseShort(root.get("enum").asText()));
    }
}
