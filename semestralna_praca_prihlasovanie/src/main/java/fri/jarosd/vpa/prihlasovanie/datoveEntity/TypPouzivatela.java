package fri.jarosd.vpa.prihlasovanie.datoveEntity;

public enum TypPouzivatela {
    REGULAR(1),
    ADMIN(2),
    SUPERADMIN(3);

    private final int idZaradenia;

    TypPouzivatela(int idZaradenia) {
        this.idZaradenia = idZaradenia;
    }

    public int getIdZaradenia() {
        return this.idZaradenia;
    }
}
