package main;
import javax.swing.*;

import icons.IconManager;
import scenario_creation_package.Scenario;
import ui_package.GameColors;
import ui_package.UiManager;

import java.awt.*;
import java.util.*;

/**
 * 
 */
/**
 * 
 */
public class GameManager extends JFrame 
{
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	public static Scenario chosenScenario;
    
    public JButton[][] gameFields;
    public int usesLeft;
    public volatile Boolean[][] searchedFields; 
    public volatile Boolean[][] foundFields;
    public volatile Boolean[][] rescuedFields;
    public volatile Boolean[][] markedFields; 
    
    public int selectedX = -1;
    public int selectedY = -1;
    
    public Set<Thread> activeThreads = new HashSet<>();
    
    public static GameManager instance;
    
	 /**
	 * Constructs frame if not testing
	 * <p>
	 * If testing is true, the constructor only assigns this class as the static instance. 
	 * If testing is false, creates the JFrame using {@link UiManager} instance,
	 * and in the end sets the frame visible.
	 * @param testing
	 * @see UiManager#createMainMenu()
	 */
    public GameManager(boolean testing)
    {
    	instance = this;
    	if(!testing)
    	{
	    	frame = this;
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setTitle("Water Rescue Operator");
	        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	        frame.setUndecorated(true);
	        frame.getContentPane().setBackground(Color.black);
	        frame.setLayout(new BorderLayout());;
	        frame.setIconImage(IconManager.mainGameLogo.getImage());
	    
	        UiManager.instance.createMainMenu();
	        setVisible(true);
    	}
    }
    
	/**
	 * Initializes the main game. 
	 * <p>
	 * This method clears the existing content of the frame, initializes game variables 
	 * based on the provided scenario size, creates the game canvas using the 
	 * {@link UiManager} instance, and finally updates the frame's display.
	 *
	 * @param scenario The game scenario to be loaded.
	 * @see UiManager#createGameCanvas(Scenario)
	 */
    public void initializeMainGame(Scenario scenario)
    {
        frame.getContentPane().removeAll(); 
        
        initializeMainGameVariables(scenario.size);
        
        UiManager.instance.createGameCanvas(scenario);

        frame.revalidate(); 
        frame.repaint();  
    }
    
    
    /**
     * Initializes the variables for the main game
     * <p>
     * The method sets all game necessary parameters to their default state depending on
     * the size parameter. Also calls {@link LostPeopleManager} to initialize its variables too. 
     * @param size
     * @see LostPeopleManager#initializeVariables(int)
     */
    public void initializeMainGameVariables(int size) 
    {
        gameFields = new JButton[size][size];
        LostPeopleManager.INSTANCE.initializeVariables(size);
        foundFields = new Boolean[size][size];
        rescuedFields = new Boolean[size][size];
        markedFields = new Boolean[size][size];
        searchedFields = new Boolean[size][size];
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                searchedFields[i][j] = false; 
                foundFields[i][j] = false; 
                rescuedFields[i][j] = false; 
                markedFields[i][j] = false; 
            }
        }
        
        usesLeft = 0;
    }

    /**
     * Updates selected coordinates and paints selected field
     * <p>
     * this method resets all fields colors with resetGridColors(). 
     * if the pressed coordinates are the same as the currently selected, 
     * inverts value of markedFields at the coordinates, then calls updateFieldColor.
     * @param x
     * @param y
     * @see GameManager#resetGridColors() 
     * @see GameManager#updateFieldColor(int, int)
     */
    public void selectField(int x, int y) 
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
    		updateFieldColor(x,y);
    	}
    }
    
    /**
     * Sets every fields color to its proper value
     * <p>
     * this method loop trough all coordinates and calls updateFieldColor(x, y) 
     * on those.
     * @see GameManager#updateFieldColor(int, int)
     */
    public void resetGridColors()
    {
    	for(int x = 0; x < chosenScenario.size; x++)
    	{
        	for(int y = 0; y < chosenScenario.size; y++)
        	{
        		updateFieldColor(x, y);
        	}
    	}
    }   
    
    /**
     * Changes color of field to proper color.
     * <p>
     * this method checks the status of bool arrays on given coordinates. 
     * Depending on the status, calls changeFieldColor with a certain color on the coordinates.
     * @param x
     * @param y
     * @see GameManager#updateFieldColor(int, int)
     */
    public void updateFieldColor(int x, int y)
    {
    	if(x == selectedX && y == selectedY)
    	{
    		changeFieldColor(x, y, GameColors.selectedColor);
    	}
    	else if(foundFields[x][y] && !rescuedFields[x][y])
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
    
    /**
     * paints field on given coordinates a certain color.
     * @param x
     * @param y
     * @param color
     */
    public void changeFieldColor(int x, int y, Color color)
    {
        gameFields[x][y].setBackground(color);
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
        new UiManager();
    	new GameManager(false);
    }
}