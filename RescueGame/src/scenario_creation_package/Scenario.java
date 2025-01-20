package scenario_creation_package;

import java.util.Objects;

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
	
	public Scenario(Scenario scenario) //Zum erstellen einer deep copy
	{
		this.name = scenario.name;
        this.size = scenario.size;
        this.clusters = scenario.clusters;
        this.survivors = scenario.survivors;
        this.description = scenario.description;
        this.assets = new Asset[scenario.assets.length];
        for (int i = 0; i < scenario.assets.length; i++) 
        {
            this.assets[i] = scenario.assets[i].deepCopy(); 
        }
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Scenario scenario = (Scenario) obj;
	    return Objects.equals(name, scenario.name) && 
	           Objects.equals(description, scenario.description); 
	}

	@Override
	public int hashCode() {
	    return Objects.hash(name, description); 
	}

}
