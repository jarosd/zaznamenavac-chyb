package fri.jarosd.vpa.bugs.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import fri.jarosd.vpa.bugs.preberaci.ImagesReceiver;
import fri.jarosd.vpa.bugs.datoveEntity.Obrazok;
import fri.jarosd.vpa.bugs.datoveEntity.Odpoved;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/obrazky")
public class ImagesController {

    @Autowired
    private Environment environment;

    @GetMapping(value = "/getObrazky/{idChyby}", produces = "application/json")
    public String getObrazky(@PathVariable("idChyby") int idChyby) {
        ImagesReceiver spracovatel = new ImagesReceiver(this.environment);
        ArrayList<Obrazok> zoznamObrazkov = spracovatel.getObrazkyKuChybe(idChyby);
        Odpoved odpoved = null;

        try {
            odpoved = new Odpoved(zoznamObrazkov, "OK");
        } catch (JsonProcessingException vynimka) {
            odpoved = new Odpoved("Nepodarilo sa skonvertovať údaje na formát JSON.", "CHYBA", 500);
        }

        return odpoved.generujOdpoved();
    }

    @PostMapping(value = "/pridajObrazok", produces = "application/json")
    public String pridajObrazok(@RequestBody Obrazok novyObrazok) {
        ImagesReceiver spracovatel = new ImagesReceiver(this.environment);
        boolean status = spracovatel.pridajObrazok(novyObrazok);
        Odpoved odpoved = null;

        if (status) {
            odpoved = new Odpoved("Obrázok bol úspešne zaznamenaný do internej databázy.", "OK");
        } else {
            odpoved = new Odpoved("Nepodarilo sa pridať záznam o novom obrázku do internej databázy.", "CHYBA", 500);
        }

        return odpoved.generujOdpoved();
    }

    @PostMapping(value = "/vymazObrazok", produces = "application/json")
    public String vymazObrazok(@RequestBody Map<String, Object> informacie) {
        ImagesReceiver spracovatel = new ImagesReceiver(this.environment);
        boolean status = spracovatel.odstranObrazok((int)informacie.get("idObrazka"));
        Odpoved odpoved = null;

        if (status) {
            odpoved = new Odpoved("Informácia o obrázku ku chybe bola z databázy odstránená.", "OK");
        } else {
            odpoved = new Odpoved("Informácia o obrázku ku chybe nebola odstránená. Nastala chyba na strane servera.", "CHYBA", 500);
        }

        return odpoved.generujOdpoved();
    }

    @GetMapping(value = "/getObrazokZobraz/{idObrazka}")
    public ResponseEntity getObrazokZobraz(@PathVariable("idObrazka") int idObrazka, HttpServletRequest request) {
        // https://www.callicoder.com/spring-boot-file-upload-download-rest-api-example/
        ImagesReceiver spracovatel = new ImagesReceiver(this.environment);
        Resource zdroj = spracovatel.getObrazokZobraz(idObrazka);

        try {
            String typObrazka = request.getServletContext().getMimeType(zdroj.getFile().getAbsolutePath());

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(typObrazka))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + zdroj.getFilename() + "\"")
                    .body(zdroj);
        } catch (IOException vynimka) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/nahrajObrazok/{autor}", produces = "application/json")
    public String nahrajObrazok(@PathVariable("autor") String autor, @RequestParam("subor") MultipartFile subor) {
        ImagesReceiver spracovatel = new ImagesReceiver(this.environment);
        String nazovSuboru = spracovatel.uploadujObrazok(autor, subor);
        Odpoved odpoved = null;

        if (nazovSuboru != null && !nazovSuboru.equals("")) {
            HashMap<String, String> data = new HashMap<String, String>();
            data.put("status", "Obrázok bol úspešne načítaný na server.");
            data.put("nazovSuboru", nazovSuboru);

            odpoved = new Odpoved(data, "OK");
        } else {
            odpoved = new Odpoved("Obrázok nebol načítaný na server.", "CHYBA", 500);
        }

        return odpoved.generujOdpoved();
    }

    @PostMapping(value = "/vymazObrazokZobraz", produces = "application/json")
    public String vymazObrazokZobraz(@RequestBody Map<String, Object> informacie) {
        ImagesReceiver spracovatel = new ImagesReceiver(this.environment);
        boolean stav = spracovatel.vymazObrazokZoServera((int)informacie.get("idObrazka"));
        Odpoved odpoved = null;

        if (stav) {
            odpoved = new Odpoved("Obrázok bol úspešne vymazaný zo servera.", "OK");
        } else {
            odpoved = new Odpoved("Obrázok nebol vymazaný zo servera.", "CHYBA", 500);
        }

        return odpoved.generujOdpoved();
    }
}
