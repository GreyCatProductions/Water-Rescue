package main;

public class Vehicle extends Asset {
    public int xRange;
    public int yRange;
    public Boolean canRescue;

    public Vehicle(String name, int amount, int xRange, int yRange, Boolean canRescue, String description) {
        super(name, amount, description);
        this.xRange = xRange;
        this.yRange = yRange;
        this.canRescue = canRescue;
    }
}
