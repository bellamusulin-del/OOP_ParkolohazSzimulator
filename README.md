# OOP_Parkolohaz_szimulator
Marosvásárhelyi háromszintes parkolóház szoftvere – az Open/Closed Principle (OCP) gyakorlására.
**Új járműtípus = új osztály**, a `Parkolohaz` és a `Jarmu` kódja nem változik.

## Felépítés
- `Jarmu` (absztrakt): rendszám-ellenőrzés + közös díjsablon (Template Method: `parkolasiDijSzamitasa`)
- `Auto`, `Motor`, `Kisbusz`, `ElektromosAuto`: mindegyiknek saját díjszabása
- `Parkolohaz`: szintek, behajtás, kihajtás, bevétel – **nincs** benne `instanceof`/`switch`
- `Ora` interfész: `RendszerOra` (valódi idő) és `FixOra` (teszt, kézzel állítható)
- UML: `docs/osztalydiagram.puml` (PlantUML; megnyitható plantuml.com/plantuml oldalon is, PNG-t exportálhatsz belőle)

## Díjszabás
| Jármű | Ingyenes | Óradíj | Napi plafon | Extra |
|---|---|---|---|---|
| Autó | 30 perc | 5 lej | 40 lej | – |
| Motor | 30 perc | 2 lej | 20 lej | – |
| Kisbusz | 15 perc | 10 lej | 80 lej | – |
| Elektromos autó | 30 perc | 5 lej | 40 lej | 2 lej / kWh töltés |

Minden összeg lejben (RON) értendő. Minden megkezdett óra számít. Egy egész nap (1440 perc) a napi plafont kéri.

## Fordítás és futtatás
Szükséges: JDK 17+ és Maven.

```
mvn test                       (tesztek futtatása)
mvn compile
java -cp target/classes parkolo.Main
```
NetBeansben: *File → Open Project*, a `pom.xml` mappáját válaszd, majd futtasd a `Main` osztályt.

## Példa konzolkimenet
```
--------------------------------
  Belvarosi parkolohaz
--------------------------------

1 - Behajtas
2 - Kihajtas (dijfizetes)
3 - Foglaltsag szintenkent
4 - Napi bevetel
0 - Kilepes
Valasztas: 1
Milyen jarmuvel erkezett?
  1 - Auto
  2 - Motor
  3 - Kisbusz
  4 - Elektromos auto
Tipus szama: 1
Rendszam: ms 12 abc
MS 12 ABC beengedve, 1. szint.

Valasztas: 2
Rendszam: MS 12 ABC
Fizetendo: 0 lej

Valasztas: 3
1. szint: 0 / 5 foglalt
2. szint: 0 / 5 foglalt
3. szint: 0 / 5 foglalt
```

## Lakóautó-próba
1. Új fájl: `Lakoauto.java` (`extends Jarmu`, saját `dij`).
2. `Jarmu`, `Parkolohaz`, `Ora`: **0 módosítás**.
3. A konzolos menüben egyetlen új sor kell, hogy a típus választható legyen (`Main` regisztrációs rész).
