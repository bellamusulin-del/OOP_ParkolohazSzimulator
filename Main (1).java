package parkolo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Console menu for the downtown parking garage.
 */
public class Main {

    private static final String ELVALASZTO_VONAL = "--------------------------------";

    // Names and factories of the vehicle types in the menu (same order in both lists)
    private static final List<String> elerhetoTipusNevek = new ArrayList<>();
    private static final List<JarmuGyarto> elerhetoTipusGyartok = new ArrayList<>();

    public static void main(String[] args) {
        // We "register" the types here. Next year the Lakoauto will be added here as one new line.
        elerhetoTipusNevek.add("Auto");
        elerhetoTipusGyartok.add((rendszam, konzolBemenet) -> new Auto(rendszam));

        elerhetoTipusNevek.add("Motor");
        elerhetoTipusGyartok.add((rendszam, konzolBemenet) -> new Motor(rendszam));

        elerhetoTipusNevek.add("Kisbusz");
        elerhetoTipusGyartok.add((rendszam, konzolBemenet) -> new Kisbusz(rendszam));

        elerhetoTipusNevek.add("Elektromos auto");
        elerhetoTipusGyartok.add((rendszam, konzolBemenet) -> elektromosAutoLetrehozasa(rendszam, konzolBemenet));

        Ora rendszerIdoforras = new RendszerOra();
        Parkolohaz belvarosiParkolohaz = new Parkolohaz(rendszerIdoforras, 3, 5);
        Scanner konzolBemenet = new Scanner(System.in);

        System.out.println(ELVALASZTO_VONAL);
        System.out.println("  Belvarosi parkolohaz");
        System.out.println(ELVALASZTO_VONAL);

        boolean futAMenu = true;
        while (futAMenu) {
            menuKiirasa();
            String menuValasztas = konzolBemenet.nextLine().trim();

            // The switch checks which menu item was chosen (not the vehicle type)
            switch (menuValasztas) {
                case "1":
                    behajtasKezelese(belvarosiParkolohaz, konzolBemenet);
                    break;
                case "2":
                    kihajtasKezelese(belvarosiParkolohaz, konzolBemenet);
                    break;
                case "3":
                    foglaltsagKiirasa(belvarosiParkolohaz);
                    break;
                case "4":
                    System.out.println("Napi bevetel: " + formazottOsszeg(belvarosiParkolohaz.getNapiBevetel()));
                    break;
                case "0":
                    futAMenu = false;
                    break;
                default:
                    System.out.println("Ismeretlen menupont, probald ujra!");
                    break;
            }
        }

        System.out.println("Viszlat!");
        konzolBemenet.close();
    }

    private static void menuKiirasa() {
        System.out.println();
        System.out.println("1 - Behajtas");
        System.out.println("2 - Kihajtas (dijfizetes)");
        System.out.println("3 - Foglaltsag szintenkent");
        System.out.println("4 - Napi bevetel");
        System.out.println("0 - Kilepes");
        System.out.print("Valasztas: ");
    }

    private static void behajtasKezelese(Parkolohaz parkolohaz, Scanner konzolBemenet) {
        System.out.println("Milyen jarmuvel erkezett?");
        for (int tipusIndex = 0; tipusIndex < elerhetoTipusNevek.size(); tipusIndex++) {
            int megjelenitettSorszam = tipusIndex + 1;
            System.out.println("  " + megjelenitettSorszam + " - " + elerhetoTipusNevek.get(tipusIndex));
        }
        System.out.print("Tipus szama: ");
        String tipusSzovege = konzolBemenet.nextLine().trim();

        int valasztottTipusIndex;
        try {
            valasztottTipusIndex = Integer.parseInt(tipusSzovege) - 1;
        } catch (NumberFormatException hiba) {
            System.out.println("Ez nem szam volt!");
            return;
        }

        boolean ervenyesATipus = valasztottTipusIndex >= 0 && valasztottTipusIndex < elerhetoTipusGyartok.size();
        if (!ervenyesATipus) {
            System.out.println("Ilyen tipus nincs a listan!");
            return;
        }

        System.out.print("Rendszam: ");
        String beirtRendszam = konzolBemenet.nextLine();

        try {
            JarmuGyarto valasztottGyarto = elerhetoTipusGyartok.get(valasztottTipusIndex);
            Jarmu beerkezoJarmu = valasztottGyarto.letrehoz(beirtRendszam, konzolBemenet);

            int szintIndex = parkolohaz.behajt(beerkezoJarmu);
            int megjelenitettSzint = szintIndex + 1;
            System.out.println(beerkezoJarmu.getRendszam() + " beengedve, " + megjelenitettSzint + ". szint.");
        } catch (TeltHazException hiba) {
            System.out.println(hiba.getMessage());
        } catch (IllegalArgumentException | IllegalStateException hiba) {
            System.out.println(hiba.getMessage());
        }
    }

    private static void kihajtasKezelese(Parkolohaz parkolohaz, Scanner konzolBemenet) {
        System.out.print("Rendszam: ");
        String tavozoRendszam = konzolBemenet.nextLine();

        try {
            double kiirandoDij = parkolohaz.kihajt(tavozoRendszam);
            System.out.println("Fizetendo: " + formazottOsszeg(kiirandoDij));
        } catch (IsmeretlenRendszamException hiba) {
            System.out.println(hiba.getMessage());
        }
    }

    private static void foglaltsagKiirasa(Parkolohaz parkolohaz) {
        for (int szintIndex = 0; szintIndex < parkolohaz.getSzintekSzama(); szintIndex++) {
            int megjelenitettSzint = szintIndex + 1;
            int foglaltHelyek = parkolohaz.getFoglaltHelyekSzama(szintIndex);
            int osszesHely = parkolohaz.getHelyekSzintenkent();
            System.out.println(megjelenitettSzint + ". szint: " + foglaltHelyek + " / " + osszesHely + " foglalt");
        }
    }

    // For the electric car we also ask how many kWh it charged
    private static Jarmu elektromosAutoLetrehozasa(String rendszam, Scanner konzolBemenet) {
        System.out.print("Hany kWh-t tolt? ");
        String toltesSzovege = konzolBemenet.nextLine().trim();

        double toltottKilowattOrak;
        try {
            toltottKilowattOrak = Double.parseDouble(toltesSzovege);
        } catch (NumberFormatException hiba) {
            throw new IllegalArgumentException("A toltes mennyisege nem szam!");
        }

        return new ElektromosAuto(rendszam, toltottKilowattOrak);
    }

    private static String formazottOsszeg(double osszeg) {
        return String.format("%.0f lej", osszeg);
    }
}
