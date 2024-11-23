package main;
import javax.swing.ImageIcon;

public class Sonar extends Asset {
    public int radius;
    public float maxNoise;
    
    public Sonar(String name, int amount, int radius, float maxNoise, String description, ImageIcon icon) 
    {
        super(name, amount, description, icon);
        this.radius = radius;
        this.maxNoise = maxNoise;
    }
}