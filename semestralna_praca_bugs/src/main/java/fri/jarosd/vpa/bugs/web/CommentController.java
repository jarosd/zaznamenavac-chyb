package fri.jarosd.vpa.bugs.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import fri.jarosd.vpa.bugs.preberaci.CommentReceiver;
import fri.jarosd.vpa.bugs.datoveEntity.Komentar;
import fri.jarosd.vpa.bugs.datoveEntity.Odpoved;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/komentar")
public class CommentController {

    @GetMapping(value = "/getKomentare/{idChyby}", produces = "application/json")
    public String getKomentare(@PathVariable("idChyby") int idChyby) {
        CommentReceiver spracovatel = new CommentReceiver();
        ArrayList<Komentar> zoznamKomentarov = spracovatel.getKomentare(idChyby);
        Odpoved odpoved = null;

        try {
            odpoved = new Odpoved(zoznamKomentarov, "OK");
        } catch (JsonProcessingException vynimka) {
            odpoved = new Odpoved("Nepodarilo sa skonvertovať údaje na formát JSON.", "CHYBA", 500);
        }

        return odpoved.generujOdpoved();
    }

    @GetMapping(value = "/getKomentar/{idKomentara}", produces = "application/json")
    public String getKomentar(@PathVariable("idKomentara") int idKomentara) {
        CommentReceiver spracovatel = new CommentReceiver();
        Komentar komentar = spracovatel.getKomentar(idKomentara);
        Odpoved odpoved = null;

        try {
            odpoved = new Odpoved(komentar, "OK");
        } catch (JsonProcessingException vynimka) {
            odpoved = new Odpoved("Nepodarilo sa skonvertovať údaje na formát JSON.", "CHYBA", 500);
        }

        return odpoved.generujOdpoved();
    }

    @PostMapping(value = "/pridajKomentar", produces = "application/json")
    public String pridajKomentar(@RequestBody Komentar novyKomentar) {
        CommentReceiver spracovatel = new CommentReceiver();
        boolean status = spracovatel.pridajKomentar(novyKomentar);
        Odpoved odpoved = null;

        if (status) {
            odpoved = new Odpoved("Komentár bol úspešné pridaný k danej chybe.", "OK");
        } else {
            odpoved = new Odpoved("Nepodarilo sa pridať nový komentár k danej chybe.", "CHYBA", 500);
        }

        return odpoved.generujOdpoved();
    }

    @PostMapping(value = "/aktualizujKomentar", produces = "application/json")
    public String aktualizujKomentar(@RequestBody Komentar komentarNaAktualizaciu) {
        CommentReceiver spracovatel = new CommentReceiver();
        boolean status = spracovatel.aktualizujKomentar(komentarNaAktualizaciu);
        Odpoved odpoved = null;

        if (status) {
            odpoved = new Odpoved("Komentár bol úspešné zaktualizovaný.", "OK");
        } else {
            odpoved = new Odpoved("Nepodarilo sa zaktualizovať komentár k danej chybe.", "CHYBA", 500);
        }

        return odpoved.generujOdpoved();
    }

    @PostMapping(value = "/vymazKomentar", produces = "application/json")
    public String vymazKomentar(@RequestBody Map<String, Object> informacie) {
        CommentReceiver spracovatel = new CommentReceiver();
        boolean status = spracovatel.vymazKomentar((int)informacie.get("idKomentara"));
        Odpoved odpoved = null;

        if (status) {
            odpoved = new Odpoved("Komentár bol vymazaný.", "OK");
        } else {
            odpoved = new Odpoved("Nepodarilo sa vymazať komentár.", "CHYBA", 500);
        }

        return odpoved.generujOdpoved();
    }
}
