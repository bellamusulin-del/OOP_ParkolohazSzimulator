package parkolo;

/** A clock we can set by hand for the tests: time only passes when we want it to. */
public class FixOra implements Ora {

    private long aktualisPerc;

    public FixOra(long kezdoPerc) {
        this.aktualisPerc = kezdoPerc;
    }

    public void percetEloreTekeres(long hanyPercet) {
        aktualisPerc = aktualisPerc + hanyPercet;
    }

    @Override
    public long most() {
        return aktualisPerc;
    }
}
