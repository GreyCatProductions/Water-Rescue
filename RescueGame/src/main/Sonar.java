package main;

public class Sonar extends Asset {
    public int radius;
    public float maxNoise;
    
    public Sonar(String name, int amount, int radius, float maxNoise, String description) 
    {
        super(name, amount, description);
        this.radius = radius;
        this.maxNoise = maxNoise;
    }
}