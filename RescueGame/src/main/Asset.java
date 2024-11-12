package main;

import javax.swing.JTextArea;

public class Asset 
{
    public String name;
    public int amount;
    public JTextArea myTextArea;
    String description;

    public Asset(String name, int amount, String description) 
    {
        this.name = name;
        this.amount = amount;
        this.description = description;
    }
}