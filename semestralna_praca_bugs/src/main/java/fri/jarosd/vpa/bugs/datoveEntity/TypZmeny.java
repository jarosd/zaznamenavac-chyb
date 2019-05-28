package fri.jarosd.vpa.bugs.datoveEntity;

public enum TypZmeny {

    // https://stackoverflow.com/questions/43551201/java-mybatis-enum-string-value
    // http://www.mybatis.org/mybatis-3/configuration.html

    INSERT("INSERT"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    private String typZmeny;

    TypZmeny() {}

    TypZmeny(String typZmeny) {
        this.typZmeny = typZmeny;
    }

    public String toString() {
        return this.typZmeny;
    }

    public void setTypZmeny(String typZmeny) { this.typZmeny = typZmeny; }

    public static TypZmeny getEnum(String hodnota) {
        for (TypZmeny typ : TypZmeny.values()) {
            if (typ.toString().equals(hodnota)) {
                return typ;
            }
        }

        return null;
    }
}
