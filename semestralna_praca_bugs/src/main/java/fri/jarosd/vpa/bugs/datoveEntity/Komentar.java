package fri.jarosd.vpa.bugs.datoveEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Timestamp;

public class Komentar {

    private int idKomentara;
    private int idChyby;
    private String autor;
    private String textKomentara;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss", timezone="Europe/Bratislava")
    private Timestamp casVlozenia;

    public Komentar(int idKomentara, int idChyby, String autor, String textKomentara, Timestamp casVlozenia) {
        this.idKomentara = idKomentara;
        this.idChyby = idChyby;
        this.autor = autor;
        this.textKomentara = textKomentara;
        this.casVlozenia = casVlozenia;
    }

    public Komentar() {

    }

    public int getIdKomentara() {
        return idKomentara;
    }

    public void setIdKomentara(int idKomentara) {
        this.idKomentara = idKomentara;
    }

    public int getIdChyby() {
        return idChyby;
    }

    public void setIdChyby(int idChyby) {
        this.idChyby = idChyby;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTextKomentara() {
        return textKomentara;
    }

    public void setTextKomentara(String textKomentara) {
        this.textKomentara = textKomentara;
    }

    public Timestamp getCasVlozenia() {
        return casVlozenia;
    }

    public void setCasVlozenia(Timestamp casVlozenia) {
        this.casVlozenia = casVlozenia;
    }
}
