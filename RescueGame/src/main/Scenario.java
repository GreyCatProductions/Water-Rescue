package main;

public class Scenario 
{
	public String name;
	public int size;
	public int survivors;
	public int clusters;
	public String description;
	public Asset[] assets;
	
	public Scenario(String name, int size, int clusters, int survivors, String description, Asset[] assets)
	{
		this.name = name;
        this.size = size;
        this.clusters = clusters;
        this.survivors = survivors;
        this.description = description;
        this.assets = assets;
	}
}
