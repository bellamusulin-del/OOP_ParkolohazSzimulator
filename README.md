#OOP_ParkolohazSzimulator

OOP_Parking_Garage_SimulatorSoftware for a three-story parking garage in Târgu Mureș – designed for practicing the Open/Closed Principle (OCP).New vehicle type = new class, without modifying the code of Parkolohaz or Jarmu.StructureJarmu (abstract): license plate validation + shared fee template (Template Method: parkolasiDijSzamitasa)Auto, Motor, Kisbusz, ElektromosAuto: each with its own pricing rulesParkolohaz: floors, entry, exit, revenue – contains no instanceof / switch statementsOra interface: RendszerOra (real time) and FixOra (test clock, manually adjustable)UML: docs/osztalydiagram.puml (PlantUML; can also be opened at plantuml.com/plantuml to export as PNG)PricingVehicleFree periodHourly rateDaily capExtraCar30 min5 RON40 RON–Motorcycle30 min2 RON20 RON–Minibus15 min10 RON80 RON–Electric Car30 min5 RON40 RON2 RON / kWh chargingAll amounts are in Lei (RON). Every started hour counts. A full day (1440 minutes) incurs the daily cap.Build and ExecutionRequirements: JDK 17+ and Maven.mvn test                        (run tests)
mvn compile
java -cp target/classes parkolo.Main
In NetBeans: File $\rightarrow$ Open Project, select the directory containing pom.xml, then run the Main class.Example Console Output--------------------------------
  Downtown Parking Garage
--------------------------------

1 - Entry
2 - Exit (Payment)
3 - Occupancy by floor
4 - Daily revenue
0 - Exit program
Choice: 1
What type of vehicle arrived?
  1 - Car
  2 - Motorcycle
  3 - Minibus
  4 - Electric car
Type number: 1
License plate: ms 12 abc
MS 12 ABC allowed entry, 1st floor.

Choice: 2
License plate: MS 12 ABC
Amount due: 0 RON

Choice: 3
1st floor: 0 / 5 occupied
2nd floor: 0 / 5 occupied
3rd floor: 0 / 5 occupied
