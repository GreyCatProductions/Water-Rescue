package scenario_creation_package;

import javax.swing.ImageIcon;
import javax.swing.JTextArea;

public abstract class Asset 
{
    public String name;
    public int amount;
    public JTextArea myTextArea;
    public String description;
    public ImageIcon icon;

    public Asset(String name, int amount, String description, ImageIcon icon) 
    {
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.icon = icon;
    }
}