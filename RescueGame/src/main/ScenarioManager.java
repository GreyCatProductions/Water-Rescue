package main;

import java.util.LinkedList;

public class ScenarioManager 
{
	protected static LinkedList<Scenario> getScenarios()
	{
		return createScenarios();
	}
	
	private static LinkedList<Scenario> createScenarios()
	{
    	LinkedList<Scenario> scenarios = new LinkedList<Scenario>();
        
        scenarios.add(new Scenario("Lake", 10, 4, 30,"A harsh storm summoned fast. The fishermen did not have time to leave it. 4 boats and 30 people are missing."
        		+ " The local fire department gave you all the ressources it has. Its not much but its all they have.",      		
        		new Asset[]{
        		new Vehicle ("Small Airtanker", 2, true, new CoordinateStep[] {new CoordinateStep(2, 3, false)}, false, "Searches a small area and marks found survivors", IconManager.smallAirtanker),
        		
        		new Vehicle ("Local Pilot", 1, false, new CoordinateStep[] {new CoordinateStep(2, 1, false), new CoordinateStep(2, 1, false), new CoordinateStep(1, 2, false), new CoordinateStep(2, 1, false), new CoordinateStep(3, 1, false)
        				}, false, "Hobby pilot offers to search. But he will not change his course.", IconManager.smallAirtanker),
        		
        		new Vehicle ("Large Aitranker", 1, true, new CoordinateStep[] {new CoordinateStep(3, 4, false)}, false, "Searches a big area and marks found survivors", IconManager.smallAirtanker), 
        		
        		new Vehicle ("Dinghie", 5, true, new CoordinateStep[] {new CoordinateStep(1, 1, false)}, true, "Small but fast. Rescues survivors", IconManager.smallAirtanker),
        		new Sonar ("Short Range Sonar Buoy", 2, 5, 0.4f, "Uses sonar to listen for survivors. Green = loud, Red = silence", IconManager.smallAirtanker)
        }));
        
        scenarios.add(new Scenario("Bay", 15, 5, 40,"The calm waters of the bay were quickly turned into a dangerous trap when an unexpected fog rolled in. "
        		+ "5 groups of recreational sailors are missing. The coast guard has mobilized what they can, but time is running out as the visibility decreases.",
        		new Asset[]{
        				new Vehicle ("Oiltanker", 1, false, new CoordinateStep[] {new CoordinateStep(15, 1, false)}, true, "An Oiltanker passes the water. We can ask the crew to choose a certain latitude", IconManager.smallAirtanker), 
               		new Vehicle ("Scoutplane", 3, true, new CoordinateStep[] {new CoordinateStep(2, 3, false)}, false, "Searches a big area and marks found survivors", IconManager.smallAirtanker), 
               		new Vehicle ("Dinghie", 2, true,new CoordinateStep[] {new CoordinateStep(1, 1, false)}, true, "Small but fast. Rescues survivors", IconManager.smallAirtanker),
               		new Vehicle ("Rescue Ship", 3, true,new CoordinateStep[] {new CoordinateStep(3, 3, false)}, true, "Ship specialized on rescue operations. Rescues survivors.", IconManager.smallAirtanker),
               		new Sonar ("Sonar Buoy V1", 3, 5, 0.3f, "Uses sonar to listen for survivors. Green = loud, Red = silence", IconManager.smallAirtanker),
               		new Sonar ("Sonar Buoy V2", 1, 7, 0.25f, "Uses sonar to listen for survivors. Improved range and quality. Green = loud, Red = silence", IconManager.smallAirtanker)
       		}));
        
        scenarios.add(new Scenario("Sea", 25, 5, 140,"An unusually strong current has swept away several ships, leaving their crews stranded in the sea. 140 people are missing. We dont know how many ships.", 
        		new Asset[]{}));
        
        scenarios.add(new Scenario("Shore", 33, 3, 50,"", new Asset[]{}));
        
        scenarios.add(new Scenario("Ocean", 40, 3, 50,"", new Asset[]{
           		new Vehicle ("Rescue Ship", 3, true,new CoordinateStep[] {new CoordinateStep(3, 3, false)}, true, "Ship specialized on rescue operations. Rescues survivors.", IconManager.smallAirtanker),
        		new Sonar ("Sonar Buoy V5", 1, 15, 0.05f, "Uses sonar to listen for survivors. Improved range and quality. Green = loud, Red = silence", IconManager.smallAirtanker)
        }));
        
    	
    	return scenarios;
    }

}
