package parkolo;

import java.util.Scanner;

/**
 * The "production line" of one vehicle type for the menu.
 * A new type (e.g. Lakoauto) only gets one new line like this in the menu.
 */
public interface JarmuGyarto {

    Jarmu letrehoz(String rendszam, Scanner billentyuzet);
}
