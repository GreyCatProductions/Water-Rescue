package main;
import javax.swing.*;

import icons.IconManager;
import scenario_creation_package.Scenario;
import ui_package.GameColors;
import ui_package.UiManager;

import java.awt.*;
import java.util.*;

public class GameManager extends JFrame 
{
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	public static Scenario chosenScenario;
    
    public JButton[][] gameFields;
    public volatile Boolean[][] changedFields;
    public int usesLeft;
    public volatile Boolean[][] searchedFields; 
    public volatile Boolean[][] foundFields;
    public volatile Boolean[][] rescuedFields;
    public volatile Boolean[][] markedFields; 
    
    public int selectedX = -1;
    public int selectedY = -1;
    
    public Set<Thread> activeThreads = new HashSet<>();
    
    public static GameManager instance;
    
    private GameManager() //Konstruiert das JFrame und ruft Hauptmenü Erstellung auf
    {
    	instance = this;
    	frame = this;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Water Rescue Operator");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setUndecorated(true);
        frame.getContentPane().setBackground(Color.black);
        frame.setLayout(new BorderLayout());;
        frame.setIconImage(IconManager.main_menu_image.getImage());
    
        UiManager.instance.createMainMenu();
        setVisible(true);
    }
    
    public void initializeMainGame(Scenario scenario) //Wird vom StartGame Knopf im Startmenü aufgerufen
    {
        frame.getContentPane().removeAll(); 
        
        initializeMainGameVariables(scenario.size);
        
        UiManager.instance.createGameCanvas(scenario);

        frame.revalidate(); 
        frame.repaint();  
    }
    
    private void initializeMainGameVariables(int size)
    {
        gameFields = new JButton[size][size];
        LostPeopleManager.INSTANCE.initializeVariables(size);
        foundFields = new Boolean[size][size];
        changedFields = new Boolean[size][size];
        rescuedFields = new Boolean[size][size];
        markedFields = new Boolean[size][size];
        searchedFields = new Boolean[size][size];
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
            	changedFields[i][j] = false;
                searchedFields[i][j] = false; 
                foundFields[i][j] = false; 
                rescuedFields[i][j] = false; 
                markedFields[i][j] = false; 
            }
        }
        
        usesLeft = 0;
    }

    public void selectField(int x, int y) //Drück-Funktion, die auf jedem Spielfeld ist
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
    
    public void resetGridColors() //Setzt alle Felder auf ihre richtige Farbe zurück
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
    
    public void updateFieldColor(int x, int y) //Färbt Feld anhand dessen Zustands
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
    
    public void changeFieldColor(int x, int y, Color color)
    {
	        gameFields[x][y].setBackground(color);
	        changedFields[x][y] = true;
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
    	new GameManager();
    }
}