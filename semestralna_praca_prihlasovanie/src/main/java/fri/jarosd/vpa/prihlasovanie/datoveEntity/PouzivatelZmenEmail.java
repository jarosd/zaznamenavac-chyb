package fri.jarosd.vpa.prihlasovanie.datoveEntity;

public class PouzivatelZmenEmail {

    private String nick;
    private String email;
    private String apiKey;

    public PouzivatelZmenEmail(String nick, String email, String apiKey) {
        this.nick = nick;
        this.email = email;
        this.apiKey = apiKey;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
