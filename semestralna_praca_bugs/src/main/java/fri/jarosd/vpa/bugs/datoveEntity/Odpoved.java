package fri.jarosd.vpa.bugs.datoveEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class Odpoved {

    private Object data;
    private String status;
    private int httpKod;

    private final int apiVersion = 1;

    public Odpoved(Object data, String status) throws JsonProcessingException {
        this.data = data;
        this.status = status;
        this.httpKod = 200;
    }

    public Odpoved(HashMap<String, String> data, String status) {
        this.data = data;
        this.status = status;
        this.httpKod = 200;
    }

    public Odpoved(String oznam, String status, int httpKod) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("status", oznam);

        this.data = data;
        this.status = status;
        this.httpKod = httpKod;
    }

    public Odpoved(String oznam, String status) {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("status", oznam);

        this.data = data;
        this.status = status;
    }

    public Odpoved() {

    }

    public Object getData() {
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

    public String generujOdpoved() {
        ObjectMapper konverter = new ObjectMapper();
        String json = "";

        try {
            json = konverter.writeValueAsString(this);
        } catch (JsonProcessingException vynimka) {
            return "Nastala chyba pri konverzií dát na JSON";
        }

        return json;
    }
}
