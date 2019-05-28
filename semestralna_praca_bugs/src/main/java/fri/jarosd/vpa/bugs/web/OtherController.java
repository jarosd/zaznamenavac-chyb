package fri.jarosd.vpa.bugs.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import fri.jarosd.vpa.bugs.datoveEntity.Dolezitost;
import fri.jarosd.vpa.bugs.datoveEntity.Odpoved;
import fri.jarosd.vpa.bugs.datoveEntity.ZmenyPortal;
import fri.jarosd.vpa.bugs.preberaci.OtherReceiver;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/doplnky")
public class OtherController {

    // https://stackoverflow.com/questions/17821731/spring-mvc-how-to-indicate-whether-a-path-variable-is-required-or-not
    @GetMapping(value = {"/getZmeny/{pouzivatel}", "/getZmeny"}, produces = "application/json")
    public String getZmeny(@PathVariable(value = "pouzivatel", required = false) String pouzivatel, @RequestParam(value = "preskoc", required = false) Integer preskoc, @RequestParam(value="zobraz", required = false) Integer zobraz) {
        OtherReceiver spracovatel = new OtherReceiver();
        ArrayList<ZmenyPortal> zoznamZmienPortal = spracovatel.getZoznamZmienPortal(pouzivatel, preskoc, zobraz);
        Odpoved odpoved = null;

        try {
            odpoved = new Odpoved(zoznamZmienPortal, "OK");
        } catch (JsonProcessingException vynimka) {
            odpoved = new Odpoved("Nepodarilo sa skonvertovať údaje na formát JSON.", "CHYBA", 500);
        }

        return odpoved.generujOdpoved();
    }

    @GetMapping(value = {"/getZmenyPocet/{pouzivatel}", "/getZmenyPocet"}, produces = "application/json")
    public String getZmenyPocet(@PathVariable(value = "pouzivatel", required = false) String pouzivatel) {
        OtherReceiver spracovatel = new OtherReceiver();
        Integer pocetZaznamov = spracovatel.getZmenyPocet(pouzivatel);

        Odpoved odpoved = null;
        HashMap<String, Object> vystup = new HashMap<String, Object>();
        vystup.put("pocet", pocetZaznamov);

        try {
            odpoved = new Odpoved(vystup, "OK");
        } catch (JsonProcessingException vynimka) {
            odpoved = new Odpoved("Nepodarilo sa skonvertovať údaje na formát JSON.", "CHYBA", 500);
        }

        return odpoved.generujOdpoved();
    }

    @GetMapping(value = "/getDolezitosti", produces = "application/json")
    public String getObrazky() {
        OtherReceiver spracovatel = new OtherReceiver();
        ArrayList<Dolezitost> zoznamDolezitosti = spracovatel.getZoznamDolezitosti();
        Odpoved odpoved = null;

        try {
            odpoved = new Odpoved(zoznamDolezitosti, "OK");
        } catch (JsonProcessingException vynimka) {
            odpoved = new Odpoved("Nepodarilo sa skonvertovať údaje na formát JSON.", "CHYBA", 500);
        }

        return odpoved.generujOdpoved();
    }
}
