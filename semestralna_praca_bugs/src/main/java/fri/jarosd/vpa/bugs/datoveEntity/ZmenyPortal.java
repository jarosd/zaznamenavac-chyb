package fri.jarosd.vpa.bugs.datoveEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Timestamp;

public class ZmenyPortal {

    private int bugId;
    private String pouzivatel;
    private TypZmeny typZmeny;
    private String typAkcie;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss", timezone="Europe/Bratislava")
    private Timestamp casVykonania;

    public ZmenyPortal(int bugId, String pouzivatel, TypZmeny typZmeny, String typAkcie, Timestamp casVykonania) {
        this.bugId = bugId;
        this.pouzivatel = pouzivatel;
        this.typZmeny = typZmeny;
        this.typAkcie = typAkcie;
        this.casVykonania = casVykonania;
    }

    public ZmenyPortal() {

    }

    public int getBugId() {
        return bugId;
    }

    public void setBugId(int bugId) {
        this.bugId = bugId;
    }

    public String getPouzivatel() {
        return pouzivatel;
    }

    public void setPouzivatel(String pouzivatel) {
        this.pouzivatel = pouzivatel;
    }

    public TypZmeny getTypZmeny() {
        return typZmeny;
    }

    public void setTypZmeny(TypZmeny typZmeny) {
        this.typZmeny = typZmeny;
    }

    public String getTypAkcie() {
        return typAkcie;
    }

    public void setTypAkcie(String typAkcie) {
        this.typAkcie = typAkcie;
    }

    public Timestamp getCasVykonania() {
        return casVykonania;
    }

    public void setCasVykonania(Timestamp casVykonania) {
        this.casVykonania = casVykonania;
    }
}
