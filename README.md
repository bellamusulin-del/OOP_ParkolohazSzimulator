#OOP_ParkolohazSzimulator

# OOP_Parkolohaz_szimulator

Software for a three-level parking garage in Târgu Mureș – designed to practice the Open/Closed Principle (OCP).

**New vehicle type = new class.** The code of `Parkolohaz` and `Jarmu` does not need to change.

## Structure

- `Jarmu` (abstract): license plate validation and a shared fee calculation template (Template Method: `parkolasiDijSzamitasa`)
- `Auto`, `Motor`, `Kisbusz`, `ElektromosAuto`: each vehicle type has its own pricing rules
- `Parkolohaz`: manages levels, vehicle entry and exit, and revenue – **does not** contain `instanceof` or `switch`
- `Ora` interface: `RendszerOra` (real time) and `FixOra` (for testing, with manually adjustable time)
- UML: `docs/osztalydiagram.puml` (PlantUML; can also be opened at [plantuml.com/plantuml](https://plantuml.com/plantuml) and exported as a PNG)

## Pricing

| Vehicle | Free parking | Hourly rate | Daily maximum | Extra |
|---|---|---|---|---|
| Car | 30 minutes | 5 RON | 40 RON | – |
| Motorcycle | 30 minutes | 2 RON | 20 RON | – |
| Minibus | 15 minutes | 10 RON | 80 RON | – |
| Electric car | 30 minutes | 5 RON | 40 RON | 2 RON per kWh charged |

All amounts are in Romanian lei (RON). Every started hour is charged. A full day (1440 minutes) is charged at the daily maximum.

## Compilation and Execution

Requirements: JDK 17+ and Maven.

```bash
mvn test
mvn compile
java -cp target/classes parkolo.Main
```

In NetBeans: select *File → Open Project*, choose the folder containing `pom.xml`, and then run the `Main` class.

## Example Console Output

```text
--------------------------------
  City Center Parking Garage
--------------------------------

1 - Enter parking garage
2 - Exit parking garage (pay fee)
3 - Occupancy by level
4 - Daily revenue
0 - Exit

Choice: 1
Which vehicle are you arriving with?
  1 - Car
  2 - Motorcycle
  3 - Minibus
  4 - Electric car

Vehicle type number: 1
License plate: ms 12 abc

MS 12 ABC admitted, level 1.

Choice: 2
License plate: MS 12 ABC

Amount due: 0 RON

Choice: 3

Level 1: 0 / 5 occupied
Level 2: 0 / 5 occupied
Level 3: 0 / 5 occupied
```

