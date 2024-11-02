package main;

public class Asset 
{
	public String name;
	public int xRange;
	public int yRange;
	public int amount;
	public Boolean canRescue;
	
	public Asset(String name, int xRange, int yRange, int amount, Boolean canRescue)
	{
        this.name = name;
        this.xRange = xRange;
        this.yRange = yRange;
        this.amount = amount;
        this.canRescue = canRescue;
	}
}
