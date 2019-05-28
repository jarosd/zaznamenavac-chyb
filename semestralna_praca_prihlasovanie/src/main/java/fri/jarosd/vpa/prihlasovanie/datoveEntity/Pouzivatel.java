package fri.jarosd.vpa.prihlasovanie.datoveEntity;

import java.io.Serializable;
import java.util.HashMap;

public class Pouzivatel implements Serializable {

    private int ID;
    private String meno;
    private String priezvisko;
    private String nick;
    private String heslo;
    private String email;
    private TypPouzivatela typPouzivatela;

    public Pouzivatel() {
    }

    public HashMap<String, String> konverziaNaHashMap() {
        HashMap<String, String> data = new HashMap<String, String>();

        data.put("ID", String.valueOf(this.ID));
        data.put("meno", this.meno);
        data.put("priezvisko", this.priezvisko);
        data.put("nick", this.nick);
        data.put("heslo", this.heslo);
        data.put("email", this.email);
        data.put("typPouzivatela", String.valueOf(this.typPouzivatela.getIdZaradenia()));

        return data;
    }

    public Pouzivatel(int ID, String meno, String priezvisko, String nick, String heslo, String email, TypPouzivatela typPouzivatela) {
        this.ID = ID;
        this.meno = meno;
        this.priezvisko = priezvisko;
        this.nick = nick;
        this.heslo = heslo;
        this.email = email;
        this.typPouzivatela = typPouzivatela;
    }

    public Pouzivatel(String meno, String priezvisko, String nick, String heslo, String email) {
        this.ID = 0;
        this.meno = meno;
        this.priezvisko = priezvisko;
        this.nick = nick;
        this.heslo = heslo;
        this.email = email;
        this.typPouzivatela = TypPouzivatela.REGULAR;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public String getPriezvisko() {
        return priezvisko;
    }

    public void setPriezvisko(String priezvisko) {
        this.priezvisko = priezvisko;
    }

    public String getHeslo() {
        return heslo;
    }

    public void setHeslo(String heslo) {
        this.heslo = heslo;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public TypPouzivatela getTypPouzivatela() {
        return typPouzivatela;
    }

    public void setTypPouzivatela(TypPouzivatela typPouzivatela) {
        this.typPouzivatela = typPouzivatela;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
