package parkolo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ParkolohazTeszt {

    @Test
    void teltHazEsetenKivetelDobodik() {
        FixOra fixOra = new FixOra(1000);
        // 1 level, 2 spots
        Parkolohaz kicsiParkolohaz = new Parkolohaz(fixOra, 1, 2);

        kicsiParkolohaz.behajt(new Auto("MS 01 AAA"));
        kicsiParkolohaz.behajt(new Auto("MS 02 BBB"));

        assertThrows(TeltHazException.class, () -> kicsiParkolohaz.behajt(new Auto("MS 03 CCC")));
    }

    @Test
    void ismeretlenRendszamKihajtasaKivetelDobodik() {
        Parkolohaz parkolohaz = new Parkolohaz(new FixOra(0), 3, 5);
        assertThrows(IsmeretlenRendszamException.class, () -> parkolohaz.kihajt("XX 99 XXX"));
    }

    @Test
    void kihajtasKiszamoljaADijatAPorgetettIdovel() {
        FixOra fixOra = new FixOra(0);
        Parkolohaz parkolohaz = new Parkolohaz(fixOra, 3, 5);

        parkolohaz.behajt(new Auto("MS 01 ABC"));
        fixOra.percetEloreTekeres(61);

        // 61 minutes: 1 paid started hour -> 5 lei
        double fizetettDij = parkolohaz.kihajt("ms 01 abc");
        assertEquals(5, fizetettDij);
    }

    @Test
    void kihajtasUtanFelszabadulAHelyEsNovekszikABevetel() {
        FixOra fixOra = new FixOra(0);
        Parkolohaz parkolohaz = new Parkolohaz(fixOra, 1, 1);

        parkolohaz.behajt(new Motor("MS 05 MOT"));
        fixOra.percetEloreTekeres(31);
        parkolohaz.kihajt("MS 05 MOT");

        assertEquals(0, parkolohaz.getFoglaltHelyekSzama(0));
        assertEquals(2, parkolohaz.getNapiBevetel());

        // Driving in again works, because the spot became free
        parkolohaz.behajt(new Auto("MS 06 NEW"));
        assertEquals(1, parkolohaz.getFoglaltHelyekSzama(0));
    }

    @Test
    void elsoSzintTeleMasodikSzintreKerulAKovetkezoAuto() {
        Parkolohaz parkolohaz = new Parkolohaz(new FixOra(0), 3, 1);

        int elsoAutoSzintje = parkolohaz.behajt(new Auto("MS 01 AAA"));
        int masodikAutoSzintje = parkolohaz.behajt(new Auto("MS 02 BBB"));

        assertEquals(0, elsoAutoSzintje);
        assertEquals(1, masodikAutoSzintje);
    }

    @Test
    void ketszeriBehajtasUgyanazzalARendszammalKivetelDobodik() {
        Parkolohaz parkolohaz = new Parkolohaz(new FixOra(0), 3, 5);
        parkolohaz.behajt(new Auto("MS 01 ABC"));

        assertThrows(IllegalStateException.class, () -> parkolohaz.behajt(new Motor("ms 01 abc")));
    }
}
