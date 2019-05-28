package fri.jarosd.vpa.bugs.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import fri.jarosd.vpa.bugs.preberaci.BugReceiver;
import fri.jarosd.vpa.bugs.datoveEntity.Bug;
import fri.jarosd.vpa.bugs.datoveEntity.Odpoved;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/chyby")
public class BugController {

    @GetMapping(value = "/zoznamChyb", produces = "application/json")
    public String getZoznamChyb(@RequestParam(required = false) Boolean informacie) {
        BugReceiver spracovatel = new BugReceiver();
        ArrayList<Bug> zoznamBugov = null;
        Odpoved odpoved = null;

        if (informacie == null) {
            zoznamBugov = spracovatel.getZoznamVsetkychBugov();

            try {
                odpoved = new Odpoved(zoznamBugov, "OK");
            } catch (JsonProcessingException vynimka) {
                odpoved = new Odpoved("Nepodarilo sa skonvertovať údaje na formát JSON.", "CHYBA", 500);
            }
        } else {
            zoznamBugov = spracovatel.getZoznamBugovVyriesene(informacie);
            try {
                odpoved = new Odpoved(zoznamBugov, "OK");
            } catch (JsonProcessingException vynimka) {
                odpoved = new Odpoved("Nepodarilo sa skonvertovať údaje na formát JSON.", "CHYBA", 500);
            }
        }

        return odpoved.generujOdpoved();
    }

    @GetMapping(value = "/zoznamChyb/autor/{autor}", produces = "application/json")
    public String getZoznamChybAutor(@PathVariable("autor") String autor, @RequestParam(required = false) Boolean informacie) {
        BugReceiver spracovatel = new BugReceiver();
        ArrayList<Bug> zoznamBugov = spracovatel.getZoznamBugovAutor(autor, informacie);
        Odpoved odpoved = null;

        try {
            odpoved = new Odpoved(zoznamBugov, "OK");
        } catch (JsonProcessingException vynimka) {
            odpoved = new Odpoved("Nepodarilo sa skonvertovať údaje na formát JSON.", "CHYBA", 500);
        }

        return odpoved.generujOdpoved();
    }

    @GetMapping(value = "/zoznamChyb/dolezitost/{dolezitost}", produces = "application/json")
    public String getZoznamChybDolezitost(@PathVariable("dolezitost") int dolezitost, @RequestParam(required = false) Boolean informacie) {
        BugReceiver spracovatel = new BugReceiver();
        ArrayList<Bug> zoznamBugov = spracovatel.getZoznamBugovDolezitost(dolezitost, informacie);
        Odpoved odpoved = null;

        try {
            odpoved = new Odpoved(zoznamBugov, "OK");
        } catch (JsonProcessingException vynimka) {
            odpoved = new Odpoved("Nepodarilo sa skonvertovať údaje na formát JSON.", "CHYBA", 500);
        }

        return odpoved.generujOdpoved();
    }

    @GetMapping(value = "/getChyba/{chybaID}", produces = "application/json")
    public String getZoznamChybDolezitost(@PathVariable int chybaID) {
        BugReceiver spracovatel = new BugReceiver();
        Bug chyba = spracovatel.getJedenBug(chybaID);
        Odpoved odpoved = null;

        try {
            odpoved = new Odpoved(chyba, "OK");
        } catch (JsonProcessingException vynimka) {
            odpoved = new Odpoved("Nepodarilo sa skonvertovať údaje na formát JSON.", "CHYBA", 500);
        }

        return odpoved.generujOdpoved();
    }

    @PostMapping(value = "/pridajChybu", produces = "application/json")
    public String pridajChybu(@RequestBody Bug chyba) {
        BugReceiver spracovatel = new BugReceiver();
        Odpoved odpoved = null;
        boolean status = spracovatel.pridajBug(chyba);

        if (status) {
            odpoved = new Odpoved("Chyba bola úspešne uložená do databázy.", "OK", 200);
        } else {
            odpoved = new Odpoved("Nepodarilo sa uložiť chybu do databázy.", "CHYBA", 500);
        }

        return odpoved.generujOdpoved();
    }

    private String oznacVyriesenieChyby(Map<String, Object> informacie, boolean jeVyriesena) {
        BugReceiver spracovatel = new BugReceiver();
        Odpoved odpoved = null;
        boolean status = spracovatel.oznacChybuAkoVyriesenu((int)informacie.get("cisloChyby"), jeVyriesena);

        if (status) {
            odpoved = new Odpoved("Informácie o chybe boli úspešne aktualizované.", "OK", 200);
        } else {
            odpoved = new Odpoved("Nepodarilo sa aktualizovať informácie o chybe.", "CHYBA", 500);
        }

        return odpoved.generujOdpoved();
    }

    @PostMapping(value = "/oznacChybuVyriesena", produces = "application/json")
    public String oznacChybuAkoVyriesenu(@RequestBody Map<String, Object> informacie) {
        return this.oznacVyriesenieChyby(informacie, true);
    }

    @PostMapping(value = "/oznacChybuNevyriesena", produces = "application/json")
    public String oznacChybuAkoNevyriesenu(@RequestBody Map<String, Object> informacie) {
        return this.oznacVyriesenieChyby(informacie, false);
    }

    @PostMapping(value = "/modifikujChybu", produces = "application/json")
    public String oznacChybuAkoNevyriesenu(@RequestBody Bug aktualizaciaChyby) {
        BugReceiver spracovatel = new BugReceiver();
        Odpoved odpoved = null;
        boolean status = spracovatel.aktualizujBug(aktualizaciaChyby);

        if (status) {
            odpoved = new Odpoved("Chyba bola úspešne zaktualizovaná.", "OK", 200);
        } else {
            odpoved = new Odpoved("Nepodarilo sa zaktualizovať chybu v databáze.", "CHYBA", 500);
        }

        return odpoved.generujOdpoved();
    }

    @PostMapping(value = "/vymazChybu", produces = "application/json")
    public String vymazChybu(@RequestBody Map<String, Object> informacie) {
        BugReceiver spracovatel = new BugReceiver();
        Odpoved odpoved = null;
        boolean status = spracovatel.vymazBug((int)informacie.get("cisloChyby"));

        if (status) {
            odpoved = new Odpoved("Chyba bola odstránená.", "OK", 200);
        } else {
            odpoved = new Odpoved("Nepodarilo sa odstrániť chybu z databázy.", "CHYBA", 500);
        }

        return odpoved.generujOdpoved();
    }
}
