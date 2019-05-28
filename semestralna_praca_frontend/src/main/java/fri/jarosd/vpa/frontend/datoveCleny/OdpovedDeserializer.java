package fri.jarosd.vpa.frontend.datoveCleny;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class OdpovedDeserializer extends JsonDeserializer<Odpoved> {

    // https://dzone.com/articles/custom-json-deserialization-with-jackson
    // https://www.programcreek.com/java-api-examples/?class=com.fasterxml.jackson.databind.JsonNode&method=getNodeType

    @Override
    public Odpoved deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec objectCodec = jsonParser.getCodec();
        JsonNode node = objectCodec.readTree(jsonParser);

        String status = node.get("status").asText();
        int httpKod = node.get("httpKod").asInt();

        if (node.get("data").isArray()) {
            JsonNode aktualneSpracovavane = node.get("data");
            ArrayList<HashMap<String, Object>> dataMultiple = new ArrayList<HashMap<String, Object>>();

            for (JsonNode polozkaPola : aktualneSpracovavane) {
                ObjectMapper mapper = new ObjectMapper();
                HashMap<String, Object> polozky = mapper.convertValue(polozkaPola, HashMap.class);

                dataMultiple.add(polozky);
            }

            Odpoved odpoved = new Odpoved(dataMultiple, status, httpKod);
            return odpoved;
        } else {
            // type JsonNodeType.OBJECT
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, Object> data = mapper.convertValue(node.get("data"), HashMap.class);

            Odpoved odpoved = new Odpoved(data, status, httpKod);
            return odpoved;
        }
    }
}
