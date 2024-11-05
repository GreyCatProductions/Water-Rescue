package main;

public class Vehicle extends Asset {
    public int xRange;
    public int yRange;
    public Boolean canRescue;

    public Vehicle(String name, int amount, int xRange, int yRange, Boolean canRescue) {
        super(name, amount);
        this.xRange = xRange;
        this.yRange = yRange;
        this.canRescue = canRescue;
    }
}
