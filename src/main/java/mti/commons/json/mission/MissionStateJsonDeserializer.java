package mti.commons.json.mission;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import mti.commons.model.MtiEnumerations.MissionState;

public class MissionStateJsonDeserializer extends JsonDeserializer<MissionState> {

    @Override
    public MissionState deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException
    {
        ObjectCodec oc = jp.getCodec();
        JsonNode root = oc.readTree(jp);
        return MissionState.getMissionState(Short.parseShort(root.get("enum").asText()));
    }
}
