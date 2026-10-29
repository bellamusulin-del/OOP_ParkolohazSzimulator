package parkolo;

/**
 * The source of time. This way the clock can be "fast-forwarded" in the tests,
 * while the real program uses the system clock.
 */
public interface Ora {

    // Returns the current time in MINUTES (only the difference between two values matters)
    long most();
}
