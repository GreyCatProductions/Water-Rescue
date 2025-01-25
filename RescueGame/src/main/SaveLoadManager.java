package main;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import scenario_creation_package.ScenarioManager;

public class SaveLoadManager 
{
    private static String gameDir = System.getProperty("user.dir");
    private static String filePath = gameDir + File.separator + "save_data.csv";

    private static Map<String, int[]> savedData = new HashMap<>();

    /**
     * Returns highscores of given player name
     * <p>
     * this method loads the game data. Then returns the highscores of the player or if not
     * found an array the length of the scenarios.
     * @param playerToFind
     * @return highscore array of highscores reached by the player.
     * @see SaveLoadManager#loadGameData()
     * @see ScenarioManager#getAmountOfScenarios()
     */
    public static int[] getStats(String playerToFind) 
    {
        savedData = loadGameData();
        return savedData.getOrDefault(playerToFind, new int[ScenarioManager.getAmountOfScenarios()]);
    }

    /**
     * Saves given parameters
     * <p>
     * this method puts the playerName, stats tuple into the savedData. Then saves the data.
     * @param playerName
     * @param stats
     * @see SaveLoadManager#saveGameData()
     * @throws IllegalArgumentException stats has a different length than the amount of scenarios
     * @throws IllegalArgumentException playerName is null
     */
    public static void saveStats(String playerName, int[] stats) 
    {
    	if(stats.length != ScenarioManager.getAmountOfScenarios())
    	{
    		throw new IllegalArgumentException("parameter 'stats' must be the length of all scenarios!");
    	}
    	
    	if(playerName == null)
    	{
    		throw new IllegalArgumentException("parameter 'playername' must not be null!");
    	}
    	
        savedData.put(playerName, stats);
        saveGameData();
    }
    
    /**
     * @param score
     * @return Returns the given score as string
     */
    public static String getUserHighscore(int score)
    {
    	return String.valueOf(score);
    }
    
    private static void saveGameData() 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) 
        {
            writer.write("PlayerName,Stats\n");

            for (Map.Entry<String, int[]> entry : savedData.entrySet()) {
                String playerName = entry.getKey();
                String stats = IntStream.of(entry.getValue())
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(","));
                writer.write(playerName + "," + stats + "\n");
            }

            System.out.println("Game data saved successfully!");
        } catch (IOException e) {
            System.err.println("Error saving game data: " + e.getMessage());
        }
    }

    private static Map<String, int[]> loadGameData() 
    {
        Map<String, int[]> data = new HashMap<>();
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("No save file found. Returning empty data.");
            return data;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Skip the header line
            String line = reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    String playerName = parts[0];
                    int[] stats = parseStats(parts[1]);
                    data.put(playerName, stats);
                }
            }

            System.out.println("Game data loaded successfully!");
        } catch (IOException e) {
            System.err.println("Error loading game data: " + e.getMessage());
        }

        return data;
    }

    private static int[] parseStats(String statsString) 
    {
        String[] statParts = statsString.split(",");
        int[] stats = new int[statParts.length];
        for (int i = 0; i < statParts.length; i++) {
            stats[i] = Integer.parseInt(statParts[i]);
        }
        return stats;
    }
}
