package fri.jarosd.vpa.frontend.web;

import fri.jarosd.vpa.frontend.FrontendApplication;
import fri.jarosd.vpa.frontend.datoveCleny.Odpoved;
import fri.jarosd.vpa.frontend.preberaci.DalsieCommunicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@Controller
public class DalsieController {

    @Autowired
    private DalsieCommunicator komunikatorDalsie;

    private final int pocetZaznamovStrana = 10;

    @GetMapping("/editujKomentarPanel/{komentarId}")
    public String editujKomentarPanel(@PathVariable("komentarId") int komentarId, Model model, HttpSession session) {
        if (!FrontendApplication.somPrihlaseny(session)) {
            return FrontendApplication.cestaNieSomPrihlaseny;
        }

        try {
            Odpoved komentarEdituj = this.komunikatorDalsie.getKomentar(komentarId);

            if (komentarEdituj.getData() != null) {
                model.addAttribute("komentar", komentarEdituj.getData());
            }
        } catch (IOException vynimka) {
            // sprav nič - nemáš ako zobraziť informáciu - je to cez AJAX
        }

        return "vlozene/komentar_formular";
    }

    @GetMapping({"/zmenyPortal/{stranka}", "/zmenyPortal"})
    public String getZmenyPortal(@PathVariable(value = "stranka", required = false) Integer cisloStrany, @RequestParam(name = "autor", required = false) String autor, Model model, HttpSession session) {
        if (!FrontendApplication.somPrihlaseny(session)) {
            return FrontendApplication.cestaNieSomPrihlaseny;
        }

        if (cisloStrany == null) {
            cisloStrany = 1;
        }

        int preskoc = pocetZaznamovStrana * (cisloStrany - 1);

        try {
            Odpoved odpoved = this.komunikatorDalsie.getZmenyPortal(autor, preskoc, this.pocetZaznamovStrana);
            Integer pocetZaznamov = this.komunikatorDalsie.getPocetZmien(autor);

            if (odpoved.getDataMultiple() != null && pocetZaznamov != null) {
                model.addAttribute("pouzivatel", autor);
                model.addAttribute("stranka", cisloStrany);
                model.addAttribute("dataZmeny", odpoved.getDataMultiple());
                model.addAttribute("pocetZaznamov", pocetZaznamov);
                model.addAttribute("pocetStran", Math.ceil(pocetZaznamov / (double) this.pocetZaznamovStrana));
            } else {
                model.addAttribute("sprava", "Nastala chyba pri preberaní záznamov o zmenách.");
                model.addAttribute("typ", "výstraha");
            }
        } catch (IOException vynimka) {
            model.addAttribute("sprava", "Nastala chyba na strane servera.");
            model.addAttribute("typ", "chyba");
        }

        return "zmeny_portal";
    }

    // pri ajax-e dávaj @RequestBody, inak stačí ti aj cez RequestParam
    @PostMapping("/vymazatKomentar")
    public String vymazatKomentar(@RequestBody HashMap<String, Object> informacie, Model model, HttpSession session) {
        if (!FrontendApplication.somPrihlaseny(session)) {
            return FrontendApplication.cestaNieSomPrihlaseny;
        }

        try {
            Odpoved kontrola = this.komunikatorDalsie.vymazatKomentar(informacie);
            model.addAttribute("sprava", kontrola.getData().get("status"));
        } catch (IOException vynimka) {
            model.addAttribute("sprava", "Nastala chyba na strane servera.");
            model.addAttribute("typ", "chyba");
        }

        return "vlozene/len_oznam";
    }

    @PostMapping("/pridajKomentar")
    public String pridajKomentar(@RequestParam("idChyby") int idChyby, @RequestParam("textKomentara") String textKomentara, Model model, RedirectAttributes redirectInfo, HttpSession session) {
        if (!FrontendApplication.somPrihlaseny(session)) {
            return FrontendApplication.cestaNieSomPrihlaseny;
        }

        try {
            HashMap<String, Object> informacie = new HashMap<String, Object>();
            informacie.put("idChyby", idChyby);
            informacie.put("textKomentara", textKomentara);
            informacie.put("autor", session.getAttribute("nick"));

            Odpoved kontrola = this.komunikatorDalsie.pridajKomentar(informacie);
            redirectInfo.addFlashAttribute("sprava", kontrola.getData().get("status"));
        } catch (IOException vynimka) {
            redirectInfo.addFlashAttribute("sprava", "Nastala chyba na strane servera.");
        }

        return "redirect:/podrobnosti/" + idChyby;
    }

    @PostMapping("/aktualizujKomentar")
    public String aktualizujKomentar(@RequestParam("idKomentara") int idKomentara, @RequestParam("idChyby") int idChyby, @RequestParam("textKomentara") String textKomentara, Model model, RedirectAttributes redirectInfo, HttpSession session) {
        if (!FrontendApplication.somPrihlaseny(session)) {
            return FrontendApplication.cestaNieSomPrihlaseny;
        }

        try {
            HashMap<String, Object> informacie = new HashMap<String, Object>();
            informacie.put("idKomentara", idKomentara);
            informacie.put("idChyby", idChyby);
            informacie.put("textKomentara", textKomentara);
            informacie.put("autor", session.getAttribute("nick"));

            Odpoved kontrola = this.komunikatorDalsie.aktualizujKomentar(informacie);
            redirectInfo.addFlashAttribute("sprava", kontrola.getData().get("status"));
        } catch (IOException vynimka) {
            redirectInfo.addFlashAttribute("sprava", "Nastala chyba na strane servera.");
            redirectInfo.addFlashAttribute("typ", "chyba");
        }

        return "redirect:/podrobnosti/" + idChyby;
    }

    @PostMapping("/pridajObrazok")
    public String pridajObrazok(@RequestParam("chybaId") int chybaId, @RequestParam("nazovObrazka") String nazovObrazka, @RequestParam("obrazok") MultipartFile obrazok, Model model, RedirectAttributes redirectInfo, HttpSession session) {
        if (!FrontendApplication.somPrihlaseny(session)) {
            return FrontendApplication.cestaNieSomPrihlaseny;
        }

        // najprv ulož obrázok, potom databáza
        try {
            String autor = (String) session.getAttribute("nick");
            Odpoved nahratieObrazka = this.komunikatorDalsie.nahrajObrazok(obrazok, autor);

            if (nahratieObrazka.getData() != null) {
                String cestaObrazok = (String) nahratieObrazka.getData().get("nazovSuboru");
                if (cestaObrazok != null && !cestaObrazok.equals("")) {
                    Odpoved ulozenieMetadat = this.komunikatorDalsie.pridajObrazokMeta(chybaId, nazovObrazka, autor, cestaObrazok);

                    redirectInfo.addFlashAttribute("sprava", ulozenieMetadat.getData().get("status"));
                } else {
                    redirectInfo.addFlashAttribute("sprava", "Nahratie nového obrázka na server sa nepodarilo.");
                    redirectInfo.addFlashAttribute("typ", "chyba");
                }
            } else {
                redirectInfo.addFlashAttribute("sprava", "Nastala chyba na strane servera.");
                redirectInfo.addFlashAttribute("typ", "chyba");
            }
        } catch (IOException vynimka) {
            redirectInfo.addFlashAttribute("sprava", "Nastala chyba na strane servera.");
            redirectInfo.addFlashAttribute("typ", "chyba");
        }

        return "redirect:/editujChybu/" + chybaId;
    }

    @PostMapping("/odstranObrazok")
    public String odstranObrazok(@RequestParam("chybaId") int chybaId, @RequestParam("obrazokId") int obrazokId, Model model, RedirectAttributes redirectInfo, HttpSession session) {
        if (!FrontendApplication.somPrihlaseny(session)) {
            return FrontendApplication.cestaNieSomPrihlaseny;
        }

        // rovnaké poradie ako pri pridávaní
        try {
            // vymazanie obrázka
            Odpoved vymazanieObrazok = this.komunikatorDalsie.vymazObrazok(obrazokId);
            Odpoved vymazanieMeta = this.komunikatorDalsie.odoberObrazokMeta(obrazokId);

            redirectInfo.addFlashAttribute("sprava", vymazanieMeta.getData().get("status"));
        } catch (IOException vynimka) {
            redirectInfo.addFlashAttribute("sprava", "Nastala chyba na strane servera.");
            redirectInfo.addFlashAttribute("typ", "chyba");
        }

        return "redirect:/editujChybu/" + chybaId;
    }
}
