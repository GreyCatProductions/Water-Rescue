package scenario_creation_package;
import javax.swing.ImageIcon;

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
}
