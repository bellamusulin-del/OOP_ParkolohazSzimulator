package parkolo;

/**
 * Electric car: parking costs the same as for a normal car,
 * but charging is paid separately (2 lei / kWh).
 */
public class ElektromosAuto extends Jarmu {

    private static final long INGYENES_PERCEK = 30;
    private static final double ORADIJ = 5;
    private static final double NAPI_PLAFON = 40;
    private static final double TOLTES_ARA_KWH_ONKENT = 2;

    private final double toltottKilowattOrak;

    public ElektromosAuto(String rendszam, double toltottKilowattOrak) {
        super(rendszam);

        if (toltottKilowattOrak < 0) {
            throw new IllegalArgumentException("A töltött energia nem lehet negatív!");
        }
        this.toltottKilowattOrak = toltottKilowattOrak;
    }

    public double getToltottKilowattOrak() {
        return toltottKilowattOrak;
    }

    @Override
    public double dij(long percek) {
        double parkolasiResz = parkolasiDijSzamitasa(percek, INGYENES_PERCEK, ORADIJ, NAPI_PLAFON);
        double toltesiResz = toltottKilowattOrak * TOLTES_ARA_KWH_ONKENT;
        return parkolasiResz + toltesiResz;
    }
}
