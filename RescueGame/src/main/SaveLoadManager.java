package main;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SaveLoadManager 
{
	private static String gameDir = System.getProperty("user.dir");
	private static String filePath = gameDir + File.separator + "save_data.ser";
	
    private static Map<String, int[]> savedData = new HashMap<String, int[]>();

    public static int[] getStats(String playerToFind) //Should be called only in main menu
    {
    	savedData = loadGameData();
    	return savedData.getOrDefault(playerToFind, new int[ScenarioManager.scenarios.size()]);
    }
    
    public static void saveStats(String playerName, int[] stats)
    {
    	savedData.put(playerName, stats);
    	saveGameData();
    }
    
    //SaveSystem
    private static void saveGameData() 
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) 
        {
            oos.writeObject(savedData);
            System.out.println("Game data saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving game data: " + e.getMessage());
        }
    }

    private static Map<String, int[]> loadGameData() 
    {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) 
        {
            return (Map<String, int[]>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) 
        {
            System.err.println("Error loading game data: " + e.getMessage());
            return new HashMap<>();
        }
    }
}
