package fri.jarosd.vpa.bugs.datoveEntity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class DolezitostDeserializer extends JsonDeserializer<Dolezitost> {

    @Override
    public Dolezitost deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode node = objectCodec.readTree(jsonParser);

        if (node.isBigInteger()) {
            return new Dolezitost(node.asInt(), null, null);
        } else {
            int dolezitost = node.get("dolezitost").asInt();
            String popis = node.get("popis").asText();
            String farba = node.get("farba").asText();

            return new Dolezitost(dolezitost, popis, farba);
        }
    }
}
