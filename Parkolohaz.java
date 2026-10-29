package parkolo;

import java.util.HashMap;
import java.util.Map;

/**
 * The parking garage: levels, spots, entry, exit, income.
 * IMPORTANT: there is no instanceof or switch on the vehicle type anywhere here,
 * the fee is always calculated by the vehicle's own dij() method (polymorphism).
 */
public class Parkolohaz {

    private final Ora idoforras;
    private final int szintekSzama;
    private final int helyekSzintenkent;

    // How many spots are taken on each level (the array index is the level number, from 0)
    private final int[] szintenkentiFoglaltsag;

    // License plate -> ticket, so we can quickly find the vehicle that is inside
    private final Map<String, ParkolasiJegy> benntParkoloJegyek = new HashMap<>();

    private double napiKassza = 0;

    public Parkolohaz(Ora idoforras, int szintekSzama, int helyekSzintenkent) {
        if (idoforras == null) {
            throw new IllegalArgumentException("Az óra nem lehet null!");
        }
        if (szintekSzama <= 0 || helyekSzintenkent <= 0) {
            throw new IllegalArgumentException("A szintek és a helyek száma legyen pozitív!");
        }

        this.idoforras = idoforras;
        this.szintekSzama = szintekSzama;
        this.helyekSzintenkent = helyekSzintenkent;
        this.szintenkentiFoglaltsag = new int[szintekSzama];
    }

    /** Entry: finds the first free spot. Returns the index of the level (from 0). */
    public int behajt(Jarmu belepoJarmu) {
        if (belepoJarmu == null) {
            throw new IllegalArgumentException("A jármű nem lehet null!");
        }

        String rendszam = belepoJarmu.getRendszam();

        // A license plate can only be inside once
        if (benntParkoloJegyek.containsKey(rendszam)) {
            throw new IllegalStateException("Ez a jármű már bent van: " + rendszam);
        }

        int elsoUresSzint = elsoSzabadSzintKeresese();
        if (elsoUresSzint == -1) {
            throw new TeltHazException();
        }

        szintenkentiFoglaltsag[elsoUresSzint]++;

        long erkezesiIdopont = idoforras.most();
        ParkolasiJegy kiallitottJegy = new ParkolasiJegy(belepoJarmu, elsoUresSzint, erkezesiIdopont);
        benntParkoloJegyek.put(rendszam, kiallitottJegy);

        return elsoUresSzint;
    }

    /** Exit: calculates and collects the fee, frees the spot. */
    public double kihajt(String rendszam) {
        if (rendszam == null) {
            throw new IsmeretlenRendszamException("(üres)");
        }

        String normalizaltRendszam = Jarmu.rendszamTisztitasa(rendszam);
        ParkolasiJegy jegy = benntParkoloJegyek.get(normalizaltRendszam);

        if (jegy == null) {
            throw new IsmeretlenRendszamException(normalizaltRendszam);
        }

        long tavozasiIdopont = idoforras.most();
        long eltoltPercek = tavozasiIdopont - jegy.getErkezesiIdopont();

        // This is the polymorphism: we do not know (and do not need to know) what kind of vehicle this is
        double kiszamoltDij = jegy.getJarmu().dij(eltoltPercek);

        szintenkentiFoglaltsag[jegy.getSzintIndex()]--;
        benntParkoloJegyek.remove(normalizaltRendszam);
        napiKassza = napiKassza + kiszamoltDij;

        return kiszamoltDij;
    }

    private int elsoSzabadSzintKeresese() {
        for (int szintIndex = 0; szintIndex < szintekSzama; szintIndex++) {
            boolean vanUresHely = szintenkentiFoglaltsag[szintIndex] < helyekSzintenkent;
            if (vanUresHely) {
                return szintIndex;
            }
        }
        return -1;
    }

    public int getFoglaltHelyekSzama(int szintIndex) {
        return szintenkentiFoglaltsag[szintIndex];
    }

    public int getSzintekSzama() {
        return szintekSzama;
    }

    public int getHelyekSzintenkent() {
        return helyekSzintenkent;
    }

    public double getNapiBevetel() {
        return napiKassza;
    }
}
