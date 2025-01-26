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
    
    /**
     * Initializes variables depending on given size
     * @param scenarioSize size of the scenario
     * @throws IllegalArgumentException - scenarioSize must be positive
     */
    public void initializeVariables(int scenarioSize)
    {
    	if(scenarioSize <= 0)
    	{
    		throw new IllegalArgumentException("parameter 'scenarioSize' must be positive!");
    	}
    	
    	survivors = new int[scenarioSize][scenarioSize];
    	survivorsSaved = 0;
    }
    
    /**
     * Places survivors randomly within the specified scenario.
     * <p>
     * This method distributes the given number of survivors across 
     * the specified number of clusters within the scenario's grid. 
     * Each cluster initially contains one survivor, then remaining 
     * survivors are randomly assigned to each clusters.
     *
     * @param scenario scenario to place people for
     * @throws IllegalArgumentException - scenario must not be null
     */
    public void placePeople(Scenario scenario) 
    {
    	if(scenario == null)
    	{
    		throw new IllegalArgumentException("parameter 'scenario' must not be null");
    	}
    	
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
  
    /**
     * Checks if survivors are present at the given coordinates
     * @param x - coordinate to check
     * @param y - coordinate to check
     * @return if there are survivors at the given coordinates
     * @throws IndexOutOfBoundsException x or y has invalid value
     */
    public boolean survivorsPresent(int x, int y)
    {
    	int size = survivors.length;
    	if(x < 0 || y < 0 || x >= size || y >= size)
    	{
    		throw new IndexOutOfBoundsException(String.format("x = %d, y = %d are not valid coordinates", x, y));
    	}
    	
    	return survivors[x][y] > 0;
    }
    
    /**
     * Rescues people at given coordinates
     * <p>
     * This method first checks if there are survivors present at given coordinates.
     * If there are, sets rescuedFields at given coordinates to true.
     * Then shows a MessageDialog to inform user about the saved people.
     * Then updates the amount of survivors saved and sets the amount of survivors left
     * at the coordinates to 0. 
     * Then checks if the amount of survivors saved has reached the total amount of survivors.
     * If it has, calls {@link UiManager} to create the end game dialog.
     * 
     * @param x - coordinate to check
     * @param y - coordinate to check
     * @param ignoreVisualFeedback - if true, does not draw dialogues
     * @throws IndexOutOfBoundsException x or y has invalid value
     */
    public synchronized void rescuePeople(int x, int y, boolean ignoreVisualFeedback)
    {
    	int size = survivors.length;
    	if(x < 0 || y < 0 || x >= size || y >= size)
    	{
    		throw new IndexOutOfBoundsException(String.format("x = %d, y = %d are not valid coordinates", x, y));
    	}
    	
    	if(survivorsPresent(x, y))
    	{
        	GameManager.instance.rescuedFields[x][y] = true;
        	if(!ignoreVisualFeedback) {JOptionPane.showMessageDialog(GameManager.frame, survivors[x][y] + " people saved.");}

            survivorsSaved += survivors[x][y];
            survivors[x][y] = 0;

            if (survivorsSaved == GameManager.chosenScenario.survivors && !ignoreVisualFeedback) 
            {
                UiManager.instance.createEndGameDialog(survivorsSaved);
            }
    	}
    }

    /**
     * Getter for the amount of saved survivors
     * @return returns the amount of survivors saved
     */
    public int getAmountOfSurvivorsSaved()
    {
    	return survivorsSaved;
    }
}
