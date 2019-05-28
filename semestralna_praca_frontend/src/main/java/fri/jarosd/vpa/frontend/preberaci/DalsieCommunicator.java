package fri.jarosd.vpa.frontend.preberaci;

import com.fasterxml.jackson.databind.ObjectMapper;
import fri.jarosd.vpa.frontend.datoveCleny.Odpoved;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@Service
public class DalsieCommunicator extends Communicator {

    public DalsieCommunicator() {
        super("http://docker-vpa-jarosd.westeurope.cloudapp.azure.com:10003/api/v1/");
    }

    public Odpoved getKomentare(int chybaID) throws IOException {
        Odpoved odpoved = this.getOdpoved(this.basicURL + "komentar/getKomentare/" + chybaID);
        return odpoved;
    }

    public Odpoved getKomentar(int idKomentara) throws IOException {
        Odpoved odpoved = this.getOdpoved(this.basicURL + "komentar/getKomentar/" + idKomentara);
        return odpoved;
    }

    public Odpoved getObrazkyMeta(int chybaID) throws IOException {
        Odpoved odpoved = this.getOdpoved(this.basicURL + "obrazky/getObrazky/" + chybaID);
        return odpoved;
    }

    public Odpoved vymazatKomentar(HashMap<String, Object> data) throws IOException {
        Odpoved odpoved = this.postOdpoved(this.basicURL + "komentar/vymazKomentar", data);
        return odpoved;
    }

    public Odpoved pridajKomentar(HashMap<String, Object> data) throws IOException {
        Odpoved odpoved = this.postOdpoved(this.basicURL + "komentar/pridajKomentar", data);
        return odpoved;
    }

    public Odpoved aktualizujKomentar(HashMap<String, Object> data) throws IOException {
        Odpoved odpoved = this.postOdpoved(this.basicURL + "komentar/aktualizujKomentar", data);
        return odpoved;
    }

    public String vygenerujLinkPreObrazok(int obrazokID) {
        return this.basicURL + "obrazky/getObrazokZobraz/" + obrazokID;
    }

    // https://www.baeldung.com/spring-rest-template-multipart-upload
    // MultivaluedMap is a map of key-values pairs. Each key can have zero or more values.
    public Odpoved nahrajObrazok(MultipartFile obrazok, String autor) throws IOException {
        HttpHeaders httpHlavicka = new HttpHeaders();
        httpHlavicka.setContentType(MediaType.MULTIPART_FORM_DATA);

        // v API je to definované ako subor
        MultiValueMap<String, Object> telo = new LinkedMultiValueMap<String, Object>();
        telo.add("subor", obrazok.getResource());

        HttpEntity<MultiValueMap<String, Object>> celaPoziadavka = new HttpEntity<MultiValueMap<String, Object>>(telo, httpHlavicka);
        String odpovedString = this.pripojenieSaKuRest.postForObject(this.basicURL + "obrazky/nahrajObrazok/" + autor, celaPoziadavka, String.class);
        ObjectMapper konverter = new ObjectMapper();
        Odpoved odpoved = konverter.readValue(odpovedString, Odpoved.class);

        return odpoved;
    }

    public Odpoved pridajObrazokMeta(int chybaId, String nazovObrazka, String autor, String cestaObrazok) throws IOException {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("chybaId", chybaId);
        data.put("nazovObrazka", nazovObrazka);
        data.put("autor", autor);
        data.put("cestaObrazok", cestaObrazok);

        Odpoved odpoved = this.postOdpoved(this.basicURL + "obrazky/pridajObrazok", data);
        return odpoved;
    }

    public Odpoved odoberObrazokMeta(int obrazokId) throws IOException {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("idObrazka", obrazokId);

        Odpoved odpoved = this.postOdpoved(this.basicURL + "obrazky/vymazObrazok", data);
        return odpoved;
    }

    public Odpoved vymazObrazok(int obrazokId) throws IOException {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("idObrazka", obrazokId);

        Odpoved odpoved = this.postOdpoved(this.basicURL + "obrazky/vymazObrazokZobraz", data);
        return odpoved;
    }

    public Odpoved getZmenyPortal(String autor, Integer preskoc, Integer zobraz) throws IOException {
        String autorDoplnok = null;
        String listerDoplnok = null;

        if (autor != null) {
            autorDoplnok = "/" + autor + "/";
        } else {
            autorDoplnok = "";
        }

        if (preskoc != null && zobraz != null) {
            listerDoplnok = "?preskoc=" + preskoc + "&zobraz=" + zobraz;
        } else {
            listerDoplnok = "";
        }

        Odpoved odpoved = this.getOdpoved(this.basicURL + "doplnky/getZmeny" + autorDoplnok + listerDoplnok);
        return odpoved;
    }

    public Integer getPocetZmien(String autor) throws IOException {
        String autorDoplnok = null;
        if (autor != null) {
            autorDoplnok = "/" + autor + "/";
        } else {
            autorDoplnok = "/";
        }

        Odpoved odpoved = this.getOdpoved(this.basicURL + "doplnky/getZmenyPocet" + autorDoplnok);
        if (odpoved.getData() != null) {
            return (Integer)odpoved.getData().get("pocet");
        } else {
            return null;
        }
    }

}
