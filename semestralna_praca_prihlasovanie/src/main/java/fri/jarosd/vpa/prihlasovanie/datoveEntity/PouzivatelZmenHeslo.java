package fri.jarosd.vpa.prihlasovanie.datoveEntity;

public class PouzivatelZmenHeslo {

    private String nick;
    private String stareHeslo;
    private String noveHeslo;
    private String apiKey;

    public PouzivatelZmenHeslo(String nick, String stareHeslo, String noveHeslo, String apiKey) {
        this.nick = nick;
        this.stareHeslo = stareHeslo;
        this.noveHeslo = noveHeslo;
        this.apiKey = apiKey;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getStareHeslo() {
        return stareHeslo;
    }

    public void setStareHeslo(String stareHeslo) {
        this.stareHeslo = stareHeslo;
    }

    public String getNoveHeslo() {
        return noveHeslo;
    }

    public void setNoveHeslo(String noveHeslo) {
        this.noveHeslo = noveHeslo;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
