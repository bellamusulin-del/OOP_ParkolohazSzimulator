package parkolo;

/** Motorbike: 30 minutes free, then 2 lei/hour, daily maximum 20 lei. */
public class Motor extends Jarmu {

    private static final long INGYENES_PERCEK = 30;
    private static final double ORADIJ = 2;
    private static final double NAPI_PLAFON = 20;

    public Motor(String rendszam) {
        super(rendszam);
    }

    @Override
    public double dij(long percek) {
        return parkolasiDijSzamitasa(percek, INGYENES_PERCEK, ORADIJ, NAPI_PLAFON);
    }
}
