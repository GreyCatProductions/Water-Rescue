package main;
import javax.swing.ImageIcon;

public class Vehicle extends Asset 
{
    public CoordinateStep[] movePattern;
    public Boolean canRescue;
    public Boolean affectedByCoordinates; 

    public Vehicle(String name, int amount, Boolean affectedByCoordinates, CoordinateStep[] movePattern, Boolean canRescue, String description, ImageIcon icon) 
    {
        super(name, amount, description, icon);
        this.affectedByCoordinates = affectedByCoordinates;
        this.movePattern = movePattern;
        this.canRescue = canRescue;
    }
}
