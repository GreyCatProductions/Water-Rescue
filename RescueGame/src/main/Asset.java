package main;

import javax.swing.ImageIcon;
import javax.swing.JTextArea;

public class Asset 
{
    public String name;
    public int amount;
    public JTextArea myTextArea;
    String description;
    public ImageIcon icon;

    public Asset(String name, int amount, String description, ImageIcon icon) 
    {
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.icon = icon;
    }
}