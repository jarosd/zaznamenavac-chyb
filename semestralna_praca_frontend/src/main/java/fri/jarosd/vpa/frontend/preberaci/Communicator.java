package fri.jarosd.vpa.frontend.preberaci;

import com.fasterxml.jackson.databind.ObjectMapper;
import fri.jarosd.vpa.frontend.datoveCleny.Odpoved;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;

public abstract class Communicator {

    protected RestTemplate pripojenieSaKuRest;
    protected String basicURL;

    public Communicator(String basicURL) {
        this.basicURL = basicURL;
        HttpComponentsClientHttpRequestFactory restNastavenia = new HttpComponentsClientHttpRequestFactory();
        restNastavenia.setConnectTimeout(20000);
        restNastavenia.setReadTimeout(30000);

        this.pripojenieSaKuRest = new RestTemplate();
        this.pripojenieSaKuRest.setRequestFactory(restNastavenia);
    }

    protected Odpoved getOdpoved(String link) throws IOException {
        String odpovedString = this.pripojenieSaKuRest.getForObject(link, String.class);
        ObjectMapper konverter = new ObjectMapper();
        Odpoved odpoved = konverter.readValue(odpovedString, Odpoved.class);

        return odpoved;
    }

    protected Odpoved postOdpoved(String link, HashMap<String, Object> data) throws IOException {
        String odpovedString = this.pripojenieSaKuRest.postForObject(link, data, String.class);
        ObjectMapper konverter = new ObjectMapper();
        Odpoved odpoved = konverter.readValue(odpovedString, Odpoved.class);

        return odpoved;
    }
}
