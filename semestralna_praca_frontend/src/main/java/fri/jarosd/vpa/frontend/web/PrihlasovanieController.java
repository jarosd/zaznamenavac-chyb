package fri.jarosd.vpa.frontend.web;

import fri.jarosd.vpa.frontend.FrontendApplication;
import fri.jarosd.vpa.frontend.datoveCleny.Odpoved;
import fri.jarosd.vpa.frontend.preberaci.PrihlasovanieCommunicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@Controller
public class PrihlasovanieController {

    @Autowired
    private PrihlasovanieCommunicator komunikatorPrihlasenie;

    @GetMapping("/")
    public String zobrazHlavnuStranku(Model model) {
        return "index";
    }

    @GetMapping("/prihlasenie")
    public String prihlasenie(Model model) {
        return "prihlasenie";
    }

    @GetMapping("/registracia")
    public String registracia(Model model) {
        return "registracia";
    }

    @GetMapping("/zmenaHesla")
    public String zmenaHesla(Model model, HttpSession session) {
        if (!FrontendApplication.somPrihlaseny(session)) {
            return FrontendApplication.cestaNieSomPrihlaseny;
        }

        String nick = (String) session.getAttribute("nick");
        HashMap<String, Object> infoPouzivatel = null;
        try {
            infoPouzivatel = this.komunikatorPrihlasenie.getDataPouzivatel(nick);
            String celeMeno = infoPouzivatel.get("meno") + " " + infoPouzivatel.get("priezvisko");

            model.addAttribute("cele_meno", celeMeno);
            model.addAttribute("email", infoPouzivatel.get("email"));
            model.addAttribute("typ_uctu", infoPouzivatel.get("typPouzivatela"));

            return "zmena_hesla";
        } catch (IOException e) {
            model.addAttribute("sprava", "Nastala chyba na strane servera.");
            model.addAttribute("typ", "chyba");

            return "zmena_hesla";
        }
    }

    @GetMapping("/odhlasenie")
    public String odhlasenie(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/skontrolujNick/{nick}")
    public String jeNickObsadeny(@PathVariable("nick") String nick, Model model) {
        try {
            String oznam = this.komunikatorPrihlasenie.jeNickObsadeny(nick);
            model.addAttribute("sprava", oznam);
        } catch (IOException vynimka) {
            model.addAttribute("sprava", "Overenie sa nepodarilo");
        }

        return "vlozene/len_oznam";
    }

    @PostMapping("/vykonajPrihlasenie")
    public String vykonajPrihlasenie(@RequestParam(value = "nick") String nick, @RequestParam(value = "heslo") String heslo, Model model, RedirectAttributes redirectInfo, HttpSession session) {
        Odpoved jsonOdpoved = null;

        try {
            jsonOdpoved = this.komunikatorPrihlasenie.prihlasPouzivatela(nick, heslo);
            HashMap<String, Object> polozkyOdpovede = jsonOdpoved.getData();

            if (jsonOdpoved.getStatus().equals("OK")) {
                session.setAttribute("id", Integer.valueOf((String) polozkyOdpovede.get("ID")));
                session.setAttribute("nick", nick);

                return "redirect:/zobrazChyby";
            } else {
                redirectInfo.addFlashAttribute("sprava", polozkyOdpovede.get("status"));
                redirectInfo.addFlashAttribute("typ", "výstraha");

                return "redirect:/prihlasenie";
            }
        } catch (IOException e) {
            redirectInfo.addFlashAttribute("sprava", "Nastala chyba na strane servera.");
            redirectInfo.addFlashAttribute("typ", "chyba");

            return "redirect:/prihlasenie";
        }
    }

    @PostMapping("/vykonajRegistraciu")
    public String vykonajRegistraciu(@RequestParam(value = "nick") String nick, @RequestParam(value = "heslo_prve") String hesloPrve, @RequestParam(value = "meno") String meno, @RequestParam(value = "priezvisko") String priezvisko, @RequestParam(value = "email") String email, @RequestParam(value = "heslo_druhe") String hesloDruhe, Model model, RedirectAttributes redirectInfo) {
        boolean vysledok = false;

        try {
            vysledok = this.komunikatorPrihlasenie.registrujPouzivatela(meno, priezvisko, nick, email, hesloPrve, hesloDruhe);
        } catch (IOException vynimka) {
            redirectInfo.addFlashAttribute("sprava", "Nastala chyba na strane servera.");
            redirectInfo.addFlashAttribute("typ", "chyba");

            return "redirect:/registracia";
        }

        if (vysledok) {
            redirectInfo.addFlashAttribute("sprava", "Registrácia bola úspešná. Môžete sa prihlásiť.");
            redirectInfo.addFlashAttribute("typ", "OK");

            return "redirect:/registracia";
        } else {
            redirectInfo.addFlashAttribute("sprava", "Registrácia nebola úspešná. Pravdepodobne už takýto používateľ existuje.");
            redirectInfo.addFlashAttribute("typ", "výstraha");

            return "redirect:/registracia";
        }
    }

    @PostMapping("/vykonajZmenuHesla")
    public String vykonajZmenuHesla(@RequestParam(value = "stareHeslo") String stareHeslo, @RequestParam(value = "noveHeslo") String noveHeslo, @RequestParam(value = "noveHesloZnova") String noveHesloZnova, Model model, RedirectAttributes redirectInfo, HttpSession session) {
        if (!FrontendApplication.somPrihlaseny(session)) {
            return FrontendApplication.cestaNieSomPrihlaseny;
        }

        boolean vysledok = false;
        String nick = (String) session.getAttribute("nick");

        if (!(noveHeslo.equals(noveHesloZnova))) {
            redirectInfo.addFlashAttribute("sprava", "Zadané nové heslá nie sú zhodné.");
            redirectInfo.addFlashAttribute("typ", "výstraha");

            return "redirect:/zmenaHesla";
        }

        try {
            vysledok = this.komunikatorPrihlasenie.zmenHeslo(nick, stareHeslo, noveHeslo);
        } catch (IOException vynimka) {
            redirectInfo.addFlashAttribute("sprava", "Nastala chyba na strane servera.");
            redirectInfo.addFlashAttribute("typ", "chyba");

            return "redirect:/zmenaHesla";
        }

        if (vysledok) {
            redirectInfo.addFlashAttribute("sprava", "Heslo bolo úspešne zmenené.");
            redirectInfo.addFlashAttribute("typ", "OK");

            return "redirect:/zmenaHesla";
        } else {
            redirectInfo.addFlashAttribute("sprava", "Heslo nebolo zmenené. Zadané aktuálne heslo nie je správne.");
            redirectInfo.addFlashAttribute("typ", "výstraha");

            return "redirect:/zmenaHesla";
        }
    }

    @PostMapping("/vykonajZmenuEmailu")
    public String vykonajZmenuEmailu(@RequestParam("novyEmail") String novyEmail, Model model, RedirectAttributes redirectInfo, HttpSession session) {
        if (!FrontendApplication.somPrihlaseny(session)) {
            return FrontendApplication.cestaNieSomPrihlaseny;
        }

        boolean vysledok = false;
        String nick = (String) session.getAttribute("nick");

        try {
            vysledok = this.komunikatorPrihlasenie.zmenEmail(nick, novyEmail);
        } catch (IOException vynimka) {
            redirectInfo.addFlashAttribute("sprava", "Nastala chyba na strane servera.");
            redirectInfo.addFlashAttribute("typ", "chyba");

            return "redirect:/zmenaHesla";
        }

        if (vysledok) {
            redirectInfo.addFlashAttribute("sprava", "Kontakný email bol zmenený.");
            redirectInfo.addFlashAttribute("typ", "OK");

            return "redirect:/zmenaHesla";
        } else {
            redirectInfo.addFlashAttribute("sprava", "Nepodarilo sa zmeniť kontakný email.");
            redirectInfo.addFlashAttribute("typ", "výstraha");

            return "redirect:/zmenaHesla";
        }
    }
}
