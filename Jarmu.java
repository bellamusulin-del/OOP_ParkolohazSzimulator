package parkolo;

/**
 * Common ancestor of every vehicle.
 * New vehicle type = new subclass, nothing has to be changed in this class.
 */
public abstract class Jarmu {

    // How many minutes a day has
    protected static final long PERC_EGY_NAPBAN = 24 * 60;

    private final String rendszam;

    protected Jarmu(String rendszam) {
        // Step 1: null check
        if (rendszam == null) {
            throw new IllegalArgumentException("A rendszám nem lehet null!");
        }

        // Step 2: clean it up (trim spaces, make it uppercase)
        String normalizaltRendszam = rendszamTisztitasa(rendszam);

        // Step 3: empty check
        if (normalizaltRendszam.isEmpty()) {
            throw new IllegalArgumentException("A rendszám nem lehet üres!");
        }

        this.rendszam = normalizaltRendszam;
    }

    // We clean the license plate the same way at entry and at exit,
    // so that "ms 12 abc " and "MS 12 ABC" count as the same plate
    public static String rendszamTisztitasa(String beirtRendszam) {
        String levagottRendszam = beirtRendszam.trim();
        String nagybetusitettRendszam = levagottRendszam.toUpperCase();
        return nagybetusitettRendszam;
    }

    public String getRendszam() {
        return rendszam;
    }

    // Every subclass decides on its own how much parking costs for it
    public abstract double dij(long percek);

    // The type name for printing (by default the name of the class)
    public String getTipusNeve() {
        return getClass().getSimpleName();
    }

    /**
     * Shared fee calculation template (subclasses only provide the numbers):
     * - the first X minutes are free
     * - after that every started hour costs the hourly rate
     * - within one day the fee cannot go above the daily cap
     * - every full day costs the daily cap, the remaining time is calculated again
     */
    protected double parkolasiDijSzamitasa(long percek, long ingyenesPercek,
                                           double oradij, double napiPlafon) {
        if (percek < 0) {
            throw new IllegalArgumentException("A parkolási idő nem lehet negatív!");
        }

        long teljesNapokDarab = percek / PERC_EGY_NAPBAN;
        long maradekPercekEgyNapon = percek % PERC_EGY_NAPBAN;

        double teljesNapokOsszege = teljesNapokDarab * napiPlafon;
        double csonkaNapOsszege = egyNapnalRovidebbIdoDija(maradekPercekEgyNapon, ingyenesPercek,
                oradij, napiPlafon);

        double vegosszeg = teljesNapokOsszege + csonkaNapOsszege;
        return vegosszeg;
    }

    // Helper method: the fee of a parking time shorter than one day
    private double egyNapnalRovidebbIdoDija(long percek, long ingyenesPercek,
                                            double oradij, double napiPlafon) {
        // If it fits into the free time, there is nothing to pay
        if (percek <= ingyenesPercek) {
            return 0;
        }

        long fizetendoPercek = percek - ingyenesPercek;

        // Round up: every started hour counts
        long szamlazottOrak = (fizetendoPercek + 59) / 60;

        double korlatozatlanDij = szamlazottOrak * oradij;

        // Apply the daily cap
        if (korlatozatlanDij > napiPlafon) {
            return napiPlafon;
        }
        return korlatozatlanDij;
    }

    @Override
    public String toString() {
        return getTipusNeve() + " [" + rendszam + "]";
    }
}
