package fri.jarosd.vpa.bugs.datoveEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Timestamp;

public class Bug {

    private int chybaID;
    private String nazovChyby;
    private String popisChyby;
    private String autor;

    // https://stackoverflow.com/questions/7556851/set-jackson-timezone-for-date-deserialization
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss", timezone="Europe/Bratislava")
    private Timestamp datumVytvorenia;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss", timezone="Europe/Bratislava")
    private Timestamp datumUkoncenia;

    private Dolezitost dolezitostObjekt;
    private String umiestnenie;

    public Bug(int chybaID, String nazovChyby, String popisChyby, String autor, Timestamp datumVytvorenia, Timestamp datumUkoncenia, Dolezitost dolezitostObjekt, String umiestnenie) {
        this.chybaID = chybaID;
        this.nazovChyby = nazovChyby;
        this.popisChyby = popisChyby;
        this.autor = autor;
        this.datumVytvorenia = datumVytvorenia;
        this.datumUkoncenia = datumUkoncenia;
        this.dolezitostObjekt = dolezitostObjekt;
        this.umiestnenie = umiestnenie;
    }

    public Bug(int chybaID, String nazovChyby, String popisChyby, String autor, Timestamp datumVytvorenia, Timestamp datumUkoncenia, int dolezitost, String umiestnenie) {
        this.chybaID = chybaID;
        this.nazovChyby = nazovChyby;
        this.popisChyby = popisChyby;
        this.autor = autor;
        this.datumVytvorenia = datumVytvorenia;
        this.datumUkoncenia = datumUkoncenia;
        this.dolezitostObjekt = new Dolezitost(dolezitost, null, null);
        this.umiestnenie = umiestnenie;
    }

    public Bug() {
    }

    public int getChybaID() {
        return chybaID;
    }

    public void setChybaID(int chybaID) {
        this.chybaID = chybaID;
    }

    public String getNazovChyby() {
        return nazovChyby;
    }

    public void setNazovChyby(String nazovChyby) {
        this.nazovChyby = nazovChyby;
    }

    public String getPopisChyby() {
        return popisChyby;
    }

    public void setPopisChyby(String popisChyby) {
        this.popisChyby = popisChyby;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Timestamp getDatumVytvorenia() {
        return datumVytvorenia;
    }

    public void setDatumVytvorenia(Timestamp datumVytvorenia) {
        this.datumVytvorenia = datumVytvorenia;
    }

    public Timestamp getDatumUkoncenia() {
        return datumUkoncenia;
    }

    public void setDatumUkoncenia(Timestamp datumUkoncenia) {
        this.datumUkoncenia = datumUkoncenia;
    }

    public Dolezitost getDolezitostObjekt() {
        return dolezitostObjekt;
    }

    public void setDolezitostObjekt(Dolezitost dolezitostObjekt) {
        this.dolezitostObjekt = dolezitostObjekt;
    }

    public void setDolezitostObjekt(int dolezitost) {
        this.dolezitostObjekt = new Dolezitost(dolezitost, null, null);
    }

    public String getUmiestnenie() {
        return umiestnenie;
    }

    public void setUmiestnenie(String umiestnenie) {
        this.umiestnenie = umiestnenie;
    }
}
