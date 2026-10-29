package parkolo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/** Fee calculation edge cases (taken from the pricing table I made on paper). */
class DijszamitasTeszt {

    @Test
    void autoNullPercEsetenIngyenes() {
        Jarmu auto = new Auto("MS 01 ABC");
        assertEquals(0, auto.dij(0));
    }

    @Test
    void autoHarmincPercEsetenMegIngyenes() {
        Jarmu auto = new Auto("MS 01 ABC");
        assertEquals(0, auto.dij(30));
    }

    @Test
    void autoHarmincegyPercEsetenEgyOraDijatFizet() {
        Jarmu auto = new Auto("MS 01 ABC");
        assertEquals(5, auto.dij(31));
    }

    @Test
    void autoNapiPlafonNemLepheto() {
        Jarmu auto = new Auto("MS 01 ABC");
        // 720 minutes: 690 paid minutes = 12 hours = 60 lei, but the daily cap is 40 lei
        assertEquals(40, auto.dij(720));
    }

    @Test
    void autoEgeszNapEsetenPontosanEgyPlafont() {
        Jarmu auto = new Auto("MS 01 ABC");
        assertEquals(40, auto.dij(1440));
    }

    @Test
    void motorOlcsobbMintAAuto() {
        Jarmu motor = new Motor("MS 02 MOT");
        // 31 minutes: 1 started hour * 2 lei
        assertEquals(2, motor.dij(31));
    }

    @Test
    void kisbuszCsakTizenotPercetKapIngyen() {
        Jarmu kisbusz = new Kisbusz("MS 03 BUS");
        assertEquals(0, kisbusz.dij(15));
        assertEquals(10, kisbusz.dij(16));
    }

    @Test
    void elektromosAutoToltesiDijatIsFizet() {
        // 31 minutes of parking = 5 lei, 10 kWh of charging = 20 lei
        Jarmu elektromosAuto = new ElektromosAuto("MS 04 EVE", 10);
        assertEquals(25, elektromosAuto.dij(31));
    }

    @Test
    void uresEsNullRendszamNemFogadhatoEl() {
        assertThrows(IllegalArgumentException.class, () -> new Auto(null));
        assertThrows(IllegalArgumentException.class, () -> new Auto("   "));
    }
}
