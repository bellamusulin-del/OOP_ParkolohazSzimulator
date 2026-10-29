package parkolo;

/** Normal car: 30 minutes free, then 5 lei/hour, daily maximum 40 lei. */
public class Auto extends Jarmu {

    private static final long INGYENES_PERCEK = 30;
    private static final double ORADIJ = 5;
    private static final double NAPI_PLAFON = 40;

    public Auto(String rendszam) {
        super(rendszam);
    }

    @Override
    public double dij(long percek) {
        return parkolasiDijSzamitasa(percek, INGYENES_PERCEK, ORADIJ, NAPI_PLAFON);
    }
}
