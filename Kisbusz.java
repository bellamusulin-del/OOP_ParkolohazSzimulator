package parkolo;

/** Minibus: only 15 minutes free, then 10 lei/hour, daily maximum 80 lei. */
public class Kisbusz extends Jarmu {

    private static final long INGYENES_PERCEK = 15;
    private static final double ORADIJ = 10;
    private static final double NAPI_PLAFON = 80;

    public Kisbusz(String rendszam) {
        super(rendszam);
    }

    @Override
    public double dij(long percek) {
        return parkolasiDijSzamitasa(percek, INGYENES_PERCEK, ORADIJ, NAPI_PLAFON);
    }
}
