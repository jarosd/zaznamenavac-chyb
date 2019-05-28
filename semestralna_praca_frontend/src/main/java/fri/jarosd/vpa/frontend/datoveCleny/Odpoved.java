package fri.jarosd.vpa.frontend.datoveCleny;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.util.ArrayList;
import java.util.HashMap;

@JsonFilter("filterData")
@JsonDeserialize(using = OdpovedDeserializer.class)
public class Odpoved {

    private ArrayList<HashMap<String, Object>> dataMultiple;
    private HashMap<String, Object> data;
    private String status;
    private int httpKod;

    private final int apiVersion = 1;

    public Odpoved(HashMap<String, Object> data, String status, int httpKod) {
        this.data = data;
        this.dataMultiple = null;
        this.status = status;
        this.httpKod = httpKod;
    }

    public Odpoved(ArrayList<HashMap<String, Object>> data, String status, int httpKod) {
        this.data = null;
        this.dataMultiple = data;
        this.status = status;
        this.httpKod = httpKod;
    }

    public Odpoved(HashMap<String, Object> data, String status) {
        this.data = data;
        this.dataMultiple = null;
        this.status = status;
        this.httpKod = 200;
    }

    public Odpoved(ArrayList<HashMap<String, Object>> data, String status) {
        this.data = null;
        this.dataMultiple = data;
        this.status = status;
        this.httpKod = 200;
    }

    public Odpoved() {}

    public Odpoved(String oznam, String status, int httpKod) {
        this.data = new HashMap<String, Object>();
        this.data.put("status", oznam);

        this.status = status;
        this.httpKod = httpKod;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public int getApiVersion() {
        return apiVersion;
    }

    public int getHttpKod() {
        return httpKod;
    }

    public ArrayList<HashMap<String, Object>> getDataMultiple() {
        return dataMultiple;
    }

    public void setDataMultiple(ArrayList<HashMap<String, Object>> dataMultiple) {
        this.dataMultiple = dataMultiple;
    }

    public String generujOdpoved() {
        ObjectMapper konverter = new ObjectMapper();
        String json = "";

        // https://www.baeldung.com/jackson-ignore-properties-on-serialization
        SimpleBeanPropertyFilter filter = null;
        FilterProvider pridanieFiltra = null;

        if (this.dataMultiple != null) {
            filter = SimpleBeanPropertyFilter.serializeAllExcept("data");
            pridanieFiltra = new SimpleFilterProvider().addFilter("filterData", filter);
        } else {
            filter = SimpleBeanPropertyFilter.serializeAllExcept("dataMultiple");
            pridanieFiltra = new SimpleFilterProvider().addFilter("filterData", filter);
        }

        try {
            json = konverter.writer(pridanieFiltra).writeValueAsString(this);
        } catch (JsonProcessingException vynimka) {
            return "Nastala chyba pri konverzií dát na JSON";
        }

        return json;
    }
}
