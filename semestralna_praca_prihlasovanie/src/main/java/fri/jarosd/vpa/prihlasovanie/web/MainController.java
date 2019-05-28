package fri.jarosd.vpa.prihlasovanie.web;

import fri.jarosd.vpa.prihlasovanie.preberaci.DataReceiver;
import fri.jarosd.vpa.prihlasovanie.datoveEntity.Odpoved;
import fri.jarosd.vpa.prihlasovanie.datoveEntity.Pouzivatel;

import fri.jarosd.vpa.prihlasovanie.datoveEntity.PouzivatelZmenEmail;
import fri.jarosd.vpa.prihlasovanie.datoveEntity.PouzivatelZmenHeslo;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MainController {

    @PostMapping(value = "/registruj", produces = "application/json")
    public String registrujPouzivatela(@RequestBody Map<String, String> informacie) {
        DataReceiver spracovatel = new DataReceiver();
        boolean mamPravo = spracovatel.skontrolujApiKluc(informacie.get("apikey"));

        Odpoved odpoved = null;

        if (mamPravo) {
            Pouzivatel novyPouzivatel = new Pouzivatel(informacie.get("meno"), informacie.get("priezvisko"), informacie.get("nick"), informacie.get("heslo"), informacie.get("email"));
            boolean kontrola = spracovatel.registrujPouzivatela(novyPouzivatel);

            if (kontrola) {
                odpoved = new Odpoved("Registrácia prebehla v poriadku.", "OK", 200);
            } else {
                odpoved = new Odpoved("Registrácia nebola úspešná. Skontrolujte si registračné údaje.", "CHYBA", 400);
            }
        } else {
            odpoved = new Odpoved("Skontrolujte si API kľúč. Zadaný kľúč nie je registrovaný v systéme.", "CHYBA", 401);
        }

        return odpoved.generujOdpoved();
    }

    @PostMapping(value = "/zmenEmail", produces = "application/json")
    public String zmenEmail(@RequestBody PouzivatelZmenEmail udajeZmena) {
        DataReceiver spracovatel = new DataReceiver();
        boolean mamPravo = spracovatel.skontrolujApiKluc(udajeZmena.getApiKey());

        Odpoved odpoved = null;

        if (mamPravo) {
            boolean kontrola = spracovatel.zmenEmail(udajeZmena);

            if (kontrola) {
                odpoved = new Odpoved("Zmena kontaktného emailu prebehla v poriadku.", "OK");
            } else {
                odpoved = new Odpoved("Zmena kontaktného emailu nebola úspešná. Skontrolujte si zadané údaje.", "CHYBA");
            }
        } else {
            odpoved = new Odpoved("Skontrolujte si API kľúč. Zadaný kľúč nie je registrovaný v systéme.", "CHYBA", 401);
        }

        return odpoved.generujOdpoved();
    }

    // vhodnejší by bol PATCH - cez postman to ide, ale cez webový prehliadač nie
    // takže som to prehodil na POST
    @PostMapping(value = "/zmenHeslo", produces = "application/json")
    public String zmenHeslo(@RequestBody PouzivatelZmenHeslo udajeZmena) {
        DataReceiver spracovatel = new DataReceiver();
        boolean mamPravo = spracovatel.skontrolujApiKluc(udajeZmena.getApiKey());

        Odpoved odpoved = null;
        if (mamPravo) {
            boolean kontrola = spracovatel.zmenHeslo(udajeZmena);

            if (kontrola) {
                odpoved = new Odpoved("Zmena hesla konta prebehla v poriadku.", "OK");
            } else {
                odpoved = new Odpoved("Zmena hesla konta nebola úspešná. Skontrolujte si zadané údaje.", "CHYBA");
            }
        } else {
            odpoved = new Odpoved("Skontrolujte si API kľúč. Zadaný kľúč nie je registrovaný v systéme.", "CHYBA", 401);
        }

        return odpoved.generujOdpoved();
    }

    @PostMapping(value = "/prihlas", produces = "application/json")
    public String prihlasPouzivatela(@RequestBody Map<String, String> loginUdaje) {
        DataReceiver spracovatel = new DataReceiver();
        boolean mamPravo = spracovatel.skontrolujApiKluc(loginUdaje.get("apikey"));

        Odpoved odpoved = null;
        if (mamPravo) {
            Pouzivatel infoPouzivatel = spracovatel.prihlasPouzivatela(loginUdaje.get("nick"), loginUdaje.get("heslo"));
            HashMap<String, String> textSpravy = new HashMap<String, String>();

            if (infoPouzivatel != null) {
                textSpravy.put("sprava", "Prihlásenie prebehlo v poriadku.");
                textSpravy.put("ID", String.valueOf(infoPouzivatel.getID()));

                odpoved = new Odpoved(textSpravy, "OK");
            } else {
                odpoved = new Odpoved("Prihlásenie nebolo úspešné. Skontrolujte si prihlasovacie údaje.", "CHYBA", 400);
            }
        } else {
            odpoved = new Odpoved("Skontrolujte si API kľúč. Zadaný kľúč nie je registrovaný v systéme.", "CHYBA", 401);
        }

        return odpoved.generujOdpoved();
    }

    @GetMapping(value = "/dajInfoPouzivatel/{nick}", produces = "application/json")
    public String dajInfoPouzivatel(@PathVariable("nick") String nick, @RequestParam("apikey") String apikey) {
        DataReceiver spracovatel = new DataReceiver();
        boolean mamPravo = spracovatel.skontrolujApiKluc(apikey);

        Odpoved odpoved = null;

        if (mamPravo) {
            Pouzivatel pouzivatel = spracovatel.getInfoPouzivatel(nick);

            if (pouzivatel != null) {
                odpoved = new Odpoved(pouzivatel.konverziaNaHashMap(), "OK");
            } else {
                odpoved = new Odpoved("Používateľ pravdepodobne neexistuje.", "CHYBA");
            }
        } else {
            odpoved = new Odpoved("Skontrolujte si API kľúč. Zadaný kľúč nie je registrovaný v systéme.", "CHYBA", 401);
        }

        return odpoved.generujOdpoved();
    }

    // tu nebudem potrebovať kontroly na kľúčik
    @GetMapping(value = "/skontrolujNick/{nick}", produces = "application/json")
    public String jeNickObsadeny(@PathVariable("nick") String nick) {
        DataReceiver spracovatel = new DataReceiver();
        boolean kontrola = spracovatel.jeNickObsadeny(nick);

        Odpoved odpoved = null;
        if (kontrola) {
            odpoved = new Odpoved("Nick je obsadený.", "OK");
        } else {
            odpoved = new Odpoved("Nick je voľný.", "OK");
        }

        return odpoved.generujOdpoved();
    }
}