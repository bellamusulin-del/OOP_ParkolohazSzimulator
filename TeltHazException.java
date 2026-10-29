package parkolo;

/** Thrown when there is no free spot on any level. */
public class TeltHazException extends RuntimeException {

    public TeltHazException() {
        super("A parkolóház tele van, nincs szabad hely.");
    }
}
