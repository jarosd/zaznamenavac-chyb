package fri.jarosd.vpa.frontend.preberaci;

import fri.jarosd.vpa.frontend.datoveCleny.Odpoved;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.HashMap;

@Service
public class BugCommunicator extends Communicator {

    public BugCommunicator() {
        super("http://docker-vpa-jarosd.westeurope.cloudapp.azure.com:10003/api/v1/");
    }

    public Odpoved getZoznamDolezitosti() throws IOException {
        return this.getOdpoved(basicURL + "doplnky/getDolezitosti");
    }

    public Odpoved getChyby() throws IOException {
        return this.getOdpoved(basicURL + "chyby/zoznamChyb");
    }

    public Odpoved getChyba(int chybaID) throws IOException {
        return this.getOdpoved(basicURL + "chyby/getChyba/" + chybaID);
    }

    public Odpoved pridajChybu(String nazovChyby, String nick, String popisChyby, String umiestnenie, int dolezitost) throws IOException {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("autor", nick);
        data.put("nazovChyby", nazovChyby);
        data.put("popisChyby", popisChyby);
        data.put("umiestnenie", umiestnenie);
        data.put("dolezitostObjekt", dolezitost);

        return this.postOdpoved(basicURL + "chyby/pridajChybu", data);
    }

    public Odpoved editujChybu(int chybaID, String nazovChyby, String nick, String popisChyby, String umiestnenie, int dolezitost) throws IOException {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("chybaID", chybaID);
        data.put("autor", nick);
        data.put("nazovChyby", nazovChyby);
        data.put("popisChyby", popisChyby);
        data.put("umiestnenie", umiestnenie);
        data.put("dolezitostObjekt", dolezitost);

        return this.postOdpoved(basicURL + "chyby/modifikujChybu", data);
    }

    public Odpoved vymazChybu(HashMap<String, Object> data) throws IOException {
        return this.postOdpoved(basicURL + "chyby/vymazChybu", data);
    }

    public Odpoved nastavVyriesenie(HashMap<String, Object> data, boolean jeVyriesena) throws IOException {
        if (jeVyriesena) {
            return this.postOdpoved(basicURL + "chyby/oznacChybuVyriesena", data);
        } else {
            return this.postOdpoved(basicURL + "chyby/oznacChybuNevyriesena", data);
        }
    }
}