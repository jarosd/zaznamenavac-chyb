package fri.jarosd.vpa.bugs.datoveEntity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = DolezitostDeserializer.class)
public class Dolezitost {

    private int dolezitost;
    private String popis;
    private String farba;

    public Dolezitost(int dolezitost, String popis, String farba) {
        this.dolezitost = dolezitost;
        this.popis = popis;
        this.farba = farba;
    }

    public Dolezitost() {

    }

    public int getDolezitost() {
        return dolezitost;
    }

    public void setDolezitost(int dolezitost) {
        this.dolezitost = dolezitost;
    }

    public String getPopis() {
        return popis;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public String getFarba() {
        return farba;
    }

    public void setFarba(String farba) {
        this.farba = farba;
    }
}
