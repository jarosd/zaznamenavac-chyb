package fri.jarosd.vpa.frontend.web;

import fri.jarosd.vpa.frontend.FrontendApplication;
import fri.jarosd.vpa.frontend.datoveCleny.Odpoved;
import fri.jarosd.vpa.frontend.preberaci.BugCommunicator;
import fri.jarosd.vpa.frontend.preberaci.DalsieCommunicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class BugController {

    @Autowired
    private BugCommunicator komunikatorBug;

    @Autowired
    private DalsieCommunicator komunikatorDalsie;

    private Odpoved dodajLinky(Odpoved odpovedObrazky) {
        if (odpovedObrazky.getDataMultiple() != null) {
            ArrayList<HashMap<String, Object>> obrazkyInfo = odpovedObrazky.getDataMultiple();

            for (int i = 0; i < obrazkyInfo.size(); i++) {
                int obrazokId = (int) ((obrazkyInfo.get(i)).get("obrazokId"));
                (obrazkyInfo.get(i)).put("link", this.komunikatorDalsie.vygenerujLinkPreObrazok(obrazokId));
            }
        }

        return odpovedObrazky;
    }

    private String oznacovanieChyb(HashMap<String, Object> informacie, boolean jeVyriesena, Model model, HttpSession session) {
        if (!FrontendApplication.somPrihlaseny(session)) {
            return FrontendApplication.cestaNieSomPrihlaseny;
        }

        try {
            Odpoved odpoved = this.komunikatorBug.nastavVyriesenie(informacie, jeVyriesena);
            model.addAttribute("sprava", odpoved.getData().get("status"));
        } catch (IOException vynimka) {
            model.addAttribute("sprava", "Nastala chyba na strane servera.");
            model.addAttribute("typ", "chyba");
        }

        return "vlozene/len_oznam";
    }

    @GetMapping("/zobrazChyby")
    public String zobrazChyby(Model model, HttpSession session) {
        if (!FrontendApplication.somPrihlaseny(session)) {
            return FrontendApplication.cestaNieSomPrihlaseny;
        }

        Odpoved vysledok = null;

        try {
            vysledok = this.komunikatorBug.getChyby();

            if (vysledok.getDataMultiple() != null) {
                // všetko v poriadku
                model.addAttribute("ziskaneData", vysledok.getDataMultiple());
            } else {
                // niekde nastala chybička
                model.addAttribute("sprava", vysledok.getData().get("status"));
                model.addAttribute("typ", "výstraha");
            }
        } catch (IOException vynimka) {
            model.addAttribute("sprava", "Nastala chyba na strane servera.");
            model.addAttribute("typ", "chyba");
        }

        return "zobraz_chyby";
    }

    @GetMapping("/pridajChybu")
    public String pridajChybu(Model model, HttpSession session) {
        if (!FrontendApplication.somPrihlaseny(session)) {
            return FrontendApplication.cestaNieSomPrihlaseny;
        }

        try {
            Odpoved odpoved = this.komunikatorBug.getZoznamDolezitosti();
            ArrayList<HashMap<String, Object>> zoznamDolezitosti = odpoved.getDataMultiple();

            model.addAttribute("zoznamDolezitosti", zoznamDolezitosti);
        } catch (IOException vynimka) {
            model.addAttribute("sprava", "Nastala chyba na strane servera.");
            model.addAttribute("typ", "chyba");
        }

        return "pridaj_chybu";
    }

    @GetMapping("/podrobnosti/{chybaID}")
    public String zistiPodrobnostiChyba(@PathVariable("chybaID") int chybaID, Model model, HttpSession session) {
        if (!FrontendApplication.somPrihlaseny(session)) {
            return FrontendApplication.cestaNieSomPrihlaseny;
        }

        try {
            Odpoved odpoved = this.komunikatorBug.getChyba(chybaID);
            Odpoved odpovedKomentare = this.komunikatorDalsie.getKomentare(chybaID);
            Odpoved odpovedObrazky = this.komunikatorDalsie.getObrazkyMeta(chybaID);
            odpovedObrazky = this.dodajLinky(odpovedObrazky);

            if (odpoved.getData() != null && odpovedKomentare.getDataMultiple() != null) {
                model.addAttribute("ziskaneData", odpoved.getData());
                model.addAttribute("ziskaneDataKomentare", odpovedKomentare.getDataMultiple());
                model.addAttribute("ziskaneDataObrazok", odpovedObrazky.getDataMultiple());
            }

        } catch (IOException vynimka) {
            model.addAttribute("sprava", "Nastala chyba na strane servera.");
            model.addAttribute("typ", "chyba");
        }

        return "zobraz_podrobnosti";
    }

    @GetMapping("/editujChybu/{chybaID}")
    public String editujChybu(@PathVariable("chybaID") int chybaID, Model model, HttpSession session) {
        if (!FrontendApplication.somPrihlaseny(session)) {
            return FrontendApplication.cestaNieSomPrihlaseny;
        }

        try {
            Odpoved odpoved = this.komunikatorBug.getChyba(chybaID);
            Odpoved odpovedDolezitosti = this.komunikatorBug.getZoznamDolezitosti();
            Odpoved odpovedObrazky = this.komunikatorDalsie.getObrazkyMeta(chybaID);
            odpovedObrazky = this.dodajLinky(odpovedObrazky);

            if (odpoved.getData() != null && odpovedDolezitosti.getDataMultiple() != null && odpovedObrazky.getDataMultiple() != null) {
                model.addAttribute("modifikacie", odpoved.getData());
                model.addAttribute("zoznamDolezitosti", odpovedDolezitosti.getDataMultiple());
                model.addAttribute("ziskaneDataObrazok", odpovedObrazky.getDataMultiple());
            }

        } catch (IOException vynimka) {
            model.addAttribute("sprava", "Nastala chyba na strane servera.");
            model.addAttribute("typ", "chyba");
        }

        return "modifikuj_chybu";
    }

    @PostMapping("/vykonajPridanieChyby")
    public String vykonajPridanieChyby(@RequestParam(value = "nazovChyby") String nazovChyby, @RequestParam(value = "popisChyby") String popisChyby, @RequestParam(value = "dolezitost") int dolezitost, @RequestParam(value = "umiestnenie") String umiestnenie, Model model, RedirectAttributes redirectInfo, HttpSession session) {
        if (!FrontendApplication.somPrihlaseny(session)) {
            return FrontendApplication.cestaNieSomPrihlaseny;
        }

        String nick = (String) session.getAttribute("nick");

        try {
            Odpoved odpoved = this.komunikatorBug.pridajChybu(nazovChyby, nick, popisChyby, umiestnenie, dolezitost);
            redirectInfo.addFlashAttribute("sprava", odpoved.getData().get("status"));
        } catch (IOException vynimka) {
            redirectInfo.addFlashAttribute("sprava", "Nastala chyba na strane servera.");
            redirectInfo.addFlashAttribute("typ", "chyba");
        }

        return "redirect:/pridajChybu";
    }

    @PostMapping("/vykonajEditujChybu")
    public String vykonajEditujChybu(@RequestParam("chybaID") int chybaID, @RequestParam(value = "nazovChyby") String nazovChyby, @RequestParam(value = "popisChyby") String popisChyby, @RequestParam(value = "dolezitost") int dolezitost, @RequestParam(value = "umiestnenie") String umiestnenie, Model model, RedirectAttributes redirectInfo, HttpSession session) {
        if (!FrontendApplication.somPrihlaseny(session)) {
            return FrontendApplication.cestaNieSomPrihlaseny;
        }

        String nick = (String) session.getAttribute("nick");
        try {
            Odpoved odpoved = this.komunikatorBug.editujChybu(chybaID, nazovChyby, nick, popisChyby, umiestnenie, dolezitost);
            redirectInfo.addFlashAttribute("sprava", odpoved.getData().get("status"));
        } catch (IOException vynimka) {
            redirectInfo.addFlashAttribute("sprava", "Nastala chyba na strane servera.");
            redirectInfo.addFlashAttribute("typ", "chyba");
        }

        return "redirect:/zobrazChyby";
    }

    @PostMapping("/vymazatChybu")
    public String vymazatChybu(@RequestBody HashMap<String, Object> informacie, Model model, HttpSession session) {
        if (!FrontendApplication.somPrihlaseny(session)) {
            return FrontendApplication.cestaNieSomPrihlaseny;
        }

        // pozor! toto je cez AJAX!
        try {
            Odpoved odpoved = this.komunikatorBug.vymazChybu(informacie);
            model.addAttribute("sprava", odpoved.getData().get("status"));
        } catch (IOException vynimka) {
            model.addAttribute("sprava", "Nastala chyba na strane servera.");
            model.addAttribute("typ", "chyba");
        }

        return "vlozene/len_oznam";
    }

    @PostMapping("/oznacChybuZaVyriesenu")
    public String oznacChybuZaVyriesenu(@RequestBody HashMap<String, Object> informacie, Model model, HttpSession session) {
        return this.oznacovanieChyb(informacie, true, model, session);
    }

    @PostMapping("/oznacChybuZaNevyriesenu")
    public String oznacChybuZaNevyriesenu(@RequestBody HashMap<String, Object> informacie, Model model, HttpSession session) {
        return this.oznacovanieChyb(informacie, false, model, session);
    }
}
