package parkolo;

/** Thrown when someone tries to leave but is not inside the parking garage. */
public class IsmeretlenRendszamException extends RuntimeException {

    public IsmeretlenRendszamException(String rendszam) {
        super("Ez a rendszám nincs bent a parkolóházban: " + rendszam);
    }
}
