package scenario_creation_package;
import java.util.LinkedList;
import icons.IconManager;

public class ScenarioManager 
{
	private static LinkedList<Scenario> scenarios;

	/**
	 * Fills the scenario list by calling all creation methods.
	 */
	public static void createScenarios()
	{
		scenarios = new LinkedList<Scenario>();
		
		scenarios.add(createLakeScenario());

		scenarios.add(createBayScenario());
		
		scenarios.add(createSeaScenario());
		
		scenarios.add(createOceanScenario());
	}
	
	private static Scenario createLakeScenario()
	{
		int amountOfPeople = 33;
		int clusters = 4;
		int size = 10;
		String description = "A harsh storm summoned fast. The fishermen did not have time to leave it. 4 boats and 30 people are missing."
				+ "The local fire department gave you all the ressources it has. Its not much but its all they have.";
		Vehicle smallAirTanker = new Vehicle ("Small Airtanker", 2, 4, new Step[] {new Step(2, 3, false)}, false, "Searches a small area and marks found survivors", IconManager.smallAirtanker1);
		Vehicle localPilot = new Vehicle ("Local Pilot", 1, 6, new Step[] {new Step(2, 1, false), new Step (1, 1, false),new Step(2, 1, false), new Step(1, 1, false), new Step(2, 1, false), new Step(3, 1, false)}, false, "Hobby pilot offers to search. But he will not change his course.", IconManager.hobbyPilot, false, false);
		Vehicle largeAirtanker = new Vehicle ("Large Aitranker", 1, 3, new Step[] {new Step(3, 4, false)}, false, "Searches a big area and marks found survivors", IconManager.bigPlane1);
		Vehicle Dinghie = new Vehicle ("Dinghie", 5, 1, new Step[] {new Step(1, 1, false)}, true, "Small but fast. Rescues survivors", IconManager.dinghie1);
		Vehicle fisherBoat = new Vehicle("Local Fisher", 3, 2, new Step[] {new Step(3, 1, false)}, true, "Some fishers are ready to help us.", IconManager.fisher1);
		Sonar shortRangeSonar = new Sonar ("Short Range Sonar Buoy", 2, 5, 0.4f, "Uses sonar to listen for survivors. Green = loud, Red = silence", IconManager.buoyv1);
		Scenario lake = new Scenario("Lake",
				size, clusters, amountOfPeople, description,      		
    		new Asset[]{fisherBoat, smallAirTanker, localPilot, largeAirtanker, Dinghie, shortRangeSonar});
		
		return lake;
	}
	
	private static Scenario createBayScenario()
	{
		int amountOfPeople = 42;
		int clusters = 5;
		int size = 15;
		String description = "The calm waters of the bay were quickly turned into a dangerous trap when an unexpected fog rolled in. "
				+ "5 groups of recreational sailors are missing. The coast guard has mobilized what they can.";
		Vehicle oiltanker = new Vehicle ("Oiltanker", 1, 2, new Step[] {new Step(15, 1, false)}, true, "An Oiltanker passes the water. We can ask the crew to choose a certain latitude", IconManager.oiltanker, false, true);
		Vehicle scoutplane = new Vehicle ("Scoutplane", 3, 6, new Step[] {new Step(2, 3, false)}, false, "Searches a big area and marks found survivors", IconManager.smallAirtanker1);
		Vehicle dinghie = new Vehicle ("Dinghie", 2, 1, new Step[] {new Step(1, 1, false)}, true, "Small but fast. Rescues survivors", IconManager.dinghie1);
		Vehicle rescueShip = new Vehicle ("Rescue Ship", 3, 3,new Step[] {new Step(3, 3, false)}, true, "Ship specialized on rescue operations. Rescues survivors.", IconManager.bigShip1);
		Sonar sonarV1 = new Sonar ("Sonar Buoy V1", 3, 5, 0.3f, "Uses sonar to listen for survivors. Green = loud, Red = silence", IconManager.buoyv1);
		Sonar sonarV2 = new Sonar ("Sonar Buoy V2", 1, 7, 0.25f, "Uses sonar to listen for survivors. Improved range and quality. Green = loud, Red = silence", IconManager.buoyv2);
		Scenario bay = new Scenario("Bay", size, clusters, amountOfPeople, description,
        		new Asset[]{oiltanker, scoutplane, dinghie, rescueShip, sonarV1, sonarV2});
		return bay;
	}
	
	private static Scenario createSeaScenario()
	{
		int amountOfPeople = 97;
		int clusters = 6;
		int size = 20;
		String description = "A wealthy pair had their weeding on a yacht. Something happened that made it sink. As far as we know 97 people managed to get on 6 inflatable rafts. A strong current spread those out in unpredictable ways. The waves are high, the weather terrible.";
		Vehicle privateYacht = new Vehicle ("Yacht", 3, 2, new Step[] {new Step(4, 1, false), new Step(4, 1, false)}, true, "One family was coming late. They can help us with 2 ships.", IconManager.yacht);
		Vehicle largeScoutPlane = new Vehicle ("Large Scoutplane", 3, 6, new Step[] {new Step(2, 20, false)}, false, "Flies in a straight line. Uses thermals to see everything below", IconManager.bigPlane1, true, false);
		Vehicle rescueHelicopter = new Vehicle ("Rescue Helicopter", 3, 5, new Step[] {new Step(2, 2, false)}, true, "Can land on a single place and rescue survivors there. Lacks equipment to see trough the fog.", IconManager.heli1);
		Vehicle rescueShip = new Vehicle ("Large Rescue Ship", 3, 3, new Step[] {new Step(5, 2, false)}, true, "Large Ship specialized on rescue operations. Rescues survivors.", IconManager.bigShip2);
		Vehicle rescueBoat = new Vehicle ("Rescue Boat", 5, 7, new Step[] {new Step(1, 1, false), new Step(1, 1, true), new Step(1, 1, false), new Step(1, 1, true), new Step(1, 1, false), new Step(1, 1, true), new Step(1, 1, false)}, true, "Small rescue boat. Has a hard time to see much.", IconManager.smallShipv1);
		Sonar sateliteScan = new Sonar ("Satelite Scan", 3, 3, 0f, "Uses thermal imaging to accuratly find survivors in an area. Green = hot, Red = cold", IconManager.satellite1);
		Sonar sonarV1 = new Sonar ("Sonar Buoy V1", 2, 5, 0.3f, "Uses sonar to listen for survivors. Green = loud, Red = silence", IconManager.buoyv1);
		Sonar sonarV2 = new Sonar ("Sonar Buoy V2", 2, 7, 0.25f, "Uses sonar to listen for survivors. Improved range and quality. Green = loud, Red = silence", IconManager.buoyv2);
		Scenario sea = new Scenario("Sea", size, clusters, amountOfPeople, description,
        		new Asset[]{privateYacht, largeScoutPlane, rescueHelicopter, rescueShip, rescueBoat, sateliteScan, sonarV1, sonarV2});
		return sea;
	}
	
	private static Scenario createOceanScenario()
	{
		int amountOfPeople = 238;
		int clusters = 10;
		int size = 30;
		String description = "A sudden tsunami swept trough the waters. Many fishers, Recreational boats and vessels sank or lost crewman to the sea. regional firedepartments and the coast guard gave us assets for rescue operatios. The weather is clear, the sea is calm now.";
		Vehicle MH65D = new Vehicle ("MH-65D", 5, 6, new Step[] {new Step(2, 7, false)}, true, "Coast Guard helicopter with high tech search equipment.", IconManager.heli2);
		Vehicle HC144 = new Vehicle("HC-144A", 5, 4, new Step[] {new Step(2, 30, false)}, false, "Coast Guard cargo plane.", IconManager.bigPlane3, true, false);
		Vehicle LegendClass = new Vehicle("Legend-class", 3, 5, new Step[] {new Step(5, 5, false)}, true, "Coast Guard ship with high tech search equipment.", IconManager.bigShip2);
		Vehicle USCGCEagle = new Vehicle("USCGC Eagle", 2, 4, new Step[] {new Step(7, 7, false)}, true, "Coast Guard Academy ship. Full of cadets, most of them on their first mission", IconManager.bigShip3);
		Vehicle K1200 = new Vehicle("K-Max K-1200", 3, 8, new Step[] {new Step(3, 3, false)}, true, "Aerial firefighting helicopter.", IconManager.heli3);
		Vehicle rescueShip = new Vehicle ("Large Rescue Ship", 3, 3, new Step[] {new Step(5, 2, false)}, true, "Large Ship specialized on rescue operations. Rescues survivors.", IconManager.bigShip1);
		Sonar sateliteScan = new Sonar ("Satelite Scan", 2, 4, 0f, "Uses thermal imaging to accuratly find survivors in an area. Green = hot, Red = cold", IconManager.satellite1);
		Sonar sonarV3 = new Sonar("Sonar Buoy V3", 3, 10, 0.25f, "High tech sonar, can even hear the fish at the seafloor. Very high range and good testtequality. Green = loud, Red = silence", IconManager.buoyv3);
		Scenario ocean = new Scenario("Ocean", size, clusters, amountOfPeople, description,
        		new Asset[]{MH65D, HC144, LegendClass, USCGCEagle, K1200, rescueShip,sateliteScan, sonarV3});
		return ocean;
	}

	/**
	 * Getter for a deep copy of a scenario from the scenarios list by index.
	 * <p>
	 * this method returns a deep copied scenario from the scenario list
	 * 
	 * @param index of scenario
	 * @returns a deep copy of a scenario by index
	 * @throws throws IndexOutOfBoundsException if scenario is out of bounds
	 */
	public static Scenario getScenario(int i)
	{
		int amount = scenarios.size();
		if(i < 0 || i >= amount)
		{
			throw new IndexOutOfBoundsException("parameter 'i' must not be negative or larger than the size of scenarios!");
		}
		return new Scenario(scenarios.get(i));
	}
	
	/**
	 * Getter for amount of scenarios
	 * @return amount of scenarios
	 */
	public static int getAmountOfScenarios() 
	{
		return scenarios.size();
	}
	
	/**
	 * @param scenario scenario to get index of
	 * @return index of the scenario. If not found, -1
	 */
	public static int getIndexOfScenario(Scenario scenario) 
	{
	    for (int i = 0; i < scenarios.size(); i++) 
	    {
	        if (scenarios.get(i).equals(scenario)) 
	        { 
	            return i;
	        }
	    }
	    return -1; 
	}
}
