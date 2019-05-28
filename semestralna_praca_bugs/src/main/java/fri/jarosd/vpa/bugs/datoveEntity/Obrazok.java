package fri.jarosd.vpa.bugs.datoveEntity;

public class Obrazok {

    private int obrazokId;
    private int chybaId;
    private String nazovObrazka;
    private String autor;
    private String cestaObrazok;

    public Obrazok(int obrazokId, int chybaId, String nazovObrazka, String autor, String cestaObrazok) {
        this.obrazokId = obrazokId;
        this.chybaId = chybaId;
        this.nazovObrazka = nazovObrazka;
        this.autor = autor;
        this.cestaObrazok = cestaObrazok;
    }

    public Obrazok() {

    }

    public int getObrazokId() {
        return obrazokId;
    }

    public void setObrazokId(int obrazokId) {
        this.obrazokId = obrazokId;
    }

    public int getChybaId() {
        return chybaId;
    }

    public void setChybaId(int chybaId) {
        this.chybaId = chybaId;
    }

    public String getNazovObrazka() {
        return nazovObrazka;
    }

    public void setNazovObrazka(String nazovObrazka) {
        this.nazovObrazka = nazovObrazka;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCestaObrazok() {
        return cestaObrazok;
    }

    public void setCestaObrazok(String cestaObrazok) {
        this.cestaObrazok = cestaObrazok;
    }
}
