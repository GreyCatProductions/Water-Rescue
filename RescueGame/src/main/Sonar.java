package main;

public class Sonar extends Asset {
    public int radius;
    public float maxNoise;
    public Sonar(String name, int amount, int radius, float maxNoise) {
        super(name, amount);
        this.radius = radius;
        this.maxNoise = maxNoise;
    }
}