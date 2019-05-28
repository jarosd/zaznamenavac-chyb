package fri.jarosd.vpa.frontend.preberaci;

import at.favre.lib.crypto.bcrypt.BCrypt;
import fri.jarosd.vpa.frontend.datoveCleny.Odpoved;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;

@Service
public class PrihlasovanieCommunicator extends Communicator {

    private String apiKey;

    @Autowired
    public PrihlasovanieCommunicator(Environment environment) {
        super("http://docker-vpa-jarosd.westeurope.cloudapp.azure.com:10000/api/v1/");
        this.apiKey = environment.getProperty("prihlasovanie.apiKey");
    }

    public Odpoved prihlasPouzivatela(String nick, String heslo) throws IOException {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("nick", nick);
        data.put("heslo", heslo);
        data.put("apikey", this.apiKey);

        return this.postOdpoved(basicURL + "prihlas", data);
    }

    public boolean registrujPouzivatela(String meno, String priezvisko, String nick, String email, String hesloPrve, String hesloDruhe) throws IOException {
        if (hesloPrve.equals(hesloDruhe)) {
            HashMap<String, Object> data = new HashMap<String, Object>();
            data.put("meno", meno);
            data.put("priezvisko", priezvisko);
            data.put("nick", nick);
            data.put("email", email);
            data.put("heslo", BCrypt.withDefaults().hashToString(12, hesloPrve.toCharArray()));
            data.put("apikey", this.apiKey);

            Odpoved odpoved = this.postOdpoved(basicURL + "registruj", data);
            return odpoved.getStatus().equals("OK");
        } else {
            return false;
        }
    }

    public boolean zmenHeslo(String nick, String hesloPrve, String hesloDruhe) throws IOException {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("nick", nick);
        data.put("stareHeslo", hesloPrve);
        data.put("noveHeslo", BCrypt.withDefaults().hashToString(12, hesloDruhe.toCharArray()));
        data.put("apiKey", this.apiKey);

        Odpoved odpoved = this.postOdpoved(basicURL + "zmenHeslo", data);
        return odpoved.getStatus().equals("OK");
    }

    public boolean zmenEmail(String nick, String novyEmail) throws IOException {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("nick", nick);
        data.put("email", novyEmail);
        data.put("apiKey", this.apiKey);

        Odpoved odpoved = this.postOdpoved(basicURL + "zmenEmail", data);
        return odpoved.getStatus().equals("OK");
    }

    public HashMap<String, Object> getDataPouzivatel(String nick) throws IOException {
        Odpoved odpoved = this.getOdpoved(basicURL + "dajInfoPouzivatel/" + nick + "?apikey=" + this.apiKey);
        return odpoved.getData();
    }

    public String jeNickObsadeny(String nick) throws IOException {
        Odpoved odpoved = this.getOdpoved(basicURL + "skontrolujNick/" + nick);
        return (String)odpoved.getData().get("status");
    }
}
