package scenario_creation_package;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import main.GameManager;
import ui_package.UiManager;

public abstract class Asset 
{
    protected String name;
    protected int amount;
    protected JTextArea myTextArea;
    protected ImageIcon icon;
    protected String description;

    /**
     * Super constructor for asset types
     * @param name name of the asset
     * @param amount amount of available assets of this type
     * @param description description of the asset
     * @param icon of the asset
     * @throws IllegalArgumentException amount must not be negative
     */
    public Asset(String name, int amount, String description, ImageIcon icon) 
    {
    	if(amount < 0)
    	{
    		throw new IllegalArgumentException("parameter 'amount' must not be negative!");
    	}
    	
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.icon = icon;
    }

    /**
     * name getter
     * @return returns the name
     */
    public String getName()
    {
    	return name;
    }
    
    /**
     * description getter
     * @return returns the description
     */
    public String getDescription()
    {
    	return description;
    }
    
    /**
     * amount getter
     * @return returns the amount
     */
    public int getAmount()
    {
    	return amount;
    }
    
    /**
     * icon getter
     * @return the icon
     */
    public ImageIcon getIcon()
    {
    	return icon;
    }
    
    protected void reduceAmountByOne()
    {	
	    GameManager.instance.usesLeft--;
	    amount--;
	    updateTextAreaText();
    }
    
    /**
     * textArea setter
     * @param sets the given JTextArea to myTextArea
     */
    public void setTextAreaReference(JTextArea area)
    {
    	myTextArea = area;
    }
    
    /**
     * Sets myTextArea text depending on current variables. Then calls {@link UiManager} to redraw the assetPanel.
     * @see UiManager#assetPanel
     */
    public void updateTextAreaText()
    {
        if(this instanceof Sonar)
        {
        	Sonar sonar = (Sonar)this;
        	myTextArea.setText(name + "\n\nRadius: " + sonar.radius + "\nAmount: " + amount);
        }
        else if(this instanceof Vehicle)
        {
        	Vehicle vehicle = (Vehicle)this;
        	String textAdd = vehicle.canRescue ? "X" : "";
            String pattern = vehicle.steps.length == 1 ? "(" + vehicle.steps[0].x + " x " + vehicle.steps[0].y + ")" : "complex";
            myTextArea.setText(name + textAdd + "\n\nPattern: " + pattern + "\nAmount: " + amount);
        }
        
	    UiManager.instance.assetPanel.revalidate();
	    UiManager.instance.assetPanel.repaint();
    }
    
    /**
     * safely tries to use asset
     * <p>
     * this method checks if a valid coordinate is selected and asset has uses left.
     * If not, shows a proper dialogue to the user and returns.
     * 
     * If valid, calls reduceAmountByOne() and action() depending on the type of the asset. 
     * 
     * @see Asset#reduceAmountByOne()
     * @see Sonar#action()
     * @see Vehicle#action()
     */
    public void tryToUse()
    {
    	int x = GameManager.instance.selectedX;
    	int y = GameManager.instance.selectedY;
    	int size = GameManager.chosenScenario.size;
    	
    	if(x > size || x < 0 || y > size || y < 0)
    	{
    		JOptionPane.showMessageDialog(GameManager.frame, "Select a field before sending your asset somewhere!");
    		return;
    	}
    	
    	if(amount <= 0)
    	{
	        JOptionPane.showMessageDialog(GameManager.frame, "No more uses left for " + name);
	        return;
    	}
    	
    	reduceAmountByOne();
    	
        if(this instanceof Sonar)
        {
        	Sonar sonar = (Sonar)this;
        	sonar.action();
        }
        else if(this instanceof Vehicle)
        {
        	Vehicle vehicle = (Vehicle)this;
            vehicle.action();
        }
        
    	GameManager.instance.selectedX = -1;
    	GameManager.instance.selectedY = -1;
    	UiManager.instance.setVisualCoordinates("X", "Y");
    }
    
    /**
     * safely tries to preview the assets area of action
     * <p>
     * this method checks if a valid coordiante is selected.
     * if not, shows a dialogue to the user and returns.
     * 
     * If valid, calls showPreview() depending on the type of the asset.
     * 
     * @see Vehicle#showPreview()
     * @see Sonar#showPreview()
     */
    public void tryToPreview()
    {
    	if(GameManager.instance.selectedX == -1 || GameManager.instance.selectedY == -1)
    	{
    		JOptionPane.showMessageDialog(GameManager.frame, "Select a field before trying to preview!");
    		return;
    	}
    	
        if(this instanceof Sonar)
        {
        	Sonar sonar = (Sonar)this;
        	sonar.showPreview();
        }
        else if(this instanceof Vehicle)
        {
        	Vehicle vehicle = (Vehicle)this;
            vehicle.showPreview();
        }
    }

    public abstract Asset deepCopy();
    
    protected abstract void action();
    
    protected abstract void showPreview();
}