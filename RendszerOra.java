package parkolo;

/**
 * The real clock: it reads the computer's system clock.
 */
public class RendszerOra implements Ora {

    private static final long EZREDMASODPERC_EGY_PERCBEN = 60000;

    @Override
    public long most() {
        long ezredmasodpercekMost = System.currentTimeMillis();
        long percekMost = ezredmasodpercekMost / EZREDMASODPERC_EGY_PERCBEN;
        return percekMost;
    }
}
