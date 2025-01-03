package scenario_creation_package;
import java.awt.Color;
import java.awt.Point;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import main.GameManager;
import main.LostPeopleManager;
import ui_package.UiManager;

public class Sonar extends Asset {
	public int radius;
	public float maxNoise;
    
    public Sonar(String name, int amount, int radius, float maxNoise, String description, ImageIcon icon) 
    {
        super(name, amount, description, icon);
        this.radius = radius;
        this.maxNoise = maxNoise;
    }
    
    @Override
    public void use()
    {
    	reduceAmountByOne();
    	Random random = new Random();
        int fixed_x = GameManager.instance.selectedX;
        int fixed_y = GameManager.instance.selectedY;
        int scenarioSize = GameManager.chosenScenario.size;
        int[][] distanceMatrix = new int[scenarioSize][scenarioSize];
        JButton[][] gameFields = GameManager.instance.buttons;
        
        for (int i = 0; i < scenarioSize; i++) 
        {
            Arrays.fill(distanceMatrix[i], Integer.MAX_VALUE);
        }
        
		JLabel solarIcon = new JLabel();
	    solarIcon.setIcon(icon); 
	    Point buttonPosition = gameFields[fixed_x][fixed_y].getLocation();
	    int x_pos = buttonPosition.x + (gameFields[fixed_x][fixed_y].getWidth() - solarIcon.getPreferredSize().width) / 2;
	    int y_pos = buttonPosition.y + (gameFields[fixed_x][fixed_y].getHeight() - solarIcon.getPreferredSize().height) / 2;
	    solarIcon.setBounds(x_pos, y_pos, solarIcon.getPreferredSize().width, solarIcon.getPreferredSize().height);
	    
        UiManager.instance.createIcon(solarIcon);
        
        Thread sonarThread = new Thread(() -> 
        {
        	try 
        	{
        		Color[][] finalColors = new Color[scenarioSize][scenarioSize];
        		
        		//Initialisierung der Startfelder für Breitensuche
	            Queue<Point> queue = new LinkedList<>();
	            for (int dx = -radius; dx <= radius; dx++) 
	            {
	                for (int dy = -radius; dy <= radius; dy++) 
	                {
	                    int x = fixed_x + dx;
	                    int y = fixed_y + dy;
	
	                    if (x >= 0 && x < scenarioSize && y >= 0 && y < scenarioSize &&
	                        Math.abs(dx) + Math.abs(dy) <= radius && LostPeopleManager.INSTANCE.survivorsPresent(x, y)) 
	                    {                 
	                        queue.add(new Point(x, y));
	                        distanceMatrix[x][y] = 0;
	                        float noisedValue =  radius * (random.nextFloat() * 2 - 1) * maxNoise;
	                        int colorValue = (int)Math.max(0, Math.min(noisedValue * 255 / radius, 255)); 
	                        finalColors[x][y] = new Color(colorValue, 255 - colorValue, 0);
	                    }
	                }
	            }
	            
	            //Wenn nichts gefunden
	            if(queue.isEmpty())
	            {
	                for (int dx = -radius; dx <= radius; dx++) {
	                    for (int dy = -radius; dy <= radius; dy++) {
	                        int x = fixed_x + dx;
	                        int y = fixed_y + dy;
	
	                        if(x >= 0 && x < scenarioSize && y >= 0 && y < scenarioSize &&
	                        Math.abs(x - fixed_x) + Math.abs(y - fixed_y) <= radius)
	                        {
	                        	float noisedValue = radius + radius * (random.nextFloat() * 2 - 1) * maxNoise;
		                        int colorValue = (int)Math.max(0, Math.min(noisedValue * 255 / radius, 255)); 
		                        finalColors[x][y] = new Color(colorValue, 255 - colorValue, 0);
	                        }
	                    }
	                }
	            }
	
	            int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
	
	            //Breitensuche von jedem Startknoten aus 
	            while (!queue.isEmpty()) 
	            {
	                Point current = queue.poll();
	                int currentDistance = distanceMatrix[current.x][current.y];
	
	                for (int[] dir : directions) 
	                {
	                    int nx = current.x + dir[0];
	                    int ny = current.y + dir[1];
	
	                    if (nx >= 0 && nx < scenarioSize && ny >= 0 && ny < scenarioSize &&
	                        Math.abs(nx - fixed_x) + Math.abs(ny - fixed_y) <= radius &&
	                        currentDistance + 1 < distanceMatrix[nx][ny]) 
	                    {
	                        distanceMatrix[nx][ny] = currentDistance + 1;
	                        
	                        queue.add(new Point(nx, ny));
	
	                        float noisedValue = distanceMatrix[nx][ny] + radius * (random.nextFloat() * 2 - 1) * maxNoise;
	                        int colorValue = (int)Math.max(0, Math.min(noisedValue * 255 / radius, 255)); 
	                        finalColors[nx][ny] = new Color(colorValue, 255 - colorValue, 0);
	                    }
	                }
	            }
	            
	            //Färbung
	            for(int cur_radius = 0; cur_radius < radius; cur_radius++)
        		{
		            for (int dx = -cur_radius; dx <= cur_radius; dx++) 
		            {
		                for (int dy = -cur_radius; dy <= cur_radius; dy++) 
		                {
		                	int x = fixed_x + dx;
	                        int y = fixed_y + dy;
	                        
	                        if(x >= 0 && x < scenarioSize && y >= 0 && y < scenarioSize &&
	                        Math.abs(x - fixed_x) + Math.abs(y - fixed_y) <= radius)
	                        {
	                        	GameManager.instance.changeFieldColor(x, y, finalColors[x][y]);
	                        }
		                }
		            }
		            Thread.sleep(250);
        		}
        	}catch (InterruptedException e) 
            {
                e.printStackTrace();
            } 
    	});
        sonarThread.start();
    }
}