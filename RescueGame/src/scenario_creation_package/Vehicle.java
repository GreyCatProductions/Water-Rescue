package scenario_creation_package;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import main.GameManager;
import main.LostPeopleManager;
import ui_package.UiManager;

public class Vehicle extends Asset 
{
    public CoordinateStep[] movePattern;
    public Boolean canRescue;
    public Boolean affectedByX; 
    public Boolean affectedByY; 

    public Vehicle(String name, int amount, CoordinateStep[] movePattern, Boolean canRescue, String description, ImageIcon icon) 
    {
        this(name, amount, movePattern, canRescue, description, icon, true, true);
    }

    public Vehicle(String name, int amount, CoordinateStep[] movePattern, Boolean canRescue, String description, ImageIcon icon, Boolean affectedByX, Boolean affectedByY) 
    {
        super(name, amount, description, icon);
        this.affectedByX = affectedByX;
        this.affectedByY = affectedByY;
        this.movePattern = movePattern;
        this.canRescue = canRescue;
    }
    
    @Override
    public void use()
    {
    	reduceAmountByOne();
        int[] currentPosition = { affectedByX ? GameManager.instance.selectedX : 0, 
                                  affectedByY ? GameManager.instance.selectedY : 0 };

        JLabel vehicleLabel = new JLabel();
        vehicleLabel.setIcon(icon);

        UiManager.instance.createIcon(vehicleLabel);

        Thread moveThread = new Thread(() -> 
        {
            try {
                GameManager.instance.activeThreads.add(Thread.currentThread());

                for (CoordinateStep step : movePattern) 
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
                                    performVehicleMove(vehicleLabel, x, y);
                                    Thread.sleep(100); 
                                }
                            } else 
                            {
                            	flip = true;
                                for (int y = endY - 1; y >= startY; y--) 
                                {
                                    performVehicleMove(vehicleLabel, x, y);
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
                GameManager.instance.activeThreads.remove(Thread.currentThread());
                if (GameManager.instance.usesLeft <= 0 && GameManager.instance.activeThreads.isEmpty()) 
                {
                    UiManager.instance.endGame(LostPeopleManager.INSTANCE.getAmountOfSurvivorsSaved());
                }
            }
        });

        moveThread.start();
    }
    
    private void performVehicleMove(JLabel vehicleLabel, int x, int y) //Bewegung eines Fahrzeugs
    {
    	int scenarioSize = GameManager.chosenScenario.size;
    	
	    if (x < 0 || x >= scenarioSize || y < 0 || y >= scenarioSize) 
	    {
	        vehicleLabel.setVisible(false);
	        return;
	    }
	
	    vehicleLabel.setVisible(true);
	    	
	    JButton[][] gameFields = GameManager.instance.buttons;
	    
	    Point buttonPosition = gameFields[x][y].getLocation();
	    int x_pos = buttonPosition.x + (gameFields[x][y].getWidth() - vehicleLabel.getPreferredSize().width) / 2;
	    int y_pos = buttonPosition.y + (gameFields[x][y].getHeight() - vehicleLabel.getPreferredSize().height) / 2;
	    vehicleLabel.setBounds(x_pos, y_pos, vehicleLabel.getPreferredSize().width, vehicleLabel.getPreferredSize().height);
	
	    if (LostPeopleManager.INSTANCE.survivorsPresent(x, y))
	    {
	        if (canRescue) 
	        {
	        	LostPeopleManager.INSTANCE.rescuePeople(x, y);
	        } 
	        else 
	        {
	        	GameManager.instance.foundFields[x][y] = true;
	        }
	    } 
	    else 
	    {
	    	GameManager.instance.searchedFields[x][y] = true;
	    }
	    
	    GameManager.instance.updateFieldColor(x, y);
        
	    try 
	    {
	        Thread.sleep(500);
	    } 
	    catch (InterruptedException e) 
	    {
	        e.printStackTrace();
	    }
    }

}
