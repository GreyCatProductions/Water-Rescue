package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class SaveSystem 
{
    private String saveLocation;

    public SaveSystem(String fileName) 
    {
        String userHome = System.getProperty("user.home");
        this.saveLocation = Paths.get(userHome, fileName).toString();
    }

    public void save(String data) 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveLocation))) 
        {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String load() 
    {
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(saveLocation))) 
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                data.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
        return data.toString();
    }
}
