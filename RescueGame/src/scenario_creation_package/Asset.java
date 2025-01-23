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

    public Asset(String name, int amount, String description, ImageIcon icon) 
    {
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.icon = icon;
    }
    
    public String getName()
    {
    	return name;
    }
    
    public String getDescription()
    {
    	return description;
    }
    
    public int getAmount()
    {
    	return amount;
    }
    
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
    
    public void assignTextAreaReference(JTextArea area)
    {
    	myTextArea = area;
    }
    
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
            String pattern = vehicle.movePattern.length == 1 ? "(" + vehicle.movePattern[0].x + " x " + vehicle.movePattern[0].y + ")" : "complex";
            myTextArea.setText(name + textAdd + "\n\nPattern: " + pattern + "\nAmount: " + amount);
        }
	    UiManager.instance.assetPanel.revalidate();
	    UiManager.instance.assetPanel.repaint();
    }
    
    public void tryToUse()
    {
    	if(GameManager.instance.selectedX == -1 || GameManager.instance.selectedY == -1)
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
    }
    
    public void tryToPreview()
    {
    	if(GameManager.instance.selectedX == -1 || GameManager.instance.selectedY == -1)
    	{
    		JOptionPane.showMessageDialog(null, "Select a field before trying to preview!");
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