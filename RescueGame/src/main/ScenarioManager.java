package main;

import java.util.Arrays;
import java.util.LinkedList;

public class ScenarioManager 
{
    	public static LinkedList<Scenario> scenarios = new LinkedList<Scenario>
    	(Arrays.asList(
    			new Scenario("Lake",
    					10, 4, 30,
    					"A harsh storm summoned fast. The fishermen did not have time to leave it. 4 boats and 30 people are missing. The local fire department gave you all the ressources it has. Its not much but its all they have.",      		
	        		new Asset[]{
	        		new Vehicle ("Small Airtanker", 2, new CoordinateStep[] {new CoordinateStep(2, 3, false)}, false, "Searches a small area and marks found survivors", IconManager.smallAirtanker),
	        		
	        		new Vehicle ("Local Pilot", 1, new CoordinateStep[] {new CoordinateStep(2, 1, false), new CoordinateStep(2, 1, false), new CoordinateStep(1, 2, false), new CoordinateStep(2, 1, false), new CoordinateStep(3, 1, false)
	        				}, false, "Hobby pilot offers to search. But he will not change his course.", IconManager.smallAirtanker, false, false),
	        		
	        		new Vehicle ("Large Aitranker", 1, new CoordinateStep[] {new CoordinateStep(3, 4, false)}, false, "Searches a big area and marks found survivors", IconManager.bigAirtanker), 
	        		
	        		new Vehicle ("Dinghie", 5, new CoordinateStep[] {new CoordinateStep(1, 1, false)}, true, "Small but fast. Rescues survivors", IconManager.dinghie),
	        		new Sonar ("Short Range Sonar Buoy", 2, 5, 0.4f, "Uses sonar to listen for survivors. Green = loud, Red = silence", IconManager.buoyV1)})
        
	        ,new Scenario("Bay",
	        		15, 5, 40,
	        		"The calm waters of the bay were quickly turned into a dangerous trap when an unexpected fog rolled in. 5 groups of recreational sailors are missing. The coast guard has mobilized what they can, but time is running out as the visibility decreases.",
	        		new Asset[]{
	        				new Vehicle ("Oiltanker", 1, new CoordinateStep[] {new CoordinateStep(15, 1, false)}, true, "An Oiltanker passes the water. We can ask the crew to choose a certain latitude", IconManager.smallAirtanker, false, true), 
	               		new Vehicle ("Scoutplane", 3, new CoordinateStep[] {new CoordinateStep(2, 3, false)}, false, "Searches a big area and marks found survivors", IconManager.smallAirtanker), 
	               		new Vehicle ("Dinghie", 2, new CoordinateStep[] {new CoordinateStep(1, 1, false)}, true, "Small but fast. Rescues survivors", IconManager.smallAirtanker),
	               		new Vehicle ("Rescue Ship", 3,new CoordinateStep[] {new CoordinateStep(3, 3, false)}, true, "Ship specialized on rescue operations. Rescues survivors.", IconManager.smallAirtanker),
	               		new Sonar ("Sonar Buoy V1", 3, 5, 0.3f, "Uses sonar to listen for survivors. Green = loud, Red = silence", IconManager.smallAirtanker),
	               		new Sonar ("Sonar Buoy V2", 1, 7, 0.25f, "Uses sonar to listen for survivors. Improved range and quality. Green = loud, Red = silence", IconManager.smallAirtanker)
	       		})
	        
	        ,new Scenario("Sea",
	        		25, 5, 140,
	        		"An unusually strong current has swept away several ships, leaving their crews stranded in the sea. 140 people are missing. We dont know how many ships.", 
	        		new Asset[]{})
	        
	        ,new Scenario("Shore",
	        		33, 3, 50,
	        		"", 
	        		new Asset[]{})
	        
	        ,new Scenario("Ocean",
	        		40, 3, 50,
	        		"",
	        		new Asset[]{
	           		new Vehicle ("Rescue Ship", 3, new CoordinateStep[] {new CoordinateStep(3, 3, false)}, true, "Ship specialized on rescue operations. Rescues survivors.", IconManager.smallAirtanker),
	        		new Sonar ("Sonar Buoy V5", 1, 15, 0.05f, "Uses sonar to listen for survivors. Improved range and quality. Green = loud, Red = silence", IconManager.smallAirtanker)
	        })
		));
}
