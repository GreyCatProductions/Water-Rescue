package main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GameManager extends JFrame 
{
    public static JFrame frame;
    protected static Scenario chosenScenario;
    
    protected JButton[][] buttons;
    protected int[][] survivors;
    protected Boolean[][] changedFields;
    protected int survivorsSaved = 0;
    protected int usesLeft;
    protected Boolean[][] searchedFields; 
    protected Boolean[][] foundFields;
    protected Boolean[][] rescuedFields;
    protected Boolean[][] markedFields; 
    
    private int selectedX = -1;
    private int selectedY = -1;
    
    Set<Thread> activeThreads = new HashSet<>();

    private ImageIcon gameIcon;
    
    public static GameManager instance;
    
    public GameManager() //Prepares frame and opens main menu
    {
    	instance = this;
    	frame = this;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Water Rescue Operator");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setUndecorated(true);
        frame.getContentPane().setBackground(Color.black);
        frame.setLayout(new BorderLayout());;
    	gameIcon = new ImageIcon(getClass().getResource("WaterRescueOperator.png"));
        frame.setIconImage(gameIcon.getImage());
    
        UiManager.instance.CreateMainMenu();
        setVisible(true);
    }
    
    public void CreateMainGame(Scenario scenario) //Switches to main game with a scenario
    {
        frame.getContentPane().removeAll(); 

        buttons = new JButton[scenario.size][scenario.size];
        survivors = new int[scenario.size][scenario.size];
        foundFields = new Boolean[scenario.size][scenario.size];
        changedFields = new Boolean[scenario.size][scenario.size];
        rescuedFields = new Boolean[scenario.size][scenario.size];
        markedFields = new Boolean[scenario.size][scenario.size];
        searchedFields = new Boolean[scenario.size][scenario.size];
        
        for (int i = 0; i < scenario.size; i++) {
            for (int j = 0; j < scenario.size; j++) {
            	changedFields[i][j] = false;
                searchedFields[i][j] = false; 
                foundFields[i][j] = false; 
                rescuedFields[i][j] = false; 
                markedFields[i][j] = false; 
            }
        }
        
        usesLeft = 0;
        survivorsSaved = 0;
        
        UiManager.instance.createGameCanvas(scenario);

        frame.revalidate(); 
        frame.repaint();  
    }

    protected void UseAsset(Asset assetToUse) 
    {
	    if (assetToUse.amount <= 0) {
	        JOptionPane.showMessageDialog(null, "No more uses left for " + assetToUse.name);
	        return;
	    }
	
	    usesLeft--;
	    assetToUse.amount--;
	    UiManager.instance.SetText(assetToUse);
	
        if (assetToUse instanceof Sonar) 
        {
        	Sonar sonar = (Sonar)assetToUse;
        	sonarAction(sonar);
        }
        else 
        {
            Vehicle vehicle = (Vehicle) assetToUse;
            vehicleAction(vehicle);
        }
    }
    
    private void sonarAction(Sonar sonar)
    {
    	Random random = new Random();
        int fixed_x = selectedX;
        int fixed_y = selectedY;
        int radius = sonar.radius;
        int[][] distanceMatrix = new int[chosenScenario.size][chosenScenario.size];
        
        for (int i = 0; i < chosenScenario.size; i++) 
        {
            Arrays.fill(distanceMatrix[i], Integer.MAX_VALUE);
        }
        
		JLabel solarIcon = new JLabel();
	    solarIcon.setIcon(sonar.icon); 
	    Point buttonPosition = buttons[fixed_x][selectedY].getLocation();
	    int x_pos = buttonPosition.x + (buttons[fixed_x][fixed_y].getWidth() - solarIcon.getPreferredSize().width) / 2;
	    int y_pos = buttonPosition.y + (buttons[fixed_x][fixed_y].getHeight() - solarIcon.getPreferredSize().height) / 2;
	    solarIcon.setBounds(x_pos, y_pos, solarIcon.getPreferredSize().width, solarIcon.getPreferredSize().height);
	    
        UiManager.instance.createIcon(solarIcon);
        
        Thread sonarThread = new Thread(() -> 
        {
        	try 
        	{
        		Color[][] finalColors = new Color[chosenScenario.size][chosenScenario.size];
        		
        		//Initialisierung der Startfelder für Breitensuche
	            Queue<Point> queue = new LinkedList<>();
	            for (int dx = -radius; dx <= radius; dx++) 
	            {
	                for (int dy = -radius; dy <= radius; dy++) 
	                {
	                    int x = fixed_x + dx;
	                    int y = fixed_y + dy;
	
	                    if (x >= 0 && x < chosenScenario.size && y >= 0 && y < chosenScenario.size &&
	                        Math.abs(dx) + Math.abs(dy) <= radius && survivors[x][y] > 0) 
	                    {                 
	                        queue.add(new Point(x, y));
	                        distanceMatrix[x][y] = 0;
	                        float noisedValue =  radius * (random.nextFloat() * 2 - 1) * sonar.maxNoise;
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
	
	                        if(x >= 0 && x < chosenScenario.size && y >= 0 && y < chosenScenario.size &&
	                        Math.abs(x - fixed_x) + Math.abs(y - fixed_y) <= radius)
	                        {
	                        	float noisedValue = radius + radius * (random.nextFloat() * 2 - 1) * sonar.maxNoise;
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
	
	                    if (nx >= 0 && nx < chosenScenario.size && ny >= 0 && ny < chosenScenario.size &&
	                        Math.abs(nx - fixed_x) + Math.abs(ny - fixed_y) <= radius &&
	                        currentDistance + 1 < distanceMatrix[nx][ny]) 
	                    {
	                        distanceMatrix[nx][ny] = currentDistance + 1;
	                        
	                        queue.add(new Point(nx, ny));
	
	                        float noisedValue = distanceMatrix[nx][ny] + radius * (random.nextFloat() * 2 - 1) * sonar.maxNoise;
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
	                        
	                        if(x >= 0 && x < chosenScenario.size && y >= 0 && y < chosenScenario.size &&
	                        Math.abs(x - fixed_x) + Math.abs(y - fixed_y) <= radius)
	                        {
	                        	changeFieldColor(x, y, finalColors[x][y]);
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

    private void vehicleAction(Vehicle vehicle)
    {

        int[] currentPosition = { vehicle.affectedByX ? selectedX : 0, 
                                  vehicle.affectedByY ? selectedY : 0 };

        JLabel vehicleLabel = new JLabel();
        vehicleLabel.setIcon(vehicle.icon);

        UiManager.instance.createIcon(vehicleLabel);

        Thread moveThread = new Thread(() -> 
        {
            try {
                activeThreads.add(Thread.currentThread());

                for (CoordinateStep step : vehicle.movePattern) 
                {
                    int xToMove = step.x;
                    int yToMove = step.y;

                    if (!step.noUse) {
                        int startX = Math.min(currentPosition[0], currentPosition[0] + xToMove);
                        int endX = Math.max(currentPosition[0], currentPosition[0] + xToMove);
                        int startY = Math.min(currentPosition[1], currentPosition[1] + yToMove);
                        int endY = Math.max(currentPosition[1], currentPosition[1] + yToMove);

                        Boolean flip = true;
                        
                        for (int x = startX; x < endX; x++) 
                        {
                            if(flip) 
                            {
                            	flip = false;
                                for (int y = startY; y < endY; y++) 
                                {
                                    performVehicleMove(vehicleLabel, vehicle, x, y);
                                    Thread.sleep(100); 
                                }
                            } else 
                            {
                            	flip = true;
                                for (int y = endY - 1; y >= startY; y--) 
                                {
                                    performVehicleMove(vehicleLabel, vehicle, x, y);
                                    Thread.sleep(100); 

                                }
                            }
                        }
                    }

                    currentPosition[0] += xToMove;
                    currentPosition[1] += yToMove;
                }
            } catch (InterruptedException e) 
            {
                e.printStackTrace();
            } 
            finally 
            {
            	UiManager.instance.removeIcon(vehicleLabel);
                activeThreads.remove(Thread.currentThread());
                if (usesLeft <= 0 && activeThreads.isEmpty()) 
                {
                    endGame();
                }
            }
        });

        moveThread.start();
    }
  
    private void performVehicleMove(JLabel vehicleLabel, Vehicle vehicle, int x, int y)
    {
	    if (x < 0 || x >= chosenScenario.size || y < 0 || y >= chosenScenario.size) 
	    {
	        vehicleLabel.setVisible(false);
	        return;
	    }
	
	    vehicleLabel.setVisible(true);
	
	    Point buttonPosition = buttons[x][y].getLocation();
	    int x_pos = buttonPosition.x + (buttons[x][y].getWidth() - vehicleLabel.getPreferredSize().width) / 2;
	    int y_pos = buttonPosition.y + (buttons[x][y].getHeight() - vehicleLabel.getPreferredSize().height) / 2;
	    vehicleLabel.setBounds(x_pos, y_pos, vehicleLabel.getPreferredSize().width, vehicleLabel.getPreferredSize().height);
	
	    if (survivors[x][y] > 0)
	    {
	        if (vehicle.canRescue) 
	        {
	        	rescuedFields[x][y] = true;
	            JOptionPane.showMessageDialog(frame, survivors[x][y] + " people saved.");
	            survivorsSaved += survivors[x][y];
	            survivors[x][y] = 0;
	
	            if (survivorsSaved == chosenScenario.survivors) 
	            {
	                endGame();
	            }
	        } 
	        else 
	        {
	        	foundFields[x][y] = true;
	        }
	    } 
	    else 
	    {
	    	searchedFields[x][y] = true;
	    }
	    
        updateFieldColor(x, y);
        
	    try 
	    {
	        Thread.sleep(500);
	    } 
	    catch (InterruptedException e) 
	    {
	        e.printStackTrace();
	    }
    }

    protected void placePeople(Scenario scenario) 
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
  
    public void selectButton(int x, int y)
    {
    	resetGridColors();
    	if(selectedX == x && selectedY == y)
    	{
        		markedFields[x][y] = !markedFields[x][y];
        		updateFieldColor(x,y);
    	}
    	else
    	{
        	selectedX = x;
        	selectedY = y;
        	UiManager.instance.setVisualCoordinates(x, y);
    		changeFieldColor(x, y, GameColors.selectedColor);
    	}
    }
    
    protected void previewAssetRange(Asset asset)
    {
    	if(selectedX == -1 || selectedY == -1)
    		return;
    	
    	resetGridColors();

    	if(asset instanceof Vehicle)
    	{
    		Vehicle vehicle = (Vehicle) asset;
    		
			int curX = vehicle.affectedByX ? selectedX : 0;
			int curY = vehicle.affectedByY ? selectedY : 0;

			for(CoordinateStep step : vehicle.movePattern)
			{
				int xToMove = step.x;
				int yToMove = step.y;
				
				if(!step.noUse)
				{
			        int startX = Math.min(curX, curX + xToMove);
			        int endX = Math.max(curX, curX + xToMove);
			        int startY = Math.min(curY, curY + yToMove);
			        int endY = Math.max(curY, curY + yToMove);
			        
			        for (int x = startX; x < endX; x++) 
			        {
			            for (int y = startY; y < endY; y++) 
			            {
			                if (x < 0 || x >= chosenScenario.size || y < 0 || y >= chosenScenario.size)
			                    continue;	
			    			if(x < chosenScenario.size && y < chosenScenario.size)
			    			{
			        			changeFieldColor(x, y, GameColors.selectedColor);
			    			}
						}
					}
				}
				curX += xToMove;
				curY += yToMove;
	    	}
    	}
    	else
    	{
    		Sonar sonar = (Sonar) asset;
            for (int dx = -sonar.radius; dx <= sonar.radius; dx++) {
                for (int dy = -sonar.radius; dy <= sonar.radius; dy++) {
                    int x = selectedX + dx;
                    int y = selectedY + dy;

                    if (x >= 0 && x < chosenScenario.size && y >= 0 && y < chosenScenario.size &&
                        Math.abs(dx) + Math.abs(dy) <= sonar.radius) 
                    {                 
                    	changeFieldColor(x, y, GameColors.selectedColor);
                    }
                }
            }
    	}
    }
    
    private void resetGridColors() 
    {
    	for(int x = 0; x < changedFields.length; x++)
    	{
        	for(int y = 0; y < changedFields.length; y++)
        	{
        		updateFieldColor(x, y);
        		changedFields[x][y] = false;
        	}
    	}
    }   
    
    private void updateFieldColor(int x, int y)
    {
    	if(foundFields[x][y] && !rescuedFields[x][y])
		{
			changeFieldColor(x, y, GameColors.foundColor);
		}
    	else if(markedFields[x][y])
		{
			changeFieldColor(x, y, GameColors.markedColor);
		}
		else if(rescuedFields[x][y])
		{
			changeFieldColor(x, y, GameColors.rescuedColor);
		}
		else if(foundFields[x][y])
		{
			changeFieldColor(x, y, GameColors.foundColor);
		}
		else if(searchedFields[x][y])
		{
			changeFieldColor(x, y, GameColors.searchedColor);
		}
		else
		{
			changeFieldColor(x, y, GameColors.seaColor);
		}
    }
    
    private void changeFieldColor(int x, int y, Color color) //Paints field to given color and marks it as changed
    {
	        buttons[x][y].setBackground(color);
	        changedFields[x][y] = true;
    }
    
    private void endGame() //Enables end screen
    {
    	String title;
    	float ratio = (float) survivorsSaved / chosenScenario.survivors;

    	if (ratio == 1.0f) 
    	    title = "PERFECT JOB";
    	else if (ratio > 0.75f)
    		title = "GOOD JOB";
    	else if (ratio > 0.5f)
    		title = "MEDIOCRE JOB";
    	else if (ratio > 0.25f)
    		title = "BAD JOB";
    	else 
    	    title = "TERRIBLE JOB";

    	
        JDialog dialog = new JDialog(frame, title, true);
        dialog.setLayout(new BorderLayout());

        String message = survivorsSaved + " / " + chosenScenario.survivors + " people saved!\n\n";
        JLabel messageLabel = new JLabel("<html>" + message + "</html>", SwingConstants.CENTER);
        dialog.add(messageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UiManager.instance.CreateLevelSelection();
                dialog.dispose();
            }
        });
        buttonPanel.add(okButton);
        
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setSize(new Dimension(300, 200));
        dialog.setLocationRelativeTo(frame); 
        dialog.setVisible(true); 
    }
       
    public static void main(String[] args)
    {
        Font mainFont = new Font("Arial", Font.BOLD, 12);
        UIManager.put("Label.font", mainFont);
        UIManager.put("Button.font", mainFont);
        UIManager.put("TextField.font", mainFont);
        UIManager.put("TextArea.font", mainFont);
        UIManager.put("TextPane.font", mainFont);
        UIManager.put("MenuItem.font", mainFont);
        new SaveSystem("Water Rescue Operator");
        new UiManager();
    	new GameManager();
    }
}