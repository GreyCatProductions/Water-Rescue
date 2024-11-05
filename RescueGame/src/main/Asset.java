package main;

import javax.swing.JTextArea;

public class Asset 
{
    public String name;
    public int amount;
    public JTextArea myTextArea;

    public Asset(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }
}