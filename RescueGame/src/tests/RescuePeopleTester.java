package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.GameManager;
import main.LostPeopleManager;
import scenario_creation_package.Asset;
import scenario_creation_package.Scenario;

class RescuePeopleTester 
{
	GameManager gameManager;
	Scenario scenario;
	@BeforeEach
	public void prepareGame()
	{
		gameManager = new GameManager(true);
		gameManager.initializeMainGameVariables(1);
		scenario = new Scenario("", 1, 1, 10, "", new Asset[] {});
		GameManager.chosenScenario = scenario;
	}
	
	@Test
	public void testInvalidCoordinates()
	{
		int x = -1;
		int y = 3;
		
        assertThrows(IndexOutOfBoundsException.class, () -> 
        {
            LostPeopleManager.INSTANCE.rescuePeople(x, y, true);
        });
	}
	
	@Test 
	public void testValidCoordinatesAndSurvivorsPresent()
	{
		int x = 0;
		int y = 0;
		LostPeopleManager.INSTANCE.placePeople(scenario);
		
        LostPeopleManager.INSTANCE.rescuePeople(x, y, true);
        
        assertTrue(LostPeopleManager.INSTANCE.getAmountOfSurvivorsSaved() == 10, "survivors saved should be 10!");
	}
	
	@Test 
	public void testValidCoordinatesAndNoSurvivorsPresent()
	{
		int x = 0;
		int y = 0;
		
        LostPeopleManager.INSTANCE.rescuePeople(x, y, true);
        
        assertTrue(LostPeopleManager.INSTANCE.getAmountOfSurvivorsSaved() == 0, "survivors saved should be 0!");
	}
}
