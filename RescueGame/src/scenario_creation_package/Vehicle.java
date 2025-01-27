package scenario_creation_package;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import main.GameManager;
import main.LostPeopleManager;
import ui_package.GameColors;
import ui_package.IconManager;
import ui_package.UiManager;

public class Vehicle extends Asset 
{
    public Step[] steps;
    public Boolean canRescue;
    public Boolean affectedByX; 
    public Boolean affectedByY; 
    public int speed; 

    /**
     * @param name name of the vehicle
     * @param amount amount of available vehicles
     * @param speed speed the vehicle moves at. Should be 1 - 10
     * @param steps steps for the vehicle to take
     * @param canRescue vehicle is able to rescue people 
     * @param description description of the vehicle
     * @param icon icon of the vehicle
     */
    public Vehicle(String name, int amount, int speed, Step[] movePattern, Boolean canRescue, String description, ImageIcon icon) 
    {
        this(name, amount, speed, movePattern, canRescue, description, icon, true, true);
    }

    /**
     * @param name name of the vehicle
     * @param amount amount of available vehicles
     * @param speed speed the vehicle moves at. Should be 1 - 10
     * @param steps steps for the vehicle to take
     * @param canRescue vehicle is able to rescue people 
     * @param description description of the vehicle
     * @param icon icon of the vehicle
     * @param affectedByX vehicle is affected by x coordinate
     * @param affectedByY vehicle is affected by y coordinate
     */
    public Vehicle(String name, int amount, int speed, Step[] steps, Boolean canRescue, String description, ImageIcon icon, Boolean affectedByX, Boolean affectedByY) 
    {
        super(name, amount, description, icon);
        this.affectedByX = affectedByX;
        this.affectedByY = affectedByY;
        this.steps = steps;
        this.canRescue = canRescue;
        this.speed = speed;
    }
    
    protected void action() 
    {
        int[] currentPosition = { affectedByX ? GameManager.instance.selectedX : 0, 
                                  affectedByY ? GameManager.instance.selectedY : 0 };

        int timeToWait = (int)(100 / speed * 30);
        
        JLabel vehicleLabel = new JLabel();
        vehicleLabel.setIcon(icon);

        IconManager.INSTANCE.createIcon(vehicleLabel);

        Thread moveThread = new Thread(() -> 
        {
            try {
                GameManager.instance.activeThreads.add(Thread.currentThread());

                for (Step step : steps) 
                {
                    int xToMove = step.x;
                    int yToMove = step.y;

                    int startX = Math.min(currentPosition[0], currentPosition[0] + xToMove);
                    int endX = Math.max(currentPosition[0], currentPosition[0] + xToMove);
                    int startY = Math.min(currentPosition[1], currentPosition[1] + yToMove);
                    int endY = Math.max(currentPosition[1], currentPosition[1] + yToMove);

                    Boolean flip = false;
                    
                    for (int x = startX; x < endX; x++)
                    {
                        if (flip) 
                        {
                            for (int y = startY; y < endY; y++) 
                            {        	
                            	processVehicleMove(vehicleLabel, x, y, step.noUse);
                                Thread.sleep(timeToWait);
                            }
                        } else 
                        {
                            for (int y = endY - 1; y >= startY; y--) 
                            {
                            	processVehicleMove(vehicleLabel, x, y, step.noUse);
                                Thread.sleep(timeToWait);
                            }
                        }
                        flip = !flip;
                    }

                currentPosition[0] += xToMove;
                currentPosition[1] += yToMove;
                }
            } catch (InterruptedException e) 
            {
            } finally 
            {
                IconManager.INSTANCE.removeIcon(vehicleLabel);
                GameManager.instance.activeThreads.remove(Thread.currentThread());
                if (GameManager.instance.usesLeft <= 0 && GameManager.instance.activeThreads.isEmpty())
                {
                    UiManager.instance.createEndGameDialog(LostPeopleManager.INSTANCE.getAmountOfSurvivorsSaved());
                }
            }
        });

        moveThread.start();
    }

    protected void showPreview()
    {
    	int selectedX = GameManager.instance.selectedX;
    	int selectedY = GameManager.instance.selectedY;
    	int scenarioSize = GameManager.chosenScenario.size;
    	
    	if(selectedX == -1 || selectedY == -1)
    		return;
    	
    	GameManager.instance.resetGridColors();

		int curX = affectedByX ? selectedX : 0;
		int curY =affectedByY ? selectedY : 0;

		for(Step step : steps)
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
		                if (x < 0 || x >= scenarioSize  || y < 0 || y >= scenarioSize )
		                    continue;	
		    			if(x < scenarioSize  && y < scenarioSize )
		    			{
		        			GameManager.instance.changeFieldColor(x, y, GameColors.selectedColor);
		    			}
					}
				}
			}
			curX += xToMove;
			curY += yToMove;
    	}
    }
    
    private void processVehicleMove(JLabel vehicleLabel, int x, int y, boolean ignoreAction)
    {
    	int scenarioSize = GameManager.chosenScenario.size;
    	
	    if (x < 0 || x >= scenarioSize || y < 0 || y >= scenarioSize) 
	    {
	        vehicleLabel.setVisible(false);
	        return;
	    }
        performVehicleMove(vehicleLabel, x, y);
        if(!ignoreAction) { assetFieldAction(x, y);}
    }

    private void performVehicleMove(JLabel vehicleLabel, int x, int y)
    {
	    vehicleLabel.setVisible(true);
	    	
	    JButton[][] gameFields = GameManager.instance.gameFields;
	    
	    Point buttonPosition = gameFields[x][y].getLocation();
	    int x_pos = buttonPosition.x + (gameFields[x][y].getWidth() - vehicleLabel.getPreferredSize().width) / 2;
	    int y_pos = buttonPosition.y + (gameFields[x][y].getHeight() - vehicleLabel.getPreferredSize().height) / 2;
	    vehicleLabel.setBounds(x_pos, y_pos, vehicleLabel.getPreferredSize().width, vehicleLabel.getPreferredSize().height);
    }
    
    private void assetFieldAction(int x, int y)
    {
	    if (LostPeopleManager.INSTANCE.survivorsPresent(x, y))
	    {
	        if (canRescue) 
	        {
	        	LostPeopleManager.INSTANCE.rescuePeople(x, y, false);
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
    }
    
    /**
     * this method returns a deep copy of this object
     * @returns deep copy of this vehicle
     * @see Vehicle#Vehicle(String, int, int, Step[], Boolean, String, ImageIcon, Boolean, Boolean)
     */
    public Vehicle deepCopy()
    {
    	return new Vehicle(name, amount, speed, steps, canRescue, description, icon, affectedByX, affectedByY);
    }
}
