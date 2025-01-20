package main;

import java.util.Random;

import javax.swing.JOptionPane;

import scenario_creation_package.Scenario;
import ui_package.UiManager;

public class LostPeopleManager 
{
    private int survivorsSaved;
    private int[][] survivors;
    
    public static final LostPeopleManager INSTANCE = new LostPeopleManager();
    
    public void initializeVariables(int scenarioSize)
    {
    	survivors = new int[scenarioSize][scenarioSize];
    	survivorsSaved = 0;
    }
    
    public void placePeople(Scenario scenario) //Platziert anhand der Szenarioparameter zufällig Personen 
    {
        Random rand = new Random();
        int[] clusters = new int[scenario.clusters];

        for (int i = 0; i < scenario.clusters; i++) {
            clusters[i] = 1; 
        }

        int remainingSurvivors = scenario.survivors - scenario.clusters;
        for (int i = 0; i < remainingSurvivors; i++) {
            int randomClusterIndex = rand.nextInt(scenario.clusters);
            clusters[randomClusterIndex]++;
        }
        for (int peopleToPlace : clusters) {
            int x, y;
            do 
            {
                x = rand.nextInt(scenario.size);
                y = rand.nextInt(scenario.size);
            } while (survivors[x][y] != 0); 

            survivors[x][y] = peopleToPlace;
        }
    }
  
    public boolean survivorsPresent(int x, int y)
    {
    	return survivors[x][y] > 0;
    }
    
    public void rescuePeople(int x, int y)
    {
    	if(survivorsPresent(x, y))
    	{
        	GameManager.instance.rescuedFields[x][y] = true;
            JOptionPane.showMessageDialog(GameManager.frame, survivors[x][y] + " people saved.");
            survivorsSaved += survivors[x][y];
            survivors[x][y] = 0;

            if (survivorsSaved == GameManager.chosenScenario.survivors) 
            {
                UiManager.instance.createEndGameDialog(survivorsSaved);
            }
    	}
    }

    public int getAmountOfSurvivorsSaved()
    {
    	return survivorsSaved;
    }
}
