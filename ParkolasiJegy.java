package parkolo;

/** Data of a parked vehicle: which vehicle it is, which level it is on and when it arrived. */
public class ParkolasiJegy {

    private final Jarmu jarmu;
    private final int szintIndex;
    private final long erkezesiIdopont;

    public ParkolasiJegy(Jarmu jarmu, int szintIndex, long erkezesiIdopont) {
        this.jarmu = jarmu;
        this.szintIndex = szintIndex;
        this.erkezesiIdopont = erkezesiIdopont;
    }

    public Jarmu getJarmu() {
        return jarmu;
    }

    public int getSzintIndex() {
        return szintIndex;
    }

    public long getErkezesiIdopont() {
        return erkezesiIdopont;
    }
}
