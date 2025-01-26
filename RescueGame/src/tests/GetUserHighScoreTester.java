package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.SaveLoadManager;
import scenario_creation_package.ScenarioManager;

class GetUserHighScoreTester 
{
	@BeforeEach
	public void prepareScenarioManager()
	{
		ScenarioManager.createScenarios();
	}
	
	/**
	 * Tests a user that has no save file
	 */
	@Test
	public void testUniqueUser()
	{
		String[] complexUniqueNames = new String[] {"wurhwahowrwao", "wohraworhwoahroaw", "howahrowahruwakjy"};
		
		for(int nameCounter = 0; nameCounter < complexUniqueNames.length; nameCounter++)
		{
			int[] scores = SaveLoadManager.getUserHighscores(complexUniqueNames[nameCounter]);
			
			assertEquals(scores.length, ScenarioManager.getAmountOfScenarios());
			
			for(int i = 0; i < ScenarioManager.getAmountOfScenarios(); i++)
			{
				int user_score = scores[i];
				assertEquals(0, user_score, "new user should have all scores on 0!");
			}
		}
		}
	
	/**
	 * Tests Axel Muster. A user that has all scores on max.
	 */
	@Test
	public void testAxelMuster()
	{
		String userName = "Axel Muster";
		
		int[] scores = SaveLoadManager.getUserHighscores(userName);
		
		assertEquals(scores.length, ScenarioManager.getAmountOfScenarios());
		
		for(int i = 0; i < ScenarioManager.getAmountOfScenarios(); i++)
		{
			int max_score = ScenarioManager.getScenario(i).survivors;
			int user_score = scores[i];
			assertEquals(max_score, user_score, "Axel Muster must have max scores!");
		}
	}
	
	/**
	 * Tests Kim Beispiel. A user that has every score on 1.
	 */
	@Test
	public void testKimBeispiel()
	{
		String userName = "Kim Beispiel";
		
		int[] scores = SaveLoadManager.getUserHighscores(userName);
		
		assertEquals(scores.length, ScenarioManager.getAmountOfScenarios());
		
		for(int i = 0; i < ScenarioManager.getAmountOfScenarios(); i++)
		{
			int user_score = scores[i];
			assertEquals(1, user_score, "Kim Beispiel must have every score on 1!");
		}
	}
}
